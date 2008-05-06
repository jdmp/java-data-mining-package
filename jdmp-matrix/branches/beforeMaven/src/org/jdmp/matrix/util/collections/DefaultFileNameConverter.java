package org.jdmp.matrix.util.collections;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class DefaultFileNameConverter implements FileNameConverter {

	private String pathPrefix = "";

	private String pathSuffix = "";

	private String filePrefix = "";

	private String fileSuffix = "";

	private int prefixLength = 3;

	public DefaultFileNameConverter(int prefexLength) {
		this.prefixLength = prefexLength;
	}

	public DefaultFileNameConverter() {
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public void setPathPrefix(String pathPrefix) {
		if (pathPrefix == null) {
			this.pathPrefix = "";
		} else {
			this.pathPrefix = pathPrefix;
		}
	}

	public List<File> getAllDirs(File path) {
		List<File> allDirs = null;
		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				String name = pathname.getName();
				if (!name.startsWith(pathPrefix)) {
					return false;
				}
				if (!name.endsWith(pathSuffix)) {
					return false;
				}
				return true;
			}
		};
		allDirs = Arrays.asList(path.listFiles(filter));
		return allDirs;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		if (filePrefix == null) {
			this.filePrefix = "";
		} else {
			this.filePrefix = filePrefix;
		}
	}

	public File getFileNameForKey(Object key) {
		if (key == null) {
			return null;
		}

		String s = "" + key;
		if (s.length() < prefixLength) {
			s = "";
		}
		while (s.length() < prefixLength) {
			s = "0" + s;
		}
		String ppref = s.substring(0, prefixLength);

		String fname = pathPrefix + ppref + pathSuffix + File.separator + filePrefix + key + fileSuffix;
		return new File(fname);
	}

	public String getPathSuffix() {
		return pathSuffix;
	}

	public void setPathSuffix(String pathSuffix) {
		this.pathSuffix = pathSuffix;
	}

	public String getFileSuffix() {
		return fileSuffix;
	}

	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}

	public int getPrefixLength() {
		return prefixLength;
	}

	public void setPrefixLength(int prefixLength) {
		this.prefixLength = prefixLength;
	}

}
