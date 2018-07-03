/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.jdmp.core.dataset.ListDataSet;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;

import weka.core.Attribute;
import weka.core.FastVector;

import java.util.ArrayList;
import java.util.List;

public class DataSetToAttributeInfoWrapper extends ArrayList<Attribute> {
    private static final long serialVersionUID = -5668139225494255695L;

    public DataSetToAttributeInfoWrapper(ListDataSet dataSet, boolean discrete) {
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
                List<String> values = new ArrayList<>();
                for (int i = 0; i < valueCounts.getAsDouble(0, j); i++) {
                    values.add("Attribute " + i);
                }
                a = new Attribute(j + "", values);
            } else {
                a = new Attribute(j + "");
            }
            add(a);
        }

        List<String> classes = new ArrayList<>();
        int classCount = dataSet.getClassCount();
        for (int i = 0; i < classCount; i++) {
            classes.add("Class " + i);
        }
        Attribute a = new Attribute("class", classes);
        add(a);
    }
}
