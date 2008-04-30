package org.jdmp.matrix.calculation.basic;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.AnnotationTransfer;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.DoubleCalculation;
import org.jdmp.matrix.interfaces.Annotation;

public class Convert extends DoubleCalculation {
	private static final long serialVersionUID = 6393277198816850597L;

	public Convert(Matrix matrix) {
		super(matrix);
	}

	@Override
	public double getDouble(long... coordinates) throws MatrixException {
		return getSource().getDouble(coordinates);
	}

	public static Matrix calcNew(EntryType entryType, AnnotationTransfer annotationTransfer,
			Matrix source) throws MatrixException {
		Matrix ret = Matrix.zeros(entryType, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			ret.setDouble(source.getDouble(c), c);
		}
		switch (annotationTransfer) {
		case LINK:
			ret.setAnnotation(source.getAnnotation());
			break;
		case COPY:
			Annotation a = source.getAnnotation();
			if (a != null) {
				ret.setAnnotation(a.clone());
			}
			break;
		default:
			break;
		}
		return ret;
	}

	public static Matrix calcNew(AnnotationTransfer annotationTransfer, Matrix matrix)
			throws MatrixException {
		return calcNew(EntryType.DOUBLE, annotationTransfer, matrix);
	}

}
