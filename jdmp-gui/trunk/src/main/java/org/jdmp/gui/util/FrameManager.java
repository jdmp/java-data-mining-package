package org.jdmp.gui.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.core.module.AbstractModule;
import org.jdmp.core.module.Module;
import org.jdmp.core.util.CoreObject;
import org.jdmp.gui.actions.ObjectAction;
import org.jdmp.gui.algorithm.AlgorithmFrame;
import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.jdmp.gui.dataset.DataSetFrame;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.jdmp.gui.matrix.MatrixFrame;
import org.jdmp.gui.module.ModuleFrame;
import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.gui.sample.SampleFrame;
import org.jdmp.gui.sample.SampleGUIObject;
import org.jdmp.gui.variable.VariableFrame;
import org.jdmp.gui.variable.VariableGUIObject;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.interfaces.GUIObject;

public abstract class FrameManager {

	private static final Map<GUIObject, AbstractFrame> frames = new HashMap<GUIObject, AbstractFrame>();

	private static List<JComponent> actions = null;

	public static void showFrame(GUIObject o) {
		if (o instanceof MatrixGUIObject) {
			showFrame((MatrixGUIObject) o);
		} else if (o instanceof AlgorithmGUIObject) {
			showFrame((AlgorithmGUIObject) o);
		} else if (o instanceof VariableGUIObject) {
			showFrame((VariableGUIObject) o);
		} else if (o instanceof DataSetGUIObject) {
			showFrame((DataSetGUIObject) o);
		} else if (o instanceof SampleGUIObject) {
			showFrame((SampleGUIObject) o);
		} else if (o instanceof Module) {
			showFrame((Module) o);
		} else {
			throw new MatrixException("cannot show frame for object: " + o);
		}
	}

	public static void showFrame(Object o) {
		if (o instanceof GUIObject) {
			showFrame((GUIObject) o);
		} else if (o instanceof CoreObject) {
			showFrame(((CoreObject) o).getGUIObject());
		} else {
			throw new MatrixException("wrong object type: " + o);
		}
	}

	public static final Collection<AbstractFrame> getFrameList() {
		return frames.values();
	}

	public static void showFrame(MatrixGUIObject m) {
		try {
			AbstractFrame frame = frames.get(m);
			if (frame == null) {
				frame = new MatrixFrame(m);
				frames.put(m, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame(AlgorithmGUIObject a) {
		try {
			AbstractFrame frame = frames.get(a);
			if (frame == null) {
				frame = new AlgorithmFrame(a);
				frames.put(a, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame() {
		try {
			AbstractFrame frame = frames.get(AbstractModule.getInstance());
			if (frame == null) {
				frame = new ModuleFrame((ModuleGUIObject) AbstractModule.getInstance()
						.getGUIObject());
				frames.put(AbstractModule.getInstance().getGUIObject(), frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame(VariableGUIObject v) {
		try {
			AbstractFrame frame = frames.get(v);
			if (frame == null) {
				frame = new VariableFrame(v);
				frames.put(v, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame(ModuleGUIObject m) {
		try {
			AbstractFrame frame = frames.get(m);
			if (frame == null) {
				frame = new ModuleFrame(m);
				frames.put(m, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame(DataSetGUIObject d) {
		try {
			AbstractFrame frame = frames.get(d);
			if (frame == null) {
				frame = new DataSetFrame(d);
				frames.put(d, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showFrame(SampleGUIObject s) {
		try {
			AbstractFrame frame = frames.get(s);
			if (frame == null) {
				frame = new SampleFrame(s);
				frames.put(s, frame);
			}
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
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
