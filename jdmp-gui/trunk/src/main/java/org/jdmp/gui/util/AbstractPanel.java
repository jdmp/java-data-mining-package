package org.jdmp.gui.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.ToolTipManager;

import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.matrix.interfaces.HasToolTip;
import org.jdmp.matrix.io.ExportJPEG;
import org.jdmp.matrix.io.ExportPDF;
import org.jdmp.matrix.io.ExportPNG;

public abstract class AbstractPanel extends JPanel implements HasToolTip {
	private static final long serialVersionUID = 4748216534779867441L;

	AbstractGUIObject object = null;

	public AbstractPanel(AbstractGUIObject o) {
		this.object = o;
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(800, 600));
		setSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		ToolTipManager.sharedInstance().registerComponent(this);
	}

	@Override
	protected void finalize() throws Throwable {
		ToolTipManager.sharedInstance().unregisterComponent(this);
		super.finalize();
	}

	public final void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

	public final void exportToPDF(File file) {
		ExportPDF.save(file, this);
	}

	public final void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public String getToolTipText() {
		return object.getToolTipText();
	}

}
