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

	public DefaultAnnotation(Annotation annotation) {
		this.matrixAnnotation = annotation.getMatrixAnnotation();
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

	public Map<Integer, Object> getAxisLabelAnnotation() {
		return axisLabelAnnotation;
	}

	public Map<Integer, Map<Integer, Object>> getAxisAnnotation() {
		return axisAnnotation;
	}

	public boolean equals(Annotation a) {
		if (a == null) {
			return false;
		}
		if (matrixAnnotation != null && !matrixAnnotation.equals(a.getMatrixAnnotation())) {
			return false;
		} else if (a.getMatrixAnnotation() != null
				&& !a.getMatrixAnnotation().equals(matrixAnnotation)) {
			return false;
		}
		if (axisLabelAnnotation != null && !axisLabelAnnotation.equals(a.getAxisLabelAnnotation())) {
			return false;
		} else {
			if (a.getAxisLabelAnnotation() != null
					&& !a.getAxisLabelAnnotation().equals(axisLabelAnnotation)) {
				return false;
			}
		}
		if (axisAnnotation != null && !axisAnnotation.equals(a.getAxisAnnotation())) {
			return false;
		} else {
			if (a.getAxisAnnotation() != null && !a.getAxisAnnotation().equals(axisAnnotation)) {
				return false;
			}
		}
		return true;
	}
}
