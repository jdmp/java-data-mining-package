package org.jdmp.gui.algorithm;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.util.AbstractGUIObject;

public class AlgorithmGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = 2744384245306464106L;

	private Algorithm algorithm = null;

	public AlgorithmGUIObject(Algorithm a) {
		this.algorithm = a;
	}

	@Override
	public void clear() {
		algorithm.clear();
	}

	public String getLongStatus() {
		return algorithm.getLongStatus();
	}

	public String getShortStatus() {
		return algorithm.getShortStatus();
	}

	public void dispose() {
		algorithm.dispose();
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

}
