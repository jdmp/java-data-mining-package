package org.jdmp.gui.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.jdmp.core.CoreObject;
import org.jdmp.core.module.Module;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.algorithm.AlgorithmFrame;
import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.jdmp.gui.dataset.DataSetFrame;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.jdmp.gui.matrix.MatrixFrame;
import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.gui.module.ModuleFrame;
import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.gui.sample.SampleFrame;
import org.jdmp.gui.sample.SampleGUIObject;
import org.jdmp.gui.variable.VariableFrame;
import org.jdmp.gui.variable.VariableGUIObject;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.GUIObject;

public abstract class FrameManager {

	private static final Map<GUIObject, AbstractFrame> frames = new HashMap<GUIObject, AbstractFrame>();

	private static List<JComponent> actions = null;

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

	public static final Collection<AbstractFrame> getFrameList() {
		return frames.values();
	}

	public static JFrame showFrame(MatrixGUIObject m) {
		try {
			AbstractFrame frame = frames.get(m);
			if (frame == null) {
				frame = new MatrixFrame(m);
				frames.put(m, frame);
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
			AbstractFrame frame = frames.get(a);
			if (frame == null) {
				frame = new AlgorithmFrame(a);
				frames.put(a, frame);
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
			AbstractFrame frame = frames.get(v);
			if (frame == null) {
				frame = new VariableFrame(v);
				frames.put(v, frame);
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
			AbstractFrame frame = frames.get(m);
			if (frame == null) {
				frame = new ModuleFrame(m);
				frames.put(m, frame);
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
			AbstractFrame frame = frames.get(d);
			if (frame == null) {
				frame = new DataSetFrame(d);
				frames.put(d, frame);
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
			AbstractFrame frame = frames.get(s);
			if (frame == null) {
				frame = new SampleFrame(s);
				frames.put(s, frame);
			}
			frame.setVisible(true);
			return frame;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void hideFrame(GUIObject m) {
		AbstractFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
		}
	}

	public static void closeFrame(GUIObject m) {
		AbstractFrame frame = frames.get(m);
		if (frame != null) {
			frame.setVisible(false);
			frame.dispose();
			frame = null;
			frames.put(m, null);
		}
	}

	public static void registerFrame(AbstractFrame frame) {
		frames.put(frame.getObject(), frame);
	}

	public static final Collection<JComponent> getActions() {
		actions = new LinkedList<JComponent>();
		for (AbstractFrame frame : frames.values()) {
			actions.add(new JMenuItem(new FrameAction(frame)));
		}
		return actions;
	}
}

class FrameAction extends ObjectAction {
	private static final long serialVersionUID = -3586115420288304054L;

	public FrameAction(AbstractFrame frame) {
		super(null, frame.getObject());
		GUIObject o = frame.getObject();
		putValue(Action.NAME, o.getClass().getSimpleName() + " " + o.getLabel());
		if (frame.isVisible()) {
			putValue(Action.SHORT_DESCRIPTION, "Show " + frame.getObject().getLabel());
		} else {
			putValue(Action.SHORT_DESCRIPTION, "Hide " + frame.getObject().getLabel());
		}
	}

	@Override
	public Object call() {
		return null;
	}

}
