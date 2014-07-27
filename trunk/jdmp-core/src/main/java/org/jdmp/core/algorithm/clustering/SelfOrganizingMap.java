/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.collections.list.FastArrayList;
import org.ujmp.core.util.MathUtil;

public class SelfOrganizingMap extends AbstractClusterer {
	private static final long serialVersionUID = -7826055336085139110L;

	private int rows = 10;
	private int cols = 10;
	private int tMax = 1;
	private double epsilonStart = 0.5;
	private double epsilonEnd = 0.01;
	private double deltaStart = Math.max(rows, cols) / 2.0;
	private double deltaEnd = 1.0;

	private Matrix[][] weightVectors;

	public SelfOrganizingMap() {
		this(10, 10);
	}

	public SelfOrganizingMap(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.tMax = 200;
		this.epsilonStart = 0.5;
		this.epsilonEnd = 0.001;
		this.deltaStart = Math.max(rows, cols) / 2.0;
		this.deltaEnd = 0.5;
		this.weightVectors = new Matrix[rows][cols];
	}

	public void reset() throws Exception {
		this.weightVectors = new Matrix[rows][cols];
	}

	public void train(DataSet dataSet) throws Exception {

		List<Sample> samples = new FastArrayList<Sample>(dataSet.getSampleMap().values());
		for (int t = 0; t < tMax; t++) {

			double epsilon = epsilonStart * Math.pow(epsilonEnd / epsilonStart, (double) t / tMax);
			double delta = deltaStart * Math.pow(deltaEnd / deltaStart, (double) t / tMax);

			System.out.println((int) ((double) t / tMax * 100) + "%");

			Collections.shuffle(samples);

			for (Sample s : samples) {
				Matrix input = s.getMatrix(INPUT);

				// find best match
				double bestDistance = Double.MAX_VALUE;
				int bestRow = -1;
				int bestCol = -1;
				Matrix distanceMatrix = Matrix.Factory.zeros(rows, cols);
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						Matrix m = weightVectors[row][col];
						if (m == null) {
							m = Matrix.Factory.randn(input.getSize());
							weightVectors[row][col] = m;
						}
						double distance = input.euklideanDistanceTo(m, true);
						distanceMatrix.setAsDouble(distance, row, col);
						if (distance < bestDistance) {
							bestDistance = distance;
							bestRow = row;
							bestCol = col;
						}
					}
				}

				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						double dist = Math.sqrt((row - bestRow) * (row - bestRow) + (col - bestCol)
								* (col - bestCol));
						double h = MathUtil.gauss(0, delta, dist);
						Matrix w = weightVectors[row][col];
						w = w.plus(input.minus(w).times(epsilon * h));
						weightVectors[row][col] = w;
					}
				}

				if (t == tMax - 1) {
					s.setMatrix("Projection", distanceMatrix);
				}

			}

		}

	}

	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {

		return null;
	}

	public void setNumberOfClusters(int numberOfClusters) throws Exception {

	}

}
