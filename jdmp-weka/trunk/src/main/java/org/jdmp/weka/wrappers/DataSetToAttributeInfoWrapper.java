package org.jdmp.weka.wrappers;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.Calculation.Ret;

import weka.core.FastVector;

public class DataSetToAttributeInfoWrapper extends FastVector {
	private static final long serialVersionUID = -5668139225494255695L;

	public DataSetToAttributeInfoWrapper(RegressionDataSet dataSet, boolean discrete) {
		super();

		// for (Attribute f : dataSet.getAttributes()) {
		// weka.core.Attribute a = null;
		// if (f.isDiscrete()) {
		// FastVector values = new FastVector();
		// for (int i = 0; i < f.getValueCount(); i++) {
		// values.addElement("Attribute " + i);
		// }
		// a = new weka.core.Attribute(f.getLabel(), values);
		// } else {
		// a = new weka.core.Attribute(f.getLabel());
		// }
		// addElement(a);
		// }
		Matrix valueCounts = dataSet.getInputMatrix().max(Ret.NEW, Matrix.ROW).plus(1);
		for (int j = 0; j < dataSet.getInputMatrix().getColumnCount(); j++) {
			weka.core.Attribute a = null;
			if (discrete) {
				FastVector values = new FastVector();
				for (int i = 0; i < valueCounts.getDouble(0, j); i++) {
					values.addElement("Attribute " + i);
				}
				a = new weka.core.Attribute(j + "", values);
			} else {
				a = new weka.core.Attribute(j + "");
			}
			addElement(a);
		}

		FastVector classes = new FastVector();
		for (int i = 0; i < ((ClassificationDataSet) dataSet).getClassCount(); i++) {
			classes.addElement("Class " + i);
		}
		weka.core.Attribute a = new weka.core.Attribute("class", classes);
		addElement(a);
	}
}
