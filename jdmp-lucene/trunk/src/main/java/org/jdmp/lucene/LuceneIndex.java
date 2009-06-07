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
import java.util.HashSet;
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
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdmp.core.algorithm.indexer.AbstractIndexer;
import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.dataset.DataSet;
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

	public LuceneIndex() throws IOException {
		this(null);
	}

	public LuceneIndex(File path) throws IOException {
		if (path == null) {
			path = File.createTempFile("lucene", "");
			path.delete();
			path.mkdir();
			path.deleteOnExit();
		}

		this.path = path;

		directory = FSDirectory.getDirectory(path);

		if (IndexReader.indexExists(directory)) {
			if (IndexWriter.isLocked(directory)) {
				IndexWriter.unlock(directory);
			}
			indexWriter = new IndexWriter(directory, analyzer,
					MaxFieldLength.UNLIMITED);
		} else {
			indexWriter = new IndexWriter(directory, analyzer, true,
					MaxFieldLength.UNLIMITED);
		}
	}

	@Override
	public synchronized void add(Sample sample) throws Exception {
		prepareWriter();
		Document doc = new Document();
		for (Variable v : sample.getVariables()) {
			String value = StringUtil.convert(v.getMatrix());
			String key = v.getLabel();
			doc.add(new Field(key, value, Store.YES, Field.Index.ANALYZED));
			fields.add(key);
		}
		doc.add(new Field("rawdata", SerializationUtil.serialize(sample),
				Store.YES));
		String id = sample.getId();
		if (id == null) {
			id = "" + runningId++;
		}
		indexWriter.updateDocument(new Term("Id", id), doc);
	}

	@Override
	public DataSet search(String query) throws Exception {
		prepareReader();
		String[] fs = new String[fields.size()];
		MultiFieldQueryParser p = new MultiFieldQueryParser(fields.toArray(fs),
				analyzer);
		Query q = null;
		if ("*".equals(query)) {
			q = new WildcardQuery(new Term("*"));
		} else {
			q = p.parse(query);
		}
		TopDocs td = indexSearcher.search(q, 1000);
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
		indexWriter.commit();
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
		indexWriter.commit();
	}

	@Override
	public synchronized void close() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
		indexWriter.close();
	}

	@Override
	public void erase() throws IOException {
		close();
		FileUtil.deleteRecursive(path);
	}

}
