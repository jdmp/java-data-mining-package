package org.jdmp.gui.algorithm;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.util.AbstractFrame;
import org.jdmp.matrix.MatrixException;

public class AlgorithmFrame extends AbstractFrame {
	private static final long serialVersionUID = -5788609279046397378L;

	public AlgorithmFrame(Algorithm a) throws MatrixException {
		super(a);
	}

}
