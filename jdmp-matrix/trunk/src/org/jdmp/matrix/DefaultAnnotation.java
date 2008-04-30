package org.jdmp.matrix;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.matrix.interfaces.Annotation;

public class DefaultAnnotation implements Annotation {
	private static final long serialVersionUID = 4497806180298615612L;

	private Object matrixAnnotation = null;

	private Map<Integer, Map<Integer, Object>> axisAnnotation = null;

	private Map<Integer, Object> axisLabelAnnotation = null;

	public DefaultAnnotation() {
		axisAnnotation = new HashMap<Integer, Map<Integer, Object>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
	}

	public DefaultAnnotation(DefaultAnnotation annotation) {
		this.matrixAnnotation = annotation.getMatrixAnnotation();
		axisAnnotation = new HashMap<Integer, Map<Integer, Object>>();
		axisLabelAnnotation = new HashMap<Integer, Object>(2);
		axisLabelAnnotation.putAll(annotation.axisLabelAnnotation);
		for (int i : annotation.axisAnnotation.keySet()) {
			Map<Integer, Object> m = axisAnnotation.get(i);
			Map<Integer, Object> mnew = new HashMap<Integer, Object>();
			mnew.putAll(m);
			axisAnnotation.put(i, mnew);
		}
	}

	public void setAxisAnnotation(int axis, int positionOnAxis, Object value) {
		Map<Integer, Object> axisMap = axisAnnotation.get(axis);
		if (axisMap == null) {
			axisMap = new HashMap<Integer, Object>(2);
			axisAnnotation.put(axis, axisMap);
		}
		axisMap.put(positionOnAxis, value);
	}

	public Object getAxisAnnotation(int axis, int positionOnAxis) {
		Map<Integer, Object> axisMap = axisAnnotation.get(axis);
		if (axisMap != null) {
			return axisMap.get(positionOnAxis);
		}
		return null;
	}

	public Object getAxisAnnotation(int axis) {
		return axisLabelAnnotation.get(axis);
	}

	public void setAxisAnnotation(int axis, Object value) {
		axisLabelAnnotation.put(axis, value);
	}

	public Object getMatrixAnnotation() {
		return matrixAnnotation;
	}

	public void setMatrixAnnotation(Object matrixAnnotation) {
		this.matrixAnnotation = matrixAnnotation;
	}

	public Annotation clone() {
		DefaultAnnotation a = new DefaultAnnotation(this);
		return a;
	}

}
