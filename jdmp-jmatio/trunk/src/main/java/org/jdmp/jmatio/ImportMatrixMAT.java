package org.jdmp.jmatio;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.ujmp.core.Matrix;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;

public class ImportMatrixMAT {

	public static Matrix fromFile(File file, Object... parameters) throws IOException {
		MatFileReader reader = new MatFileReader();
		Map<String, MLArray> map = reader.read(file);
		String key = map.keySet().iterator().next();
		return new MLDoubleMatrix((MLDouble) map.get(key));
	}

}
