package org.jdmp.jmatio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

import com.jmatio.io.MatFileWriter;
import com.jmatio.types.MLArray;

public abstract class ExportMatrixMAT {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException, MatrixException {
		MatFileWriter writer = new MatFileWriter();
		Collection<MLArray> matrixList = new ArrayList<MLArray>();
		matrixList.add(new MLDoubleMatrix(matrix).getWrappedObject());
		writer.write(file, matrixList);
	}

	public static void main(String[] args) throws Exception {
		Matrix m = MatrixFactory.rand(10, 10);
		m.setLabel("A");
		ExportMatrixMAT.toFile(new File("/home/arndt/matlab.mat"), m);
		Matrix m2 = ImportMatrixMAT.fromFile(new File("/home/arndt/matlab.mat"));
		System.out.println(m2);
	}

}
