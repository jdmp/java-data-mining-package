package org.jdmp.gui.algorithm;

import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.gui.util.AbstractFrame;

public class AlgorithmFrame extends AbstractFrame {
	private static final long serialVersionUID = -5788609279046397378L;

	public AlgorithmFrame(AlgorithmGUIObject a) throws MatrixException {
		super(a, new AlgorithmPanel(a));
	}

}
