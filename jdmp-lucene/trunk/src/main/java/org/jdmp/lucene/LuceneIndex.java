/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.lucene;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexReader.FieldOption;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdmp.core.algorithm.indexer.AbstractIndexer;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.FileUtil;

public class LuceneIndex extends AbstractIndexer implements Flushable,
		Closeable, Erasable {
	private static final long serialVersionUID = -8483996550983833243L;

	private IndexWriter indexWriter = null;

	private IndexSearcher indexSearcher = null;

	private final Set<String> fields = new HashSet<String>();

	private Directory directory = null;

	private File path = null;

	private long runningId = 0;

	private final Analyzer analyzer = new StandardAnalyzer();

	private boolean readOnly = true;

	public LuceneIndex() throws Exception {
		this(null, false);
	}

	public LuceneIndex(File path) throws Exception {
		this(path, false);
	}

	public LuceneIndex(File path, boolean readOnly) throws Exception {
		this.readOnly = readOnly;
		if (path == null) {
			path = File.createTempFile("lucene", "");
			path.delete();
			path.mkdir();
			path.deleteOnExit();
			readOnly = false;
		}

		fields.add(Sample.ID);

		this.path = path;

		directory = FSDirectory.getDirectory(path);

		if (IndexReader.indexExists(directory)) {
			if (!readOnly) {
				if (IndexWriter.isLocked(directory)) {
					IndexWriter.unlock(directory);
				}
				indexWriter = new IndexWriter(directory, analyzer,
						MaxFieldLength.UNLIMITED);
			}

			prepareReader();
			Collection<?> c = indexSearcher.getIndexReader().getFieldNames(
					FieldOption.ALL);
			for (Object o : c) {
				fields.add((String) o);
			}

		} else if (!readOnly) {
			indexWriter = new IndexWriter(directory, analyzer, true,
					MaxFieldLength.UNLIMITED);
		}
	}

	@Override
	public synchronized void add(Sample sample) throws Exception {
		if (readOnly || sample == null) {
			return;
		}

		prepareWriter();
		Document doc = new Document();

		String id = sample.getId();
		if (id == null) {
			id = "" + runningId++;
		}

		doc.add(new Field(Sample.ID, id, Store.YES, Field.Index.NOT_ANALYZED));

		for (Variable v : sample.getVariables()) {
			String key = v.getLabel();
			if (!Sample.ID.equals(key)) {
				String value = StringUtil.convert(v.getMatrix());
				doc.add(new Field(key, value, Store.YES, Field.Index.ANALYZED));
				fields.add(key);
			}
		}

		doc.add(new Field("rawdata", SerializationUtil.serialize(sample),
				Store.COMPRESS));

		indexWriter.updateDocument(new Term(Sample.ID, id), doc);
	}

	public int getSize() {
		try {
			if (indexWriter != null) {
				return indexWriter.maxDoc();
			} else {
				prepareReader();
				return indexSearcher.maxDoc();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public synchronized Sample get(int index) throws Exception {
		prepareReader();
		if (indexSearcher.getIndexReader().isDeleted(index)) {
			return null;
		} else {
			Document doc = indexSearcher.doc(index);
			Sample s = (Sample) SerializationUtil.deserialize(doc
					.getBinaryValue("rawdata"));
			return s;
		}
	}

	public synchronized DataSet search(List<String> query) throws Exception {
		return search(query, 1000);
	}

	public synchronized DataSet search(List<String> query, int count)
			throws Exception {
		BooleanQuery bq = new BooleanQuery();
		String[] fs = new String[fields.size()];
		MultiFieldQueryParser p = new MultiFieldQueryParser(fields.toArray(fs),
				analyzer);

		for (String s : query) {
			Query q = null;
			if ("*".equals(s)) {
				q = p.parse("*");
			} else {
				q = p.parse(s);
			}
			bq.add(new BooleanClause(q, Occur.SHOULD));
		}

		return search(bq, count);
	}

	public synchronized DataSet search(String query) throws Exception {
		return search(query, 1000);
	}

	public synchronized DataSet search(String query, int count)
			throws Exception {
		String[] fs = new String[fields.size()];
		MultiFieldQueryParser p = new MultiFieldQueryParser(fields.toArray(fs),
				analyzer);
		Query q = null;
		if (query == null || "".equals(query)) {
			q = p.parse("*");
		} else if ("*".equals(query)) {
			q = p.parse("*");
		} else {
			q = p.parse(query);
		}
		return search(q, count);
	}

	public synchronized DataSet search(Query query, int count) throws Exception {
		prepareReader();
		TopDocs td = indexSearcher.search(query, count);
		DataSet result = new DefaultDataSet();
		for (ScoreDoc sd : td.scoreDocs) {
			int id = sd.doc;
			Document doc = indexSearcher.doc(id);
			Sample s = (Sample) SerializationUtil.deserialize(doc
					.getBinaryValue("rawdata"));
			s.setMatrix(Sample.SCORE, MathUtil.getMatrix(sd.score));
			result.getSamples().add(s);
		}
		return result;
	}

	private synchronized void prepareReader() throws CorruptIndexException,
			IOException {
		if (indexWriter != null) {
			indexWriter.commit();
		}
		if (indexSearcher != null
				&& !indexSearcher.getIndexReader().isCurrent()) {
			indexSearcher.close();
			indexSearcher = null;
		}
		if (indexSearcher == null) {
			indexSearcher = new IndexSearcher(directory);
		}
	}

	private synchronized void prepareWriter() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
	}

	@Override
	public synchronized void flush() throws IOException {
		if (indexWriter != null) {
			indexWriter.commit();
		}
	}

	public synchronized void optimize() throws IOException {
		if (indexWriter != null) {
			indexWriter.optimize();
		}
	}

	@Override
	public synchronized void close() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	@Override
	public synchronized void erase() throws IOException {
		if (readOnly) {
			return;
		}
		close();
		FileUtil.deleteRecursive(path);
	}

}
