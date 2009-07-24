package org.jdmp.core.algorithm.indexer;

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

public class MultiIndex extends AbstractIndexer {
	private static final long serialVersionUID = 3828613564416089927L;

	private ExecutorService executors = Executors.newFixedThreadPool(4);

	public MultiIndex(Indexer... indices) {
		for (int i = 0; i < indices.length; i++) {
			getAlgorithms().put("Index" + i, (Algorithm) indices[i]);
		}
	}

	@Override
	public void add(final Sample sample) throws Exception {
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public DataSet search(String query) throws Exception {
		DataSet ds = DataSetFactory.emptyDataSet();
		try {
			List<Future<DataSet>> futures = new ArrayList<Future<DataSet>>();
			for (Object key : getAlgorithms().keySet()) {
				Algorithm a = getAlgorithms().get(key);
				if (a instanceof Indexer) {
					futures.add(executors.submit(new SearchFuture((Indexer) a, query)));
				}
			}
			for (Future<DataSet> f : futures) {
				ds.getSamples().addAll(f.get().getSamples().toCollection());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		getDataSets().add(ds);
		return ds;
	}

	class SearchFuture implements Callable<DataSet> {

		private Indexer index = null;

		private String query = null;

		public SearchFuture(Indexer index, String query) {
			this.index = index;
			this.query = query;
		}

		@Override
		public DataSet call() throws Exception {
			return index.search(query);
		}

	}
}
