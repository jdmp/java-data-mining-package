package org.jdmp.jmatio;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.DefaultAnnotation;
import org.jdmp.matrix.interfaces.Annotation;
import org.jdmp.matrix.util.StringUtil;

import com.jmatio.types.MLArray;

public class MLAnnotation extends DefaultAnnotation {
	private static final long serialVersionUID = 86587787686451L;

	private transient MLArray matrix = null;

	public MLAnnotation(MLArray matrix) {
		this.matrix = matrix;
	}

	public MLAnnotation(MLAnnotation annotation) {
		this.matrix = annotation.matrix;
		matrixAnnotation = annotation.getMatrixAnnotation();
		axisAnnotation = new HashMap<Integer, Map<Integer, Object>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
		axisLabelAnnotation.putAll(annotation.getAxisLabelAnnotation());
		for (int i : annotation.getAxisAnnotation().keySet()) {
			Map<Integer, Object> m = annotation.getAxisAnnotation().get(i);
			Map<Integer, Object> mnew = new HashMap<Integer, Object>();
			mnew.putAll(m);
			axisAnnotation.put(i, mnew);
		}
	}

	@Override
	public Object getMatrixAnnotation() {
		return matrix.getName();
	}

	@Override
	public void setMatrixAnnotation(Object matrixAnnotation) {
		matrix.name = StringUtil.convert(matrixAnnotation);
	}

	@Override
	public Annotation clone() {
		return new MLAnnotation(this);
	}

	public void setMLArray(MLArray matrix) {
		this.matrix = matrix;
	}

}