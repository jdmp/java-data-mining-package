package org.jdmp.gui.util;

import javax.swing.JFrame;

import org.jdmp.core.CoreObject;
import org.jdmp.core.module.Module;
import org.jdmp.gui.algorithm.AlgorithmFrame;
import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.jdmp.gui.dataset.DataSetFrame;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.jdmp.gui.module.ModuleFrame;
import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.gui.sample.SampleFrame;
import org.jdmp.gui.sample.SampleGUIObject;
import org.jdmp.gui.variable.VariableFrame;
import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.gui.matrix.MatrixFrame;
import org.ujmp.gui.matrix.MatrixGUIObject;
import org.ujmp.gui.util.AbstractFrame;
import org.ujmp.gui.util.FrameManager;

public abstract class JDMPFrameManager {

	public static JFrame showFrame(GUIObject o) {
		if (o instanceof MatrixGUIObject) {
			return showFrame((MatrixGUIObject) o);
		} else if (o instanceof AlgorithmGUIObject) {
			return showFrame((AlgorithmGUIObject) o);
		} else if (o instanceof VariableGUIObject) {
			return showFrame((VariableGUIObject) o);
		} else if (o instanceof DataSetGUIObject) {
			return showFrame((DataSetGUIObject) o);
		} else if (o instanceof SampleGUIObject) {
			return showFrame((SampleGUIObject) o);
		} else if (o instanceof Module) {
			return showFrame((Module) o);
		} else {
			throw new MatrixException("cannot show frame for object: " + o);
		}
	}

	public static JFrame showFrame(Object o) {
		if (o instanceof GUIObject) {
			return showFrame((GUIObject) o);
		} else if (o instanceof CoreObject) {
			return showFrame(((CoreObject) o).getGUIObject());
		} else {
			throw new MatrixException("wrong object type: " + o);
		}
	}

	public static JFrame showFrame(MatrixGUIObject m) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(m);
			if (frame == null) {
				frame = new MatrixFrame(m);
				FrameManager.getFrames().put(m, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFrame showFrame(AlgorithmGUIObject a) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(a);
			if (frame == null) {
				frame = new AlgorithmFrame(a);
				FrameManager.getFrames().put(a, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFrame showFrame(VariableGUIObject v) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(v);
			if (frame == null) {
				frame = new VariableFrame(v);
				FrameManager.getFrames().put(v, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFrame showFrame(ModuleGUIObject m) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(m);
			if (frame == null) {
				frame = new ModuleFrame(m);
				FrameManager.getFrames().put(m, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFrame showFrame(DataSetGUIObject d) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(d);
			if (frame == null) {
				frame = new DataSetFrame(d);
				FrameManager.getFrames().put(d, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static JFrame showFrame(SampleGUIObject s) {
		try {
			AbstractFrame frame = FrameManager.getFrames().get(s);
			if (frame == null) {
				frame = new SampleFrame(s);
				FrameManager.getFrames().put(s, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
