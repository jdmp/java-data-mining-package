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

package org.jdmp.core.algorithm.regression;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exception.NotImplementedException;

import java.util.Collection;

/**
 * AlgorithmLinearRegression extends AlgorithmClassifier and not
 * AlgorithmRegression because also classification is possible using regression
 */
public class LinearRegression extends AbstractRegressor {
    private static final long serialVersionUID = 3483912497269476834L;

    public static final String PARAMETERS = "Parameters";

    private Matrix mean = null;
    private Matrix std = null;

    private final int numberOfPrincipalComponents;

    public LinearRegression() {
        this(0);
    }

    public LinearRegression(int numberOfPrincipalComponents) {
        super();
        this.numberOfPrincipalComponents = numberOfPrincipalComponents;
        setParameterVariable(Variable.Factory.labeledVariable("Regression Parameters"));
    }

    public void setParameterVariable(Variable variable) {
        setVariable(PARAMETERS, variable);
    }

    public Variable getParameterVariable() {
        return getVariableMap().get(PARAMETERS);
    }

    public Matrix getParameterMatrix() {
        return getParameterVariable().getLast();
    }

    public void predictOne(Sample sample) {
        Matrix input = sample.getAsMatrix(INPUT);
        input = input.toColumnVector(Ret.NEW);

        Matrix x = Matrix.Factory.zeros(1, input.getColumnCount() + 1);
        for (int c = 0; c < input.getColumnCount(); c++) {
            x.setAsDouble(input.getAsDouble(0, c), 0, c + 1);
        }

        x = x.minus(mean).divide(std);
        x.setAsDouble(1, 0, 0);

        Matrix result = x.mtimes(getParameterMatrix());
        sample.put(PREDICTED, result);
    }

    public void trainAll(ListDataSet dataSet) {
        System.out.println("training started");

        final int featureCount = getFeatureCount(dataSet);
        final int classCount = getClassCount(dataSet);
        final int sampleCount = dataSet.size();

        Matrix x = Matrix.Factory.zeros(dataSet.size(), featureCount + 1);
        Matrix y = Matrix.Factory.zeros(dataSet.size(), classCount);

        int i = 0;
        for (Sample s : dataSet) {
            Matrix input = s.getAsMatrix(getInputLabel()).toColumnVector(Ret.LINK);
            for (int c = 0; c < input.getColumnCount(); c++) {
                x.setAsDouble(input.getAsDouble(0, c), i, c + 1);
            }
            Matrix target = s.getAsMatrix(getTargetLabel()).toColumnVector(Ret.LINK);
            for (int c = 0; c < target.getColumnCount(); c++) {
                y.setAsDouble(target.getAsDouble(0, c), i, c);
            }
            i++;
        }

        mean = x.mean(Ret.NEW, ROW, true);

        for (int r = 0; r < x.getRowCount(); r++) {
            for (int c = 0; c < x.getColumnCount(); c++) {
                x.setAsDouble(x.getAsDouble(r, c) - mean.getAsDouble(0, c), r, c);
            }
        }

        std = x.std(Ret.NEW, ROW, true, true);

        for (int r = 0; r < x.getRowCount(); r++) {
            for (int c = 0; c < x.getColumnCount(); c++) {
                double s = std.getAsDouble(0, c);
                if (s == 0) {
                    x.setAsDouble(x.getAsDouble(r, c), r, c);
                } else {
                    x.setAsDouble(x.getAsDouble(r, c) / s, r, c);
                }
            }
        }

        for (int r = 0; r < x.getRowCount(); r++) {
            x.setAsDouble(1, r, 0);
        }

        System.out.println("data loaded");

        Matrix parameters;

        if (numberOfPrincipalComponents > 0 && numberOfPrincipalComponents < Math
                .min(x.getRowCount(), x.getColumnCount() - 1)) {
            if (sampleCount < featureCount) {
                // final Matrix xt = x.transpose();
                parameters = x.pinv(numberOfPrincipalComponents).mtimes(y);
            } else {
                final Matrix xt = x.transpose();
                parameters = xt.mtimes(x).pinv(numberOfPrincipalComponents).mtimes(xt).mtimes(y);
            }
        } else {
            if (sampleCount < featureCount) {
                parameters = x.pinv().mtimes(y);
            } else {
                final Matrix xt = x.transpose();
                parameters = xt.mtimes(x).pinv().mtimes(xt).mtimes(y);
            }
        }

        System.out.println("training finished");

        setParameterMatrix(parameters);
    }

    public void setParameterMatrix(Matrix parameters) {
        getVariableMap().setMatrix(PARAMETERS, parameters);
    }

    public void reset() {
        getParameterVariable().clear();
    }

    public Regressor emptyCopy() {
        LinearRegression lr = new LinearRegression(numberOfPrincipalComponents);
        lr.setInputLabel(getInputLabel());
        lr.setTargetLabel(getTargetLabel());
        return lr;
    }

    @Override
    public void trainBatch(Collection<Sample> samples) {
        throw new NotImplementedException();
    }

}
