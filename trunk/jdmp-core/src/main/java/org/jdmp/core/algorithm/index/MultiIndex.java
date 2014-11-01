/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.core.algorithm.index;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.similarity.SimilaritySearcher;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableMap;

public class MultiIndex extends AbstractIndex implements SimilaritySearcher {
	private static final long serialVersionUID = 3828613564416089927L;

	private ExecutorService executors = Executors.newCachedThreadPool();

	public MultiIndex(Index... indices) {
		String label = "";
		for (int i = 0; i < indices.length; i++) {
			getAlgorithmMap().put("Index" + i, (Algorithm) indices[i]);
			label += ((Algorithm) indices[i]).getLabel();
			if (i < indices.length - 1) {
				label += ", ";
			}
		}
		setLabel(label);
	}

	public void add(final Sample sample) throws Exception {
	}

	public int getSize() {
		return 0;
	}

	public ListDataSet search(String query, int start, int count) throws Exception {
		ListDataSet ds = ListDataSet.Factory.emptyDataSet();
		try {
			List<Future<ListDataSet>> futures = new ArrayList<Future<ListDataSet>>();
			for (Object key : getAlgorithmMap().keySet()) {
				Algorithm a = getAlgorithmMap().get(key);
				if (a instanceof Index) {
					futures.add(executors.submit(new SearchFuture((Index) a, query, start, count)));
				}
			}
			for (Future<ListDataSet> f : futures) {
				ds.addAll(f.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	public ListDataSet searchSimilar(Sample sample, int start, int count) throws Exception {
		ListDataSet ds = ListDataSet.Factory.emptyDataSet();
		try {
			List<Future<ListDataSet>> futures = new ArrayList<Future<ListDataSet>>();
			for (Object key : getAlgorithmMap().keySet()) {
				Algorithm a = getAlgorithmMap().get(key);
				if (a instanceof SimilaritySearcher) {
					futures.add(executors.submit(new SearchSimilarFuture((SimilaritySearcher) a,
							sample, start, count)));
				}
			}
			for (Future<ListDataSet> f : futures) {
				ds.addAll(f.get());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ds;
	}

	class SearchFuture implements Callable<ListDataSet> {

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

		public ListDataSet call() throws Exception {
			return index.search(query, start, count);
		}

	}

	class SearchSimilarFuture implements Callable<ListDataSet> {

		private SimilaritySearcher index = null;

		private Sample sample = null;

		private int count = 0;

		private int start = 0;

		public SearchSimilarFuture(SimilaritySearcher index, Sample sample, int start, int count) {
			this.index = index;
			this.sample = sample;
			this.start = start;
			this.count = count;
		}

		public ListDataSet call() throws Exception {
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

		public Sample call() throws Exception {
			return index.getSample(id);
		}
	}

	public Sample getSample(String id) throws Exception {
		List<Future<Sample>> futures = new ArrayList<Future<Sample>>();
		for (Object key : getAlgorithmMap().keySet()) {
			Algorithm a = getAlgorithmMap().get(key);
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

	public final ListDataSet searchSimilar(Sample sample) throws Exception {
		return searchSimilar(sample, 0, 100);
	}

	public final ListDataSet searchSimilar(Sample sample, int count) throws Exception {
		return searchSimilar(sample, 0, count);
	}

	public ObservableMap<Sample> getSampleMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSamples(ObservableMap<Sample> samples) {
		// TODO Auto-generated method stub

	}
}
