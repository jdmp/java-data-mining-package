package org.jdmp.matrix.implementations.io;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jdmp.matrix.stubs.AbstractDenseStringMatrix2D;

public class FileListMatrix extends AbstractDenseStringMatrix2D {
	private static final long serialVersionUID = -2627484975560893624L;

	private File path = null;

	private List<File> files = null;

	public FileListMatrix(String path) {
		this(new File(path));
	}

	public FileListMatrix(File path) {
		this.path = path;
		this.files = Arrays.asList(path.listFiles());
		Collections.sort(files);
		setMatrixAnnotation(path);
	}

	public long[] getSize() {
		return new long[] { files.size(), 2 };
	}

	public String getString(long... coordinates) {
		switch ((int) coordinates[COLUMN]) {
		case 0:
			File f = files.get((int) coordinates[ROW]);
			return f.getName();
		case 1:
			return "" + files.get((int) coordinates[ROW]).length();
		}
		return null;
	}

	public void setString(String value, long... coordinates) {
		if (coordinates[COLUMN] == 0 && coordinates[ROW] < files.size()) {
			File source = files.get((int) coordinates[ROW]);
			File target = new File(source.getParent() + File.separator + value);
			source.renameTo(target);
			files.set((int) coordinates[ROW], target);
		}
	}

}
