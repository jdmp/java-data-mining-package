/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.jdmp.core.variable.Variable;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class CreateIris extends AbstractAlgorithm {
	private static final long serialVersionUID = 5005634034611593171L;

	public static final String DESCRIPTION = "generates the Iris flower DataSet";

	public CreateIris(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
		addVariableKey(TARGET);
		setEdgeLabel(TARGET, "Target");
		setEdgeDirection(TARGET, EdgeDirection.Outgoing);
		setVariables(variables);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) throws MatrixException {
		Map<String, Object> result = new HashMap<String, Object>();

		DataSet iris = DataSetFactory.classificationDataSet();
		iris.setLabel("Iris flower data set");
		iris.getVariables().setObject(Sample.URL, "http://archive.ics.uci.edu/ml/datasets/Iris");
		iris
				.setDescription("Fisher's Iris data set is a multivariate data set introduced by Sir Ronald Aylmer Fisher (1936) as an example of discriminant analysis.");

		Sample s0 = SampleFactory.emptySample();
		s0.getVariables().setObject("SepalLength", 5.1);
		s0.getVariables().setObject("SepalWidth", 3.5);
		s0.getVariables().setObject("PetalLength", 1.4);
		s0.getVariables().setObject("PetalWidth", 0.2);
		Matrix input0 = MatrixFactory.linkToArray(new double[] { 5.1, 3.5, 1.4, 0.2 }).transpose();
		s0.getVariables().setMatrix(INPUT, input0);
		Matrix output0 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s0.getVariables().setMatrix(TARGET, output0);
		s0.setLabel("Iris-setosa");
		s0.setId("iris-0");
		iris.getSamples().add(s0);

		Sample s1 = SampleFactory.emptySample();
		s1.getVariables().setObject("SepalLength", 4.9);
		s1.getVariables().setObject("SepalWidth", 3.0);
		s1.getVariables().setObject("PetalLength", 1.4);
		s1.getVariables().setObject("PetalWidth", 0.2);
		Matrix input1 = MatrixFactory.linkToArray(new double[] { 4.9, 3.0, 1.4, 0.2 }).transpose();
		s1.getVariables().setMatrix(INPUT, input1);
		Matrix output1 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s1.getVariables().setMatrix(TARGET, output1);
		s1.setLabel("Iris-setosa");
		s1.setId("iris-1");
		iris.getSamples().add(s1);

		Sample s2 = SampleFactory.emptySample();
		s2.getVariables().setObject("SepalLength", 4.7);
		s2.getVariables().setObject("SepalWidth", 3.2);
		s2.getVariables().setObject("PetalLength", 1.3);
		s2.getVariables().setObject("PetalWidth", 0.2);
		Matrix input2 = MatrixFactory.linkToArray(new double[] { 4.7, 3.2, 1.3, 0.2 }).transpose();
		s2.getVariables().setMatrix(INPUT, input2);
		Matrix output2 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s2.getVariables().setMatrix(TARGET, output2);
		s2.setLabel("Iris-setosa");
		s2.setId("iris-2");
		iris.getSamples().add(s2);

		Sample s3 = SampleFactory.emptySample();
		s3.getVariables().setObject("SepalLength", 4.6);
		s3.getVariables().setObject("SepalWidth", 3.1);
		s3.getVariables().setObject("PetalLength", 1.5);
		s3.getVariables().setObject("PetalWidth", 0.2);
		Matrix input3 = MatrixFactory.linkToArray(new double[] { 4.6, 3.1, 1.5, 0.2 }).transpose();
		s3.getVariables().setMatrix(INPUT, input3);
		Matrix output3 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s3.getVariables().setMatrix(TARGET, output3);
		s3.setLabel("Iris-setosa");
		s3.setId("iris-3");
		iris.getSamples().add(s3);

		Sample s4 = SampleFactory.emptySample();
		s4.getVariables().setObject("SepalLength", 5.0);
		s4.getVariables().setObject("SepalWidth", 3.6);
		s4.getVariables().setObject("PetalLength", 1.4);
		s4.getVariables().setObject("PetalWidth", 0.2);
		Matrix input4 = MatrixFactory.linkToArray(new double[] { 5.0, 3.6, 1.4, 0.2 }).transpose();
		s4.getVariables().setMatrix(INPUT, input4);
		Matrix output4 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s4.getVariables().setMatrix(TARGET, output4);
		s4.setLabel("Iris-setosa");
		s4.setId("iris-4");
		iris.getSamples().add(s4);

		Sample s5 = SampleFactory.emptySample();
		s5.getVariables().setObject("SepalLength", 5.4);
		s5.getVariables().setObject("SepalWidth", 3.9);
		s5.getVariables().setObject("PetalLength", 1.7);
		s5.getVariables().setObject("PetalWidth", 0.4);
		Matrix input5 = MatrixFactory.linkToArray(new double[] { 5.4, 3.9, 1.7, 0.4 }).transpose();
		s5.getVariables().setMatrix(INPUT, input5);
		Matrix output5 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s5.getVariables().setMatrix(TARGET, output5);
		s5.setLabel("Iris-setosa");
		s5.setId("iris-5");
		iris.getSamples().add(s5);

		Sample s6 = SampleFactory.emptySample();
		s6.getVariables().setObject("SepalLength", 4.6);
		s6.getVariables().setObject("SepalWidth", 3.4);
		s6.getVariables().setObject("PetalLength", 1.4);
		s6.getVariables().setObject("PetalWidth", 0.3);
		Matrix input6 = MatrixFactory.linkToArray(new double[] { 4.6, 3.4, 1.4, 0.3 }).transpose();
		s6.getVariables().setMatrix(INPUT, input6);
		Matrix output6 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s6.getVariables().setMatrix(TARGET, output6);
		s6.setLabel("Iris-setosa");
		s6.setId("iris-6");
		iris.getSamples().add(s6);

		Sample s7 = SampleFactory.emptySample();
		s7.getVariables().setObject("SepalLength", 5.0);
		s7.getVariables().setObject("SepalWidth", 3.4);
		s7.getVariables().setObject("PetalLength", 1.5);
		s7.getVariables().setObject("PetalWidth", 0.2);
		Matrix input7 = MatrixFactory.linkToArray(new double[] { 5.0, 3.4, 1.5, 0.2 }).transpose();
		s7.getVariables().setMatrix(INPUT, input7);
		Matrix output7 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s7.getVariables().setMatrix(TARGET, output7);
		s7.setLabel("Iris-setosa");
		s7.setId("iris-7");
		iris.getSamples().add(s7);

		Sample s8 = SampleFactory.emptySample();
		s8.getVariables().setObject("SepalLength", 4.4);
		s8.getVariables().setObject("SepalWidth", 2.9);
		s8.getVariables().setObject("PetalLength", 1.4);
		s8.getVariables().setObject("PetalWidth", 0.2);
		Matrix input8 = MatrixFactory.linkToArray(new double[] { 4.4, 2.9, 1.4, 0.2 }).transpose();
		s8.getVariables().setMatrix(INPUT, input8);
		Matrix output8 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s8.getVariables().setMatrix(TARGET, output8);
		s8.setLabel("Iris-setosa");
		s8.setId("iris-8");
		iris.getSamples().add(s8);

		Sample s9 = SampleFactory.emptySample();
		s9.getVariables().setObject("SepalLength", 4.9);
		s9.getVariables().setObject("SepalWidth", 3.1);
		s9.getVariables().setObject("PetalLength", 1.5);
		s9.getVariables().setObject("PetalWidth", 0.1);
		Matrix input9 = MatrixFactory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 }).transpose();
		s9.getVariables().setMatrix(INPUT, input9);
		Matrix output9 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s9.getVariables().setMatrix(TARGET, output9);
		s9.setLabel("Iris-setosa");
		s9.setId("iris-9");
		iris.getSamples().add(s9);

		Sample s10 = SampleFactory.emptySample();
		s10.getVariables().setObject("SepalLength", 5.4);
		s10.getVariables().setObject("SepalWidth", 3.7);
		s10.getVariables().setObject("PetalLength", 1.5);
		s10.getVariables().setObject("PetalWidth", 0.2);
		Matrix input10 = MatrixFactory.linkToArray(new double[] { 5.4, 3.7, 1.5, 0.2 }).transpose();
		s10.getVariables().setMatrix(INPUT, input10);
		Matrix output10 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s10.getVariables().setMatrix(TARGET, output10);
		s10.setLabel("Iris-setosa");
		s10.setId("iris-10");
		iris.getSamples().add(s10);

		Sample s11 = SampleFactory.emptySample();
		s11.getVariables().setObject("SepalLength", 4.8);
		s11.getVariables().setObject("SepalWidth", 3.4);
		s11.getVariables().setObject("PetalLength", 1.6);
		s11.getVariables().setObject("PetalWidth", 0.2);
		Matrix input11 = MatrixFactory.linkToArray(new double[] { 4.8, 3.4, 1.6, 0.2 }).transpose();
		s11.getVariables().setMatrix(INPUT, input11);
		Matrix output11 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s11.getVariables().setMatrix(TARGET, output11);
		s11.setLabel("Iris-setosa");
		s11.setId("iris-11");
		iris.getSamples().add(s11);

		Sample s12 = SampleFactory.emptySample();
		s12.getVariables().setObject("SepalLength", 4.8);
		s12.getVariables().setObject("SepalWidth", 3.0);
		s12.getVariables().setObject("PetalLength", 1.4);
		s12.getVariables().setObject("PetalWidth", 0.1);
		Matrix input12 = MatrixFactory.linkToArray(new double[] { 4.8, 3.0, 1.4, 0.1 }).transpose();
		s12.getVariables().setMatrix(INPUT, input12);
		Matrix output12 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s12.getVariables().setMatrix(TARGET, output12);
		s12.setLabel("Iris-setosa");
		s12.setId("iris-12");
		iris.getSamples().add(s12);

		Sample s13 = SampleFactory.emptySample();
		s13.getVariables().setObject("SepalLength", 4.3);
		s13.getVariables().setObject("SepalWidth", 3.0);
		s13.getVariables().setObject("PetalLength", 1.1);
		s13.getVariables().setObject("PetalWidth", 0.1);
		Matrix input13 = MatrixFactory.linkToArray(new double[] { 4.3, 3.0, 1.1, 0.1 }).transpose();
		s13.getVariables().setMatrix(INPUT, input13);
		Matrix output13 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s13.getVariables().setMatrix(TARGET, output13);
		s13.setLabel("Iris-setosa");
		s13.setId("iris-13");
		iris.getSamples().add(s13);

		Sample s14 = SampleFactory.emptySample();
		s14.getVariables().setObject("SepalLength", 5.8);
		s14.getVariables().setObject("SepalWidth", 4.0);
		s14.getVariables().setObject("PetalLength", 1.2);
		s14.getVariables().setObject("PetalWidth", 0.2);
		Matrix input14 = MatrixFactory.linkToArray(new double[] { 5.8, 4.0, 1.2, 0.2 }).transpose();
		s14.getVariables().setMatrix(INPUT, input14);
		Matrix output14 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s14.getVariables().setMatrix(TARGET, output14);
		s14.setLabel("Iris-setosa");
		s14.setId("iris-14");
		iris.getSamples().add(s14);

		Sample s15 = SampleFactory.emptySample();
		s15.getVariables().setObject("SepalLength", 5.7);
		s15.getVariables().setObject("SepalWidth", 4.4);
		s15.getVariables().setObject("PetalLength", 1.5);
		s15.getVariables().setObject("PetalWidth", 0.4);
		Matrix input15 = MatrixFactory.linkToArray(new double[] { 5.7, 4.4, 1.5, 0.4 }).transpose();
		s15.getVariables().setMatrix(INPUT, input15);
		Matrix output15 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s15.getVariables().setMatrix(TARGET, output15);
		s15.setLabel("Iris-setosa");
		s15.setId("iris-15");
		iris.getSamples().add(s15);

		Sample s16 = SampleFactory.emptySample();
		s16.getVariables().setObject("SepalLength", 5.4);
		s16.getVariables().setObject("SepalWidth", 3.9);
		s16.getVariables().setObject("PetalLength", 1.3);
		s16.getVariables().setObject("PetalWidth", 0.4);
		Matrix input16 = MatrixFactory.linkToArray(new double[] { 5.4, 3.9, 1.3, 0.4 }).transpose();
		s16.getVariables().setMatrix(INPUT, input16);
		Matrix output16 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s16.getVariables().setMatrix(TARGET, output16);
		s16.setLabel("Iris-setosa");
		s16.setId("iris-16");
		iris.getSamples().add(s16);

		Sample s17 = SampleFactory.emptySample();
		s17.getVariables().setObject("SepalLength", 5.1);
		s17.getVariables().setObject("SepalWidth", 3.5);
		s17.getVariables().setObject("PetalLength", 1.4);
		s17.getVariables().setObject("PetalWidth", 0.3);
		Matrix input17 = MatrixFactory.linkToArray(new double[] { 5.1, 3.5, 1.4, 0.3 }).transpose();
		s17.getVariables().setMatrix(INPUT, input17);
		Matrix output17 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s17.getVariables().setMatrix(TARGET, output17);
		s17.setLabel("Iris-setosa");
		s17.setId("iris-17");
		iris.getSamples().add(s17);

		Sample s18 = SampleFactory.emptySample();
		s18.getVariables().setObject("SepalLength", 5.7);
		s18.getVariables().setObject("SepalWidth", 3.8);
		s18.getVariables().setObject("PetalLength", 1.7);
		s18.getVariables().setObject("PetalWidth", 0.3);
		Matrix input18 = MatrixFactory.linkToArray(new double[] { 5.7, 3.8, 1.7, 0.3 }).transpose();
		s18.getVariables().setMatrix(INPUT, input18);
		Matrix output18 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s18.getVariables().setMatrix(TARGET, output18);
		s18.setLabel("Iris-setosa");
		s18.setId("iris-18");
		iris.getSamples().add(s18);

		Sample s19 = SampleFactory.emptySample();
		s19.getVariables().setObject("SepalLength", 5.1);
		s19.getVariables().setObject("SepalWidth", 3.8);
		s19.getVariables().setObject("PetalLength", 1.5);
		s19.getVariables().setObject("PetalWidth", 0.3);
		Matrix input19 = MatrixFactory.linkToArray(new double[] { 5.1, 3.8, 1.5, 0.3 }).transpose();
		s19.getVariables().setMatrix(INPUT, input19);
		Matrix output19 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s19.getVariables().setMatrix(TARGET, output19);
		s19.setLabel("Iris-setosa");
		s19.setId("iris-19");
		iris.getSamples().add(s19);

		Sample s20 = SampleFactory.emptySample();
		s20.getVariables().setObject("SepalLength", 5.4);
		s20.getVariables().setObject("SepalWidth", 3.4);
		s20.getVariables().setObject("PetalLength", 1.7);
		s20.getVariables().setObject("PetalWidth", 0.2);
		Matrix input20 = MatrixFactory.linkToArray(new double[] { 5.4, 3.4, 1.7, 0.2 }).transpose();
		s20.getVariables().setMatrix(INPUT, input20);
		Matrix output20 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s20.getVariables().setMatrix(TARGET, output20);
		s20.setLabel("Iris-setosa");
		s20.setId("iris-20");
		iris.getSamples().add(s20);

		Sample s21 = SampleFactory.emptySample();
		s21.getVariables().setObject("SepalLength", 5.1);
		s21.getVariables().setObject("SepalWidth", 3.7);
		s21.getVariables().setObject("PetalLength", 1.5);
		s21.getVariables().setObject("PetalWidth", 0.4);
		Matrix input21 = MatrixFactory.linkToArray(new double[] { 5.1, 3.7, 1.5, 0.4 }).transpose();
		s21.getVariables().setMatrix(INPUT, input21);
		Matrix output21 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s21.getVariables().setMatrix(TARGET, output21);
		s21.setLabel("Iris-setosa");
		s21.setId("iris-21");
		iris.getSamples().add(s21);

		Sample s22 = SampleFactory.emptySample();
		s22.getVariables().setObject("SepalLength", 4.6);
		s22.getVariables().setObject("SepalWidth", 3.6);
		s22.getVariables().setObject("PetalLength", 1.0);
		s22.getVariables().setObject("PetalWidth", 0.2);
		Matrix input22 = MatrixFactory.linkToArray(new double[] { 4.6, 3.6, 1.0, 0.2 }).transpose();
		s22.getVariables().setMatrix(INPUT, input22);
		Matrix output22 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s22.getVariables().setMatrix(TARGET, output22);
		s22.setLabel("Iris-setosa");
		s22.setId("iris-22");
		iris.getSamples().add(s22);

		Sample s23 = SampleFactory.emptySample();
		s23.getVariables().setObject("SepalLength", 5.1);
		s23.getVariables().setObject("SepalWidth", 3.3);
		s23.getVariables().setObject("PetalLength", 1.7);
		s23.getVariables().setObject("PetalWidth", 0.5);
		Matrix input23 = MatrixFactory.linkToArray(new double[] { 5.1, 3.3, 1.7, 0.5 }).transpose();
		s23.getVariables().setMatrix(INPUT, input23);
		Matrix output23 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s23.getVariables().setMatrix(TARGET, output23);
		s23.setLabel("Iris-setosa");
		s23.setId("iris-23");
		iris.getSamples().add(s23);

		Sample s24 = SampleFactory.emptySample();
		s24.getVariables().setObject("SepalLength", 4.8);
		s24.getVariables().setObject("SepalWidth", 3.4);
		s24.getVariables().setObject("PetalLength", 1.9);
		s24.getVariables().setObject("PetalWidth", 0.2);
		Matrix input24 = MatrixFactory.linkToArray(new double[] { 4.8, 3.4, 1.9, 0.2 }).transpose();
		s24.getVariables().setMatrix(INPUT, input24);
		Matrix output24 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s24.getVariables().setMatrix(TARGET, output24);
		s24.setLabel("Iris-setosa");
		s24.setId("iris-24");
		iris.getSamples().add(s24);

		Sample s25 = SampleFactory.emptySample();
		s25.getVariables().setObject("SepalLength", 5.0);
		s25.getVariables().setObject("SepalWidth", 3.0);
		s25.getVariables().setObject("PetalLength", 1.6);
		s25.getVariables().setObject("PetalWidth", 0.2);
		Matrix input25 = MatrixFactory.linkToArray(new double[] { 5.0, 3.0, 1.6, 0.2 }).transpose();
		s25.getVariables().setMatrix(INPUT, input25);
		Matrix output25 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s25.getVariables().setMatrix(TARGET, output25);
		s25.setLabel("Iris-setosa");
		s25.setId("iris-25");
		iris.getSamples().add(s25);

		Sample s26 = SampleFactory.emptySample();
		s26.getVariables().setObject("SepalLength", 5.0);
		s26.getVariables().setObject("SepalWidth", 3.4);
		s26.getVariables().setObject("PetalLength", 1.6);
		s26.getVariables().setObject("PetalWidth", 0.4);
		Matrix input26 = MatrixFactory.linkToArray(new double[] { 5.0, 3.4, 1.6, 0.4 }).transpose();
		s26.getVariables().setMatrix(INPUT, input26);
		Matrix output26 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s26.getVariables().setMatrix(TARGET, output26);
		s26.setLabel("Iris-setosa");
		s26.setId("iris-26");
		iris.getSamples().add(s26);

		Sample s27 = SampleFactory.emptySample();
		s27.getVariables().setObject("SepalLength", 5.2);
		s27.getVariables().setObject("SepalWidth", 3.5);
		s27.getVariables().setObject("PetalLength", 1.5);
		s27.getVariables().setObject("PetalWidth", 0.2);
		Matrix input27 = MatrixFactory.linkToArray(new double[] { 5.2, 3.5, 1.5, 0.2 }).transpose();
		s27.getVariables().setMatrix(INPUT, input27);
		Matrix output27 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s27.getVariables().setMatrix(TARGET, output27);
		s27.setLabel("Iris-setosa");
		s27.setId("iris-27");
		iris.getSamples().add(s27);

		Sample s28 = SampleFactory.emptySample();
		s28.getVariables().setObject("SepalLength", 5.2);
		s28.getVariables().setObject("SepalWidth", 3.4);
		s28.getVariables().setObject("PetalLength", 1.4);
		s28.getVariables().setObject("PetalWidth", 0.2);
		Matrix input28 = MatrixFactory.linkToArray(new double[] { 5.2, 3.4, 1.4, 0.2 }).transpose();
		s28.getVariables().setMatrix(INPUT, input28);
		Matrix output28 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s28.getVariables().setMatrix(TARGET, output28);
		s28.setLabel("Iris-setosa");
		s28.setId("iris-28");
		iris.getSamples().add(s28);

		Sample s29 = SampleFactory.emptySample();
		s29.getVariables().setObject("SepalLength", 4.7);
		s29.getVariables().setObject("SepalWidth", 3.2);
		s29.getVariables().setObject("PetalLength", 1.6);
		s29.getVariables().setObject("PetalWidth", 0.2);
		Matrix input29 = MatrixFactory.linkToArray(new double[] { 4.7, 3.2, 1.6, 0.2 }).transpose();
		s29.getVariables().setMatrix(INPUT, input29);
		Matrix output29 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s29.getVariables().setMatrix(TARGET, output29);
		s29.setLabel("Iris-setosa");
		s29.setId("iris-29");
		iris.getSamples().add(s29);

		Sample s30 = SampleFactory.emptySample();
		s30.getVariables().setObject("SepalLength", 4.8);
		s30.getVariables().setObject("SepalWidth", 3.1);
		s30.getVariables().setObject("PetalLength", 1.6);
		s30.getVariables().setObject("PetalWidth", 0.2);
		Matrix input30 = MatrixFactory.linkToArray(new double[] { 4.8, 3.1, 1.6, 0.2 }).transpose();
		s30.getVariables().setMatrix(INPUT, input30);
		Matrix output30 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s30.getVariables().setMatrix(TARGET, output30);
		s30.setLabel("Iris-setosa");
		s30.setId("iris-30");
		iris.getSamples().add(s30);

		Sample s31 = SampleFactory.emptySample();
		s31.getVariables().setObject("SepalLength", 5.4);
		s31.getVariables().setObject("SepalWidth", 3.4);
		s31.getVariables().setObject("PetalLength", 1.5);
		s31.getVariables().setObject("PetalWidth", 0.4);
		Matrix input31 = MatrixFactory.linkToArray(new double[] { 5.4, 3.4, 1.5, 0.4 }).transpose();
		s31.getVariables().setMatrix(INPUT, input31);
		Matrix output31 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s31.getVariables().setMatrix(TARGET, output31);
		s31.setLabel("Iris-setosa");
		s31.setId("iris-31");
		iris.getSamples().add(s31);

		Sample s32 = SampleFactory.emptySample();
		s32.getVariables().setObject("SepalLength", 5.2);
		s32.getVariables().setObject("SepalWidth", 4.1);
		s32.getVariables().setObject("PetalLength", 1.5);
		s32.getVariables().setObject("PetalWidth", 0.1);
		Matrix input32 = MatrixFactory.linkToArray(new double[] { 5.2, 4.1, 1.5, 0.1 }).transpose();
		s32.getVariables().setMatrix(INPUT, input32);
		Matrix output32 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s32.getVariables().setMatrix(TARGET, output32);
		s32.setLabel("Iris-setosa");
		s32.setId("iris-32");
		iris.getSamples().add(s32);

		Sample s33 = SampleFactory.emptySample();
		s33.getVariables().setObject("SepalLength", 5.5);
		s33.getVariables().setObject("SepalWidth", 4.2);
		s33.getVariables().setObject("PetalLength", 1.4);
		s33.getVariables().setObject("PetalWidth", 0.2);
		Matrix input33 = MatrixFactory.linkToArray(new double[] { 5.5, 4.2, 1.4, 0.2 }).transpose();
		s33.getVariables().setMatrix(INPUT, input33);
		Matrix output33 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s33.getVariables().setMatrix(TARGET, output33);
		s33.setLabel("Iris-setosa");
		s33.setId("iris-33");
		iris.getSamples().add(s33);

		Sample s34 = SampleFactory.emptySample();
		s34.getVariables().setObject("SepalLength", 4.9);
		s34.getVariables().setObject("SepalWidth", 3.1);
		s34.getVariables().setObject("PetalLength", 1.5);
		s34.getVariables().setObject("PetalWidth", 0.1);
		Matrix input34 = MatrixFactory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 }).transpose();
		s34.getVariables().setMatrix(INPUT, input34);
		Matrix output34 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s34.getVariables().setMatrix(TARGET, output34);
		s34.setLabel("Iris-setosa");
		s34.setId("iris-34");
		iris.getSamples().add(s34);

		Sample s35 = SampleFactory.emptySample();
		s35.getVariables().setObject("SepalLength", 5.0);
		s35.getVariables().setObject("SepalWidth", 3.2);
		s35.getVariables().setObject("PetalLength", 1.2);
		s35.getVariables().setObject("PetalWidth", 0.2);
		Matrix input35 = MatrixFactory.linkToArray(new double[] { 5.0, 3.2, 1.2, 0.2 }).transpose();
		s35.getVariables().setMatrix(INPUT, input35);
		Matrix output35 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s35.getVariables().setMatrix(TARGET, output35);
		s35.setLabel("Iris-setosa");
		s35.setId("iris-35");
		iris.getSamples().add(s35);

		Sample s36 = SampleFactory.emptySample();
		s36.getVariables().setObject("SepalLength", 5.5);
		s36.getVariables().setObject("SepalWidth", 3.5);
		s36.getVariables().setObject("PetalLength", 1.3);
		s36.getVariables().setObject("PetalWidth", 0.2);
		Matrix input36 = MatrixFactory.linkToArray(new double[] { 5.5, 3.5, 1.3, 0.2 }).transpose();
		s36.getVariables().setMatrix(INPUT, input36);
		Matrix output36 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s36.getVariables().setMatrix(TARGET, output36);
		s36.setLabel("Iris-setosa");
		s36.setId("iris-36");
		iris.getSamples().add(s36);

		Sample s37 = SampleFactory.emptySample();
		s37.getVariables().setObject("SepalLength", 4.9);
		s37.getVariables().setObject("SepalWidth", 3.1);
		s37.getVariables().setObject("PetalLength", 1.5);
		s37.getVariables().setObject("PetalWidth", 0.1);
		Matrix input37 = MatrixFactory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 }).transpose();
		s37.getVariables().setMatrix(INPUT, input37);
		Matrix output37 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s37.getVariables().setMatrix(TARGET, output37);
		s37.setLabel("Iris-setosa");
		s37.setId("iris-37");
		iris.getSamples().add(s37);

		Sample s38 = SampleFactory.emptySample();
		s38.getVariables().setObject("SepalLength", 4.4);
		s38.getVariables().setObject("SepalWidth", 3.0);
		s38.getVariables().setObject("PetalLength", 1.3);
		s38.getVariables().setObject("PetalWidth", 0.2);
		Matrix input38 = MatrixFactory.linkToArray(new double[] { 4.4, 3.0, 1.3, 0.2 }).transpose();
		s38.getVariables().setMatrix(INPUT, input38);
		Matrix output38 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s38.getVariables().setMatrix(TARGET, output38);
		s38.setLabel("Iris-setosa");
		s38.setId("iris-38");
		iris.getSamples().add(s38);

		Sample s39 = SampleFactory.emptySample();
		s39.getVariables().setObject("SepalLength", 5.1);
		s39.getVariables().setObject("SepalWidth", 3.4);
		s39.getVariables().setObject("PetalLength", 1.5);
		s39.getVariables().setObject("PetalWidth", 0.2);
		Matrix input39 = MatrixFactory.linkToArray(new double[] { 5.1, 3.4, 1.5, 0.2 }).transpose();
		s39.getVariables().setMatrix(INPUT, input39);
		Matrix output39 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s39.getVariables().setMatrix(TARGET, output39);
		s39.setLabel("Iris-setosa");
		s39.setId("iris-39");
		iris.getSamples().add(s39);

		Sample s40 = SampleFactory.emptySample();
		s40.getVariables().setObject("SepalLength", 5.0);
		s40.getVariables().setObject("SepalWidth", 3.5);
		s40.getVariables().setObject("PetalLength", 1.3);
		s40.getVariables().setObject("PetalWidth", 0.3);
		Matrix input40 = MatrixFactory.linkToArray(new double[] { 5.0, 3.5, 1.3, 0.3 }).transpose();
		s40.getVariables().setMatrix(INPUT, input40);
		Matrix output40 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s40.getVariables().setMatrix(TARGET, output40);
		s40.setLabel("Iris-setosa");
		s40.setId("iris-40");
		iris.getSamples().add(s40);

		Sample s41 = SampleFactory.emptySample();
		s41.getVariables().setObject("SepalLength", 4.5);
		s41.getVariables().setObject("SepalWidth", 2.3);
		s41.getVariables().setObject("PetalLength", 1.3);
		s41.getVariables().setObject("PetalWidth", 0.3);
		Matrix input41 = MatrixFactory.linkToArray(new double[] { 4.5, 2.3, 1.3, 0.3 }).transpose();
		s41.getVariables().setMatrix(INPUT, input41);
		Matrix output41 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s41.getVariables().setMatrix(TARGET, output41);
		s41.setLabel("Iris-setosa");
		s41.setId("iris-41");
		iris.getSamples().add(s41);

		Sample s42 = SampleFactory.emptySample();
		s42.getVariables().setObject("SepalLength", 4.4);
		s42.getVariables().setObject("SepalWidth", 3.2);
		s42.getVariables().setObject("PetalLength", 1.3);
		s42.getVariables().setObject("PetalWidth", 0.2);
		Matrix input42 = MatrixFactory.linkToArray(new double[] { 4.4, 3.2, 1.3, 0.2 }).transpose();
		s42.getVariables().setMatrix(INPUT, input42);
		Matrix output42 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s42.getVariables().setMatrix(TARGET, output42);
		s42.setLabel("Iris-setosa");
		s42.setId("iris-42");
		iris.getSamples().add(s42);

		Sample s43 = SampleFactory.emptySample();
		s43.getVariables().setObject("SepalLength", 5.0);
		s43.getVariables().setObject("SepalWidth", 3.5);
		s43.getVariables().setObject("PetalLength", 1.6);
		s43.getVariables().setObject("PetalWidth", 0.6);
		Matrix input43 = MatrixFactory.linkToArray(new double[] { 5.0, 3.5, 1.6, 0.6 }).transpose();
		s43.getVariables().setMatrix(INPUT, input43);
		Matrix output43 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s43.getVariables().setMatrix(TARGET, output43);
		s43.setLabel("Iris-setosa");
		s43.setId("iris-43");
		iris.getSamples().add(s43);

		Sample s44 = SampleFactory.emptySample();
		s44.getVariables().setObject("SepalLength", 5.1);
		s44.getVariables().setObject("SepalWidth", 3.8);
		s44.getVariables().setObject("PetalLength", 1.9);
		s44.getVariables().setObject("PetalWidth", 0.4);
		Matrix input44 = MatrixFactory.linkToArray(new double[] { 5.1, 3.8, 1.9, 0.4 }).transpose();
		s44.getVariables().setMatrix(INPUT, input44);
		Matrix output44 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s44.getVariables().setMatrix(TARGET, output44);
		s44.setLabel("Iris-setosa");
		s44.setId("iris-44");
		iris.getSamples().add(s44);

		Sample s45 = SampleFactory.emptySample();
		s45.getVariables().setObject("SepalLength", 4.8);
		s45.getVariables().setObject("SepalWidth", 3.0);
		s45.getVariables().setObject("PetalLength", 1.4);
		s45.getVariables().setObject("PetalWidth", 0.3);
		Matrix input45 = MatrixFactory.linkToArray(new double[] { 4.8, 3.0, 1.4, 0.3 }).transpose();
		s45.getVariables().setMatrix(INPUT, input45);
		Matrix output45 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s45.getVariables().setMatrix(TARGET, output45);
		s45.setLabel("Iris-setosa");
		s45.setId("iris-45");
		iris.getSamples().add(s45);

		Sample s46 = SampleFactory.emptySample();
		s46.getVariables().setObject("SepalLength", 5.1);
		s46.getVariables().setObject("SepalWidth", 3.8);
		s46.getVariables().setObject("PetalLength", 1.6);
		s46.getVariables().setObject("PetalWidth", 0.2);
		Matrix input46 = MatrixFactory.linkToArray(new double[] { 5.1, 3.8, 1.6, 0.2 }).transpose();
		s46.getVariables().setMatrix(INPUT, input46);
		Matrix output46 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s46.getVariables().setMatrix(TARGET, output46);
		s46.setLabel("Iris-setosa");
		s46.setId("iris-46");
		iris.getSamples().add(s46);

		Sample s47 = SampleFactory.emptySample();
		s47.getVariables().setObject("SepalLength", 4.6);
		s47.getVariables().setObject("SepalWidth", 3.2);
		s47.getVariables().setObject("PetalLength", 1.4);
		s47.getVariables().setObject("PetalWidth", 0.2);
		Matrix input47 = MatrixFactory.linkToArray(new double[] { 4.6, 3.2, 1.4, 0.2 }).transpose();
		s47.getVariables().setMatrix(INPUT, input47);
		Matrix output47 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s47.getVariables().setMatrix(TARGET, output47);
		s47.setLabel("Iris-setosa");
		s47.setId("iris-47");
		iris.getSamples().add(s47);

		Sample s48 = SampleFactory.emptySample();
		s48.getVariables().setObject("SepalLength", 5.3);
		s48.getVariables().setObject("SepalWidth", 3.7);
		s48.getVariables().setObject("PetalLength", 1.5);
		s48.getVariables().setObject("PetalWidth", 0.2);
		Matrix input48 = MatrixFactory.linkToArray(new double[] { 5.3, 3.7, 1.5, 0.2 }).transpose();
		s48.getVariables().setMatrix(INPUT, input48);
		Matrix output48 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s48.getVariables().setMatrix(TARGET, output48);
		s48.setLabel("Iris-setosa");
		s48.setId("iris-48");
		iris.getSamples().add(s48);

		Sample s49 = SampleFactory.emptySample();
		s49.getVariables().setObject("SepalLength", 5.0);
		s49.getVariables().setObject("SepalWidth", 3.3);
		s49.getVariables().setObject("PetalLength", 1.4);
		s49.getVariables().setObject("PetalWidth", 0.2);
		Matrix input49 = MatrixFactory.linkToArray(new double[] { 5.0, 3.3, 1.4, 0.2 }).transpose();
		s49.getVariables().setMatrix(INPUT, input49);
		Matrix output49 = MatrixFactory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s49.getVariables().setMatrix(TARGET, output49);
		s49.setLabel("Iris-setosa");
		s49.setId("iris-49");
		iris.getSamples().add(s49);

		Sample s50 = SampleFactory.emptySample();
		s50.getVariables().setObject("SepalLength", 7.0);
		s50.getVariables().setObject("SepalWidth", 3.2);
		s50.getVariables().setObject("PetalLength", 4.7);
		s50.getVariables().setObject("PetalWidth", 1.4);
		Matrix input50 = MatrixFactory.linkToArray(new double[] { 7.0, 3.2, 4.7, 1.4 }).transpose();
		s50.getVariables().setMatrix(INPUT, input50);
		Matrix output50 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s50.getVariables().setMatrix(TARGET, output50);
		s50.setLabel("Iris-versicolor");
		s50.setId("iris-50");
		iris.getSamples().add(s50);

		Sample s51 = SampleFactory.emptySample();
		s51.getVariables().setObject("SepalLength", 6.4);
		s51.getVariables().setObject("SepalWidth", 3.2);
		s51.getVariables().setObject("PetalLength", 4.5);
		s51.getVariables().setObject("PetalWidth", 1.5);
		Matrix input51 = MatrixFactory.linkToArray(new double[] { 6.4, 3.2, 4.5, 1.5 }).transpose();
		s51.getVariables().setMatrix(INPUT, input51);
		Matrix output51 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s51.getVariables().setMatrix(TARGET, output51);
		s51.setLabel("Iris-versicolor");
		s51.setId("iris-51");
		iris.getSamples().add(s51);

		Sample s52 = SampleFactory.emptySample();
		s52.getVariables().setObject("SepalLength", 6.9);
		s52.getVariables().setObject("SepalWidth", 3.1);
		s52.getVariables().setObject("PetalLength", 4.9);
		s52.getVariables().setObject("PetalWidth", 1.5);
		Matrix input52 = MatrixFactory.linkToArray(new double[] { 6.9, 3.1, 4.9, 1.5 }).transpose();
		s52.getVariables().setMatrix(INPUT, input52);
		Matrix output52 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s52.getVariables().setMatrix(TARGET, output52);
		s52.setLabel("Iris-versicolor");
		s52.setId("iris-52");
		iris.getSamples().add(s52);

		Sample s53 = SampleFactory.emptySample();
		s53.getVariables().setObject("SepalLength", 5.5);
		s53.getVariables().setObject("SepalWidth", 2.3);
		s53.getVariables().setObject("PetalLength", 4.0);
		s53.getVariables().setObject("PetalWidth", 1.3);
		Matrix input53 = MatrixFactory.linkToArray(new double[] { 5.5, 2.3, 4.0, 1.3 }).transpose();
		s53.getVariables().setMatrix(INPUT, input53);
		Matrix output53 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s53.getVariables().setMatrix(TARGET, output53);
		s53.setLabel("Iris-versicolor");
		s53.setId("iris-53");
		iris.getSamples().add(s53);

		Sample s54 = SampleFactory.emptySample();
		s54.getVariables().setObject("SepalLength", 6.5);
		s54.getVariables().setObject("SepalWidth", 2.8);
		s54.getVariables().setObject("PetalLength", 4.6);
		s54.getVariables().setObject("PetalWidth", 1.5);
		Matrix input54 = MatrixFactory.linkToArray(new double[] { 6.5, 2.8, 4.6, 1.5 }).transpose();
		s54.getVariables().setMatrix(INPUT, input54);
		Matrix output54 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s54.getVariables().setMatrix(TARGET, output54);
		s54.setLabel("Iris-versicolor");
		s54.setId("iris-54");
		iris.getSamples().add(s54);

		Sample s55 = SampleFactory.emptySample();
		s55.getVariables().setObject("SepalLength", 5.7);
		s55.getVariables().setObject("SepalWidth", 2.8);
		s55.getVariables().setObject("PetalLength", 4.5);
		s55.getVariables().setObject("PetalWidth", 1.3);
		Matrix input55 = MatrixFactory.linkToArray(new double[] { 5.7, 2.8, 4.5, 1.3 }).transpose();
		s55.getVariables().setMatrix(INPUT, input55);
		Matrix output55 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s55.getVariables().setMatrix(TARGET, output55);
		s55.setLabel("Iris-versicolor");
		s55.setId("iris-55");
		iris.getSamples().add(s55);

		Sample s56 = SampleFactory.emptySample();
		s56.getVariables().setObject("SepalLength", 6.3);
		s56.getVariables().setObject("SepalWidth", 3.3);
		s56.getVariables().setObject("PetalLength", 4.7);
		s56.getVariables().setObject("PetalWidth", 1.6);
		Matrix input56 = MatrixFactory.linkToArray(new double[] { 6.3, 3.3, 4.7, 1.6 }).transpose();
		s56.getVariables().setMatrix(INPUT, input56);
		Matrix output56 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s56.getVariables().setMatrix(TARGET, output56);
		s56.setLabel("Iris-versicolor");
		s56.setId("iris-56");
		iris.getSamples().add(s56);

		Sample s57 = SampleFactory.emptySample();
		s57.getVariables().setObject("SepalLength", 4.9);
		s57.getVariables().setObject("SepalWidth", 2.4);
		s57.getVariables().setObject("PetalLength", 3.3);
		s57.getVariables().setObject("PetalWidth", 1.0);
		Matrix input57 = MatrixFactory.linkToArray(new double[] { 4.9, 2.4, 3.3, 1.0 }).transpose();
		s57.getVariables().setMatrix(INPUT, input57);
		Matrix output57 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s57.getVariables().setMatrix(TARGET, output57);
		s57.setLabel("Iris-versicolor");
		s57.setId("iris-57");
		iris.getSamples().add(s57);

		Sample s58 = SampleFactory.emptySample();
		s58.getVariables().setObject("SepalLength", 6.6);
		s58.getVariables().setObject("SepalWidth", 2.9);
		s58.getVariables().setObject("PetalLength", 4.6);
		s58.getVariables().setObject("PetalWidth", 1.3);
		Matrix input58 = MatrixFactory.linkToArray(new double[] { 6.6, 2.9, 4.6, 1.3 }).transpose();
		s58.getVariables().setMatrix(INPUT, input58);
		Matrix output58 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s58.getVariables().setMatrix(TARGET, output58);
		s58.setLabel("Iris-versicolor");
		s58.setId("iris-58");
		iris.getSamples().add(s58);

		Sample s59 = SampleFactory.emptySample();
		s59.getVariables().setObject("SepalLength", 5.2);
		s59.getVariables().setObject("SepalWidth", 2.7);
		s59.getVariables().setObject("PetalLength", 3.9);
		s59.getVariables().setObject("PetalWidth", 1.4);
		Matrix input59 = MatrixFactory.linkToArray(new double[] { 5.2, 2.7, 3.9, 1.4 }).transpose();
		s59.getVariables().setMatrix(INPUT, input59);
		Matrix output59 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s59.getVariables().setMatrix(TARGET, output59);
		s59.setLabel("Iris-versicolor");
		s59.setId("iris-59");
		iris.getSamples().add(s59);

		Sample s60 = SampleFactory.emptySample();
		s60.getVariables().setObject("SepalLength", 5.0);
		s60.getVariables().setObject("SepalWidth", 2.0);
		s60.getVariables().setObject("PetalLength", 3.5);
		s60.getVariables().setObject("PetalWidth", 1.0);
		Matrix input60 = MatrixFactory.linkToArray(new double[] { 5.0, 2.0, 3.5, 1.0 }).transpose();
		s60.getVariables().setMatrix(INPUT, input60);
		Matrix output60 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s60.getVariables().setMatrix(TARGET, output60);
		s60.setLabel("Iris-versicolor");
		s60.setId("iris-60");
		iris.getSamples().add(s60);

		Sample s61 = SampleFactory.emptySample();
		s61.getVariables().setObject("SepalLength", 5.9);
		s61.getVariables().setObject("SepalWidth", 3.0);
		s61.getVariables().setObject("PetalLength", 4.2);
		s61.getVariables().setObject("PetalWidth", 1.5);
		Matrix input61 = MatrixFactory.linkToArray(new double[] { 5.9, 3.0, 4.2, 1.5 }).transpose();
		s61.getVariables().setMatrix(INPUT, input61);
		Matrix output61 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s61.getVariables().setMatrix(TARGET, output61);
		s61.setLabel("Iris-versicolor");
		s61.setId("iris-61");
		iris.getSamples().add(s61);

		Sample s62 = SampleFactory.emptySample();
		s62.getVariables().setObject("SepalLength", 6.0);
		s62.getVariables().setObject("SepalWidth", 2.2);
		s62.getVariables().setObject("PetalLength", 4.0);
		s62.getVariables().setObject("PetalWidth", 1.0);
		Matrix input62 = MatrixFactory.linkToArray(new double[] { 6.0, 2.2, 4.0, 1.0 }).transpose();
		s62.getVariables().setMatrix(INPUT, input62);
		Matrix output62 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s62.getVariables().setMatrix(TARGET, output62);
		s62.setLabel("Iris-versicolor");
		s62.setId("iris-62");
		iris.getSamples().add(s62);

		Sample s63 = SampleFactory.emptySample();
		s63.getVariables().setObject("SepalLength", 6.1);
		s63.getVariables().setObject("SepalWidth", 2.9);
		s63.getVariables().setObject("PetalLength", 4.7);
		s63.getVariables().setObject("PetalWidth", 1.4);
		Matrix input63 = MatrixFactory.linkToArray(new double[] { 6.1, 2.9, 4.7, 1.4 }).transpose();
		s63.getVariables().setMatrix(INPUT, input63);
		Matrix output63 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s63.getVariables().setMatrix(TARGET, output63);
		s63.setLabel("Iris-versicolor");
		s63.setId("iris-63");
		iris.getSamples().add(s63);

		Sample s64 = SampleFactory.emptySample();
		s64.getVariables().setObject("SepalLength", 5.6);
		s64.getVariables().setObject("SepalWidth", 2.9);
		s64.getVariables().setObject("PetalLength", 3.6);
		s64.getVariables().setObject("PetalWidth", 1.3);
		Matrix input64 = MatrixFactory.linkToArray(new double[] { 5.6, 2.9, 3.6, 1.3 }).transpose();
		s64.getVariables().setMatrix(INPUT, input64);
		Matrix output64 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s64.getVariables().setMatrix(TARGET, output64);
		s64.setLabel("Iris-versicolor");
		s64.setId("iris-64");
		iris.getSamples().add(s64);

		Sample s65 = SampleFactory.emptySample();
		s65.getVariables().setObject("SepalLength", 6.7);
		s65.getVariables().setObject("SepalWidth", 3.1);
		s65.getVariables().setObject("PetalLength", 4.4);
		s65.getVariables().setObject("PetalWidth", 1.4);
		Matrix input65 = MatrixFactory.linkToArray(new double[] { 6.7, 3.1, 4.4, 1.4 }).transpose();
		s65.getVariables().setMatrix(INPUT, input65);
		Matrix output65 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s65.getVariables().setMatrix(TARGET, output65);
		s65.setLabel("Iris-versicolor");
		s65.setId("iris-65");
		iris.getSamples().add(s65);

		Sample s66 = SampleFactory.emptySample();
		s66.getVariables().setObject("SepalLength", 5.6);
		s66.getVariables().setObject("SepalWidth", 3.0);
		s66.getVariables().setObject("PetalLength", 4.5);
		s66.getVariables().setObject("PetalWidth", 1.5);
		Matrix input66 = MatrixFactory.linkToArray(new double[] { 5.6, 3.0, 4.5, 1.5 }).transpose();
		s66.getVariables().setMatrix(INPUT, input66);
		Matrix output66 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s66.getVariables().setMatrix(TARGET, output66);
		s66.setLabel("Iris-versicolor");
		s66.setId("iris-66");
		iris.getSamples().add(s66);

		Sample s67 = SampleFactory.emptySample();
		s67.getVariables().setObject("SepalLength", 5.8);
		s67.getVariables().setObject("SepalWidth", 2.7);
		s67.getVariables().setObject("PetalLength", 4.1);
		s67.getVariables().setObject("PetalWidth", 1.0);
		Matrix input67 = MatrixFactory.linkToArray(new double[] { 5.8, 2.7, 4.1, 1.0 }).transpose();
		s67.getVariables().setMatrix(INPUT, input67);
		Matrix output67 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s67.getVariables().setMatrix(TARGET, output67);
		s67.setLabel("Iris-versicolor");
		s67.setId("iris-67");
		iris.getSamples().add(s67);

		Sample s68 = SampleFactory.emptySample();
		s68.getVariables().setObject("SepalLength", 6.2);
		s68.getVariables().setObject("SepalWidth", 2.2);
		s68.getVariables().setObject("PetalLength", 4.5);
		s68.getVariables().setObject("PetalWidth", 1.5);
		Matrix input68 = MatrixFactory.linkToArray(new double[] { 6.2, 2.2, 4.5, 1.5 }).transpose();
		s68.getVariables().setMatrix(INPUT, input68);
		Matrix output68 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s68.getVariables().setMatrix(TARGET, output68);
		s68.setLabel("Iris-versicolor");
		s68.setId("iris-68");
		iris.getSamples().add(s68);

		Sample s69 = SampleFactory.emptySample();
		s69.getVariables().setObject("SepalLength", 5.6);
		s69.getVariables().setObject("SepalWidth", 2.5);
		s69.getVariables().setObject("PetalLength", 3.9);
		s69.getVariables().setObject("PetalWidth", 1.1);
		Matrix input69 = MatrixFactory.linkToArray(new double[] { 5.6, 2.5, 3.9, 1.1 }).transpose();
		s69.getVariables().setMatrix(INPUT, input69);
		Matrix output69 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s69.getVariables().setMatrix(TARGET, output69);
		s69.setLabel("Iris-versicolor");
		s69.setId("iris-69");
		iris.getSamples().add(s69);

		Sample s70 = SampleFactory.emptySample();
		s70.getVariables().setObject("SepalLength", 5.9);
		s70.getVariables().setObject("SepalWidth", 3.2);
		s70.getVariables().setObject("PetalLength", 4.8);
		s70.getVariables().setObject("PetalWidth", 1.8);
		Matrix input70 = MatrixFactory.linkToArray(new double[] { 5.9, 3.2, 4.8, 1.8 }).transpose();
		s70.getVariables().setMatrix(INPUT, input70);
		Matrix output70 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s70.getVariables().setMatrix(TARGET, output70);
		s70.setLabel("Iris-versicolor");
		s70.setId("iris-70");
		iris.getSamples().add(s70);

		Sample s71 = SampleFactory.emptySample();
		s71.getVariables().setObject("SepalLength", 6.1);
		s71.getVariables().setObject("SepalWidth", 2.8);
		s71.getVariables().setObject("PetalLength", 4.0);
		s71.getVariables().setObject("PetalWidth", 1.3);
		Matrix input71 = MatrixFactory.linkToArray(new double[] { 6.1, 2.8, 4.0, 1.3 }).transpose();
		s71.getVariables().setMatrix(INPUT, input71);
		Matrix output71 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s71.getVariables().setMatrix(TARGET, output71);
		s71.setLabel("Iris-versicolor");
		s71.setId("iris-71");
		iris.getSamples().add(s71);

		Sample s72 = SampleFactory.emptySample();
		s72.getVariables().setObject("SepalLength", 6.3);
		s72.getVariables().setObject("SepalWidth", 2.5);
		s72.getVariables().setObject("PetalLength", 4.9);
		s72.getVariables().setObject("PetalWidth", 1.5);
		Matrix input72 = MatrixFactory.linkToArray(new double[] { 6.3, 2.5, 4.9, 1.5 }).transpose();
		s72.getVariables().setMatrix(INPUT, input72);
		Matrix output72 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s72.getVariables().setMatrix(TARGET, output72);
		s72.setLabel("Iris-versicolor");
		s72.setId("iris-72");
		iris.getSamples().add(s72);

		Sample s73 = SampleFactory.emptySample();
		s73.getVariables().setObject("SepalLength", 6.1);
		s73.getVariables().setObject("SepalWidth", 2.8);
		s73.getVariables().setObject("PetalLength", 4.7);
		s73.getVariables().setObject("PetalWidth", 1.2);
		Matrix input73 = MatrixFactory.linkToArray(new double[] { 6.1, 2.8, 4.7, 1.2 }).transpose();
		s73.getVariables().setMatrix(INPUT, input73);
		Matrix output73 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s73.getVariables().setMatrix(TARGET, output73);
		s73.setLabel("Iris-versicolor");
		s73.setId("iris-73");
		iris.getSamples().add(s73);

		Sample s74 = SampleFactory.emptySample();
		s74.getVariables().setObject("SepalLength", 6.4);
		s74.getVariables().setObject("SepalWidth", 2.9);
		s74.getVariables().setObject("PetalLength", 4.3);
		s74.getVariables().setObject("PetalWidth", 1.3);
		Matrix input74 = MatrixFactory.linkToArray(new double[] { 6.4, 2.9, 4.3, 1.3 }).transpose();
		s74.getVariables().setMatrix(INPUT, input74);
		Matrix output74 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s74.getVariables().setMatrix(TARGET, output74);
		s74.setLabel("Iris-versicolor");
		s74.setId("iris-74");
		iris.getSamples().add(s74);

		Sample s75 = SampleFactory.emptySample();
		s75.getVariables().setObject("SepalLength", 6.6);
		s75.getVariables().setObject("SepalWidth", 3.0);
		s75.getVariables().setObject("PetalLength", 4.4);
		s75.getVariables().setObject("PetalWidth", 1.4);
		Matrix input75 = MatrixFactory.linkToArray(new double[] { 6.6, 3.0, 4.4, 1.4 }).transpose();
		s75.getVariables().setMatrix(INPUT, input75);
		Matrix output75 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s75.getVariables().setMatrix(TARGET, output75);
		s75.setLabel("Iris-versicolor");
		s75.setId("iris-75");
		iris.getSamples().add(s75);

		Sample s76 = SampleFactory.emptySample();
		s76.getVariables().setObject("SepalLength", 6.8);
		s76.getVariables().setObject("SepalWidth", 2.8);
		s76.getVariables().setObject("PetalLength", 4.8);
		s76.getVariables().setObject("PetalWidth", 1.4);
		Matrix input76 = MatrixFactory.linkToArray(new double[] { 6.8, 2.8, 4.8, 1.4 }).transpose();
		s76.getVariables().setMatrix(INPUT, input76);
		Matrix output76 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s76.getVariables().setMatrix(TARGET, output76);
		s76.setLabel("Iris-versicolor");
		s76.setId("iris-76");
		iris.getSamples().add(s76);

		Sample s77 = SampleFactory.emptySample();
		s77.getVariables().setObject("SepalLength", 6.7);
		s77.getVariables().setObject("SepalWidth", 3.0);
		s77.getVariables().setObject("PetalLength", 5.0);
		s77.getVariables().setObject("PetalWidth", 1.7);
		Matrix input77 = MatrixFactory.linkToArray(new double[] { 6.7, 3.0, 5.0, 1.7 }).transpose();
		s77.getVariables().setMatrix(INPUT, input77);
		Matrix output77 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s77.getVariables().setMatrix(TARGET, output77);
		s77.setLabel("Iris-versicolor");
		s77.setId("iris-77");
		iris.getSamples().add(s77);

		Sample s78 = SampleFactory.emptySample();
		s78.getVariables().setObject("SepalLength", 6.0);
		s78.getVariables().setObject("SepalWidth", 2.9);
		s78.getVariables().setObject("PetalLength", 4.5);
		s78.getVariables().setObject("PetalWidth", 1.5);
		Matrix input78 = MatrixFactory.linkToArray(new double[] { 6.0, 2.9, 4.5, 1.5 }).transpose();
		s78.getVariables().setMatrix(INPUT, input78);
		Matrix output78 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s78.getVariables().setMatrix(TARGET, output78);
		s78.setLabel("Iris-versicolor");
		s78.setId("iris-78");
		iris.getSamples().add(s78);

		Sample s79 = SampleFactory.emptySample();
		s79.getVariables().setObject("SepalLength", 5.7);
		s79.getVariables().setObject("SepalWidth", 2.6);
		s79.getVariables().setObject("PetalLength", 3.5);
		s79.getVariables().setObject("PetalWidth", 1.0);
		Matrix input79 = MatrixFactory.linkToArray(new double[] { 5.7, 2.6, 3.5, 1.0 }).transpose();
		s79.getVariables().setMatrix(INPUT, input79);
		Matrix output79 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s79.getVariables().setMatrix(TARGET, output79);
		s79.setLabel("Iris-versicolor");
		s79.setId("iris-79");
		iris.getSamples().add(s79);

		Sample s80 = SampleFactory.emptySample();
		s80.getVariables().setObject("SepalLength", 5.5);
		s80.getVariables().setObject("SepalWidth", 2.4);
		s80.getVariables().setObject("PetalLength", 3.8);
		s80.getVariables().setObject("PetalWidth", 1.1);
		Matrix input80 = MatrixFactory.linkToArray(new double[] { 5.5, 2.4, 3.8, 1.1 }).transpose();
		s80.getVariables().setMatrix(INPUT, input80);
		Matrix output80 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s80.getVariables().setMatrix(TARGET, output80);
		s80.setLabel("Iris-versicolor");
		s80.setId("iris-80");
		iris.getSamples().add(s80);

		Sample s81 = SampleFactory.emptySample();
		s81.getVariables().setObject("SepalLength", 5.5);
		s81.getVariables().setObject("SepalWidth", 2.4);
		s81.getVariables().setObject("PetalLength", 3.7);
		s81.getVariables().setObject("PetalWidth", 1.0);
		Matrix input81 = MatrixFactory.linkToArray(new double[] { 5.5, 2.4, 3.7, 1.0 }).transpose();
		s81.getVariables().setMatrix(INPUT, input81);
		Matrix output81 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s81.getVariables().setMatrix(TARGET, output81);
		s81.setLabel("Iris-versicolor");
		s81.setId("iris-81");
		iris.getSamples().add(s81);

		Sample s82 = SampleFactory.emptySample();
		s82.getVariables().setObject("SepalLength", 5.8);
		s82.getVariables().setObject("SepalWidth", 2.7);
		s82.getVariables().setObject("PetalLength", 3.9);
		s82.getVariables().setObject("PetalWidth", 1.2);
		Matrix input82 = MatrixFactory.linkToArray(new double[] { 5.8, 2.7, 3.9, 1.2 }).transpose();
		s82.getVariables().setMatrix(INPUT, input82);
		Matrix output82 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s82.getVariables().setMatrix(TARGET, output82);
		s82.setLabel("Iris-versicolor");
		s82.setId("iris-82");
		iris.getSamples().add(s82);

		Sample s83 = SampleFactory.emptySample();
		s83.getVariables().setObject("SepalLength", 6.0);
		s83.getVariables().setObject("SepalWidth", 2.7);
		s83.getVariables().setObject("PetalLength", 5.1);
		s83.getVariables().setObject("PetalWidth", 1.6);
		Matrix input83 = MatrixFactory.linkToArray(new double[] { 6.0, 2.7, 5.1, 1.6 }).transpose();
		s83.getVariables().setMatrix(INPUT, input83);
		Matrix output83 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s83.getVariables().setMatrix(TARGET, output83);
		s83.setLabel("Iris-versicolor");
		s83.setId("iris-83");
		iris.getSamples().add(s83);

		Sample s84 = SampleFactory.emptySample();
		s84.getVariables().setObject("SepalLength", 5.4);
		s84.getVariables().setObject("SepalWidth", 3.0);
		s84.getVariables().setObject("PetalLength", 4.5);
		s84.getVariables().setObject("PetalWidth", 1.5);
		Matrix input84 = MatrixFactory.linkToArray(new double[] { 5.4, 3.0, 4.5, 1.5 }).transpose();
		s84.getVariables().setMatrix(INPUT, input84);
		Matrix output84 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s84.getVariables().setMatrix(TARGET, output84);
		s84.setLabel("Iris-versicolor");
		s84.setId("iris-84");
		iris.getSamples().add(s84);

		Sample s85 = SampleFactory.emptySample();
		s85.getVariables().setObject("SepalLength", 6.0);
		s85.getVariables().setObject("SepalWidth", 3.4);
		s85.getVariables().setObject("PetalLength", 4.5);
		s85.getVariables().setObject("PetalWidth", 1.6);
		Matrix input85 = MatrixFactory.linkToArray(new double[] { 6.0, 3.4, 4.5, 1.6 }).transpose();
		s85.getVariables().setMatrix(INPUT, input85);
		Matrix output85 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s85.getVariables().setMatrix(TARGET, output85);
		s85.setLabel("Iris-versicolor");
		s85.setId("iris-85");
		iris.getSamples().add(s85);

		Sample s86 = SampleFactory.emptySample();
		s86.getVariables().setObject("SepalLength", 6.7);
		s86.getVariables().setObject("SepalWidth", 3.1);
		s86.getVariables().setObject("PetalLength", 4.7);
		s86.getVariables().setObject("PetalWidth", 1.5);
		Matrix input86 = MatrixFactory.linkToArray(new double[] { 6.7, 3.1, 4.7, 1.5 }).transpose();
		s86.getVariables().setMatrix(INPUT, input86);
		Matrix output86 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s86.getVariables().setMatrix(TARGET, output86);
		s86.setLabel("Iris-versicolor");
		s86.setId("iris-86");
		iris.getSamples().add(s86);

		Sample s87 = SampleFactory.emptySample();
		s87.getVariables().setObject("SepalLength", 6.3);
		s87.getVariables().setObject("SepalWidth", 2.3);
		s87.getVariables().setObject("PetalLength", 4.4);
		s87.getVariables().setObject("PetalWidth", 1.3);
		Matrix input87 = MatrixFactory.linkToArray(new double[] { 6.3, 2.3, 4.4, 1.3 }).transpose();
		s87.getVariables().setMatrix(INPUT, input87);
		Matrix output87 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s87.getVariables().setMatrix(TARGET, output87);
		s87.setLabel("Iris-versicolor");
		s87.setId("iris-87");
		iris.getSamples().add(s87);

		Sample s88 = SampleFactory.emptySample();
		s88.getVariables().setObject("SepalLength", 5.6);
		s88.getVariables().setObject("SepalWidth", 3.0);
		s88.getVariables().setObject("PetalLength", 4.1);
		s88.getVariables().setObject("PetalWidth", 1.3);
		Matrix input88 = MatrixFactory.linkToArray(new double[] { 5.6, 3.0, 4.1, 1.3 }).transpose();
		s88.getVariables().setMatrix(INPUT, input88);
		Matrix output88 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s88.getVariables().setMatrix(TARGET, output88);
		s88.setLabel("Iris-versicolor");
		s88.setId("iris-88");
		iris.getSamples().add(s88);

		Sample s89 = SampleFactory.emptySample();
		s89.getVariables().setObject("SepalLength", 5.5);
		s89.getVariables().setObject("SepalWidth", 2.5);
		s89.getVariables().setObject("PetalLength", 4.0);
		s89.getVariables().setObject("PetalWidth", 1.3);
		Matrix input89 = MatrixFactory.linkToArray(new double[] { 5.5, 2.5, 4.0, 1.3 }).transpose();
		s89.getVariables().setMatrix(INPUT, input89);
		Matrix output89 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s89.getVariables().setMatrix(TARGET, output89);
		s89.setLabel("Iris-versicolor");
		s89.setId("iris-89");
		iris.getSamples().add(s89);

		Sample s90 = SampleFactory.emptySample();
		s90.getVariables().setObject("SepalLength", 5.5);
		s90.getVariables().setObject("SepalWidth", 2.6);
		s90.getVariables().setObject("PetalLength", 4.4);
		s90.getVariables().setObject("PetalWidth", 1.2);
		Matrix input90 = MatrixFactory.linkToArray(new double[] { 5.5, 2.6, 4.4, 1.2 }).transpose();
		s90.getVariables().setMatrix(INPUT, input90);
		Matrix output90 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s90.getVariables().setMatrix(TARGET, output90);
		s90.setLabel("Iris-versicolor");
		s90.setId("iris-90");
		iris.getSamples().add(s90);

		Sample s91 = SampleFactory.emptySample();
		s91.getVariables().setObject("SepalLength", 6.1);
		s91.getVariables().setObject("SepalWidth", 3.0);
		s91.getVariables().setObject("PetalLength", 4.6);
		s91.getVariables().setObject("PetalWidth", 1.4);
		Matrix input91 = MatrixFactory.linkToArray(new double[] { 6.1, 3.0, 4.6, 1.4 }).transpose();
		s91.getVariables().setMatrix(INPUT, input91);
		Matrix output91 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s91.getVariables().setMatrix(TARGET, output91);
		s91.setLabel("Iris-versicolor");
		s91.setId("iris-91");
		iris.getSamples().add(s91);

		Sample s92 = SampleFactory.emptySample();
		s92.getVariables().setObject("SepalLength", 5.8);
		s92.getVariables().setObject("SepalWidth", 2.6);
		s92.getVariables().setObject("PetalLength", 4.0);
		s92.getVariables().setObject("PetalWidth", 1.2);
		Matrix input92 = MatrixFactory.linkToArray(new double[] { 5.8, 2.6, 4.0, 1.2 }).transpose();
		s92.getVariables().setMatrix(INPUT, input92);
		Matrix output92 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s92.getVariables().setMatrix(TARGET, output92);
		s92.setLabel("Iris-versicolor");
		s92.setId("iris-92");
		iris.getSamples().add(s92);

		Sample s93 = SampleFactory.emptySample();
		s93.getVariables().setObject("SepalLength", 5.0);
		s93.getVariables().setObject("SepalWidth", 2.3);
		s93.getVariables().setObject("PetalLength", 3.3);
		s93.getVariables().setObject("PetalWidth", 1.0);
		Matrix input93 = MatrixFactory.linkToArray(new double[] { 5.0, 2.3, 3.3, 1.0 }).transpose();
		s93.getVariables().setMatrix(INPUT, input93);
		Matrix output93 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s93.getVariables().setMatrix(TARGET, output93);
		s93.setLabel("Iris-versicolor");
		s93.setId("iris-93");
		iris.getSamples().add(s93);

		Sample s94 = SampleFactory.emptySample();
		s94.getVariables().setObject("SepalLength", 5.6);
		s94.getVariables().setObject("SepalWidth", 2.7);
		s94.getVariables().setObject("PetalLength", 4.2);
		s94.getVariables().setObject("PetalWidth", 1.3);
		Matrix input94 = MatrixFactory.linkToArray(new double[] { 5.6, 2.7, 4.2, 1.3 }).transpose();
		s94.getVariables().setMatrix(INPUT, input94);
		Matrix output94 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s94.getVariables().setMatrix(TARGET, output94);
		s94.setLabel("Iris-versicolor");
		s94.setId("iris-94");
		iris.getSamples().add(s94);

		Sample s95 = SampleFactory.emptySample();
		s95.getVariables().setObject("SepalLength", 5.7);
		s95.getVariables().setObject("SepalWidth", 3.0);
		s95.getVariables().setObject("PetalLength", 4.2);
		s95.getVariables().setObject("PetalWidth", 1.2);
		Matrix input95 = MatrixFactory.linkToArray(new double[] { 5.7, 3.0, 4.2, 1.2 }).transpose();
		s95.getVariables().setMatrix(INPUT, input95);
		Matrix output95 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s95.getVariables().setMatrix(TARGET, output95);
		s95.setLabel("Iris-versicolor");
		s95.setId("iris-95");
		iris.getSamples().add(s95);

		Sample s96 = SampleFactory.emptySample();
		s96.getVariables().setObject("SepalLength", 5.7);
		s96.getVariables().setObject("SepalWidth", 2.9);
		s96.getVariables().setObject("PetalLength", 4.2);
		s96.getVariables().setObject("PetalWidth", 1.3);
		Matrix input96 = MatrixFactory.linkToArray(new double[] { 5.7, 2.9, 4.2, 1.3 }).transpose();
		s96.getVariables().setMatrix(INPUT, input96);
		Matrix output96 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s96.getVariables().setMatrix(TARGET, output96);
		s96.setLabel("Iris-versicolor");
		s96.setId("iris-96");
		iris.getSamples().add(s96);

		Sample s97 = SampleFactory.emptySample();
		s97.getVariables().setObject("SepalLength", 6.2);
		s97.getVariables().setObject("SepalWidth", 2.9);
		s97.getVariables().setObject("PetalLength", 4.3);
		s97.getVariables().setObject("PetalWidth", 1.3);
		Matrix input97 = MatrixFactory.linkToArray(new double[] { 6.2, 2.9, 4.3, 1.3 }).transpose();
		s97.getVariables().setMatrix(INPUT, input97);
		Matrix output97 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s97.getVariables().setMatrix(TARGET, output97);
		s97.setLabel("Iris-versicolor");
		s97.setId("iris-97");
		iris.getSamples().add(s97);

		Sample s98 = SampleFactory.emptySample();
		s98.getVariables().setObject("SepalLength", 5.1);
		s98.getVariables().setObject("SepalWidth", 2.5);
		s98.getVariables().setObject("PetalLength", 3.0);
		s98.getVariables().setObject("PetalWidth", 1.1);
		Matrix input98 = MatrixFactory.linkToArray(new double[] { 5.1, 2.5, 3.0, 1.1 }).transpose();
		s98.getVariables().setMatrix(INPUT, input98);
		Matrix output98 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s98.getVariables().setMatrix(TARGET, output98);
		s98.setLabel("Iris-versicolor");
		s98.setId("iris-98");
		iris.getSamples().add(s98);

		Sample s99 = SampleFactory.emptySample();
		s99.getVariables().setObject("SepalLength", 5.7);
		s99.getVariables().setObject("SepalWidth", 2.8);
		s99.getVariables().setObject("PetalLength", 4.1);
		s99.getVariables().setObject("PetalWidth", 1.3);
		Matrix input99 = MatrixFactory.linkToArray(new double[] { 5.7, 2.8, 4.1, 1.3 }).transpose();
		s99.getVariables().setMatrix(INPUT, input99);
		Matrix output99 = MatrixFactory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s99.getVariables().setMatrix(TARGET, output99);
		s99.setLabel("Iris-versicolor");
		s99.setId("iris-99");
		iris.getSamples().add(s99);

		Sample s100 = SampleFactory.emptySample();
		s100.getVariables().setObject("SepalLength", 6.3);
		s100.getVariables().setObject("SepalWidth", 3.3);
		s100.getVariables().setObject("PetalLength", 6.0);
		s100.getVariables().setObject("PetalWidth", 2.5);
		Matrix input100 = MatrixFactory.linkToArray(new double[] { 6.3, 3.3, 6.0, 2.5 })
				.transpose();
		s100.getVariables().setMatrix(INPUT, input100);
		Matrix output100 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s100.getVariables().setMatrix(TARGET, output100);
		s100.setLabel("Iris-virginica");
		s100.setId("iris-100");
		iris.getSamples().add(s100);

		Sample s101 = SampleFactory.emptySample();
		s101.getVariables().setObject("SepalLength", 5.8);
		s101.getVariables().setObject("SepalWidth", 2.7);
		s101.getVariables().setObject("PetalLength", 5.1);
		s101.getVariables().setObject("PetalWidth", 1.9);
		Matrix input101 = MatrixFactory.linkToArray(new double[] { 5.8, 2.7, 5.1, 1.9 })
				.transpose();
		s101.getVariables().setMatrix(INPUT, input101);
		Matrix output101 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s101.getVariables().setMatrix(TARGET, output101);
		s101.setLabel("Iris-virginica");
		s101.setId("iris-101");
		iris.getSamples().add(s101);

		Sample s102 = SampleFactory.emptySample();
		s102.getVariables().setObject("SepalLength", 7.1);
		s102.getVariables().setObject("SepalWidth", 3.0);
		s102.getVariables().setObject("PetalLength", 5.9);
		s102.getVariables().setObject("PetalWidth", 2.1);
		Matrix input102 = MatrixFactory.linkToArray(new double[] { 7.1, 3.0, 5.9, 2.1 })
				.transpose();
		s102.getVariables().setMatrix(INPUT, input102);
		Matrix output102 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s102.getVariables().setMatrix(TARGET, output102);
		s102.setLabel("Iris-virginica");
		s102.setId("iris-102");
		iris.getSamples().add(s102);

		Sample s103 = SampleFactory.emptySample();
		s103.getVariables().setObject("SepalLength", 6.3);
		s103.getVariables().setObject("SepalWidth", 2.9);
		s103.getVariables().setObject("PetalLength", 5.6);
		s103.getVariables().setObject("PetalWidth", 1.8);
		Matrix input103 = MatrixFactory.linkToArray(new double[] { 6.3, 2.9, 5.6, 1.8 })
				.transpose();
		s103.getVariables().setMatrix(INPUT, input103);
		Matrix output103 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s103.getVariables().setMatrix(TARGET, output103);
		s103.setLabel("Iris-virginica");
		s103.setId("iris-103");
		iris.getSamples().add(s103);

		Sample s104 = SampleFactory.emptySample();
		s104.getVariables().setObject("SepalLength", 6.5);
		s104.getVariables().setObject("SepalWidth", 3.0);
		s104.getVariables().setObject("PetalLength", 5.8);
		s104.getVariables().setObject("PetalWidth", 2.2);
		Matrix input104 = MatrixFactory.linkToArray(new double[] { 6.5, 3.0, 5.8, 2.2 })
				.transpose();
		s104.getVariables().setMatrix(INPUT, input104);
		Matrix output104 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s104.getVariables().setMatrix(TARGET, output104);
		s104.setLabel("Iris-virginica");
		s104.setId("iris-104");
		iris.getSamples().add(s104);

		Sample s105 = SampleFactory.emptySample();
		s105.getVariables().setObject("SepalLength", 7.6);
		s105.getVariables().setObject("SepalWidth", 3.0);
		s105.getVariables().setObject("PetalLength", 6.6);
		s105.getVariables().setObject("PetalWidth", 2.1);
		Matrix input105 = MatrixFactory.linkToArray(new double[] { 7.6, 3.0, 6.6, 2.1 })
				.transpose();
		s105.getVariables().setMatrix(INPUT, input105);
		Matrix output105 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s105.getVariables().setMatrix(TARGET, output105);
		s105.setLabel("Iris-virginica");
		s105.setId("iris-105");
		iris.getSamples().add(s105);

		Sample s106 = SampleFactory.emptySample();
		s106.getVariables().setObject("SepalLength", 4.9);
		s106.getVariables().setObject("SepalWidth", 2.5);
		s106.getVariables().setObject("PetalLength", 4.5);
		s106.getVariables().setObject("PetalWidth", 1.7);
		Matrix input106 = MatrixFactory.linkToArray(new double[] { 4.9, 2.5, 4.5, 1.7 })
				.transpose();
		s106.getVariables().setMatrix(INPUT, input106);
		Matrix output106 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s106.getVariables().setMatrix(TARGET, output106);
		s106.setLabel("Iris-virginica");
		s106.setId("iris-106");
		iris.getSamples().add(s106);

		Sample s107 = SampleFactory.emptySample();
		s107.getVariables().setObject("SepalLength", 7.3);
		s107.getVariables().setObject("SepalWidth", 2.9);
		s107.getVariables().setObject("PetalLength", 6.3);
		s107.getVariables().setObject("PetalWidth", 1.8);
		Matrix input107 = MatrixFactory.linkToArray(new double[] { 7.3, 2.9, 6.3, 1.8 })
				.transpose();
		s107.getVariables().setMatrix(INPUT, input107);
		Matrix output107 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s107.getVariables().setMatrix(TARGET, output107);
		s107.setLabel("Iris-virginica");
		s107.setId("iris-107");
		iris.getSamples().add(s107);

		Sample s108 = SampleFactory.emptySample();
		s108.getVariables().setObject("SepalLength", 6.7);
		s108.getVariables().setObject("SepalWidth", 2.5);
		s108.getVariables().setObject("PetalLength", 5.8);
		s108.getVariables().setObject("PetalWidth", 1.8);
		Matrix input108 = MatrixFactory.linkToArray(new double[] { 6.7, 2.5, 5.8, 1.8 })
				.transpose();
		s108.getVariables().setMatrix(INPUT, input108);
		Matrix output108 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s108.getVariables().setMatrix(TARGET, output108);
		s108.setLabel("Iris-virginica");
		s108.setId("iris-108");
		iris.getSamples().add(s108);

		Sample s109 = SampleFactory.emptySample();
		s109.getVariables().setObject("SepalLength", 7.2);
		s109.getVariables().setObject("SepalWidth", 3.6);
		s109.getVariables().setObject("PetalLength", 6.1);
		s109.getVariables().setObject("PetalWidth", 2.5);
		Matrix input109 = MatrixFactory.linkToArray(new double[] { 7.2, 3.6, 6.1, 2.5 })
				.transpose();
		s109.getVariables().setMatrix(INPUT, input109);
		Matrix output109 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s109.getVariables().setMatrix(TARGET, output109);
		s109.setLabel("Iris-virginica");
		s109.setId("iris-109");
		iris.getSamples().add(s109);

		Sample s110 = SampleFactory.emptySample();
		s110.getVariables().setObject("SepalLength", 6.5);
		s110.getVariables().setObject("SepalWidth", 3.2);
		s110.getVariables().setObject("PetalLength", 5.1);
		s110.getVariables().setObject("PetalWidth", 2.0);
		Matrix input110 = MatrixFactory.linkToArray(new double[] { 6.5, 3.2, 5.1, 2.0 })
				.transpose();
		s110.getVariables().setMatrix(INPUT, input110);
		Matrix output110 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s110.getVariables().setMatrix(TARGET, output110);
		s110.setLabel("Iris-virginica");
		s110.setId("iris-110");
		iris.getSamples().add(s110);

		Sample s111 = SampleFactory.emptySample();
		s111.getVariables().setObject("SepalLength", 6.4);
		s111.getVariables().setObject("SepalWidth", 2.7);
		s111.getVariables().setObject("PetalLength", 5.3);
		s111.getVariables().setObject("PetalWidth", 1.9);
		Matrix input111 = MatrixFactory.linkToArray(new double[] { 6.4, 2.7, 5.3, 1.9 })
				.transpose();
		s111.getVariables().setMatrix(INPUT, input111);
		Matrix output111 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s111.getVariables().setMatrix(TARGET, output111);
		s111.setLabel("Iris-virginica");
		s111.setId("iris-111");
		iris.getSamples().add(s111);

		Sample s112 = SampleFactory.emptySample();
		s112.getVariables().setObject("SepalLength", 6.8);
		s112.getVariables().setObject("SepalWidth", 3.0);
		s112.getVariables().setObject("PetalLength", 5.5);
		s112.getVariables().setObject("PetalWidth", 2.1);
		Matrix input112 = MatrixFactory.linkToArray(new double[] { 6.8, 3.0, 5.5, 2.1 })
				.transpose();
		s112.getVariables().setMatrix(INPUT, input112);
		Matrix output112 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s112.getVariables().setMatrix(TARGET, output112);
		s112.setLabel("Iris-virginica");
		s112.setId("iris-112");
		iris.getSamples().add(s112);

		Sample s113 = SampleFactory.emptySample();
		s113.getVariables().setObject("SepalLength", 5.7);
		s113.getVariables().setObject("SepalWidth", 2.5);
		s113.getVariables().setObject("PetalLength", 5.0);
		s113.getVariables().setObject("PetalWidth", 2.0);
		Matrix input113 = MatrixFactory.linkToArray(new double[] { 5.7, 2.5, 5.0, 2.0 })
				.transpose();
		s113.getVariables().setMatrix(INPUT, input113);
		Matrix output113 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s113.getVariables().setMatrix(TARGET, output113);
		s113.setLabel("Iris-virginica");
		s113.setId("iris-113");
		iris.getSamples().add(s113);

		Sample s114 = SampleFactory.emptySample();
		s114.getVariables().setObject("SepalLength", 5.8);
		s114.getVariables().setObject("SepalWidth", 2.8);
		s114.getVariables().setObject("PetalLength", 5.1);
		s114.getVariables().setObject("PetalWidth", 2.4);
		Matrix input114 = MatrixFactory.linkToArray(new double[] { 5.8, 2.8, 5.1, 2.4 })
				.transpose();
		s114.getVariables().setMatrix(INPUT, input114);
		Matrix output114 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s114.getVariables().setMatrix(TARGET, output114);
		s114.setLabel("Iris-virginica");
		s114.setId("iris-114");
		iris.getSamples().add(s114);

		Sample s115 = SampleFactory.emptySample();
		s115.getVariables().setObject("SepalLength", 6.4);
		s115.getVariables().setObject("SepalWidth", 3.2);
		s115.getVariables().setObject("PetalLength", 5.3);
		s115.getVariables().setObject("PetalWidth", 2.3);
		Matrix input115 = MatrixFactory.linkToArray(new double[] { 6.4, 3.2, 5.3, 2.3 })
				.transpose();
		s115.getVariables().setMatrix(INPUT, input115);
		Matrix output115 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s115.getVariables().setMatrix(TARGET, output115);
		s115.setLabel("Iris-virginica");
		s115.setId("iris-115");
		iris.getSamples().add(s115);

		Sample s116 = SampleFactory.emptySample();
		s116.getVariables().setObject("SepalLength", 6.5);
		s116.getVariables().setObject("SepalWidth", 3.0);
		s116.getVariables().setObject("PetalLength", 5.5);
		s116.getVariables().setObject("PetalWidth", 1.8);
		Matrix input116 = MatrixFactory.linkToArray(new double[] { 6.5, 3.0, 5.5, 1.8 })
				.transpose();
		s116.getVariables().setMatrix(INPUT, input116);
		Matrix output116 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s116.getVariables().setMatrix(TARGET, output116);
		s116.setLabel("Iris-virginica");
		s116.setId("iris-116");
		iris.getSamples().add(s116);

		Sample s117 = SampleFactory.emptySample();
		s117.getVariables().setObject("SepalLength", 7.7);
		s117.getVariables().setObject("SepalWidth", 3.8);
		s117.getVariables().setObject("PetalLength", 6.7);
		s117.getVariables().setObject("PetalWidth", 2.2);
		Matrix input117 = MatrixFactory.linkToArray(new double[] { 7.7, 3.8, 6.7, 2.2 })
				.transpose();
		s117.getVariables().setMatrix(INPUT, input117);
		Matrix output117 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s117.getVariables().setMatrix(TARGET, output117);
		s117.setLabel("Iris-virginica");
		s117.setId("iris-117");
		iris.getSamples().add(s117);

		Sample s118 = SampleFactory.emptySample();
		s118.getVariables().setObject("SepalLength", 7.7);
		s118.getVariables().setObject("SepalWidth", 2.6);
		s118.getVariables().setObject("PetalLength", 6.9);
		s118.getVariables().setObject("PetalWidth", 2.3);
		Matrix input118 = MatrixFactory.linkToArray(new double[] { 7.7, 2.6, 6.9, 2.3 })
				.transpose();
		s118.getVariables().setMatrix(INPUT, input118);
		Matrix output118 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s118.getVariables().setMatrix(TARGET, output118);
		s118.setLabel("Iris-virginica");
		s118.setId("iris-118");
		iris.getSamples().add(s118);

		Sample s119 = SampleFactory.emptySample();
		s119.getVariables().setObject("SepalLength", 6.0);
		s119.getVariables().setObject("SepalWidth", 2.2);
		s119.getVariables().setObject("PetalLength", 5.0);
		s119.getVariables().setObject("PetalWidth", 1.5);
		Matrix input119 = MatrixFactory.linkToArray(new double[] { 6.0, 2.2, 5.0, 1.5 })
				.transpose();
		s119.getVariables().setMatrix(INPUT, input119);
		Matrix output119 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s119.getVariables().setMatrix(TARGET, output119);
		s119.setLabel("Iris-virginica");
		s119.setId("iris-119");
		iris.getSamples().add(s119);

		Sample s120 = SampleFactory.emptySample();
		s120.getVariables().setObject("SepalLength", 6.9);
		s120.getVariables().setObject("SepalWidth", 3.2);
		s120.getVariables().setObject("PetalLength", 5.7);
		s120.getVariables().setObject("PetalWidth", 2.3);
		Matrix input120 = MatrixFactory.linkToArray(new double[] { 6.9, 3.2, 5.7, 2.3 })
				.transpose();
		s120.getVariables().setMatrix(INPUT, input120);
		Matrix output120 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s120.getVariables().setMatrix(TARGET, output120);
		s120.setLabel("Iris-virginica");
		s120.setId("iris-120");
		iris.getSamples().add(s120);

		Sample s121 = SampleFactory.emptySample();
		s121.getVariables().setObject("SepalLength", 5.6);
		s121.getVariables().setObject("SepalWidth", 2.8);
		s121.getVariables().setObject("PetalLength", 4.9);
		s121.getVariables().setObject("PetalWidth", 2.0);
		Matrix input121 = MatrixFactory.linkToArray(new double[] { 5.6, 2.8, 4.9, 2.0 })
				.transpose();
		s121.getVariables().setMatrix(INPUT, input121);
		Matrix output121 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s121.getVariables().setMatrix(TARGET, output121);
		s121.setLabel("Iris-virginica");
		s121.setId("iris-121");
		iris.getSamples().add(s121);

		Sample s122 = SampleFactory.emptySample();
		s122.getVariables().setObject("SepalLength", 7.7);
		s122.getVariables().setObject("SepalWidth", 2.8);
		s122.getVariables().setObject("PetalLength", 6.7);
		s122.getVariables().setObject("PetalWidth", 2.0);
		Matrix input122 = MatrixFactory.linkToArray(new double[] { 7.7, 2.8, 6.7, 2.0 })
				.transpose();
		s122.getVariables().setMatrix(INPUT, input122);
		Matrix output122 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s122.getVariables().setMatrix(TARGET, output122);
		s122.setLabel("Iris-virginica");
		s122.setId("iris-122");
		iris.getSamples().add(s122);

		Sample s123 = SampleFactory.emptySample();
		s123.getVariables().setObject("SepalLength", 6.3);
		s123.getVariables().setObject("SepalWidth", 2.7);
		s123.getVariables().setObject("PetalLength", 4.9);
		s123.getVariables().setObject("PetalWidth", 1.8);
		Matrix input123 = MatrixFactory.linkToArray(new double[] { 6.3, 2.7, 4.9, 1.8 })
				.transpose();
		s123.getVariables().setMatrix(INPUT, input123);
		Matrix output123 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s123.getVariables().setMatrix(TARGET, output123);
		s123.setLabel("Iris-virginica");
		s123.setId("iris-123");
		iris.getSamples().add(s123);

		Sample s124 = SampleFactory.emptySample();
		s124.getVariables().setObject("SepalLength", 6.7);
		s124.getVariables().setObject("SepalWidth", 3.3);
		s124.getVariables().setObject("PetalLength", 5.7);
		s124.getVariables().setObject("PetalWidth", 2.1);
		Matrix input124 = MatrixFactory.linkToArray(new double[] { 6.7, 3.3, 5.7, 2.1 })
				.transpose();
		s124.getVariables().setMatrix(INPUT, input124);
		Matrix output124 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s124.getVariables().setMatrix(TARGET, output124);
		s124.setLabel("Iris-virginica");
		s124.setId("iris-124");
		iris.getSamples().add(s124);

		Sample s125 = SampleFactory.emptySample();
		s125.getVariables().setObject("SepalLength", 7.2);
		s125.getVariables().setObject("SepalWidth", 3.2);
		s125.getVariables().setObject("PetalLength", 6.0);
		s125.getVariables().setObject("PetalWidth", 1.8);
		Matrix input125 = MatrixFactory.linkToArray(new double[] { 7.2, 3.2, 6.0, 1.8 })
				.transpose();
		s125.getVariables().setMatrix(INPUT, input125);
		Matrix output125 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s125.getVariables().setMatrix(TARGET, output125);
		s125.setLabel("Iris-virginica");
		s125.setId("iris-125");
		iris.getSamples().add(s125);

		Sample s126 = SampleFactory.emptySample();
		s126.getVariables().setObject("SepalLength", 6.2);
		s126.getVariables().setObject("SepalWidth", 2.8);
		s126.getVariables().setObject("PetalLength", 4.8);
		s126.getVariables().setObject("PetalWidth", 1.8);
		Matrix input126 = MatrixFactory.linkToArray(new double[] { 6.2, 2.8, 4.8, 1.8 })
				.transpose();
		s126.getVariables().setMatrix(INPUT, input126);
		Matrix output126 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s126.getVariables().setMatrix(TARGET, output126);
		s126.setLabel("Iris-virginica");
		s126.setId("iris-126");
		iris.getSamples().add(s126);

		Sample s127 = SampleFactory.emptySample();
		s127.getVariables().setObject("SepalLength", 6.1);
		s127.getVariables().setObject("SepalWidth", 3.0);
		s127.getVariables().setObject("PetalLength", 4.9);
		s127.getVariables().setObject("PetalWidth", 1.8);
		Matrix input127 = MatrixFactory.linkToArray(new double[] { 6.1, 3.0, 4.9, 1.8 })
				.transpose();
		s127.getVariables().setMatrix(INPUT, input127);
		Matrix output127 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s127.getVariables().setMatrix(TARGET, output127);
		s127.setLabel("Iris-virginica");
		s127.setId("iris-127");
		iris.getSamples().add(s127);

		Sample s128 = SampleFactory.emptySample();
		s128.getVariables().setObject("SepalLength", 6.4);
		s128.getVariables().setObject("SepalWidth", 2.8);
		s128.getVariables().setObject("PetalLength", 5.6);
		s128.getVariables().setObject("PetalWidth", 2.1);
		Matrix input128 = MatrixFactory.linkToArray(new double[] { 6.4, 2.8, 5.6, 2.1 })
				.transpose();
		s128.getVariables().setMatrix(INPUT, input128);
		Matrix output128 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s128.getVariables().setMatrix(TARGET, output128);
		s128.setLabel("Iris-virginica");
		s128.setId("iris-128");
		iris.getSamples().add(s128);

		Sample s129 = SampleFactory.emptySample();
		s129.getVariables().setObject("SepalLength", 7.2);
		s129.getVariables().setObject("SepalWidth", 3.0);
		s129.getVariables().setObject("PetalLength", 5.8);
		s129.getVariables().setObject("PetalWidth", 1.6);
		Matrix input129 = MatrixFactory.linkToArray(new double[] { 7.2, 3.0, 5.8, 1.6 })
				.transpose();
		s129.getVariables().setMatrix(INPUT, input129);
		Matrix output129 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s129.getVariables().setMatrix(TARGET, output129);
		s129.setLabel("Iris-virginica");
		s129.setId("iris-129");
		iris.getSamples().add(s129);

		Sample s130 = SampleFactory.emptySample();
		s130.getVariables().setObject("SepalLength", 7.4);
		s130.getVariables().setObject("SepalWidth", 2.8);
		s130.getVariables().setObject("PetalLength", 6.1);
		s130.getVariables().setObject("PetalWidth", 1.9);
		Matrix input130 = MatrixFactory.linkToArray(new double[] { 7.4, 2.8, 6.1, 1.9 })
				.transpose();
		s130.getVariables().setMatrix(INPUT, input130);
		Matrix output130 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s130.getVariables().setMatrix(TARGET, output130);
		s130.setLabel("Iris-virginica");
		s130.setId("iris-130");
		iris.getSamples().add(s130);

		Sample s131 = SampleFactory.emptySample();
		s131.getVariables().setObject("SepalLength", 7.9);
		s131.getVariables().setObject("SepalWidth", 3.8);
		s131.getVariables().setObject("PetalLength", 6.4);
		s131.getVariables().setObject("PetalWidth", 2.0);
		Matrix input131 = MatrixFactory.linkToArray(new double[] { 7.9, 3.8, 6.4, 2.0 })
				.transpose();
		s131.getVariables().setMatrix(INPUT, input131);
		Matrix output131 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s131.getVariables().setMatrix(TARGET, output131);
		s131.setLabel("Iris-virginica");
		s131.setId("iris-131");
		iris.getSamples().add(s131);

		Sample s132 = SampleFactory.emptySample();
		s132.getVariables().setObject("SepalLength", 6.4);
		s132.getVariables().setObject("SepalWidth", 2.8);
		s132.getVariables().setObject("PetalLength", 5.6);
		s132.getVariables().setObject("PetalWidth", 2.2);
		Matrix input132 = MatrixFactory.linkToArray(new double[] { 6.4, 2.8, 5.6, 2.2 })
				.transpose();
		s132.getVariables().setMatrix(INPUT, input132);
		Matrix output132 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s132.getVariables().setMatrix(TARGET, output132);
		s132.setLabel("Iris-virginica");
		s132.setId("iris-132");
		iris.getSamples().add(s132);

		Sample s133 = SampleFactory.emptySample();
		s133.getVariables().setObject("SepalLength", 6.3);
		s133.getVariables().setObject("SepalWidth", 2.8);
		s133.getVariables().setObject("PetalLength", 5.1);
		s133.getVariables().setObject("PetalWidth", 1.5);
		Matrix input133 = MatrixFactory.linkToArray(new double[] { 6.3, 2.8, 5.1, 1.5 })
				.transpose();
		s133.getVariables().setMatrix(INPUT, input133);
		Matrix output133 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s133.getVariables().setMatrix(TARGET, output133);
		s133.setLabel("Iris-virginica");
		s133.setId("iris-133");
		iris.getSamples().add(s133);

		Sample s134 = SampleFactory.emptySample();
		s134.getVariables().setObject("SepalLength", 6.1);
		s134.getVariables().setObject("SepalWidth", 2.6);
		s134.getVariables().setObject("PetalLength", 5.6);
		s134.getVariables().setObject("PetalWidth", 1.4);
		Matrix input134 = MatrixFactory.linkToArray(new double[] { 6.1, 2.6, 5.6, 1.4 })
				.transpose();
		s134.getVariables().setMatrix(INPUT, input134);
		Matrix output134 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s134.getVariables().setMatrix(TARGET, output134);
		s134.setLabel("Iris-virginica");
		s134.setId("iris-134");
		iris.getSamples().add(s134);

		Sample s135 = SampleFactory.emptySample();
		s135.getVariables().setObject("SepalLength", 7.7);
		s135.getVariables().setObject("SepalWidth", 3.0);
		s135.getVariables().setObject("PetalLength", 6.1);
		s135.getVariables().setObject("PetalWidth", 2.3);
		Matrix input135 = MatrixFactory.linkToArray(new double[] { 7.7, 3.0, 6.1, 2.3 })
				.transpose();
		s135.getVariables().setMatrix(INPUT, input135);
		Matrix output135 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s135.getVariables().setMatrix(TARGET, output135);
		s135.setLabel("Iris-virginica");
		s135.setId("iris-135");
		iris.getSamples().add(s135);

		Sample s136 = SampleFactory.emptySample();
		s136.getVariables().setObject("SepalLength", 6.3);
		s136.getVariables().setObject("SepalWidth", 3.4);
		s136.getVariables().setObject("PetalLength", 5.6);
		s136.getVariables().setObject("PetalWidth", 2.4);
		Matrix input136 = MatrixFactory.linkToArray(new double[] { 6.3, 3.4, 5.6, 2.4 })
				.transpose();
		s136.getVariables().setMatrix(INPUT, input136);
		Matrix output136 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s136.getVariables().setMatrix(TARGET, output136);
		s136.setLabel("Iris-virginica");
		s136.setId("iris-136");
		iris.getSamples().add(s136);

		Sample s137 = SampleFactory.emptySample();
		s137.getVariables().setObject("SepalLength", 6.4);
		s137.getVariables().setObject("SepalWidth", 3.1);
		s137.getVariables().setObject("PetalLength", 5.5);
		s137.getVariables().setObject("PetalWidth", 1.8);
		Matrix input137 = MatrixFactory.linkToArray(new double[] { 6.4, 3.1, 5.5, 1.8 })
				.transpose();
		s137.getVariables().setMatrix(INPUT, input137);
		Matrix output137 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s137.getVariables().setMatrix(TARGET, output137);
		s137.setLabel("Iris-virginica");
		s137.setId("iris-137");
		iris.getSamples().add(s137);

		Sample s138 = SampleFactory.emptySample();
		s138.getVariables().setObject("SepalLength", 6.0);
		s138.getVariables().setObject("SepalWidth", 3.0);
		s138.getVariables().setObject("PetalLength", 4.8);
		s138.getVariables().setObject("PetalWidth", 1.8);
		Matrix input138 = MatrixFactory.linkToArray(new double[] { 6.0, 3.0, 4.8, 1.8 })
				.transpose();
		s138.getVariables().setMatrix(INPUT, input138);
		Matrix output138 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s138.getVariables().setMatrix(TARGET, output138);
		s138.setLabel("Iris-virginica");
		s138.setId("iris-138");
		iris.getSamples().add(s138);

		Sample s139 = SampleFactory.emptySample();
		s139.getVariables().setObject("SepalLength", 6.9);
		s139.getVariables().setObject("SepalWidth", 3.1);
		s139.getVariables().setObject("PetalLength", 5.4);
		s139.getVariables().setObject("PetalWidth", 2.1);
		Matrix input139 = MatrixFactory.linkToArray(new double[] { 6.9, 3.1, 5.4, 2.1 })
				.transpose();
		s139.getVariables().setMatrix(INPUT, input139);
		Matrix output139 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s139.getVariables().setMatrix(TARGET, output139);
		s139.setLabel("Iris-virginica");
		s139.setId("iris-139");
		iris.getSamples().add(s139);

		Sample s140 = SampleFactory.emptySample();
		s140.getVariables().setObject("SepalLength", 6.7);
		s140.getVariables().setObject("SepalWidth", 3.1);
		s140.getVariables().setObject("PetalLength", 5.6);
		s140.getVariables().setObject("PetalWidth", 2.4);
		Matrix input140 = MatrixFactory.linkToArray(new double[] { 6.7, 3.1, 5.6, 2.4 })
				.transpose();
		s140.getVariables().setMatrix(INPUT, input140);
		Matrix output140 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s140.getVariables().setMatrix(TARGET, output140);
		s140.setLabel("Iris-virginica");
		s140.setId("iris-140");
		iris.getSamples().add(s140);

		Sample s141 = SampleFactory.emptySample();
		s141.getVariables().setObject("SepalLength", 6.9);
		s141.getVariables().setObject("SepalWidth", 3.1);
		s141.getVariables().setObject("PetalLength", 5.1);
		s141.getVariables().setObject("PetalWidth", 2.3);
		Matrix input141 = MatrixFactory.linkToArray(new double[] { 6.9, 3.1, 5.1, 2.3 })
				.transpose();
		s141.getVariables().setMatrix(INPUT, input141);
		Matrix output141 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s141.getVariables().setMatrix(TARGET, output141);
		s141.setLabel("Iris-virginica");
		s141.setId("iris-141");
		iris.getSamples().add(s141);

		Sample s142 = SampleFactory.emptySample();
		s142.getVariables().setObject("SepalLength", 5.8);
		s142.getVariables().setObject("SepalWidth", 2.7);
		s142.getVariables().setObject("PetalLength", 5.1);
		s142.getVariables().setObject("PetalWidth", 1.9);
		Matrix input142 = MatrixFactory.linkToArray(new double[] { 5.8, 2.7, 5.1, 1.9 })
				.transpose();
		s142.getVariables().setMatrix(INPUT, input142);
		Matrix output142 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s142.getVariables().setMatrix(TARGET, output142);
		s142.setLabel("Iris-virginica");
		s142.setId("iris-142");
		iris.getSamples().add(s142);

		Sample s143 = SampleFactory.emptySample();
		s143.getVariables().setObject("SepalLength", 6.8);
		s143.getVariables().setObject("SepalWidth", 3.2);
		s143.getVariables().setObject("PetalLength", 5.9);
		s143.getVariables().setObject("PetalWidth", 2.3);
		Matrix input143 = MatrixFactory.linkToArray(new double[] { 6.8, 3.2, 5.9, 2.3 })
				.transpose();
		s143.getVariables().setMatrix(INPUT, input143);
		Matrix output143 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s143.getVariables().setMatrix(TARGET, output143);
		s143.setLabel("Iris-virginica");
		s143.setId("iris-143");
		iris.getSamples().add(s143);

		Sample s144 = SampleFactory.emptySample();
		s144.getVariables().setObject("SepalLength", 6.7);
		s144.getVariables().setObject("SepalWidth", 3.3);
		s144.getVariables().setObject("PetalLength", 5.7);
		s144.getVariables().setObject("PetalWidth", 2.5);
		Matrix input144 = MatrixFactory.linkToArray(new double[] { 6.7, 3.3, 5.7, 2.5 })
				.transpose();
		s144.getVariables().setMatrix(INPUT, input144);
		Matrix output144 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s144.getVariables().setMatrix(TARGET, output144);
		s144.setLabel("Iris-virginica");
		s144.setId("iris-144");
		iris.getSamples().add(s144);

		Sample s145 = SampleFactory.emptySample();
		s145.getVariables().setObject("SepalLength", 6.7);
		s145.getVariables().setObject("SepalWidth", 3.0);
		s145.getVariables().setObject("PetalLength", 5.2);
		s145.getVariables().setObject("PetalWidth", 2.3);
		Matrix input145 = MatrixFactory.linkToArray(new double[] { 6.7, 3.0, 5.2, 2.3 })
				.transpose();
		s145.getVariables().setMatrix(INPUT, input145);
		Matrix output145 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s145.getVariables().setMatrix(TARGET, output145);
		s145.setLabel("Iris-virginica");
		s145.setId("iris-145");
		iris.getSamples().add(s145);

		Sample s146 = SampleFactory.emptySample();
		s146.getVariables().setObject("SepalLength", 6.3);
		s146.getVariables().setObject("SepalWidth", 2.5);
		s146.getVariables().setObject("PetalLength", 5.0);
		s146.getVariables().setObject("PetalWidth", 1.9);
		Matrix input146 = MatrixFactory.linkToArray(new double[] { 6.3, 2.5, 5.0, 1.9 })
				.transpose();
		s146.getVariables().setMatrix(INPUT, input146);
		Matrix output146 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s146.getVariables().setMatrix(TARGET, output146);
		s146.setLabel("Iris-virginica");
		s146.setId("iris-146");
		iris.getSamples().add(s146);

		Sample s147 = SampleFactory.emptySample();
		s147.getVariables().setObject("SepalLength", 6.5);
		s147.getVariables().setObject("SepalWidth", 3.0);
		s147.getVariables().setObject("PetalLength", 5.2);
		s147.getVariables().setObject("PetalWidth", 2.0);
		Matrix input147 = MatrixFactory.linkToArray(new double[] { 6.5, 3.0, 5.2, 2.0 })
				.transpose();
		s147.getVariables().setMatrix(INPUT, input147);
		Matrix output147 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s147.getVariables().setMatrix(TARGET, output147);
		s147.setLabel("Iris-virginica");
		s147.setId("iris-147");
		iris.getSamples().add(s147);

		Sample s148 = SampleFactory.emptySample();
		s148.getVariables().setObject("SepalLength", 6.2);
		s148.getVariables().setObject("SepalWidth", 3.4);
		s148.getVariables().setObject("PetalLength", 5.4);
		s148.getVariables().setObject("PetalWidth", 2.3);
		Matrix input148 = MatrixFactory.linkToArray(new double[] { 6.2, 3.4, 5.4, 2.3 })
				.transpose();
		s148.getVariables().setMatrix(INPUT, input148);
		Matrix output148 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s148.getVariables().setMatrix(TARGET, output148);
		s148.setLabel("Iris-virginica");
		s148.setId("iris-148");
		iris.getSamples().add(s148);

		Sample s149 = SampleFactory.emptySample();
		s149.getVariables().setObject("SepalLength", 5.9);
		s149.getVariables().setObject("SepalWidth", 3.0);
		s149.getVariables().setObject("PetalLength", 5.1);
		s149.getVariables().setObject("PetalWidth", 1.8);
		Matrix input149 = MatrixFactory.linkToArray(new double[] { 5.9, 3.0, 5.1, 1.8 })
				.transpose();
		s149.getVariables().setMatrix(INPUT, input149);
		Matrix output149 = MatrixFactory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s149.getVariables().setMatrix(TARGET, output149);
		s149.setLabel("Iris-virginica");
		s149.setId("iris-149");
		iris.getSamples().add(s149);

		result.put(TARGET, iris);
		return result;
	}
}
