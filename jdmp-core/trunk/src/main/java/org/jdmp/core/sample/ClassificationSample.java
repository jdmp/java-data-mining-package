package org.jdmp.core.sample;

import org.jdmp.matrix.Matrix;
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

	public ClassificationSample(Matrix input, Matrix target) {
		super();
		setMatrix(INPUT, input);
		setMatrix(TARGET, target);
	}

	public boolean isCorrect() throws MatrixException {
		return getDesiredClass() == getRecognizedClass();
	}

	public ClassificationSample clone() {
		ClassificationSample s = new ClassificationSample();
		s.setMatrix(INPUT, getMatrix(INPUT).clone());
		s.setMatrix(TARGET, getMatrix(TARGET).clone());
		return s;
	}

	public String toString() {
		return "ClassificationSample [" + Coordinates.toString(getMatrix(INPUT).getSize()) + "]->"
				+ getDesiredClass();
	}

	public int getDesiredClass() throws MatrixException {
		return (int) getMatrix(TARGET).getCoordinatesOfMaximum()[COLUMN];
	}

	public int getRecognizedClass() throws MatrixException {
		return (int) getMatrix(PREDICTED).getCoordinatesOfMaximum()[COLUMN];
	}

}
