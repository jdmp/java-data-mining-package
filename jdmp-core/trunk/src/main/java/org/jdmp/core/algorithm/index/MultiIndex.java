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
	public DataSet search(String query) throws Exception {
		DataSet ds = DataSetFactory.emptyDataSet();
		try {
			List<Future<DataSet>> futures = new ArrayList<Future<DataSet>>();
			for (Object key : getAlgorithms().keySet()) {
				Algorithm a = getAlgorithms().get(key);
				if (a instanceof Index) {
					futures.add(executors.submit(new SearchFuture((Index) a, query)));
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

		public SearchFuture(Index index, String query) {
			this.index = index;
			this.query = query;
		}

		@Override
		public DataSet call() throws Exception {
			return index.search(query);
		}

	}
}
