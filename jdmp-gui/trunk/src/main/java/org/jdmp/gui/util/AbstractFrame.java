package org.jdmp.gui.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.module.Module;
import org.jdmp.gui.algorithm.AlgorithmPanel;
import org.jdmp.gui.dataset.DataSetPanel;
import org.jdmp.gui.io.ExportJPEG;
import org.jdmp.gui.io.ExportPDF;
import org.jdmp.gui.io.ExportPNG;
import org.jdmp.gui.matrix.MatrixPanel;
import org.jdmp.gui.module.ModulePanel;
import org.jdmp.gui.sample.SampleGUIObject;
import org.jdmp.gui.sample.SamplePanel;
import org.jdmp.gui.variable.VariableGUIObject;
import org.jdmp.gui.variable.VariablePanel;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class AbstractFrame extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = -4656308453503586700L;

	private static final Logger logger = Logger.getLogger(AbstractFrame.class.getName());

	private static Image image = Toolkit.getDefaultToolkit().getImage("jdmp16.png");

	GUIObject object = null;

	StatusBar statusBar = null;

	private static int frameCount = 0;

	public AbstractFrame(GUIObject o) throws MatrixException {
		UIDefaults.setDefaults();
		FrameManager.registerFrame(this);
		this.object = o;
		String label = o.getLabel() == null ? "no label" : o.getLabel();
		if (o instanceof MatrixGUIObject) {
			MatrixGUIObject mgui = (MatrixGUIObject) o;
			Matrix m = mgui.getMatrix();
			String size = Coordinates.toString(m.getSize()).replaceAll(",", "x");
			setTitle("[" + size + "] " + m.getClass().getSimpleName() + " [" + label + "]");
		} else {
			setTitle(o.getClass().getSimpleName() + " [" + label + "]");
		}

		setIconImage(image);
		o.addPropertyChangeListener(this);

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if (d.getHeight() < 1024) {
			setExtendedState(MAXIMIZED_BOTH);
		} else {
			setPreferredSize(new Dimension(1280, 800));
			setSize(new Dimension(1280, 800));
		}

		statusBar = new StatusBar(object);
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		JComponent component = null;
		if (o instanceof Algorithm) {
			component = new AlgorithmPanel((Algorithm) o);
		} else if (o instanceof DataSet) {
			component = new DataSetPanel((DataSet) o);
		} else if (o instanceof MatrixGUIObject) {
			component = new MatrixPanel((MatrixGUIObject) o);
		} else if (o instanceof Module) {
			component = new ModulePanel((Module) o);
		} else if (o instanceof SampleGUIObject) {
			component = new SamplePanel((SampleGUIObject) o);
		} else if (o instanceof VariableGUIObject) {
			component = new VariablePanel((VariableGUIObject) o);
		} else {
			logger.log(Level.WARNING, "unknown object type: " + o);
		}

		getContentPane().add(component, BorderLayout.CENTER);

		DefaultToolbar toolbar = new DefaultToolbar(component, o);
		getContentPane().add(toolbar, BorderLayout.NORTH);

		setJMenuBar(new DefaultMenuBar(component, o));

	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("Label".equals(evt.getPropertyName())) {
			setTitle(object.getClass().getSimpleName() + " [" + object.getLabel() + "]");
		}
	}

	public void setVisible(boolean state) {
		if (state == true && isVisible()) {
			return;
		}
		if (state == false && !isVisible()) {
			return;
		}

		super.setVisible(state);
		if (state) {
			frameCount++;
			statusBar.start();
		} else {
			frameCount--;
			statusBar.stop();
		}

		if (frameCount == 0) {
			Component parentComponent = null;
			String message = "Do you want to exit the program, close the window or restore it?";
			String title = "Last Window Closed";
			int messageType = JOptionPane.QUESTION_MESSAGE;
			Icon icon = null;
			int initialValue = 0;
			String[] options = new String[] { "Exit", "Close", "Restore" };
			int ret = JOptionPane.showOptionDialog(parentComponent, message, title, 0, messageType,
					icon, options, initialValue);

			if (ret == 0) {
				System.exit(0);
			} else if (ret == 2) {
				setVisible(true);
			}
		}
	}

	public final GUIObject getObject() {
		return object;
	}

	public final void exportToPDF(File file) {
		ExportPDF.save(file, this);
	}

	public final void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public final void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

}
