package org.jdmp.core.algorithm.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.sample.Sample;

public class MultiIndex extends AbstractIndex {
	private static final long serialVersionUID = 3828613564416089927L;

	private ExecutorService executors = Executors.newCachedThreadPool();

	public MultiIndex(Index... indices) {
		String label = "";
		for (int i = 0; i < indices.length; i++) {
			getAlgorithms().put("Index" + i, (Algorithm) indices[i]);
			label += ((Algorithm) indices[i]).getLabel();
			if (i < indices.length - 1) {
				label += ", ";
			}
		}
		setLabel(label);
	}

	@Override
	public void add(final Sample sample) throws Exception {
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public DataSet search(String query, int start, int count) throws Exception {
		DataSet ds = DataSetFactory.emptyDataSet();
		try {
			List<Future<DataSet>> futures = new ArrayList<Future<DataSet>>();
			for (Object key : getAlgorithms().keySet()) {
				Algorithm a = getAlgorithms().get(key);
				if (a instanceof Index) {
					futures.add(executors.submit(new SearchFuture((Index) a, query, start, count)));
				}
			}
			for (Future<DataSet> f : futures) {
				ds.getSamples().addAll(f.get().getSamples().toCollection());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	@Override
	public DataSet searchSimilar(Sample sample, int start, int count) throws Exception {
		DataSet ds = DataSetFactory.emptyDataSet();
		try {
			List<Future<DataSet>> futures = new ArrayList<Future<DataSet>>();
			for (Object key : getAlgorithms().keySet()) {
				Algorithm a = getAlgorithms().get(key);
				if (a instanceof Index) {
					futures.add(executors.submit(new SearchSimilarFuture((Index) a, sample, start,
							count)));
				}
			}
			for (Future<DataSet> f : futures) {
				ds.getSamples().addAll(f.get().getSamples().toCollection());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	class SearchFuture implements Callable<DataSet> {

		private Index index = null;

		private String query = null;

		private int count = 0;

		private int start = 0;

		public SearchFuture(Index index, String query, int start, int count) {
			this.index = index;
			this.query = query;
			this.start = start;
			this.count = count;
		}

		@Override
		public DataSet call() throws Exception {
			return index.search(query, start, count);
		}

	}

	class SearchSimilarFuture implements Callable<DataSet> {

		private Index index = null;

		private Sample sample = null;

		private int count = 0;

		private int start = 0;

		public SearchSimilarFuture(Index index, Sample sample, int start, int count) {
			this.index = index;
			this.sample = sample;
			this.start = start;
			this.count = count;
		}

		@Override
		public DataSet call() throws Exception {
			return index.searchSimilar(sample, start, count);
		}

	}

	class GetFuture implements Callable<Sample> {

		private Index index = null;

		private String id = null;

		public GetFuture(Index index, String id) {
			this.index = index;
			this.id = id;
		}

		@Override
		public Sample call() throws Exception {
			return index.getSample(id);
		}
	}

	@Override
	public Sample getSample(String id) throws Exception {
		List<Future<Sample>> futures = new ArrayList<Future<Sample>>();
		for (Object key : getAlgorithms().keySet()) {
			Algorithm a = getAlgorithms().get(key);
			if (a instanceof Index) {
				futures.add(executors.submit(new GetFuture((Index) a, id)));
			}
		}
		Sample sample = null;
		for (Future<Sample> f : futures) {
			Sample s = f.get();
			if (s != null) {
				sample = s;
			}
		}
		return sample;
	}
}
