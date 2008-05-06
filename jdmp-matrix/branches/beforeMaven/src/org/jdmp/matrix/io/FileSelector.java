package org.jdmp.matrix.io;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

public abstract class FileSelector {

	public static final File selectFile(String label, String suffix) {
		return selectFile(null, label, suffix);
	}

	public static final File selectFile(String suffix) {
		return selectFile(null, "*." + suffix, suffix);
	}

	public static final File selectFile(Component c, String label, String suffix) {
		JFileChooser chooser = new JFileChooser();
		JDMPFileFilter filter = new JDMPFileFilter(label, suffix);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(c);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getAbsolutePath().toLowerCase().endsWith(suffix))
				file = new File(file.getAbsolutePath() + suffix);
			return file;
		}
		return null;
	}

}
