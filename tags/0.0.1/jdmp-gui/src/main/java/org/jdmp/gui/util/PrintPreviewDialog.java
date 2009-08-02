package org.jdmp.gui.util;

import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JComponent;
import javax.swing.JDialog;

public class PrintPreviewDialog extends JDialog {
	private static final long serialVersionUID = -6511289081075236107L;

	public PrintPreviewDialog(JComponent component) {
		this(null, component);
	}

	public PrintPreviewDialog(Frame frame, JComponent component) {
		super(frame, "Print Preview");
		setPreferredSize(new Dimension(1000, 800));
		setSize(new Dimension(1000, 800));
		setModal(true);
		PrintPreviewPanel pp = new PrintPreviewPanel(component);
		setContentPane(pp);
	}
}
