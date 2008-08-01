package org.jdmp.core.dataset;

import java.util.List;

import org.jdmp.core.algorithm.classification.Classifier;
import org.ujmp.core.Matrix;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public class CrossValidation {

	public static ListMatrix<Double> run(Classifier algorithm, ClassificationDataSet dataSet)
			throws Exception {
		return run(algorithm, dataSet, 10, 10, System.currentTimeMillis());
	}

	public static ListMatrix<Double> run(Classifier algorithm, ClassificationDataSet dataSet,
			int folds, int runs, long randomSeed) throws Exception {

		ListMatrix<Double> all = new DefaultListMatrix<Double>();

		for (int run = 0; run < runs; run++) {

			ListMatrix<Double> acc = new DefaultListMatrix<Double>();

			System.out.print(run + "\t");

			for (int fold = 0; fold < folds; fold++) {
				List<DataSet> dss = dataSet.splitForCV(folds, fold, randomSeed + run);
				ClassificationDataSet train = (ClassificationDataSet) dss.get(0);
				ClassificationDataSet test = (ClassificationDataSet) dss.get(1);
				algorithm.reset();
				algorithm.train(train);
				algorithm.predict(test);
				acc.add(test.getAccuracy());
				System.out.print(test.getAccuracy() + "\t");
			}
			System.out.println();

			double mean = acc.getMeanValue();
			all.add(mean);

			System.out.println(run + "\t" + mean);
		}

		if (all.size() > 1) {
			Matrix std = all.std(Ret.NEW, Matrix.ROW, false);
			System.out.println(all.getMeanValue() + "+-" + std.getEuklideanValue());
		} else {
			System.out.println(all.getMeanValue());
		}

		return all;
	}

}
