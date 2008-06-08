package org.jdmp.weka;

import java.lang.reflect.Constructor;

import org.jdmp.core.algorithm.clustering.AbstractClusteringAlgorithm;

import weka.clusterers.Clusterer;
import weka.core.Instances;

public class WekaClusterer extends AbstractClusteringAlgorithm {

	public enum WekaClusterers {
		SimpleKMeans
	};

	private Clusterer wekaClusterer = null;

	private Instances instances = null;

	private WekaClusterers clustererName = null;

	private String[] options = null;

	private boolean discrete = false;

	public WekaClusterer(WekaClusterers classifierName, boolean discrete, String... options)
			throws Exception {
		super("Weka-" + classifierName.name());
		this.clustererName = classifierName;
		this.options = options;
		this.discrete = discrete;
		createAlgorithm();
	}

	public void createAlgorithm() throws Exception {
		Class<?> c = null;
		if (c == null) {
			try {
				c = Class.forName(clustererName.name());
			} catch (ClassNotFoundException e) {
			}
		}
		if (c == null) {
			try {
				c = Class.forName("weka.clusterers." + clustererName.name());
			} catch (ClassNotFoundException e) {
			}
		}

		if (c == null) {
			throw new ClassNotFoundException("class not found: " + clustererName);
		} else {
			Constructor<?> constr = c.getConstructor(new Class[] {});
			wekaClusterer = (Clusterer) constr.newInstance(new Object[] {});
			if (options != null || options.length != 0) {
				//wekaClusterer.setOptions(options);
			}
		}
	}
	
	public void reset() throws Exception {
		createAlgorithm();
	}

}
