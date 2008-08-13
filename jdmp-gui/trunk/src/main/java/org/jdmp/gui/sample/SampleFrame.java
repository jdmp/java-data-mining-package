package org.jdmp.gui.sample;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.util.AbstractFrame;

public class SampleFrame extends AbstractFrame {
	private static final long serialVersionUID = 4143701040194007821L;

	public SampleFrame(SampleGUIObject p) throws MatrixException {
		super(p, new SamplePanel(p));
	}

}
