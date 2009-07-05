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
import org.jdmp.core.util.DefaultObservableMap;
import org.jdmp.core.util.ObservableList;
import org.jdmp.core.util.ObservableMap;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.collections.SoftHashMap;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.interfaces.Refreshable;

public class DirDataSet extends AbstractDataSet implements Refreshable {
	private static final long serialVersionUID = -766353065930158949L;

	private transient ObservableList<Sample> sampleList = null;

	private ObservableMap<Variable> variableList = null;

	private transient ObservableList<DataSet> dataSetList = null;

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
		this.variableList = new DefaultObservableMap<Variable>();
		this.dirs = new ArrayList<File>();
		this.files = new ArrayList<File>();
		setLabel(dir.getAbsolutePath());
		refresh();
	}

	public void refresh() {
		sampleList = null;
		dataSetList = null;
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

	public ObservableList<Sample> getSamples() {
		if (sampleList == null) {
			sampleList = new DefaultObservableList<Sample>(new FileList());
		}
		return sampleList;
	}

	public final ObservableMap<Variable> getVariables() {
		return variableList;
	}

	public final ObservableList<DataSet> getDataSets() {
		if (dataSetList == null) {
			dataSetList = new DefaultObservableList<DataSet>(new DirectoryList());
		}
		return dataSetList;
	}

	@Override
	public DataSet clone() {
		throw new RuntimeException("not implemented");
	}

	@Override
	public void setSamples(ObservableList<Sample> samples) {
		this.sampleList = samples;
	}

	public void setVariables(ObservableMap<Variable> variables) {
		this.variableList = variables;
	}

	public void setDataSets(ObservableList<DataSet> dataSets) {
		this.dataSetList = dataSets;
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
