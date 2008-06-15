package org.jdmp.gui.algorithm;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.gui.AbstractGUIObject;

public class AlgorithmGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 2744384245306464106L;

	private Algorithm algorithm = null;

	public AlgorithmGUIObject(Algorithm a) {
		this.algorithm = a;
	}

	public void clear() {
		algorithm.clear();
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

}