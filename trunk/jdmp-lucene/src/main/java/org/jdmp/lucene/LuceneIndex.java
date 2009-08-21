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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractListModel;

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
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.index.AbstractIndex;
import org.jdmp.core.algorithm.index.Index;
import org.jdmp.core.algorithm.index.MultiIndex;
import org.jdmp.core.algorithm.similarity.SimilaritySearcher;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.sample.HasSampleMap;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.Erasable;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.SerializationUtil;
import org.ujmp.core.util.StringUtil;
import org.ujmp.core.util.io.FileUtil;

public class LuceneIndex extends AbstractIndex implements Flushable, Closeable,
		Erasable, HasSampleMap, SimilaritySearcher {
	private static final long serialVersionUID = -8483996550983833243L;

	private IndexWriter indexWriter = null;

	private IndexSearcher indexSearcher = null;

	private ThreadPoolExecutor executor = null;

	private final Set<String> fields = new HashSet<String>();

	private Directory directory = null;

	private File path = null;

	private final Analyzer analyzer = new StandardAnalyzer();

	private boolean readOnly = true;

	private LuceneSampleMap sampleMap = null;

	private static final int MAXWORDLENGTH = 20;

	public LuceneIndex(Index index) throws Exception {
		this(null, false, new Index[] { index });
	}

	public LuceneIndex() throws Exception {
		this(null, false, new Index[] {});
	}

	public LuceneIndex(DataSet dataSet) throws Exception {
		this(null, false, new Index[] {});
		setLabel(dataSet.getLabel());
		add(dataSet);
	}

	public LuceneIndex(Index... indices) throws Exception {
		this(null, false, indices);
	}

	public LuceneIndex(File path, Index... indices) throws Exception {
		this(path, false, indices);
	}

	public LuceneIndex(File path, boolean readOnly, Index... indices)
			throws Exception {
		this.readOnly = readOnly;

		if (indices.length == 1) {
			getAlgorithms().put("Index0", (Algorithm) indices[0]);
			setLabel(((Algorithm) indices[0]).getLabel());
		} else if (indices.length > 1) {
			MultiIndex multiIndex = new MultiIndex(indices);
			getAlgorithms().put("Index0", multiIndex);
			setLabel(multiIndex.getLabel());
		}

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

		setSamples(new LuceneSampleMap(this));

		executor = new ThreadPoolExecutor(0, 1, 1000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	
	public synchronized void add(Sample sample) throws Exception {
		if (readOnly || sample == null) {
			return;
		}

		// score must not be saved
		sample.getVariables().remove("Score");

		prepareWriter();
		Document doc = new Document();

		String id = sample.getId();
		doc.add(new Field(Sample.ID, id, Store.COMPRESS,
				Field.Index.NOT_ANALYZED));

		for (Variable v : sample.getVariables()) {
			String key = v.getLabel();
			if (Sample.ID.equals(key)) {
				// skip
			} else if (Variable.CONTENT.equals(key)) {
				// skip
			} else if (Variable.BYTES.equals(key)) {
				// skip
			} else {
				String value = "";
				for (Matrix m : v.getMatrixList()) {
					for (long[] c : m.availableCoordinates()) {
						value += " " + m.getAsString(c);
					}
				}
				doc.add(new Field(key, value.trim(), Store.COMPRESS,
						Field.Index.ANALYZED));
				fields.add(key);
			}
		}

		doc.add(new Field("RawData", SerializationUtil.serialize(sample),
				Store.COMPRESS));

		indexWriter.updateDocument(new Term(Sample.ID, id), doc);
	}

	public final DataSet searchSimilar(Sample sample) throws Exception {
		return searchSimilar(sample, 0, 100);
	}

	public final DataSet searchSimilar(Sample sample, int count)
			throws Exception {
		return searchSimilar(sample, 0, count);
	}

	public int getSize() {
		try {
			if (indexWriter != null) {
				indexWriter.commit();
				return indexWriter.numDocs();
			} else {
				prepareReader();
				return indexSearcher.getIndexReader().numDeletedDocs();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public synchronized Sample getSampleAt(int index) throws Exception {
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

	public synchronized Sample getSample(String id) throws Exception {
		prepareReader();
		Term term = new Term(Sample.ID, id);
		TermQuery tq = new TermQuery(term);
		TopDocs td = indexSearcher.search(tq, 1);
		if (td.totalHits > 0) {
			Document doc = indexSearcher.doc(td.scoreDocs[0].doc);
			Sample s = (Sample) SerializationUtil.deserialize(doc
					.getBinaryValue("RawData"));

			MoreLikeThis mlt = new MoreLikeThis(indexSearcher.getIndexReader());
			mlt.setFieldNames(new String[] { Variable.LABEL,
					Variable.DESCRIPTION, Variable.TAGS });
			mlt.setMaxWordLen(MAXWORDLENGTH);
			String[] terms = mlt.retrieveInterestingTerms(td.scoreDocs[0].doc);
			for (int i = 0; i < 10 && i < terms.length; i++) {
				s.setObject(Variable.SUGGESTEDTAGS, terms[i]);
			}

			return s;
		} else {
			return null;
		}
	}

	public synchronized DataSet search(String query, int start, int count)
			throws Exception {
		return search(parseQuery(query), start, count);
	}

	public synchronized DataSet search(Query query, int start, int count)
			throws Exception {
		prepareReader();
		System.out.println("searching for: " + query);

		if (executor.getQueue().size() < 100000) {
			executor.submit(new SearchCallable(query));
		}

		MoreLikeThis mlt = new MoreLikeThis(indexSearcher.getIndexReader());
		mlt.setFieldNames(new String[] { Variable.LABEL, Variable.DESCRIPTION,
				Variable.TAGS });
		mlt.setMaxWordLen(MAXWORDLENGTH);
		TopDocs td = indexSearcher.search(query, count);
		DataSet result = new DefaultDataSet();
		result.setObject("Total", td.totalHits);
		for (ScoreDoc sd : td.scoreDocs) {
			int id = sd.doc;
			Document doc = indexSearcher.doc(id);
			Sample s = null;
			s = (Sample) SerializationUtil.deserialize(doc
					.getBinaryValue("RawData"));
			s.setMatrix(Sample.SCORE, MathUtil.getMatrix(sd.score));
			String[] terms = mlt.retrieveInterestingTerms(id);
			for (int i = 0; i < 10 && i < terms.length; i++) {
				s.setObject(Variable.SUGGESTEDTAGS, terms[i]);
			}
			result.getSamples().add(s);
		}
		return result;
	}

	protected synchronized void prepareReader() throws CorruptIndexException,
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

	public Directory getDirectory() {
		return directory;
	}

	private synchronized void prepareWriter() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
	}

	public IndexSearcher getIndexSearcher() throws CorruptIndexException,
			IOException {
		prepareReader();
		return indexSearcher;
	}

	
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

	
	public synchronized void close() throws IOException {
		if (indexSearcher != null) {
			indexSearcher.close();
			indexSearcher = null;
		}
		if (indexWriter != null) {
			indexWriter.close();
		}
	}

	
	public synchronized void erase() throws IOException {
		if (readOnly) {
			return;
		}
		close();
		FileUtil.deleteRecursive(path);
	}

	
	public synchronized DataSet searchSimilar(Sample sample, int start,
			int count) throws Exception {
		prepareReader();
		Term term = new Term(Sample.ID, sample.getId());
		TermQuery tq = new TermQuery(term);
		TopDocs td = indexSearcher.search(tq, count);
		if (td == null || td.totalHits == 0) {
			DataSet ds = DataSetFactory.emptyDataSet();
			return ds;
		}
		MoreLikeThis mlt = new MoreLikeThis(indexSearcher.getIndexReader());
		mlt.setFieldNames(new String[] { Variable.LABEL, Variable.DESCRIPTION,
				Variable.TAGS });
		mlt.setMaxWordLen(MAXWORDLENGTH);
		Query query = mlt.like(td.scoreDocs[0].doc);
		BooleanQuery bq = new BooleanQuery();
		bq.add(query, Occur.MUST);
		bq.add(new TermQuery(new Term("Id", sample.getId())), Occur.MUST_NOT);

		return search(bq, start, count);
	}

	class LuceneSampleMap extends AbstractListModel implements
			ObservableMap<Sample> {
		private static final long serialVersionUID = -7189321183317113764L;

		private LuceneIndex index = null;

		public LuceneSampleMap(LuceneIndex index) {
			this.index = index;
		}

		
		public void add(Sample sample) {
			try {
				index.add(sample);
				fireContentsChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		public void addAll(Collection<Sample> values) {
			throw new RuntimeException("not implemented");
		}

		
		public void clear() {
			throw new RuntimeException("not implemented");
		}

		
		public Sample getElementAt(int i) {
			try {
				return index.getSampleAt(i);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		
		public void fireContentsChanged() {
			fireContentsChanged(this, -1, -1);
		}

		
		public int indexOf(Sample value) {
			throw new RuntimeException("not implemented");
		}

		
		public boolean isEmpty() {
			return getSize() == 0;
		}

		
		public Collection<Sample> toCollection() {
			throw new RuntimeException("not implemented");
		}

		
		public int getSize() {
			try {
				return index.getSize();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

		
		public Iterator<Sample> iterator() {
			throw new RuntimeException("not implemented");
		}

		
		public boolean containsKey(Object key) {
			// TODO Auto-generated method stub
			return false;
		}

		
		public boolean containsValue(Object value) {
			// TODO Auto-generated method stub
			return false;
		}

		
		public Set<java.util.Map.Entry<String, Sample>> entrySet() {
			// TODO Auto-generated method stub
			return null;
		}

		
		public Sample get(Object key) {
			try {
				return index.getSample(StringUtil.convert(key));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		
		public Set<String> keySet() {
			// TODO Auto-generated method stub
			return null;
		}

		
		public Sample put(String key, Sample value) {
			// TODO Auto-generated method stub
			return null;
		}

		
		public void putAll(Map<? extends String, ? extends Sample> m) {
			// TODO Auto-generated method stub

		}

		
		public Sample remove(Object key) {
			// TODO Auto-generated method stub
			return null;
		}

		
		public int size() {
			// TODO Auto-generated method stub
			return 0;
		}

		
		public Collection<Sample> values() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	class SearchCallable implements Callable<Object> {

		private String query = null;

		public SearchCallable(Query query) {
			Set<String> terms = extractTerms(query);
			String search = "";
			int i = 0;
			for (String s : terms) {
				if (i > 10) {
					break;
				}
				search += s;
				if (i < terms.size() - 1) {
					search += " ";
				}
				i++;
			}
			this.query = search;

		}

		public SearchCallable(String query) {
			this.query = query;
		}

		
		public Object call() throws Exception {
			for (Algorithm a : getAlgorithms()) {
				try {
					if (a instanceof Index) {
						System.out.println("searching for " + query + " in "
								+ a);
						DataSet ds = ((Index) a).search(query);
						if (ds != null) {
							for (Sample sample : ds.getSamples()) {
								Sample oldSample = getSample(sample.getId());
								if (oldSample != null) {
									Variable tags = oldSample.getVariables()
											.get("Tags");
									if (tags != null
											&& !tags.getMatrixList().isEmpty()) {
										sample.getVariables().put("Tags", tags);
									}
								}
								add(sample);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}

	}

	
	public synchronized int countResults(String query) throws Exception {
		return countResults(parseQuery(query));
	}

	public Set<String> extractTerms(Query query) {
		Set<String> terms = new HashSet<String>();
		if (query instanceof BooleanQuery) {
			BooleanQuery b = (BooleanQuery) query;
			for (BooleanClause bc : b.getClauses()) {
				Query q = bc.getQuery();
				terms.addAll(extractTerms(q));
			}
		} else if (query instanceof TermQuery) {
			TermQuery tq = (TermQuery) query;
			terms.add(tq.getTerm().text());
		} else if (query instanceof WildcardQuery) {
			WildcardQuery wq = (WildcardQuery) query;
			terms.add(wq.getTerm().text());
		}
		return terms;
	}

	public Query parseQuery(String query) throws ParseException {
		Query q = null;
		String[] fs = new String[fields.size()];
		MultiFieldQueryParser p = new MultiFieldQueryParser(fields.toArray(fs),
				analyzer);
		p.setDefaultOperator(MultiFieldQueryParser.AND_OPERATOR);
		if (query == null || "".equals(query) || "*".equals(query)) {
			BooleanQuery bq = new BooleanQuery();
			for (int i = 0; i <= 9; i++) {
				Term term = new Term(Sample.ID, i + "*");
				WildcardQuery wq = new WildcardQuery(term);
				bq.add(wq, Occur.SHOULD);
			}
			for (char i = 'a'; i <= 'z'; i++) {
				Term term = new Term(Sample.ID, i + "*");
				WildcardQuery wq = new WildcardQuery(term);
				bq.add(wq, Occur.SHOULD);
			}
			for (char i = 'A'; i <= 'Z'; i++) {
				Term term = new Term(Sample.ID, i + "*");
				WildcardQuery wq = new WildcardQuery(term);
				bq.add(wq, Occur.SHOULD);
			}
			q = bq;
		} else {
			for (String field : fields) {
				Pattern pat = Pattern.compile(field + ":",
						Pattern.CASE_INSENSITIVE);
				Matcher mat = pat.matcher(query);
				query = mat.replaceAll(field + ":");
			}
			q = p.parse(query);
		}
		return q;
	}

	public synchronized int countResults(Query query) throws Exception {
		prepareReader();
		System.out.println("searching for: " + query);
		TopDocs td = indexSearcher.search(query, 1);
		return td.totalHits;
	}

	
	public void setSamples(ObservableMap<Sample> samples) {
	}

	
	public ObservableMap<Sample> getSamples() {
		if (sampleMap == null) {
			sampleMap = new LuceneSampleMap(this);
		}
		return sampleMap;
	}

}
