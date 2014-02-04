/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.weka.wrappers;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.RegressionDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

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
				for (int i = 0; i < valueCounts.getAsDouble(0, j); i++) {
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
