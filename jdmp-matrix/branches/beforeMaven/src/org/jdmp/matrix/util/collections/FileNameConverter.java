package org.jdmp.matrix.util.collections;

import java.io.File;
import java.util.List;

public interface FileNameConverter {

	public File getFileNameForKey(Object key);

	public List<File> getAllDirs(File path);

}
