package org.jdmp.core.dataset;

import java.util.List;

import org.jdmp.core.algorithm.classification.AlgorithmClassifier;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.implementations.collections.DefaultListMatrix;
import org.jdmp.matrix.interfaces.ListMatrix;

public class CrossValidation {

	public static ListMatrix<Double> run(AlgorithmClassifier algorithm,
			ClassificationDataSet dataSet, int folds, int runs, long randomSeed) throws Exception {

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
