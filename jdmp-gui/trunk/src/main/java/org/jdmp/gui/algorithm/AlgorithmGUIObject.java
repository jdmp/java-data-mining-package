package org.jdmp.gui.algorithm;

import javax.swing.Icon;

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

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return algorithm.getLabel();
	}

	public void setLabel(String label) {
		algorithm.setLabel(label);
	}

	public String getDescription() {
		return algorithm.getDescription();
	}

	public void setDescription(String description) {
		algorithm.setDescription(description);
	}

	public String toString() {
		return algorithm.toString();
	}

}
