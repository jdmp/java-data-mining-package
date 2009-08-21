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

package org.jdmp.core.dataset;

import java.io.File;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdmp.core.sample.FileSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
import org.jdmp.core.util.DefaultObservableMap;
import org.ujmp.core.collections.SoftHashMap;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.interfaces.Refreshable;

public class DirDataSet extends AbstractDataSet implements Refreshable {
	private static final long serialVersionUID = -766353065930158949L;

	private FileFormat fileFormat = null;

	private File dir = null;

	private Object[] parameters = null;

	private List<File> files = null;

	private List<File> dirs = null;

	public DirDataSet(File dir, Object... parameters) {
		this(null, dir, parameters);
	}

	public DirDataSet(FileFormat fileFormat, File dir, Object... parameters) {
		this.fileFormat = fileFormat;
		this.dir = dir;
		this.parameters = parameters;
		this.dirs = new ArrayList<File>();
		this.files = new ArrayList<File>();
		setLabel(dir.getAbsolutePath());
		refresh();
	}

	public void refresh() {
		dirs.clear();
		files.clear();
		File[] fs = dir.listFiles();
		if (fs != null) {
			for (File f : fs) {
				if (f.isDirectory()) {
					dirs.add(f);
				} else {
					files.add(f);
				}
			}
		}
		setSamples(new DefaultObservableMap<Sample>(new FileMap()));
		setDataSets(new DefaultObservableList<DataSet>(new DirectoryList()));
	}

	
	public DataSet clone() {
		throw new RuntimeException("not implemented");
	}

	class DirectoryList extends AbstractList<DataSet> implements Serializable {
		private static final long serialVersionUID = -8528843067468420806L;

		private Map<Integer, DataSet> map = new SoftHashMap<Integer, DataSet>();

		
		public DataSet get(int index) {
			DataSet ds = map.get(index);
			if (ds == null) {
				ds = new DirDataSet(fileFormat, dirs.get(index), parameters);
				map.put(index, ds);
			}
			return ds;
		}

		
		public int size() {
			return dirs.size();
		}

	}

	class FileMap extends AbstractMap<String, Sample> implements Serializable {
		private static final long serialVersionUID = -3756763351749907268L;

		private Map<String, Sample> map = new HashMap<String, Sample>();

		public FileMap() {
			for (int i = 0; i < files.size(); i++) {
				try {
					Sample s = new FileSample(fileFormat, files.get(i), parameters);
					map.put(files.get(i).getAbsolutePath(), s);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		
		public Sample get(Object key) {
			return map.get(key);
		}

		
		public int size() {
			return map.size();
		}

		
		public Set<java.util.Map.Entry<String, Sample>> entrySet() {
			return map.entrySet();
		}

	}

}
