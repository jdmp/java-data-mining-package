package org.jdmp.core.interpreter;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmCommand extends Command {

	private String algorithm = null;

	private List<String> targets = new ArrayList<String>();

	private List<String> sources = new ArrayList<String>();

	public void addTarget(String target) {
		targets.add(target);
	}

	public void addSource(String source) {
		sources.add(source);
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String toString() {
		return targets + " = " + algorithm + "(" + sources + ")";
	}

}
