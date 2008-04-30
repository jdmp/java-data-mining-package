package org.jdmp.matrix.calculation.general.missingvalues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.util.MathUtil;

public class ImputeRegression extends DoubleCalculation {
	private static final long serialVersionUID = 2147234720707721364L;

	Matrix firstGuess = null;

	Matrix imputedFinal = null;

	public ImputeRegression(Matrix matrix) {
		super(matrix);
	}

	public ImputeRegression(Matrix matrix, Matrix firstGuess) {
		super(matrix);
		this.firstGuess = firstGuess;
	}

	public double getDouble(long... coordinates) throws MatrixException {
		if (firstGuess == null) {
			firstGuess = getSource().imputeMean(Ret.NEW, Matrix.ROW);
		}
		if (imputedFinal == null) {
			createMatrix();
		}
		double v = getSource().getDouble(coordinates);
		if (MathUtil.isNaNOrInfinite(v)) {
			return imputedFinal.getDouble(coordinates);
		} else {
			return v;
		}
	}

	private void createMatrix() {
		try {
			Matrix x = getSource();

			imputedFinal = Matrix.zeros(x.getSize());
			imputedFinal.showGUI();

			ExecutorService executor = Executors.newFixedThreadPool(1);
			List<Future<Long>> futures = new ArrayList<Future<Long>>();

			long t0 = System.currentTimeMillis();

			// for (long c = 0; c < x.getColumnCount(); c++) {
			for (long c = 0; c < 9; c++) {
				futures.add(executor.submit(new PredictColumn(c)));
			}

			for (Future<Long> f : futures) {
				Long completedCols = f.get();
				long elapsedTime = System.currentTimeMillis() - t0;
				long remainingCols = x.getColumnCount() - completedCols;
				double colsPerMillisecond = (double) completedCols / (double) elapsedTime;
				long remainingTime = (long) (remainingCols / colsPerMillisecond / 1000.0);
				System.out.println((completedCols * 1000 / x.getColumnCount() / 10.0) + "% completed (" + remainingTime
						+ " seconds remaining)");
			}

			executor.shutdown();

		} catch (Exception e) {
			logger.log(Level.WARNING, "error", e);
		}
	}

	class PredictColumn implements Callable<Long> {

		long column = 0;

		public PredictColumn(long column) {
			this.column = column;
		}

		public Long call() throws Exception {
			Matrix newColumn = replaceInColumn(getSource(), firstGuess, column);
			for (int r = 0; r < newColumn.getRowCount(); r++) {
				imputedFinal.setDouble(newColumn.getDouble(r, 0), r, column);
			}
			return column;
		}

	}

	private static Matrix replaceInColumn(Matrix original, Matrix firstGuess, long column) throws MatrixException {

		Matrix x = firstGuess.deleteColumns(Ret.NEW, column);
		Matrix y = original.selectColumns(Ret.NEW, column);

		List<Long> missingRows = new ArrayList<Long>();
		for (long i = y.getRowCount(); --i >= 0;) {
			double v = y.getDouble(i, 0);
			if (MathUtil.isNaNOrInfinite(v)) {
				missingRows.add(i);
			}
		}

		Matrix xtrain = x.deleteRows(Ret.NEW, missingRows);
		Matrix ytrain = y.deleteRows(Ret.NEW, missingRows);

		Matrix xinv = xtrain.pinv();
		Matrix b = xinv.mtimes(ytrain);
		Matrix ypredicted = x.mtimes(b);

		return ypredicted;
	}

}
