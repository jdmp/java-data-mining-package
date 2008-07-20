package org.jdmp.gui.actions;

import java.io.File;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.util.io.UJMPFileFilter;

public class ExportAction extends ObjectAction {
	private static final long serialVersionUID = -3132833245167910507L;

	public ExportAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Export...");
		putValue(Action.SHORT_DESCRIPTION, "Export to file");
	}

	public Object call() {
		File file = null;
		JFileChooser chooser = new JFileChooser();

		for (FileFilter f : UJMPFileFilter.getChoosableFileFilters(getObject())) {
			chooser.addChoosableFileFilter(f);
		}
		for (FileFilter f : UJMPFileFilter.getChoosableFileFilters(getComponent())) {
			chooser.addChoosableFileFilter(f);
		}

		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setDialogTitle("Export");

		int returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = chooser.getSelectedFile();

			FileFilter filter = chooser.getFileFilter();

			String suffix = ((UJMPFileFilter) filter).getSuffix();
			if (!file.getAbsolutePath().toLowerCase().endsWith(suffix)) {
				file = new File(file.getAbsolutePath() + suffix);
			}
		}

		if (file == null)
			return null;

		if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?",
					"Warning", JOptionPane.YES_NO_OPTION);
			if (result != JOptionPane.YES_OPTION)
				return null;
		}

		return null;
	}
}
