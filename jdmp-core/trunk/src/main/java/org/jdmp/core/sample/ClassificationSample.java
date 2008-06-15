package org.jdmp.core.sample;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;

public class ClassificationSample extends RegressionSample {
	private static final long serialVersionUID = 7327527059314579381L;

	public ClassificationSample(String label) {
		super(label);
	}

	public ClassificationSample() {
		super();
	}

	public ClassificationSample(Matrix input, Matrix output) {
		super();
		setMatrix(INPUT, input);
		setDesiredOutputMatrix(output);
	}

	public boolean isCorrect() throws MatrixException {
		return getDesiredClass() == getRecognizedClass();
	}

	public ClassificationSample clone() {
		ClassificationSample s = new ClassificationSample();
		s.setMatrix(INPUT, getMatrix(INPUT).clone());
		s.setDesiredOutputMatrix(getDesiredOutputMatrix().clone());
		return s;
	}

	public String toString() {
		return "ClassificationSample [" + Coordinates.toString(getMatrix(INPUT).getSize()) + "]->"
				+ getDesiredClass();
	}

	public int getDesiredClass() throws MatrixException {
		return (int) getDesiredOutputMatrix().getCoordinatesOfMaximum()[COLUMN];
	}

	public int getRecognizedClass() throws MatrixException {
		return (int) getOutputMatrix().getCoordinatesOfMaximum()[COLUMN];
	}

	public void setClassID(int classID) {
		Matrix m = MatrixFactory.linkToValue(classID);
		// setMatrixForType(VariableType.OUTPUTCLASS, m);
	}

	public void setClassID(int classID, String label) {
		Matrix m = MatrixFactory.linkToValue(classID);
		m.setLabel(label);
		// setMatrixForType(VariableType.OUTPUTCLASS, m);
	}

}
