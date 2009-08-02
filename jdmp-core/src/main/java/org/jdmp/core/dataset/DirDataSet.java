package org.jdmp.core.dataset;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdmp.core.sample.FileSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.DefaultObservableList;
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
		setSamples(new DefaultObservableList<Sample>(new FileList()));
		setDataSets(new DefaultObservableList<DataSet>(new DirectoryList()));
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
	}

	@Override
	public DataSet clone() {
		throw new RuntimeException("not implemented");
	}

	class DirectoryList extends AbstractList<DataSet> implements Serializable {
		private static final long serialVersionUID = -8528843067468420806L;

		private Map<Integer, DataSet> map = new SoftHashMap<Integer, DataSet>();

		@Override
		public DataSet get(int index) {
			DataSet ds = map.get(index);
			if (ds == null) {
				ds = new DirDataSet(fileFormat, dirs.get(index), parameters);
				map.put(index, ds);
			}
			return ds;
		}

		@Override
		public int size() {
			return dirs.size();
		}

	}

	class FileList extends AbstractList<Sample> implements Serializable {
		private static final long serialVersionUID = -3756763351749907268L;

		private Map<Integer, Sample> map = new SoftHashMap<Integer, Sample>();

		@Override
		public Sample get(int index) {
			Sample s = map.get(index);
			if (s == null) {
				try {
					s = new FileSample(fileFormat, files.get(index), parameters);
					map.put(index, s);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}
			return s;
		}

		@Override
		public int size() {
			return files.size();
		}

	}

}
