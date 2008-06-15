package org.jdmp.weka.clusterer;

import java.lang.reflect.Constructor;

import org.jdmp.core.algorithm.clustering.AbstractClusterer;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.weka.wrappers.DataSetToInstancesWrapper;
import org.jdmp.weka.wrappers.SampleToInstanceWrapper;

import weka.clusterers.Clusterer;
import weka.clusterers.NumberOfClustersRequestable;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClusterer extends AbstractClusterer {
	private static final long serialVersionUID = 1308337964347655655L;

	public enum WekaClustererType {
		Cobweb, DBScan, EM, FarthestFirst, FilteredClusterer, MakeDensityBasedClusterer, SimpleKMeans, XMeans
	};

	private Clusterer wekaClusterer = null;

	private Instances instances = null;

	private WekaClustererType clustererName = null;

	private String[] options = null;

	private boolean discrete = false;

	public WekaClusterer(WekaClustererType classifierName, boolean discrete, String... options)
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
				// wekaClusterer.setOptions(options);
			}
		}
	}

	public void reset() throws Exception {
		createAlgorithm();
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		instances = new DataSetToInstancesWrapper(dataSet, discrete, false);
		wekaClusterer.buildClusterer(instances);
	}

	public Matrix predict(Matrix input, Matrix weight) throws Exception {
		double[] probabilities = null;
		Instance instance = new SampleToInstanceWrapper(input, weight, null, discrete, false);
		instance.setDataset(instances);
		probabilities = wekaClusterer.distributionForInstance(instance);
		double[][] v = new double[1][];
		v[0] = probabilities;
		Matrix output = MatrixFactory.linkToArray(v);
		return output;
	}

	public void setNumberOfClusters(int numberOfClusters) throws Exception {
		if (wekaClusterer instanceof NumberOfClustersRequestable) {
			((NumberOfClustersRequestable) wekaClusterer).setNumClusters(numberOfClusters);
		} else {
			throw new RuntimeException("Cannot set number of Clusters for this Clusterer");
		}
	}

}
