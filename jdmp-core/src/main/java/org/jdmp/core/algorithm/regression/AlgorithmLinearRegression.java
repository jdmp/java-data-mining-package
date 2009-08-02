package org.jdmp.core.algorithm.regression;

import org.jdmp.core.algorithm.classification.AlgorithmClassifier;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;

/**
 * AlgorithmLinearRegression extends AlgorithmClassifier and not
 * AlgorithmRegression because also classification is possible using regression
 */
public class AlgorithmLinearRegression extends AlgorithmClassifier {
	private static final long serialVersionUID = 3483912497269476834L;

	public static final int PARAMETERS = 1;

	public AlgorithmLinearRegression() {
		super("Linear Regression");
		setParameterVariable(new DefaultVariable("Regression Parameters"));
	}

	public void setParameterVariable(Variable variable) {
		setVariable(PARAMETERS, variable);
	}

	public Variable getParameterVariable() {
		return getVariable(PARAMETERS);
	}

	public Matrix getParameterMatrix() {
		return getParameterVariable().getMatrix();
	}

	@Override
	public Matrix predict(Matrix input, Matrix sampleWeight) throws Exception {
		return input.addColumnWithOnes().mtimes(getParameterMatrix());
	}

	@Override
	public void train(Matrix input, Matrix sampleWeight, Matrix desiredOutput) throws Exception {
		throw (new Exception("pattern-by-pattern learning not supported"));
	}

	public void train(RegressionDataSet dataSet) throws Exception {
		Matrix x = dataSet.getInputMatrix().addColumnWithOnes();
		Matrix y = dataSet.getDesiredOutputMatrix();
		// Matrix parameters =
		// x.transpose().mtimes(x).pinv().mtimes(x.transpose()).mtimes(y);
		Matrix parameters = x.pinv().mtimes(y);
		setParameterMatrix(parameters);
	}

	public void setParameterMatrix(Matrix parameters) {
		addMatrixForVariable(PARAMETERS, parameters);
	}

	@Override
	public void reset() throws MatrixException {
		getParameterVariable().clear();
	}

}
