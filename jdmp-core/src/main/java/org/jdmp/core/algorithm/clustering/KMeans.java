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

package org.jdmp.core.algorithm.clustering;

import java.util.Collections;
import java.util.List;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.collections.list.FastArrayList;

public class KMeans extends AbstractClusterer {
	private static final long serialVersionUID = 6823002403320626583L;

	private int k = 10;
	private int maxIterations = 100;
	private double epsilon = 1e-6;

	private Matrix[] clusterCenters = null;

	public KMeans() {
		this(10);
	}

	public KMeans(int k) {
		this.k = k;
	}

	public void reset() throws Exception {
		clusterCenters = null;
	}

	public void trainAll(ListDataSet dataSet) {

		clusterCenters = new Matrix[k];

		List<Sample> samples = new FastArrayList<Sample>(dataSet);
		Collections.shuffle(samples);

		// set initial cluster centers to actual samples
		for (int i = 0; i < k; i++) {
			Sample s = samples.get(i);
			Matrix input = s.getAsMatrix(getInputLabel()).toRowVector(Ret.NEW);
			clusterCenters[i] = input.clone();
		}

		for (int j = 0; j < maxIterations; j++) {

			// initialize new cluster centers to zero
			Matrix[] newClusterCenters = new Matrix[k];
			for (int i = 0; i < k; i++) {
				newClusterCenters[i] = Matrix.Factory.zeros(clusterCenters[0].getRowCount(), 1);
			}

			// distribute samples to clusters and update new cluster centers
			int[] clusterCounts = new int[k];
			for (Sample s : samples) {
				Matrix input = s.getAsMatrix(getInputLabel());
				int bestCluster = predictOne(input).intValue();

				// sum up vectors
				newClusterCenters[bestCluster] = newClusterCenters[bestCluster].plus(input);
				clusterCounts[bestCluster]++;
			}

			// calculate average for final new clusters
			for (int i = 0; i < k; i++) {
				if (clusterCounts[i] != 0) {
					newClusterCenters[i] = newClusterCenters[i].divide(clusterCounts[i]);
				}
			}

			// measure the change of the cluster centers and update
			double sum = 0;
			for (int i = 0; i < k; i++) {
				sum += clusterCenters[i].euklideanDistanceTo(newClusterCenters[i], true);
				clusterCenters[i] = newClusterCenters[i];
			}
			sum = sum / k;

			System.out.println("iteration " + j + " change of cluster centers: " + sum);

			if (sum < epsilon) {
				break;
			}

		}
	}

	public Matrix predictOne(Matrix input) {
		double minDistance = Double.MAX_VALUE;
		int bestCluster = -1;
		for (int i = 0; i < k; i++) {
			double distance = clusterCenters[i].euklideanDistanceTo(input, true);
			if (distance < minDistance) {
				minDistance = distance;
				bestCluster = i;
			}
		}
		return Matrix.Factory.linkToValue(bestCluster);
	}

	public Clusterer emptyCopy() {
		return new KMeans(k);
	}

}
