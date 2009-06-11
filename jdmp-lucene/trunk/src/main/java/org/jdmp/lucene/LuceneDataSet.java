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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.ObservableList;

public class LuceneDataSet extends DefaultDataSet {
	private static final long serialVersionUID = -1783524677465942349L;

	private LuceneIndex index = null;

	public LuceneDataSet(DataSet ds) throws Exception {
		this(null, ds);
	}

	public LuceneDataSet() throws Exception {
		this(null, null);
	}

	public LuceneDataSet(File path) throws Exception {
		this(path, null);
	}

	public DataSet search(String query) {
		try {
			DataSet ds = index.search(query);
			ds.setLabel("Results for " + query);
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultDataSet();
		}
	}

	public DataSet search(List<String> query) {
		try {
			DataSet ds = index.search(query);
			ds.setLabel("Results for " + query);
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
			return new DefaultDataSet();
		}
	}

	public LuceneDataSet(File path, DataSet ds) throws Exception {
		index = new LuceneIndex(path);
		setSamples(new LuceneSampleList());
		if (ds != null) {
			setVariables(ds.getVariables());
			new AddSamplesThread(ds).start();
		}
	}

	class AddSamplesThread extends Thread {
		private DataSet ds = null;

		public AddSamplesThread(DataSet ds) {
			this.ds = ds;
			setPriority(Thread.MIN_PRIORITY);
		}

		@Override
		public void run() {
			for (int i = 0; i < ds.getSamples().getSize(); i++) {
				getSamples().add(ds.getSamples().getElementAt(i));
			}
		}
	}

	class LuceneSampleList extends AbstractListModel implements
			ObservableList<Sample> {
		private static final long serialVersionUID = -7189321183317113764L;

		@Override
		public void add(Sample sample) {
			try {
				index.add(sample);
				fireContentsChanged();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void addAll(Collection<Sample> values) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean remove(Sample value) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public void clear() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public Sample getElementAt(int i) {
			try {
				return index.get(i);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void fireContentsChanged() {
			fireContentsChanged(this, -1, -1);
		}

		@Override
		public int indexOf(Sample value) {
			throw new RuntimeException("not implemented");
		}

		@Override
		public boolean isEmpty() {
			return getSize() == 0;
		}

		@Override
		public Collection<Sample> toCollection() {
			throw new RuntimeException("not implemented");
		}

		@Override
		public int getSize() {
			try {
				return index.getSize();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}

		@Override
		public Iterator<Sample> iterator() {
			throw new RuntimeException("not implemented");
		}

	}

	public synchronized void flush() {
		try {
			index.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void optimize() {
		try {
			index.optimize();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
