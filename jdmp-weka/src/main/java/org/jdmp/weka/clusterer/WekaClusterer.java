/*
 * Copyright (C) 2008-2016 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.weka.clusterer;

import java.lang.reflect.Constructor;
import java.util.Collection;

import org.jdmp.core.algorithm.clustering.AbstractClusterer;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.weka.wrappers.DataSetToInstancesWrapper;
import org.jdmp.weka.wrappers.SampleToInstanceWrapper;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;

import org.ujmp.core.exception.NotImplementedException;
import weka.clusterers.Clusterer;
import weka.clusterers.NumberOfClustersRequestable;
import weka.core.Instance;
import weka.core.Instances;

public class WekaClusterer extends AbstractClusterer {
    private static final long serialVersionUID = 1308337964347655655L;

    public enum WekaClustererType {
        Cobweb, DBScan, EM, FarthestFirst, FilteredClusterer, MakeDensityBasedClusterer, SimpleKMeans, XMeans
    }

    private Clusterer wekaClusterer = null;

    private Instances instances = null;

    private WekaClustererType clustererName = null;

    private String[] options = null;

    private boolean discrete = false;

    public WekaClusterer(WekaClustererType classifierName, boolean discrete, String... options)
            throws Exception {
        setLabel("Weka-" + classifierName.name());
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
            Constructor<?> constr = c.getConstructor(new Class[]{});
            wekaClusterer = (Clusterer) constr.newInstance(new Object[]{});
            if (options != null || options.length != 0) {
                // wekaClusterer.setOptions(options);
            }
        }
    }

    public void reset() {
        try {
            createAlgorithm();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void trainAll(ListDataSet dataSet) {
        instances = new DataSetToInstancesWrapper(dataSet, discrete, false);
        try {
            wekaClusterer.buildClusterer(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void predictOne(Sample sample) {
        try {
            double[] probabilities = null;
            Matrix input = sample.getAsMatrix(INPUT);
            Instance instance = new SampleToInstanceWrapper(input, null, null, discrete, false);
            instance.setDataset(instances);
            probabilities = wekaClusterer.distributionForInstance(instance);
            double[][] v = new double[1][];
            v[0] = probabilities;
            DenseDoubleMatrix2D output = Matrix.Factory.linkToArray(v);
            sample.put(PREDICTED, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNumberOfClusters(int numberOfClusters) throws Exception {
        if (wekaClusterer instanceof NumberOfClustersRequestable) {
            ((NumberOfClustersRequestable) wekaClusterer).setNumClusters(numberOfClusters);
        } else {
            throw new RuntimeException("Cannot set number of Clusters for this Clusterer");
        }
    }

    @Override
    public void trainBatch(Collection<Sample> samples) {
        throw new NotImplementedException();
    }

    public org.jdmp.core.algorithm.clustering.Clusterer emptyCopy() {
        throw new NotImplementedException();
    }

}
