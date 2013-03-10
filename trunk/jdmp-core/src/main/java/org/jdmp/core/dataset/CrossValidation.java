/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.jdmp.core.dataset;

import java.util.List;

import org.jdmp.core.algorithm.classification.Classifier;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public class CrossValidation {

	public static ListMatrix<Double> run(Classifier algorithm, ClassificationDataSet dataSet)
			throws Exception {
		return run(algorithm, dataSet, 10, 10, System.currentTimeMillis());
	}

	public static ListMatrix<Double> run(Classifier algorithm, ClassificationDataSet dataSet,
			int folds, int runs, long randomSeed) throws Exception {

		ListMatrix<Double> allacc = new DefaultListMatrix<Double>();
		ListMatrix<Double> allfm = new DefaultListMatrix<Double>();
		ListMatrix<Double> allsens = new DefaultListMatrix<Double>();
		ListMatrix<Double> allspec = new DefaultListMatrix<Double>();
		ListMatrix<Double> allprec = new DefaultListMatrix<Double>();
		ListMatrix<Double> allrec = new DefaultListMatrix<Double>();

		for (int run = 0; run < runs; run++) {

			ListMatrix<Double> acc = new DefaultListMatrix<Double>();
			ListMatrix<Double> fm = new DefaultListMatrix<Double>();
			ListMatrix<Double> sens = new DefaultListMatrix<Double>();
			ListMatrix<Double> spec = new DefaultListMatrix<Double>();
			ListMatrix<Double> prec = new DefaultListMatrix<Double>();
			ListMatrix<Double> rec = new DefaultListMatrix<Double>();

			System.out.print("F-Measure (macro) in run " + run + ":\t");

			for (int fold = 0; fold < folds; fold++) {
				List<DataSet> dss = dataSet.splitForCV(folds, fold, randomSeed + run);
				ClassificationDataSet train = (ClassificationDataSet) dss.get(0);
				ClassificationDataSet test = (ClassificationDataSet) dss.get(1);
				algorithm.reset();
				algorithm.train(train);
				algorithm.predict(test);

				acc.add(test.getAccuracy());
				fm.add(test.getVariables().getAsDouble(Variable.FMEASUREMACRO));
				sens.add(test.getVariables().getAsDouble(Variable.SENSITIVITY));
				spec.add(test.getVariables().getAsDouble(Variable.SPECIFICITY));
				prec.add(test.getVariables().getAsDouble(Variable.PRECISION));
				rec.add(test.getVariables().getAsDouble(Variable.RECALL));
				// System.out.print(test.getAccuracy() + "\t");
				System.out.print(test.getVariables().getAsDouble(Variable.FMEASUREMACRO) + "\t");
			}
			System.out.println();

			double meanacc = acc.getMeanValue();
			allacc.add(meanacc);

			double meanfm = fm.getMeanValue();
			allfm.add(meanfm);

			double meansens = sens.getMeanValue();
			allsens.add(meansens);

			double meanspec = spec.getMeanValue();
			allspec.add(meanspec);

			double meanprec = prec.getMeanValue();
			allprec.add(meanprec);

			double meanrec = rec.getMeanValue();
			allrec.add(meanrec);

			System.out.println("Average Accuracy in run " + run + ":\t" + meanacc);
			System.out.println("Average F-Measure in run " + run + ":\t" + meanfm);
			System.out.println("Average Sensitivity in run " + run + ":\t" + meansens);
			System.out.println("Average Specificity in run " + run + ":\t" + meanspec);
			System.out.println("Average Precision in run " + run + ":\t" + meanprec);
			System.out.println("Average Recall in run " + run + ":\t" + meanrec);
		}

		if (allacc.size() > 1) {
			Matrix stdacc = allacc.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("Accuracy: " + allacc.getMeanValue() + "+-" + stdacc.doubleValue());
			Matrix stdfm = allfm.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("F-Measure (macro): " + allfm.getMeanValue() + "+-"
					+ stdfm.doubleValue());
			Matrix stdsens = allsens.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("Sensitivity: " + allsens.getMeanValue() + "+-"
					+ stdsens.doubleValue());
			Matrix stdspec = allspec.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("Specificity: " + allspec.getMeanValue() + "+-"
					+ stdspec.doubleValue());
			Matrix stdprec = allprec.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("Precision: " + allprec.getMeanValue() + "+-"
					+ stdprec.doubleValue());
			Matrix stdrec = allrec.std(Ret.NEW, Matrix.ROW, false, true);
			System.out.println("Recall: " + allrec.getMeanValue() + "+-" + stdrec.doubleValue());
		} else {
			System.out.println("Accuracy: " + allacc.getMeanValue());
			System.out.println("F-Measure (macro): " + allfm.getMeanValue());
			System.out.println("Sensitivity: " + allsens.getMeanValue());
			System.out.println("Specificity: " + allspec.getMeanValue());
			System.out.println("Precision: " + allprec.getMeanValue());
			System.out.println("Recall: " + allrec.getMeanValue());
		}

		return allacc;
	}

}
