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

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();

		DataSet iris = DataSetFactory.classificationDataSet();
		iris.setLabel("Iris flower data set");
		iris.getVariableMap().setObject(Sample.URL, "http://archive.ics.uci.edu/ml/datasets/Iris");
		iris.setDescription("Fisher's Iris data set is a multivariate data set introduced by Sir Ronald Aylmer Fisher (1936) as an example of discriminant analysis.");

		Sample s0 = SampleFactory.emptySample();
		s0.getVariableMap().setObject("SepalLength", 5.1);
		s0.getVariableMap().setObject("SepalWidth", 3.5);
		s0.getVariableMap().setObject("PetalLength", 1.4);
		s0.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input0 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.5, 1.4, 0.2 }).transpose();
		s0.getVariableMap().setMatrix(INPUT, input0);
		Matrix output0 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s0.getVariableMap().setMatrix(TARGET, output0);
		s0.setLabel("Iris-setosa");
		s0.setId("iris-0");
		iris.getSampleMap().add(s0);

		Sample s1 = SampleFactory.emptySample();
		s1.getVariableMap().setObject("SepalLength", 4.9);
		s1.getVariableMap().setObject("SepalWidth", 3.0);
		s1.getVariableMap().setObject("PetalLength", 1.4);
		s1.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input1 = Matrix.Factory.linkToArray(new double[] { 4.9, 3.0, 1.4, 0.2 }).transpose();
		s1.getVariableMap().setMatrix(INPUT, input1);
		Matrix output1 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s1.getVariableMap().setMatrix(TARGET, output1);
		s1.setLabel("Iris-setosa");
		s1.setId("iris-1");
		iris.getSampleMap().add(s1);

		Sample s2 = SampleFactory.emptySample();
		s2.getVariableMap().setObject("SepalLength", 4.7);
		s2.getVariableMap().setObject("SepalWidth", 3.2);
		s2.getVariableMap().setObject("PetalLength", 1.3);
		s2.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input2 = Matrix.Factory.linkToArray(new double[] { 4.7, 3.2, 1.3, 0.2 }).transpose();
		s2.getVariableMap().setMatrix(INPUT, input2);
		Matrix output2 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s2.getVariableMap().setMatrix(TARGET, output2);
		s2.setLabel("Iris-setosa");
		s2.setId("iris-2");
		iris.getSampleMap().add(s2);

		Sample s3 = SampleFactory.emptySample();
		s3.getVariableMap().setObject("SepalLength", 4.6);
		s3.getVariableMap().setObject("SepalWidth", 3.1);
		s3.getVariableMap().setObject("PetalLength", 1.5);
		s3.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input3 = Matrix.Factory.linkToArray(new double[] { 4.6, 3.1, 1.5, 0.2 }).transpose();
		s3.getVariableMap().setMatrix(INPUT, input3);
		Matrix output3 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s3.getVariableMap().setMatrix(TARGET, output3);
		s3.setLabel("Iris-setosa");
		s3.setId("iris-3");
		iris.getSampleMap().add(s3);

		Sample s4 = SampleFactory.emptySample();
		s4.getVariableMap().setObject("SepalLength", 5.0);
		s4.getVariableMap().setObject("SepalWidth", 3.6);
		s4.getVariableMap().setObject("PetalLength", 1.4);
		s4.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input4 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.6, 1.4, 0.2 }).transpose();
		s4.getVariableMap().setMatrix(INPUT, input4);
		Matrix output4 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s4.getVariableMap().setMatrix(TARGET, output4);
		s4.setLabel("Iris-setosa");
		s4.setId("iris-4");
		iris.getSampleMap().add(s4);

		Sample s5 = SampleFactory.emptySample();
		s5.getVariableMap().setObject("SepalLength", 5.4);
		s5.getVariableMap().setObject("SepalWidth", 3.9);
		s5.getVariableMap().setObject("PetalLength", 1.7);
		s5.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input5 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.9, 1.7, 0.4 }).transpose();
		s5.getVariableMap().setMatrix(INPUT, input5);
		Matrix output5 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s5.getVariableMap().setMatrix(TARGET, output5);
		s5.setLabel("Iris-setosa");
		s5.setId("iris-5");
		iris.getSampleMap().add(s5);

		Sample s6 = SampleFactory.emptySample();
		s6.getVariableMap().setObject("SepalLength", 4.6);
		s6.getVariableMap().setObject("SepalWidth", 3.4);
		s6.getVariableMap().setObject("PetalLength", 1.4);
		s6.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input6 = Matrix.Factory.linkToArray(new double[] { 4.6, 3.4, 1.4, 0.3 }).transpose();
		s6.getVariableMap().setMatrix(INPUT, input6);
		Matrix output6 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s6.getVariableMap().setMatrix(TARGET, output6);
		s6.setLabel("Iris-setosa");
		s6.setId("iris-6");
		iris.getSampleMap().add(s6);

		Sample s7 = SampleFactory.emptySample();
		s7.getVariableMap().setObject("SepalLength", 5.0);
		s7.getVariableMap().setObject("SepalWidth", 3.4);
		s7.getVariableMap().setObject("PetalLength", 1.5);
		s7.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input7 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.4, 1.5, 0.2 }).transpose();
		s7.getVariableMap().setMatrix(INPUT, input7);
		Matrix output7 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s7.getVariableMap().setMatrix(TARGET, output7);
		s7.setLabel("Iris-setosa");
		s7.setId("iris-7");
		iris.getSampleMap().add(s7);

		Sample s8 = SampleFactory.emptySample();
		s8.getVariableMap().setObject("SepalLength", 4.4);
		s8.getVariableMap().setObject("SepalWidth", 2.9);
		s8.getVariableMap().setObject("PetalLength", 1.4);
		s8.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input8 = Matrix.Factory.linkToArray(new double[] { 4.4, 2.9, 1.4, 0.2 }).transpose();
		s8.getVariableMap().setMatrix(INPUT, input8);
		Matrix output8 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s8.getVariableMap().setMatrix(TARGET, output8);
		s8.setLabel("Iris-setosa");
		s8.setId("iris-8");
		iris.getSampleMap().add(s8);

		Sample s9 = SampleFactory.emptySample();
		s9.getVariableMap().setObject("SepalLength", 4.9);
		s9.getVariableMap().setObject("SepalWidth", 3.1);
		s9.getVariableMap().setObject("PetalLength", 1.5);
		s9.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input9 = Matrix.Factory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 }).transpose();
		s9.getVariableMap().setMatrix(INPUT, input9);
		Matrix output9 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s9.getVariableMap().setMatrix(TARGET, output9);
		s9.setLabel("Iris-setosa");
		s9.setId("iris-9");
		iris.getSampleMap().add(s9);

		Sample s10 = SampleFactory.emptySample();
		s10.getVariableMap().setObject("SepalLength", 5.4);
		s10.getVariableMap().setObject("SepalWidth", 3.7);
		s10.getVariableMap().setObject("PetalLength", 1.5);
		s10.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input10 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.7, 1.5, 0.2 })
				.transpose();
		s10.getVariableMap().setMatrix(INPUT, input10);
		Matrix output10 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s10.getVariableMap().setMatrix(TARGET, output10);
		s10.setLabel("Iris-setosa");
		s10.setId("iris-10");
		iris.getSampleMap().add(s10);

		Sample s11 = SampleFactory.emptySample();
		s11.getVariableMap().setObject("SepalLength", 4.8);
		s11.getVariableMap().setObject("SepalWidth", 3.4);
		s11.getVariableMap().setObject("PetalLength", 1.6);
		s11.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input11 = Matrix.Factory.linkToArray(new double[] { 4.8, 3.4, 1.6, 0.2 })
				.transpose();
		s11.getVariableMap().setMatrix(INPUT, input11);
		Matrix output11 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s11.getVariableMap().setMatrix(TARGET, output11);
		s11.setLabel("Iris-setosa");
		s11.setId("iris-11");
		iris.getSampleMap().add(s11);

		Sample s12 = SampleFactory.emptySample();
		s12.getVariableMap().setObject("SepalLength", 4.8);
		s12.getVariableMap().setObject("SepalWidth", 3.0);
		s12.getVariableMap().setObject("PetalLength", 1.4);
		s12.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input12 = Matrix.Factory.linkToArray(new double[] { 4.8, 3.0, 1.4, 0.1 })
				.transpose();
		s12.getVariableMap().setMatrix(INPUT, input12);
		Matrix output12 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s12.getVariableMap().setMatrix(TARGET, output12);
		s12.setLabel("Iris-setosa");
		s12.setId("iris-12");
		iris.getSampleMap().add(s12);

		Sample s13 = SampleFactory.emptySample();
		s13.getVariableMap().setObject("SepalLength", 4.3);
		s13.getVariableMap().setObject("SepalWidth", 3.0);
		s13.getVariableMap().setObject("PetalLength", 1.1);
		s13.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input13 = Matrix.Factory.linkToArray(new double[] { 4.3, 3.0, 1.1, 0.1 })
				.transpose();
		s13.getVariableMap().setMatrix(INPUT, input13);
		Matrix output13 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s13.getVariableMap().setMatrix(TARGET, output13);
		s13.setLabel("Iris-setosa");
		s13.setId("iris-13");
		iris.getSampleMap().add(s13);

		Sample s14 = SampleFactory.emptySample();
		s14.getVariableMap().setObject("SepalLength", 5.8);
		s14.getVariableMap().setObject("SepalWidth", 4.0);
		s14.getVariableMap().setObject("PetalLength", 1.2);
		s14.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input14 = Matrix.Factory.linkToArray(new double[] { 5.8, 4.0, 1.2, 0.2 })
				.transpose();
		s14.getVariableMap().setMatrix(INPUT, input14);
		Matrix output14 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s14.getVariableMap().setMatrix(TARGET, output14);
		s14.setLabel("Iris-setosa");
		s14.setId("iris-14");
		iris.getSampleMap().add(s14);

		Sample s15 = SampleFactory.emptySample();
		s15.getVariableMap().setObject("SepalLength", 5.7);
		s15.getVariableMap().setObject("SepalWidth", 4.4);
		s15.getVariableMap().setObject("PetalLength", 1.5);
		s15.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input15 = Matrix.Factory.linkToArray(new double[] { 5.7, 4.4, 1.5, 0.4 })
				.transpose();
		s15.getVariableMap().setMatrix(INPUT, input15);
		Matrix output15 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s15.getVariableMap().setMatrix(TARGET, output15);
		s15.setLabel("Iris-setosa");
		s15.setId("iris-15");
		iris.getSampleMap().add(s15);

		Sample s16 = SampleFactory.emptySample();
		s16.getVariableMap().setObject("SepalLength", 5.4);
		s16.getVariableMap().setObject("SepalWidth", 3.9);
		s16.getVariableMap().setObject("PetalLength", 1.3);
		s16.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input16 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.9, 1.3, 0.4 })
				.transpose();
		s16.getVariableMap().setMatrix(INPUT, input16);
		Matrix output16 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s16.getVariableMap().setMatrix(TARGET, output16);
		s16.setLabel("Iris-setosa");
		s16.setId("iris-16");
		iris.getSampleMap().add(s16);

		Sample s17 = SampleFactory.emptySample();
		s17.getVariableMap().setObject("SepalLength", 5.1);
		s17.getVariableMap().setObject("SepalWidth", 3.5);
		s17.getVariableMap().setObject("PetalLength", 1.4);
		s17.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input17 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.5, 1.4, 0.3 })
				.transpose();
		s17.getVariableMap().setMatrix(INPUT, input17);
		Matrix output17 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s17.getVariableMap().setMatrix(TARGET, output17);
		s17.setLabel("Iris-setosa");
		s17.setId("iris-17");
		iris.getSampleMap().add(s17);

		Sample s18 = SampleFactory.emptySample();
		s18.getVariableMap().setObject("SepalLength", 5.7);
		s18.getVariableMap().setObject("SepalWidth", 3.8);
		s18.getVariableMap().setObject("PetalLength", 1.7);
		s18.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input18 = Matrix.Factory.linkToArray(new double[] { 5.7, 3.8, 1.7, 0.3 })
				.transpose();
		s18.getVariableMap().setMatrix(INPUT, input18);
		Matrix output18 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s18.getVariableMap().setMatrix(TARGET, output18);
		s18.setLabel("Iris-setosa");
		s18.setId("iris-18");
		iris.getSampleMap().add(s18);

		Sample s19 = SampleFactory.emptySample();
		s19.getVariableMap().setObject("SepalLength", 5.1);
		s19.getVariableMap().setObject("SepalWidth", 3.8);
		s19.getVariableMap().setObject("PetalLength", 1.5);
		s19.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input19 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.8, 1.5, 0.3 })
				.transpose();
		s19.getVariableMap().setMatrix(INPUT, input19);
		Matrix output19 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s19.getVariableMap().setMatrix(TARGET, output19);
		s19.setLabel("Iris-setosa");
		s19.setId("iris-19");
		iris.getSampleMap().add(s19);

		Sample s20 = SampleFactory.emptySample();
		s20.getVariableMap().setObject("SepalLength", 5.4);
		s20.getVariableMap().setObject("SepalWidth", 3.4);
		s20.getVariableMap().setObject("PetalLength", 1.7);
		s20.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input20 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.4, 1.7, 0.2 })
				.transpose();
		s20.getVariableMap().setMatrix(INPUT, input20);
		Matrix output20 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s20.getVariableMap().setMatrix(TARGET, output20);
		s20.setLabel("Iris-setosa");
		s20.setId("iris-20");
		iris.getSampleMap().add(s20);

		Sample s21 = SampleFactory.emptySample();
		s21.getVariableMap().setObject("SepalLength", 5.1);
		s21.getVariableMap().setObject("SepalWidth", 3.7);
		s21.getVariableMap().setObject("PetalLength", 1.5);
		s21.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input21 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.7, 1.5, 0.4 })
				.transpose();
		s21.getVariableMap().setMatrix(INPUT, input21);
		Matrix output21 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s21.getVariableMap().setMatrix(TARGET, output21);
		s21.setLabel("Iris-setosa");
		s21.setId("iris-21");
		iris.getSampleMap().add(s21);

		Sample s22 = SampleFactory.emptySample();
		s22.getVariableMap().setObject("SepalLength", 4.6);
		s22.getVariableMap().setObject("SepalWidth", 3.6);
		s22.getVariableMap().setObject("PetalLength", 1.0);
		s22.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input22 = Matrix.Factory.linkToArray(new double[] { 4.6, 3.6, 1.0, 0.2 })
				.transpose();
		s22.getVariableMap().setMatrix(INPUT, input22);
		Matrix output22 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s22.getVariableMap().setMatrix(TARGET, output22);
		s22.setLabel("Iris-setosa");
		s22.setId("iris-22");
		iris.getSampleMap().add(s22);

		Sample s23 = SampleFactory.emptySample();
		s23.getVariableMap().setObject("SepalLength", 5.1);
		s23.getVariableMap().setObject("SepalWidth", 3.3);
		s23.getVariableMap().setObject("PetalLength", 1.7);
		s23.getVariableMap().setObject("PetalWidth", 0.5);
		Matrix input23 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.3, 1.7, 0.5 })
				.transpose();
		s23.getVariableMap().setMatrix(INPUT, input23);
		Matrix output23 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s23.getVariableMap().setMatrix(TARGET, output23);
		s23.setLabel("Iris-setosa");
		s23.setId("iris-23");
		iris.getSampleMap().add(s23);

		Sample s24 = SampleFactory.emptySample();
		s24.getVariableMap().setObject("SepalLength", 4.8);
		s24.getVariableMap().setObject("SepalWidth", 3.4);
		s24.getVariableMap().setObject("PetalLength", 1.9);
		s24.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input24 = Matrix.Factory.linkToArray(new double[] { 4.8, 3.4, 1.9, 0.2 })
				.transpose();
		s24.getVariableMap().setMatrix(INPUT, input24);
		Matrix output24 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s24.getVariableMap().setMatrix(TARGET, output24);
		s24.setLabel("Iris-setosa");
		s24.setId("iris-24");
		iris.getSampleMap().add(s24);

		Sample s25 = SampleFactory.emptySample();
		s25.getVariableMap().setObject("SepalLength", 5.0);
		s25.getVariableMap().setObject("SepalWidth", 3.0);
		s25.getVariableMap().setObject("PetalLength", 1.6);
		s25.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input25 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.0, 1.6, 0.2 })
				.transpose();
		s25.getVariableMap().setMatrix(INPUT, input25);
		Matrix output25 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s25.getVariableMap().setMatrix(TARGET, output25);
		s25.setLabel("Iris-setosa");
		s25.setId("iris-25");
		iris.getSampleMap().add(s25);

		Sample s26 = SampleFactory.emptySample();
		s26.getVariableMap().setObject("SepalLength", 5.0);
		s26.getVariableMap().setObject("SepalWidth", 3.4);
		s26.getVariableMap().setObject("PetalLength", 1.6);
		s26.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input26 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.4, 1.6, 0.4 })
				.transpose();
		s26.getVariableMap().setMatrix(INPUT, input26);
		Matrix output26 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s26.getVariableMap().setMatrix(TARGET, output26);
		s26.setLabel("Iris-setosa");
		s26.setId("iris-26");
		iris.getSampleMap().add(s26);

		Sample s27 = SampleFactory.emptySample();
		s27.getVariableMap().setObject("SepalLength", 5.2);
		s27.getVariableMap().setObject("SepalWidth", 3.5);
		s27.getVariableMap().setObject("PetalLength", 1.5);
		s27.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input27 = Matrix.Factory.linkToArray(new double[] { 5.2, 3.5, 1.5, 0.2 })
				.transpose();
		s27.getVariableMap().setMatrix(INPUT, input27);
		Matrix output27 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s27.getVariableMap().setMatrix(TARGET, output27);
		s27.setLabel("Iris-setosa");
		s27.setId("iris-27");
		iris.getSampleMap().add(s27);

		Sample s28 = SampleFactory.emptySample();
		s28.getVariableMap().setObject("SepalLength", 5.2);
		s28.getVariableMap().setObject("SepalWidth", 3.4);
		s28.getVariableMap().setObject("PetalLength", 1.4);
		s28.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input28 = Matrix.Factory.linkToArray(new double[] { 5.2, 3.4, 1.4, 0.2 })
				.transpose();
		s28.getVariableMap().setMatrix(INPUT, input28);
		Matrix output28 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s28.getVariableMap().setMatrix(TARGET, output28);
		s28.setLabel("Iris-setosa");
		s28.setId("iris-28");
		iris.getSampleMap().add(s28);

		Sample s29 = SampleFactory.emptySample();
		s29.getVariableMap().setObject("SepalLength", 4.7);
		s29.getVariableMap().setObject("SepalWidth", 3.2);
		s29.getVariableMap().setObject("PetalLength", 1.6);
		s29.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input29 = Matrix.Factory.linkToArray(new double[] { 4.7, 3.2, 1.6, 0.2 })
				.transpose();
		s29.getVariableMap().setMatrix(INPUT, input29);
		Matrix output29 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s29.getVariableMap().setMatrix(TARGET, output29);
		s29.setLabel("Iris-setosa");
		s29.setId("iris-29");
		iris.getSampleMap().add(s29);

		Sample s30 = SampleFactory.emptySample();
		s30.getVariableMap().setObject("SepalLength", 4.8);
		s30.getVariableMap().setObject("SepalWidth", 3.1);
		s30.getVariableMap().setObject("PetalLength", 1.6);
		s30.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input30 = Matrix.Factory.linkToArray(new double[] { 4.8, 3.1, 1.6, 0.2 })
				.transpose();
		s30.getVariableMap().setMatrix(INPUT, input30);
		Matrix output30 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s30.getVariableMap().setMatrix(TARGET, output30);
		s30.setLabel("Iris-setosa");
		s30.setId("iris-30");
		iris.getSampleMap().add(s30);

		Sample s31 = SampleFactory.emptySample();
		s31.getVariableMap().setObject("SepalLength", 5.4);
		s31.getVariableMap().setObject("SepalWidth", 3.4);
		s31.getVariableMap().setObject("PetalLength", 1.5);
		s31.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input31 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.4, 1.5, 0.4 })
				.transpose();
		s31.getVariableMap().setMatrix(INPUT, input31);
		Matrix output31 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s31.getVariableMap().setMatrix(TARGET, output31);
		s31.setLabel("Iris-setosa");
		s31.setId("iris-31");
		iris.getSampleMap().add(s31);

		Sample s32 = SampleFactory.emptySample();
		s32.getVariableMap().setObject("SepalLength", 5.2);
		s32.getVariableMap().setObject("SepalWidth", 4.1);
		s32.getVariableMap().setObject("PetalLength", 1.5);
		s32.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input32 = Matrix.Factory.linkToArray(new double[] { 5.2, 4.1, 1.5, 0.1 })
				.transpose();
		s32.getVariableMap().setMatrix(INPUT, input32);
		Matrix output32 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s32.getVariableMap().setMatrix(TARGET, output32);
		s32.setLabel("Iris-setosa");
		s32.setId("iris-32");
		iris.getSampleMap().add(s32);

		Sample s33 = SampleFactory.emptySample();
		s33.getVariableMap().setObject("SepalLength", 5.5);
		s33.getVariableMap().setObject("SepalWidth", 4.2);
		s33.getVariableMap().setObject("PetalLength", 1.4);
		s33.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input33 = Matrix.Factory.linkToArray(new double[] { 5.5, 4.2, 1.4, 0.2 })
				.transpose();
		s33.getVariableMap().setMatrix(INPUT, input33);
		Matrix output33 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s33.getVariableMap().setMatrix(TARGET, output33);
		s33.setLabel("Iris-setosa");
		s33.setId("iris-33");
		iris.getSampleMap().add(s33);

		Sample s34 = SampleFactory.emptySample();
		s34.getVariableMap().setObject("SepalLength", 4.9);
		s34.getVariableMap().setObject("SepalWidth", 3.1);
		s34.getVariableMap().setObject("PetalLength", 1.5);
		s34.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input34 = Matrix.Factory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 })
				.transpose();
		s34.getVariableMap().setMatrix(INPUT, input34);
		Matrix output34 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s34.getVariableMap().setMatrix(TARGET, output34);
		s34.setLabel("Iris-setosa");
		s34.setId("iris-34");
		iris.getSampleMap().add(s34);

		Sample s35 = SampleFactory.emptySample();
		s35.getVariableMap().setObject("SepalLength", 5.0);
		s35.getVariableMap().setObject("SepalWidth", 3.2);
		s35.getVariableMap().setObject("PetalLength", 1.2);
		s35.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input35 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.2, 1.2, 0.2 })
				.transpose();
		s35.getVariableMap().setMatrix(INPUT, input35);
		Matrix output35 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s35.getVariableMap().setMatrix(TARGET, output35);
		s35.setLabel("Iris-setosa");
		s35.setId("iris-35");
		iris.getSampleMap().add(s35);

		Sample s36 = SampleFactory.emptySample();
		s36.getVariableMap().setObject("SepalLength", 5.5);
		s36.getVariableMap().setObject("SepalWidth", 3.5);
		s36.getVariableMap().setObject("PetalLength", 1.3);
		s36.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input36 = Matrix.Factory.linkToArray(new double[] { 5.5, 3.5, 1.3, 0.2 })
				.transpose();
		s36.getVariableMap().setMatrix(INPUT, input36);
		Matrix output36 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s36.getVariableMap().setMatrix(TARGET, output36);
		s36.setLabel("Iris-setosa");
		s36.setId("iris-36");
		iris.getSampleMap().add(s36);

		Sample s37 = SampleFactory.emptySample();
		s37.getVariableMap().setObject("SepalLength", 4.9);
		s37.getVariableMap().setObject("SepalWidth", 3.1);
		s37.getVariableMap().setObject("PetalLength", 1.5);
		s37.getVariableMap().setObject("PetalWidth", 0.1);
		Matrix input37 = Matrix.Factory.linkToArray(new double[] { 4.9, 3.1, 1.5, 0.1 })
				.transpose();
		s37.getVariableMap().setMatrix(INPUT, input37);
		Matrix output37 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s37.getVariableMap().setMatrix(TARGET, output37);
		s37.setLabel("Iris-setosa");
		s37.setId("iris-37");
		iris.getSampleMap().add(s37);

		Sample s38 = SampleFactory.emptySample();
		s38.getVariableMap().setObject("SepalLength", 4.4);
		s38.getVariableMap().setObject("SepalWidth", 3.0);
		s38.getVariableMap().setObject("PetalLength", 1.3);
		s38.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input38 = Matrix.Factory.linkToArray(new double[] { 4.4, 3.0, 1.3, 0.2 })
				.transpose();
		s38.getVariableMap().setMatrix(INPUT, input38);
		Matrix output38 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s38.getVariableMap().setMatrix(TARGET, output38);
		s38.setLabel("Iris-setosa");
		s38.setId("iris-38");
		iris.getSampleMap().add(s38);

		Sample s39 = SampleFactory.emptySample();
		s39.getVariableMap().setObject("SepalLength", 5.1);
		s39.getVariableMap().setObject("SepalWidth", 3.4);
		s39.getVariableMap().setObject("PetalLength", 1.5);
		s39.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input39 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.4, 1.5, 0.2 })
				.transpose();
		s39.getVariableMap().setMatrix(INPUT, input39);
		Matrix output39 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s39.getVariableMap().setMatrix(TARGET, output39);
		s39.setLabel("Iris-setosa");
		s39.setId("iris-39");
		iris.getSampleMap().add(s39);

		Sample s40 = SampleFactory.emptySample();
		s40.getVariableMap().setObject("SepalLength", 5.0);
		s40.getVariableMap().setObject("SepalWidth", 3.5);
		s40.getVariableMap().setObject("PetalLength", 1.3);
		s40.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input40 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.5, 1.3, 0.3 })
				.transpose();
		s40.getVariableMap().setMatrix(INPUT, input40);
		Matrix output40 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s40.getVariableMap().setMatrix(TARGET, output40);
		s40.setLabel("Iris-setosa");
		s40.setId("iris-40");
		iris.getSampleMap().add(s40);

		Sample s41 = SampleFactory.emptySample();
		s41.getVariableMap().setObject("SepalLength", 4.5);
		s41.getVariableMap().setObject("SepalWidth", 2.3);
		s41.getVariableMap().setObject("PetalLength", 1.3);
		s41.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input41 = Matrix.Factory.linkToArray(new double[] { 4.5, 2.3, 1.3, 0.3 })
				.transpose();
		s41.getVariableMap().setMatrix(INPUT, input41);
		Matrix output41 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s41.getVariableMap().setMatrix(TARGET, output41);
		s41.setLabel("Iris-setosa");
		s41.setId("iris-41");
		iris.getSampleMap().add(s41);

		Sample s42 = SampleFactory.emptySample();
		s42.getVariableMap().setObject("SepalLength", 4.4);
		s42.getVariableMap().setObject("SepalWidth", 3.2);
		s42.getVariableMap().setObject("PetalLength", 1.3);
		s42.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input42 = Matrix.Factory.linkToArray(new double[] { 4.4, 3.2, 1.3, 0.2 })
				.transpose();
		s42.getVariableMap().setMatrix(INPUT, input42);
		Matrix output42 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s42.getVariableMap().setMatrix(TARGET, output42);
		s42.setLabel("Iris-setosa");
		s42.setId("iris-42");
		iris.getSampleMap().add(s42);

		Sample s43 = SampleFactory.emptySample();
		s43.getVariableMap().setObject("SepalLength", 5.0);
		s43.getVariableMap().setObject("SepalWidth", 3.5);
		s43.getVariableMap().setObject("PetalLength", 1.6);
		s43.getVariableMap().setObject("PetalWidth", 0.6);
		Matrix input43 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.5, 1.6, 0.6 })
				.transpose();
		s43.getVariableMap().setMatrix(INPUT, input43);
		Matrix output43 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s43.getVariableMap().setMatrix(TARGET, output43);
		s43.setLabel("Iris-setosa");
		s43.setId("iris-43");
		iris.getSampleMap().add(s43);

		Sample s44 = SampleFactory.emptySample();
		s44.getVariableMap().setObject("SepalLength", 5.1);
		s44.getVariableMap().setObject("SepalWidth", 3.8);
		s44.getVariableMap().setObject("PetalLength", 1.9);
		s44.getVariableMap().setObject("PetalWidth", 0.4);
		Matrix input44 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.8, 1.9, 0.4 })
				.transpose();
		s44.getVariableMap().setMatrix(INPUT, input44);
		Matrix output44 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s44.getVariableMap().setMatrix(TARGET, output44);
		s44.setLabel("Iris-setosa");
		s44.setId("iris-44");
		iris.getSampleMap().add(s44);

		Sample s45 = SampleFactory.emptySample();
		s45.getVariableMap().setObject("SepalLength", 4.8);
		s45.getVariableMap().setObject("SepalWidth", 3.0);
		s45.getVariableMap().setObject("PetalLength", 1.4);
		s45.getVariableMap().setObject("PetalWidth", 0.3);
		Matrix input45 = Matrix.Factory.linkToArray(new double[] { 4.8, 3.0, 1.4, 0.3 })
				.transpose();
		s45.getVariableMap().setMatrix(INPUT, input45);
		Matrix output45 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s45.getVariableMap().setMatrix(TARGET, output45);
		s45.setLabel("Iris-setosa");
		s45.setId("iris-45");
		iris.getSampleMap().add(s45);

		Sample s46 = SampleFactory.emptySample();
		s46.getVariableMap().setObject("SepalLength", 5.1);
		s46.getVariableMap().setObject("SepalWidth", 3.8);
		s46.getVariableMap().setObject("PetalLength", 1.6);
		s46.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input46 = Matrix.Factory.linkToArray(new double[] { 5.1, 3.8, 1.6, 0.2 })
				.transpose();
		s46.getVariableMap().setMatrix(INPUT, input46);
		Matrix output46 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s46.getVariableMap().setMatrix(TARGET, output46);
		s46.setLabel("Iris-setosa");
		s46.setId("iris-46");
		iris.getSampleMap().add(s46);

		Sample s47 = SampleFactory.emptySample();
		s47.getVariableMap().setObject("SepalLength", 4.6);
		s47.getVariableMap().setObject("SepalWidth", 3.2);
		s47.getVariableMap().setObject("PetalLength", 1.4);
		s47.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input47 = Matrix.Factory.linkToArray(new double[] { 4.6, 3.2, 1.4, 0.2 })
				.transpose();
		s47.getVariableMap().setMatrix(INPUT, input47);
		Matrix output47 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s47.getVariableMap().setMatrix(TARGET, output47);
		s47.setLabel("Iris-setosa");
		s47.setId("iris-47");
		iris.getSampleMap().add(s47);

		Sample s48 = SampleFactory.emptySample();
		s48.getVariableMap().setObject("SepalLength", 5.3);
		s48.getVariableMap().setObject("SepalWidth", 3.7);
		s48.getVariableMap().setObject("PetalLength", 1.5);
		s48.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input48 = Matrix.Factory.linkToArray(new double[] { 5.3, 3.7, 1.5, 0.2 })
				.transpose();
		s48.getVariableMap().setMatrix(INPUT, input48);
		Matrix output48 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s48.getVariableMap().setMatrix(TARGET, output48);
		s48.setLabel("Iris-setosa");
		s48.setId("iris-48");
		iris.getSampleMap().add(s48);

		Sample s49 = SampleFactory.emptySample();
		s49.getVariableMap().setObject("SepalLength", 5.0);
		s49.getVariableMap().setObject("SepalWidth", 3.3);
		s49.getVariableMap().setObject("PetalLength", 1.4);
		s49.getVariableMap().setObject("PetalWidth", 0.2);
		Matrix input49 = Matrix.Factory.linkToArray(new double[] { 5.0, 3.3, 1.4, 0.2 })
				.transpose();
		s49.getVariableMap().setMatrix(INPUT, input49);
		Matrix output49 = Matrix.Factory.linkToArray(new double[] { 1, 0, 0 }).transpose();
		s49.getVariableMap().setMatrix(TARGET, output49);
		s49.setLabel("Iris-setosa");
		s49.setId("iris-49");
		iris.getSampleMap().add(s49);

		Sample s50 = SampleFactory.emptySample();
		s50.getVariableMap().setObject("SepalLength", 7.0);
		s50.getVariableMap().setObject("SepalWidth", 3.2);
		s50.getVariableMap().setObject("PetalLength", 4.7);
		s50.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input50 = Matrix.Factory.linkToArray(new double[] { 7.0, 3.2, 4.7, 1.4 })
				.transpose();
		s50.getVariableMap().setMatrix(INPUT, input50);
		Matrix output50 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s50.getVariableMap().setMatrix(TARGET, output50);
		s50.setLabel("Iris-versicolor");
		s50.setId("iris-50");
		iris.getSampleMap().add(s50);

		Sample s51 = SampleFactory.emptySample();
		s51.getVariableMap().setObject("SepalLength", 6.4);
		s51.getVariableMap().setObject("SepalWidth", 3.2);
		s51.getVariableMap().setObject("PetalLength", 4.5);
		s51.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input51 = Matrix.Factory.linkToArray(new double[] { 6.4, 3.2, 4.5, 1.5 })
				.transpose();
		s51.getVariableMap().setMatrix(INPUT, input51);
		Matrix output51 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s51.getVariableMap().setMatrix(TARGET, output51);
		s51.setLabel("Iris-versicolor");
		s51.setId("iris-51");
		iris.getSampleMap().add(s51);

		Sample s52 = SampleFactory.emptySample();
		s52.getVariableMap().setObject("SepalLength", 6.9);
		s52.getVariableMap().setObject("SepalWidth", 3.1);
		s52.getVariableMap().setObject("PetalLength", 4.9);
		s52.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input52 = Matrix.Factory.linkToArray(new double[] { 6.9, 3.1, 4.9, 1.5 })
				.transpose();
		s52.getVariableMap().setMatrix(INPUT, input52);
		Matrix output52 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s52.getVariableMap().setMatrix(TARGET, output52);
		s52.setLabel("Iris-versicolor");
		s52.setId("iris-52");
		iris.getSampleMap().add(s52);

		Sample s53 = SampleFactory.emptySample();
		s53.getVariableMap().setObject("SepalLength", 5.5);
		s53.getVariableMap().setObject("SepalWidth", 2.3);
		s53.getVariableMap().setObject("PetalLength", 4.0);
		s53.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input53 = Matrix.Factory.linkToArray(new double[] { 5.5, 2.3, 4.0, 1.3 })
				.transpose();
		s53.getVariableMap().setMatrix(INPUT, input53);
		Matrix output53 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s53.getVariableMap().setMatrix(TARGET, output53);
		s53.setLabel("Iris-versicolor");
		s53.setId("iris-53");
		iris.getSampleMap().add(s53);

		Sample s54 = SampleFactory.emptySample();
		s54.getVariableMap().setObject("SepalLength", 6.5);
		s54.getVariableMap().setObject("SepalWidth", 2.8);
		s54.getVariableMap().setObject("PetalLength", 4.6);
		s54.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input54 = Matrix.Factory.linkToArray(new double[] { 6.5, 2.8, 4.6, 1.5 })
				.transpose();
		s54.getVariableMap().setMatrix(INPUT, input54);
		Matrix output54 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s54.getVariableMap().setMatrix(TARGET, output54);
		s54.setLabel("Iris-versicolor");
		s54.setId("iris-54");
		iris.getSampleMap().add(s54);

		Sample s55 = SampleFactory.emptySample();
		s55.getVariableMap().setObject("SepalLength", 5.7);
		s55.getVariableMap().setObject("SepalWidth", 2.8);
		s55.getVariableMap().setObject("PetalLength", 4.5);
		s55.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input55 = Matrix.Factory.linkToArray(new double[] { 5.7, 2.8, 4.5, 1.3 })
				.transpose();
		s55.getVariableMap().setMatrix(INPUT, input55);
		Matrix output55 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s55.getVariableMap().setMatrix(TARGET, output55);
		s55.setLabel("Iris-versicolor");
		s55.setId("iris-55");
		iris.getSampleMap().add(s55);

		Sample s56 = SampleFactory.emptySample();
		s56.getVariableMap().setObject("SepalLength", 6.3);
		s56.getVariableMap().setObject("SepalWidth", 3.3);
		s56.getVariableMap().setObject("PetalLength", 4.7);
		s56.getVariableMap().setObject("PetalWidth", 1.6);
		Matrix input56 = Matrix.Factory.linkToArray(new double[] { 6.3, 3.3, 4.7, 1.6 })
				.transpose();
		s56.getVariableMap().setMatrix(INPUT, input56);
		Matrix output56 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s56.getVariableMap().setMatrix(TARGET, output56);
		s56.setLabel("Iris-versicolor");
		s56.setId("iris-56");
		iris.getSampleMap().add(s56);

		Sample s57 = SampleFactory.emptySample();
		s57.getVariableMap().setObject("SepalLength", 4.9);
		s57.getVariableMap().setObject("SepalWidth", 2.4);
		s57.getVariableMap().setObject("PetalLength", 3.3);
		s57.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input57 = Matrix.Factory.linkToArray(new double[] { 4.9, 2.4, 3.3, 1.0 })
				.transpose();
		s57.getVariableMap().setMatrix(INPUT, input57);
		Matrix output57 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s57.getVariableMap().setMatrix(TARGET, output57);
		s57.setLabel("Iris-versicolor");
		s57.setId("iris-57");
		iris.getSampleMap().add(s57);

		Sample s58 = SampleFactory.emptySample();
		s58.getVariableMap().setObject("SepalLength", 6.6);
		s58.getVariableMap().setObject("SepalWidth", 2.9);
		s58.getVariableMap().setObject("PetalLength", 4.6);
		s58.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input58 = Matrix.Factory.linkToArray(new double[] { 6.6, 2.9, 4.6, 1.3 })
				.transpose();
		s58.getVariableMap().setMatrix(INPUT, input58);
		Matrix output58 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s58.getVariableMap().setMatrix(TARGET, output58);
		s58.setLabel("Iris-versicolor");
		s58.setId("iris-58");
		iris.getSampleMap().add(s58);

		Sample s59 = SampleFactory.emptySample();
		s59.getVariableMap().setObject("SepalLength", 5.2);
		s59.getVariableMap().setObject("SepalWidth", 2.7);
		s59.getVariableMap().setObject("PetalLength", 3.9);
		s59.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input59 = Matrix.Factory.linkToArray(new double[] { 5.2, 2.7, 3.9, 1.4 })
				.transpose();
		s59.getVariableMap().setMatrix(INPUT, input59);
		Matrix output59 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s59.getVariableMap().setMatrix(TARGET, output59);
		s59.setLabel("Iris-versicolor");
		s59.setId("iris-59");
		iris.getSampleMap().add(s59);

		Sample s60 = SampleFactory.emptySample();
		s60.getVariableMap().setObject("SepalLength", 5.0);
		s60.getVariableMap().setObject("SepalWidth", 2.0);
		s60.getVariableMap().setObject("PetalLength", 3.5);
		s60.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input60 = Matrix.Factory.linkToArray(new double[] { 5.0, 2.0, 3.5, 1.0 })
				.transpose();
		s60.getVariableMap().setMatrix(INPUT, input60);
		Matrix output60 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s60.getVariableMap().setMatrix(TARGET, output60);
		s60.setLabel("Iris-versicolor");
		s60.setId("iris-60");
		iris.getSampleMap().add(s60);

		Sample s61 = SampleFactory.emptySample();
		s61.getVariableMap().setObject("SepalLength", 5.9);
		s61.getVariableMap().setObject("SepalWidth", 3.0);
		s61.getVariableMap().setObject("PetalLength", 4.2);
		s61.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input61 = Matrix.Factory.linkToArray(new double[] { 5.9, 3.0, 4.2, 1.5 })
				.transpose();
		s61.getVariableMap().setMatrix(INPUT, input61);
		Matrix output61 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s61.getVariableMap().setMatrix(TARGET, output61);
		s61.setLabel("Iris-versicolor");
		s61.setId("iris-61");
		iris.getSampleMap().add(s61);

		Sample s62 = SampleFactory.emptySample();
		s62.getVariableMap().setObject("SepalLength", 6.0);
		s62.getVariableMap().setObject("SepalWidth", 2.2);
		s62.getVariableMap().setObject("PetalLength", 4.0);
		s62.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input62 = Matrix.Factory.linkToArray(new double[] { 6.0, 2.2, 4.0, 1.0 })
				.transpose();
		s62.getVariableMap().setMatrix(INPUT, input62);
		Matrix output62 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s62.getVariableMap().setMatrix(TARGET, output62);
		s62.setLabel("Iris-versicolor");
		s62.setId("iris-62");
		iris.getSampleMap().add(s62);

		Sample s63 = SampleFactory.emptySample();
		s63.getVariableMap().setObject("SepalLength", 6.1);
		s63.getVariableMap().setObject("SepalWidth", 2.9);
		s63.getVariableMap().setObject("PetalLength", 4.7);
		s63.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input63 = Matrix.Factory.linkToArray(new double[] { 6.1, 2.9, 4.7, 1.4 })
				.transpose();
		s63.getVariableMap().setMatrix(INPUT, input63);
		Matrix output63 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s63.getVariableMap().setMatrix(TARGET, output63);
		s63.setLabel("Iris-versicolor");
		s63.setId("iris-63");
		iris.getSampleMap().add(s63);

		Sample s64 = SampleFactory.emptySample();
		s64.getVariableMap().setObject("SepalLength", 5.6);
		s64.getVariableMap().setObject("SepalWidth", 2.9);
		s64.getVariableMap().setObject("PetalLength", 3.6);
		s64.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input64 = Matrix.Factory.linkToArray(new double[] { 5.6, 2.9, 3.6, 1.3 })
				.transpose();
		s64.getVariableMap().setMatrix(INPUT, input64);
		Matrix output64 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s64.getVariableMap().setMatrix(TARGET, output64);
		s64.setLabel("Iris-versicolor");
		s64.setId("iris-64");
		iris.getSampleMap().add(s64);

		Sample s65 = SampleFactory.emptySample();
		s65.getVariableMap().setObject("SepalLength", 6.7);
		s65.getVariableMap().setObject("SepalWidth", 3.1);
		s65.getVariableMap().setObject("PetalLength", 4.4);
		s65.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input65 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.1, 4.4, 1.4 })
				.transpose();
		s65.getVariableMap().setMatrix(INPUT, input65);
		Matrix output65 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s65.getVariableMap().setMatrix(TARGET, output65);
		s65.setLabel("Iris-versicolor");
		s65.setId("iris-65");
		iris.getSampleMap().add(s65);

		Sample s66 = SampleFactory.emptySample();
		s66.getVariableMap().setObject("SepalLength", 5.6);
		s66.getVariableMap().setObject("SepalWidth", 3.0);
		s66.getVariableMap().setObject("PetalLength", 4.5);
		s66.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input66 = Matrix.Factory.linkToArray(new double[] { 5.6, 3.0, 4.5, 1.5 })
				.transpose();
		s66.getVariableMap().setMatrix(INPUT, input66);
		Matrix output66 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s66.getVariableMap().setMatrix(TARGET, output66);
		s66.setLabel("Iris-versicolor");
		s66.setId("iris-66");
		iris.getSampleMap().add(s66);

		Sample s67 = SampleFactory.emptySample();
		s67.getVariableMap().setObject("SepalLength", 5.8);
		s67.getVariableMap().setObject("SepalWidth", 2.7);
		s67.getVariableMap().setObject("PetalLength", 4.1);
		s67.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input67 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.7, 4.1, 1.0 })
				.transpose();
		s67.getVariableMap().setMatrix(INPUT, input67);
		Matrix output67 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s67.getVariableMap().setMatrix(TARGET, output67);
		s67.setLabel("Iris-versicolor");
		s67.setId("iris-67");
		iris.getSampleMap().add(s67);

		Sample s68 = SampleFactory.emptySample();
		s68.getVariableMap().setObject("SepalLength", 6.2);
		s68.getVariableMap().setObject("SepalWidth", 2.2);
		s68.getVariableMap().setObject("PetalLength", 4.5);
		s68.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input68 = Matrix.Factory.linkToArray(new double[] { 6.2, 2.2, 4.5, 1.5 })
				.transpose();
		s68.getVariableMap().setMatrix(INPUT, input68);
		Matrix output68 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s68.getVariableMap().setMatrix(TARGET, output68);
		s68.setLabel("Iris-versicolor");
		s68.setId("iris-68");
		iris.getSampleMap().add(s68);

		Sample s69 = SampleFactory.emptySample();
		s69.getVariableMap().setObject("SepalLength", 5.6);
		s69.getVariableMap().setObject("SepalWidth", 2.5);
		s69.getVariableMap().setObject("PetalLength", 3.9);
		s69.getVariableMap().setObject("PetalWidth", 1.1);
		Matrix input69 = Matrix.Factory.linkToArray(new double[] { 5.6, 2.5, 3.9, 1.1 })
				.transpose();
		s69.getVariableMap().setMatrix(INPUT, input69);
		Matrix output69 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s69.getVariableMap().setMatrix(TARGET, output69);
		s69.setLabel("Iris-versicolor");
		s69.setId("iris-69");
		iris.getSampleMap().add(s69);

		Sample s70 = SampleFactory.emptySample();
		s70.getVariableMap().setObject("SepalLength", 5.9);
		s70.getVariableMap().setObject("SepalWidth", 3.2);
		s70.getVariableMap().setObject("PetalLength", 4.8);
		s70.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input70 = Matrix.Factory.linkToArray(new double[] { 5.9, 3.2, 4.8, 1.8 })
				.transpose();
		s70.getVariableMap().setMatrix(INPUT, input70);
		Matrix output70 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s70.getVariableMap().setMatrix(TARGET, output70);
		s70.setLabel("Iris-versicolor");
		s70.setId("iris-70");
		iris.getSampleMap().add(s70);

		Sample s71 = SampleFactory.emptySample();
		s71.getVariableMap().setObject("SepalLength", 6.1);
		s71.getVariableMap().setObject("SepalWidth", 2.8);
		s71.getVariableMap().setObject("PetalLength", 4.0);
		s71.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input71 = Matrix.Factory.linkToArray(new double[] { 6.1, 2.8, 4.0, 1.3 })
				.transpose();
		s71.getVariableMap().setMatrix(INPUT, input71);
		Matrix output71 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s71.getVariableMap().setMatrix(TARGET, output71);
		s71.setLabel("Iris-versicolor");
		s71.setId("iris-71");
		iris.getSampleMap().add(s71);

		Sample s72 = SampleFactory.emptySample();
		s72.getVariableMap().setObject("SepalLength", 6.3);
		s72.getVariableMap().setObject("SepalWidth", 2.5);
		s72.getVariableMap().setObject("PetalLength", 4.9);
		s72.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input72 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.5, 4.9, 1.5 })
				.transpose();
		s72.getVariableMap().setMatrix(INPUT, input72);
		Matrix output72 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s72.getVariableMap().setMatrix(TARGET, output72);
		s72.setLabel("Iris-versicolor");
		s72.setId("iris-72");
		iris.getSampleMap().add(s72);

		Sample s73 = SampleFactory.emptySample();
		s73.getVariableMap().setObject("SepalLength", 6.1);
		s73.getVariableMap().setObject("SepalWidth", 2.8);
		s73.getVariableMap().setObject("PetalLength", 4.7);
		s73.getVariableMap().setObject("PetalWidth", 1.2);
		Matrix input73 = Matrix.Factory.linkToArray(new double[] { 6.1, 2.8, 4.7, 1.2 })
				.transpose();
		s73.getVariableMap().setMatrix(INPUT, input73);
		Matrix output73 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s73.getVariableMap().setMatrix(TARGET, output73);
		s73.setLabel("Iris-versicolor");
		s73.setId("iris-73");
		iris.getSampleMap().add(s73);

		Sample s74 = SampleFactory.emptySample();
		s74.getVariableMap().setObject("SepalLength", 6.4);
		s74.getVariableMap().setObject("SepalWidth", 2.9);
		s74.getVariableMap().setObject("PetalLength", 4.3);
		s74.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input74 = Matrix.Factory.linkToArray(new double[] { 6.4, 2.9, 4.3, 1.3 })
				.transpose();
		s74.getVariableMap().setMatrix(INPUT, input74);
		Matrix output74 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s74.getVariableMap().setMatrix(TARGET, output74);
		s74.setLabel("Iris-versicolor");
		s74.setId("iris-74");
		iris.getSampleMap().add(s74);

		Sample s75 = SampleFactory.emptySample();
		s75.getVariableMap().setObject("SepalLength", 6.6);
		s75.getVariableMap().setObject("SepalWidth", 3.0);
		s75.getVariableMap().setObject("PetalLength", 4.4);
		s75.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input75 = Matrix.Factory.linkToArray(new double[] { 6.6, 3.0, 4.4, 1.4 })
				.transpose();
		s75.getVariableMap().setMatrix(INPUT, input75);
		Matrix output75 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s75.getVariableMap().setMatrix(TARGET, output75);
		s75.setLabel("Iris-versicolor");
		s75.setId("iris-75");
		iris.getSampleMap().add(s75);

		Sample s76 = SampleFactory.emptySample();
		s76.getVariableMap().setObject("SepalLength", 6.8);
		s76.getVariableMap().setObject("SepalWidth", 2.8);
		s76.getVariableMap().setObject("PetalLength", 4.8);
		s76.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input76 = Matrix.Factory.linkToArray(new double[] { 6.8, 2.8, 4.8, 1.4 })
				.transpose();
		s76.getVariableMap().setMatrix(INPUT, input76);
		Matrix output76 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s76.getVariableMap().setMatrix(TARGET, output76);
		s76.setLabel("Iris-versicolor");
		s76.setId("iris-76");
		iris.getSampleMap().add(s76);

		Sample s77 = SampleFactory.emptySample();
		s77.getVariableMap().setObject("SepalLength", 6.7);
		s77.getVariableMap().setObject("SepalWidth", 3.0);
		s77.getVariableMap().setObject("PetalLength", 5.0);
		s77.getVariableMap().setObject("PetalWidth", 1.7);
		Matrix input77 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.0, 5.0, 1.7 })
				.transpose();
		s77.getVariableMap().setMatrix(INPUT, input77);
		Matrix output77 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s77.getVariableMap().setMatrix(TARGET, output77);
		s77.setLabel("Iris-versicolor");
		s77.setId("iris-77");
		iris.getSampleMap().add(s77);

		Sample s78 = SampleFactory.emptySample();
		s78.getVariableMap().setObject("SepalLength", 6.0);
		s78.getVariableMap().setObject("SepalWidth", 2.9);
		s78.getVariableMap().setObject("PetalLength", 4.5);
		s78.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input78 = Matrix.Factory.linkToArray(new double[] { 6.0, 2.9, 4.5, 1.5 })
				.transpose();
		s78.getVariableMap().setMatrix(INPUT, input78);
		Matrix output78 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s78.getVariableMap().setMatrix(TARGET, output78);
		s78.setLabel("Iris-versicolor");
		s78.setId("iris-78");
		iris.getSampleMap().add(s78);

		Sample s79 = SampleFactory.emptySample();
		s79.getVariableMap().setObject("SepalLength", 5.7);
		s79.getVariableMap().setObject("SepalWidth", 2.6);
		s79.getVariableMap().setObject("PetalLength", 3.5);
		s79.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input79 = Matrix.Factory.linkToArray(new double[] { 5.7, 2.6, 3.5, 1.0 })
				.transpose();
		s79.getVariableMap().setMatrix(INPUT, input79);
		Matrix output79 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s79.getVariableMap().setMatrix(TARGET, output79);
		s79.setLabel("Iris-versicolor");
		s79.setId("iris-79");
		iris.getSampleMap().add(s79);

		Sample s80 = SampleFactory.emptySample();
		s80.getVariableMap().setObject("SepalLength", 5.5);
		s80.getVariableMap().setObject("SepalWidth", 2.4);
		s80.getVariableMap().setObject("PetalLength", 3.8);
		s80.getVariableMap().setObject("PetalWidth", 1.1);
		Matrix input80 = Matrix.Factory.linkToArray(new double[] { 5.5, 2.4, 3.8, 1.1 })
				.transpose();
		s80.getVariableMap().setMatrix(INPUT, input80);
		Matrix output80 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s80.getVariableMap().setMatrix(TARGET, output80);
		s80.setLabel("Iris-versicolor");
		s80.setId("iris-80");
		iris.getSampleMap().add(s80);

		Sample s81 = SampleFactory.emptySample();
		s81.getVariableMap().setObject("SepalLength", 5.5);
		s81.getVariableMap().setObject("SepalWidth", 2.4);
		s81.getVariableMap().setObject("PetalLength", 3.7);
		s81.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input81 = Matrix.Factory.linkToArray(new double[] { 5.5, 2.4, 3.7, 1.0 })
				.transpose();
		s81.getVariableMap().setMatrix(INPUT, input81);
		Matrix output81 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s81.getVariableMap().setMatrix(TARGET, output81);
		s81.setLabel("Iris-versicolor");
		s81.setId("iris-81");
		iris.getSampleMap().add(s81);

		Sample s82 = SampleFactory.emptySample();
		s82.getVariableMap().setObject("SepalLength", 5.8);
		s82.getVariableMap().setObject("SepalWidth", 2.7);
		s82.getVariableMap().setObject("PetalLength", 3.9);
		s82.getVariableMap().setObject("PetalWidth", 1.2);
		Matrix input82 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.7, 3.9, 1.2 })
				.transpose();
		s82.getVariableMap().setMatrix(INPUT, input82);
		Matrix output82 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s82.getVariableMap().setMatrix(TARGET, output82);
		s82.setLabel("Iris-versicolor");
		s82.setId("iris-82");
		iris.getSampleMap().add(s82);

		Sample s83 = SampleFactory.emptySample();
		s83.getVariableMap().setObject("SepalLength", 6.0);
		s83.getVariableMap().setObject("SepalWidth", 2.7);
		s83.getVariableMap().setObject("PetalLength", 5.1);
		s83.getVariableMap().setObject("PetalWidth", 1.6);
		Matrix input83 = Matrix.Factory.linkToArray(new double[] { 6.0, 2.7, 5.1, 1.6 })
				.transpose();
		s83.getVariableMap().setMatrix(INPUT, input83);
		Matrix output83 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s83.getVariableMap().setMatrix(TARGET, output83);
		s83.setLabel("Iris-versicolor");
		s83.setId("iris-83");
		iris.getSampleMap().add(s83);

		Sample s84 = SampleFactory.emptySample();
		s84.getVariableMap().setObject("SepalLength", 5.4);
		s84.getVariableMap().setObject("SepalWidth", 3.0);
		s84.getVariableMap().setObject("PetalLength", 4.5);
		s84.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input84 = Matrix.Factory.linkToArray(new double[] { 5.4, 3.0, 4.5, 1.5 })
				.transpose();
		s84.getVariableMap().setMatrix(INPUT, input84);
		Matrix output84 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s84.getVariableMap().setMatrix(TARGET, output84);
		s84.setLabel("Iris-versicolor");
		s84.setId("iris-84");
		iris.getSampleMap().add(s84);

		Sample s85 = SampleFactory.emptySample();
		s85.getVariableMap().setObject("SepalLength", 6.0);
		s85.getVariableMap().setObject("SepalWidth", 3.4);
		s85.getVariableMap().setObject("PetalLength", 4.5);
		s85.getVariableMap().setObject("PetalWidth", 1.6);
		Matrix input85 = Matrix.Factory.linkToArray(new double[] { 6.0, 3.4, 4.5, 1.6 })
				.transpose();
		s85.getVariableMap().setMatrix(INPUT, input85);
		Matrix output85 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s85.getVariableMap().setMatrix(TARGET, output85);
		s85.setLabel("Iris-versicolor");
		s85.setId("iris-85");
		iris.getSampleMap().add(s85);

		Sample s86 = SampleFactory.emptySample();
		s86.getVariableMap().setObject("SepalLength", 6.7);
		s86.getVariableMap().setObject("SepalWidth", 3.1);
		s86.getVariableMap().setObject("PetalLength", 4.7);
		s86.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input86 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.1, 4.7, 1.5 })
				.transpose();
		s86.getVariableMap().setMatrix(INPUT, input86);
		Matrix output86 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s86.getVariableMap().setMatrix(TARGET, output86);
		s86.setLabel("Iris-versicolor");
		s86.setId("iris-86");
		iris.getSampleMap().add(s86);

		Sample s87 = SampleFactory.emptySample();
		s87.getVariableMap().setObject("SepalLength", 6.3);
		s87.getVariableMap().setObject("SepalWidth", 2.3);
		s87.getVariableMap().setObject("PetalLength", 4.4);
		s87.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input87 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.3, 4.4, 1.3 })
				.transpose();
		s87.getVariableMap().setMatrix(INPUT, input87);
		Matrix output87 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s87.getVariableMap().setMatrix(TARGET, output87);
		s87.setLabel("Iris-versicolor");
		s87.setId("iris-87");
		iris.getSampleMap().add(s87);

		Sample s88 = SampleFactory.emptySample();
		s88.getVariableMap().setObject("SepalLength", 5.6);
		s88.getVariableMap().setObject("SepalWidth", 3.0);
		s88.getVariableMap().setObject("PetalLength", 4.1);
		s88.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input88 = Matrix.Factory.linkToArray(new double[] { 5.6, 3.0, 4.1, 1.3 })
				.transpose();
		s88.getVariableMap().setMatrix(INPUT, input88);
		Matrix output88 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s88.getVariableMap().setMatrix(TARGET, output88);
		s88.setLabel("Iris-versicolor");
		s88.setId("iris-88");
		iris.getSampleMap().add(s88);

		Sample s89 = SampleFactory.emptySample();
		s89.getVariableMap().setObject("SepalLength", 5.5);
		s89.getVariableMap().setObject("SepalWidth", 2.5);
		s89.getVariableMap().setObject("PetalLength", 4.0);
		s89.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input89 = Matrix.Factory.linkToArray(new double[] { 5.5, 2.5, 4.0, 1.3 })
				.transpose();
		s89.getVariableMap().setMatrix(INPUT, input89);
		Matrix output89 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s89.getVariableMap().setMatrix(TARGET, output89);
		s89.setLabel("Iris-versicolor");
		s89.setId("iris-89");
		iris.getSampleMap().add(s89);

		Sample s90 = SampleFactory.emptySample();
		s90.getVariableMap().setObject("SepalLength", 5.5);
		s90.getVariableMap().setObject("SepalWidth", 2.6);
		s90.getVariableMap().setObject("PetalLength", 4.4);
		s90.getVariableMap().setObject("PetalWidth", 1.2);
		Matrix input90 = Matrix.Factory.linkToArray(new double[] { 5.5, 2.6, 4.4, 1.2 })
				.transpose();
		s90.getVariableMap().setMatrix(INPUT, input90);
		Matrix output90 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s90.getVariableMap().setMatrix(TARGET, output90);
		s90.setLabel("Iris-versicolor");
		s90.setId("iris-90");
		iris.getSampleMap().add(s90);

		Sample s91 = SampleFactory.emptySample();
		s91.getVariableMap().setObject("SepalLength", 6.1);
		s91.getVariableMap().setObject("SepalWidth", 3.0);
		s91.getVariableMap().setObject("PetalLength", 4.6);
		s91.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input91 = Matrix.Factory.linkToArray(new double[] { 6.1, 3.0, 4.6, 1.4 })
				.transpose();
		s91.getVariableMap().setMatrix(INPUT, input91);
		Matrix output91 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s91.getVariableMap().setMatrix(TARGET, output91);
		s91.setLabel("Iris-versicolor");
		s91.setId("iris-91");
		iris.getSampleMap().add(s91);

		Sample s92 = SampleFactory.emptySample();
		s92.getVariableMap().setObject("SepalLength", 5.8);
		s92.getVariableMap().setObject("SepalWidth", 2.6);
		s92.getVariableMap().setObject("PetalLength", 4.0);
		s92.getVariableMap().setObject("PetalWidth", 1.2);
		Matrix input92 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.6, 4.0, 1.2 })
				.transpose();
		s92.getVariableMap().setMatrix(INPUT, input92);
		Matrix output92 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s92.getVariableMap().setMatrix(TARGET, output92);
		s92.setLabel("Iris-versicolor");
		s92.setId("iris-92");
		iris.getSampleMap().add(s92);

		Sample s93 = SampleFactory.emptySample();
		s93.getVariableMap().setObject("SepalLength", 5.0);
		s93.getVariableMap().setObject("SepalWidth", 2.3);
		s93.getVariableMap().setObject("PetalLength", 3.3);
		s93.getVariableMap().setObject("PetalWidth", 1.0);
		Matrix input93 = Matrix.Factory.linkToArray(new double[] { 5.0, 2.3, 3.3, 1.0 })
				.transpose();
		s93.getVariableMap().setMatrix(INPUT, input93);
		Matrix output93 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s93.getVariableMap().setMatrix(TARGET, output93);
		s93.setLabel("Iris-versicolor");
		s93.setId("iris-93");
		iris.getSampleMap().add(s93);

		Sample s94 = SampleFactory.emptySample();
		s94.getVariableMap().setObject("SepalLength", 5.6);
		s94.getVariableMap().setObject("SepalWidth", 2.7);
		s94.getVariableMap().setObject("PetalLength", 4.2);
		s94.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input94 = Matrix.Factory.linkToArray(new double[] { 5.6, 2.7, 4.2, 1.3 })
				.transpose();
		s94.getVariableMap().setMatrix(INPUT, input94);
		Matrix output94 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s94.getVariableMap().setMatrix(TARGET, output94);
		s94.setLabel("Iris-versicolor");
		s94.setId("iris-94");
		iris.getSampleMap().add(s94);

		Sample s95 = SampleFactory.emptySample();
		s95.getVariableMap().setObject("SepalLength", 5.7);
		s95.getVariableMap().setObject("SepalWidth", 3.0);
		s95.getVariableMap().setObject("PetalLength", 4.2);
		s95.getVariableMap().setObject("PetalWidth", 1.2);
		Matrix input95 = Matrix.Factory.linkToArray(new double[] { 5.7, 3.0, 4.2, 1.2 })
				.transpose();
		s95.getVariableMap().setMatrix(INPUT, input95);
		Matrix output95 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s95.getVariableMap().setMatrix(TARGET, output95);
		s95.setLabel("Iris-versicolor");
		s95.setId("iris-95");
		iris.getSampleMap().add(s95);

		Sample s96 = SampleFactory.emptySample();
		s96.getVariableMap().setObject("SepalLength", 5.7);
		s96.getVariableMap().setObject("SepalWidth", 2.9);
		s96.getVariableMap().setObject("PetalLength", 4.2);
		s96.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input96 = Matrix.Factory.linkToArray(new double[] { 5.7, 2.9, 4.2, 1.3 })
				.transpose();
		s96.getVariableMap().setMatrix(INPUT, input96);
		Matrix output96 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s96.getVariableMap().setMatrix(TARGET, output96);
		s96.setLabel("Iris-versicolor");
		s96.setId("iris-96");
		iris.getSampleMap().add(s96);

		Sample s97 = SampleFactory.emptySample();
		s97.getVariableMap().setObject("SepalLength", 6.2);
		s97.getVariableMap().setObject("SepalWidth", 2.9);
		s97.getVariableMap().setObject("PetalLength", 4.3);
		s97.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input97 = Matrix.Factory.linkToArray(new double[] { 6.2, 2.9, 4.3, 1.3 })
				.transpose();
		s97.getVariableMap().setMatrix(INPUT, input97);
		Matrix output97 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s97.getVariableMap().setMatrix(TARGET, output97);
		s97.setLabel("Iris-versicolor");
		s97.setId("iris-97");
		iris.getSampleMap().add(s97);

		Sample s98 = SampleFactory.emptySample();
		s98.getVariableMap().setObject("SepalLength", 5.1);
		s98.getVariableMap().setObject("SepalWidth", 2.5);
		s98.getVariableMap().setObject("PetalLength", 3.0);
		s98.getVariableMap().setObject("PetalWidth", 1.1);
		Matrix input98 = Matrix.Factory.linkToArray(new double[] { 5.1, 2.5, 3.0, 1.1 })
				.transpose();
		s98.getVariableMap().setMatrix(INPUT, input98);
		Matrix output98 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s98.getVariableMap().setMatrix(TARGET, output98);
		s98.setLabel("Iris-versicolor");
		s98.setId("iris-98");
		iris.getSampleMap().add(s98);

		Sample s99 = SampleFactory.emptySample();
		s99.getVariableMap().setObject("SepalLength", 5.7);
		s99.getVariableMap().setObject("SepalWidth", 2.8);
		s99.getVariableMap().setObject("PetalLength", 4.1);
		s99.getVariableMap().setObject("PetalWidth", 1.3);
		Matrix input99 = Matrix.Factory.linkToArray(new double[] { 5.7, 2.8, 4.1, 1.3 })
				.transpose();
		s99.getVariableMap().setMatrix(INPUT, input99);
		Matrix output99 = Matrix.Factory.linkToArray(new double[] { 0, 1, 0 }).transpose();
		s99.getVariableMap().setMatrix(TARGET, output99);
		s99.setLabel("Iris-versicolor");
		s99.setId("iris-99");
		iris.getSampleMap().add(s99);

		Sample s100 = SampleFactory.emptySample();
		s100.getVariableMap().setObject("SepalLength", 6.3);
		s100.getVariableMap().setObject("SepalWidth", 3.3);
		s100.getVariableMap().setObject("PetalLength", 6.0);
		s100.getVariableMap().setObject("PetalWidth", 2.5);
		Matrix input100 = Matrix.Factory.linkToArray(new double[] { 6.3, 3.3, 6.0, 2.5 })
				.transpose();
		s100.getVariableMap().setMatrix(INPUT, input100);
		Matrix output100 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s100.getVariableMap().setMatrix(TARGET, output100);
		s100.setLabel("Iris-virginica");
		s100.setId("iris-100");
		iris.getSampleMap().add(s100);

		Sample s101 = SampleFactory.emptySample();
		s101.getVariableMap().setObject("SepalLength", 5.8);
		s101.getVariableMap().setObject("SepalWidth", 2.7);
		s101.getVariableMap().setObject("PetalLength", 5.1);
		s101.getVariableMap().setObject("PetalWidth", 1.9);
		Matrix input101 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.7, 5.1, 1.9 })
				.transpose();
		s101.getVariableMap().setMatrix(INPUT, input101);
		Matrix output101 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s101.getVariableMap().setMatrix(TARGET, output101);
		s101.setLabel("Iris-virginica");
		s101.setId("iris-101");
		iris.getSampleMap().add(s101);

		Sample s102 = SampleFactory.emptySample();
		s102.getVariableMap().setObject("SepalLength", 7.1);
		s102.getVariableMap().setObject("SepalWidth", 3.0);
		s102.getVariableMap().setObject("PetalLength", 5.9);
		s102.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input102 = Matrix.Factory.linkToArray(new double[] { 7.1, 3.0, 5.9, 2.1 })
				.transpose();
		s102.getVariableMap().setMatrix(INPUT, input102);
		Matrix output102 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s102.getVariableMap().setMatrix(TARGET, output102);
		s102.setLabel("Iris-virginica");
		s102.setId("iris-102");
		iris.getSampleMap().add(s102);

		Sample s103 = SampleFactory.emptySample();
		s103.getVariableMap().setObject("SepalLength", 6.3);
		s103.getVariableMap().setObject("SepalWidth", 2.9);
		s103.getVariableMap().setObject("PetalLength", 5.6);
		s103.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input103 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.9, 5.6, 1.8 })
				.transpose();
		s103.getVariableMap().setMatrix(INPUT, input103);
		Matrix output103 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s103.getVariableMap().setMatrix(TARGET, output103);
		s103.setLabel("Iris-virginica");
		s103.setId("iris-103");
		iris.getSampleMap().add(s103);

		Sample s104 = SampleFactory.emptySample();
		s104.getVariableMap().setObject("SepalLength", 6.5);
		s104.getVariableMap().setObject("SepalWidth", 3.0);
		s104.getVariableMap().setObject("PetalLength", 5.8);
		s104.getVariableMap().setObject("PetalWidth", 2.2);
		Matrix input104 = Matrix.Factory.linkToArray(new double[] { 6.5, 3.0, 5.8, 2.2 })
				.transpose();
		s104.getVariableMap().setMatrix(INPUT, input104);
		Matrix output104 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s104.getVariableMap().setMatrix(TARGET, output104);
		s104.setLabel("Iris-virginica");
		s104.setId("iris-104");
		iris.getSampleMap().add(s104);

		Sample s105 = SampleFactory.emptySample();
		s105.getVariableMap().setObject("SepalLength", 7.6);
		s105.getVariableMap().setObject("SepalWidth", 3.0);
		s105.getVariableMap().setObject("PetalLength", 6.6);
		s105.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input105 = Matrix.Factory.linkToArray(new double[] { 7.6, 3.0, 6.6, 2.1 })
				.transpose();
		s105.getVariableMap().setMatrix(INPUT, input105);
		Matrix output105 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s105.getVariableMap().setMatrix(TARGET, output105);
		s105.setLabel("Iris-virginica");
		s105.setId("iris-105");
		iris.getSampleMap().add(s105);

		Sample s106 = SampleFactory.emptySample();
		s106.getVariableMap().setObject("SepalLength", 4.9);
		s106.getVariableMap().setObject("SepalWidth", 2.5);
		s106.getVariableMap().setObject("PetalLength", 4.5);
		s106.getVariableMap().setObject("PetalWidth", 1.7);
		Matrix input106 = Matrix.Factory.linkToArray(new double[] { 4.9, 2.5, 4.5, 1.7 })
				.transpose();
		s106.getVariableMap().setMatrix(INPUT, input106);
		Matrix output106 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s106.getVariableMap().setMatrix(TARGET, output106);
		s106.setLabel("Iris-virginica");
		s106.setId("iris-106");
		iris.getSampleMap().add(s106);

		Sample s107 = SampleFactory.emptySample();
		s107.getVariableMap().setObject("SepalLength", 7.3);
		s107.getVariableMap().setObject("SepalWidth", 2.9);
		s107.getVariableMap().setObject("PetalLength", 6.3);
		s107.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input107 = Matrix.Factory.linkToArray(new double[] { 7.3, 2.9, 6.3, 1.8 })
				.transpose();
		s107.getVariableMap().setMatrix(INPUT, input107);
		Matrix output107 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s107.getVariableMap().setMatrix(TARGET, output107);
		s107.setLabel("Iris-virginica");
		s107.setId("iris-107");
		iris.getSampleMap().add(s107);

		Sample s108 = SampleFactory.emptySample();
		s108.getVariableMap().setObject("SepalLength", 6.7);
		s108.getVariableMap().setObject("SepalWidth", 2.5);
		s108.getVariableMap().setObject("PetalLength", 5.8);
		s108.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input108 = Matrix.Factory.linkToArray(new double[] { 6.7, 2.5, 5.8, 1.8 })
				.transpose();
		s108.getVariableMap().setMatrix(INPUT, input108);
		Matrix output108 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s108.getVariableMap().setMatrix(TARGET, output108);
		s108.setLabel("Iris-virginica");
		s108.setId("iris-108");
		iris.getSampleMap().add(s108);

		Sample s109 = SampleFactory.emptySample();
		s109.getVariableMap().setObject("SepalLength", 7.2);
		s109.getVariableMap().setObject("SepalWidth", 3.6);
		s109.getVariableMap().setObject("PetalLength", 6.1);
		s109.getVariableMap().setObject("PetalWidth", 2.5);
		Matrix input109 = Matrix.Factory.linkToArray(new double[] { 7.2, 3.6, 6.1, 2.5 })
				.transpose();
		s109.getVariableMap().setMatrix(INPUT, input109);
		Matrix output109 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s109.getVariableMap().setMatrix(TARGET, output109);
		s109.setLabel("Iris-virginica");
		s109.setId("iris-109");
		iris.getSampleMap().add(s109);

		Sample s110 = SampleFactory.emptySample();
		s110.getVariableMap().setObject("SepalLength", 6.5);
		s110.getVariableMap().setObject("SepalWidth", 3.2);
		s110.getVariableMap().setObject("PetalLength", 5.1);
		s110.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input110 = Matrix.Factory.linkToArray(new double[] { 6.5, 3.2, 5.1, 2.0 })
				.transpose();
		s110.getVariableMap().setMatrix(INPUT, input110);
		Matrix output110 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s110.getVariableMap().setMatrix(TARGET, output110);
		s110.setLabel("Iris-virginica");
		s110.setId("iris-110");
		iris.getSampleMap().add(s110);

		Sample s111 = SampleFactory.emptySample();
		s111.getVariableMap().setObject("SepalLength", 6.4);
		s111.getVariableMap().setObject("SepalWidth", 2.7);
		s111.getVariableMap().setObject("PetalLength", 5.3);
		s111.getVariableMap().setObject("PetalWidth", 1.9);
		Matrix input111 = Matrix.Factory.linkToArray(new double[] { 6.4, 2.7, 5.3, 1.9 })
				.transpose();
		s111.getVariableMap().setMatrix(INPUT, input111);
		Matrix output111 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s111.getVariableMap().setMatrix(TARGET, output111);
		s111.setLabel("Iris-virginica");
		s111.setId("iris-111");
		iris.getSampleMap().add(s111);

		Sample s112 = SampleFactory.emptySample();
		s112.getVariableMap().setObject("SepalLength", 6.8);
		s112.getVariableMap().setObject("SepalWidth", 3.0);
		s112.getVariableMap().setObject("PetalLength", 5.5);
		s112.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input112 = Matrix.Factory.linkToArray(new double[] { 6.8, 3.0, 5.5, 2.1 })
				.transpose();
		s112.getVariableMap().setMatrix(INPUT, input112);
		Matrix output112 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s112.getVariableMap().setMatrix(TARGET, output112);
		s112.setLabel("Iris-virginica");
		s112.setId("iris-112");
		iris.getSampleMap().add(s112);

		Sample s113 = SampleFactory.emptySample();
		s113.getVariableMap().setObject("SepalLength", 5.7);
		s113.getVariableMap().setObject("SepalWidth", 2.5);
		s113.getVariableMap().setObject("PetalLength", 5.0);
		s113.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input113 = Matrix.Factory.linkToArray(new double[] { 5.7, 2.5, 5.0, 2.0 })
				.transpose();
		s113.getVariableMap().setMatrix(INPUT, input113);
		Matrix output113 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s113.getVariableMap().setMatrix(TARGET, output113);
		s113.setLabel("Iris-virginica");
		s113.setId("iris-113");
		iris.getSampleMap().add(s113);

		Sample s114 = SampleFactory.emptySample();
		s114.getVariableMap().setObject("SepalLength", 5.8);
		s114.getVariableMap().setObject("SepalWidth", 2.8);
		s114.getVariableMap().setObject("PetalLength", 5.1);
		s114.getVariableMap().setObject("PetalWidth", 2.4);
		Matrix input114 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.8, 5.1, 2.4 })
				.transpose();
		s114.getVariableMap().setMatrix(INPUT, input114);
		Matrix output114 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s114.getVariableMap().setMatrix(TARGET, output114);
		s114.setLabel("Iris-virginica");
		s114.setId("iris-114");
		iris.getSampleMap().add(s114);

		Sample s115 = SampleFactory.emptySample();
		s115.getVariableMap().setObject("SepalLength", 6.4);
		s115.getVariableMap().setObject("SepalWidth", 3.2);
		s115.getVariableMap().setObject("PetalLength", 5.3);
		s115.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input115 = Matrix.Factory.linkToArray(new double[] { 6.4, 3.2, 5.3, 2.3 })
				.transpose();
		s115.getVariableMap().setMatrix(INPUT, input115);
		Matrix output115 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s115.getVariableMap().setMatrix(TARGET, output115);
		s115.setLabel("Iris-virginica");
		s115.setId("iris-115");
		iris.getSampleMap().add(s115);

		Sample s116 = SampleFactory.emptySample();
		s116.getVariableMap().setObject("SepalLength", 6.5);
		s116.getVariableMap().setObject("SepalWidth", 3.0);
		s116.getVariableMap().setObject("PetalLength", 5.5);
		s116.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input116 = Matrix.Factory.linkToArray(new double[] { 6.5, 3.0, 5.5, 1.8 })
				.transpose();
		s116.getVariableMap().setMatrix(INPUT, input116);
		Matrix output116 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s116.getVariableMap().setMatrix(TARGET, output116);
		s116.setLabel("Iris-virginica");
		s116.setId("iris-116");
		iris.getSampleMap().add(s116);

		Sample s117 = SampleFactory.emptySample();
		s117.getVariableMap().setObject("SepalLength", 7.7);
		s117.getVariableMap().setObject("SepalWidth", 3.8);
		s117.getVariableMap().setObject("PetalLength", 6.7);
		s117.getVariableMap().setObject("PetalWidth", 2.2);
		Matrix input117 = Matrix.Factory.linkToArray(new double[] { 7.7, 3.8, 6.7, 2.2 })
				.transpose();
		s117.getVariableMap().setMatrix(INPUT, input117);
		Matrix output117 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s117.getVariableMap().setMatrix(TARGET, output117);
		s117.setLabel("Iris-virginica");
		s117.setId("iris-117");
		iris.getSampleMap().add(s117);

		Sample s118 = SampleFactory.emptySample();
		s118.getVariableMap().setObject("SepalLength", 7.7);
		s118.getVariableMap().setObject("SepalWidth", 2.6);
		s118.getVariableMap().setObject("PetalLength", 6.9);
		s118.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input118 = Matrix.Factory.linkToArray(new double[] { 7.7, 2.6, 6.9, 2.3 })
				.transpose();
		s118.getVariableMap().setMatrix(INPUT, input118);
		Matrix output118 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s118.getVariableMap().setMatrix(TARGET, output118);
		s118.setLabel("Iris-virginica");
		s118.setId("iris-118");
		iris.getSampleMap().add(s118);

		Sample s119 = SampleFactory.emptySample();
		s119.getVariableMap().setObject("SepalLength", 6.0);
		s119.getVariableMap().setObject("SepalWidth", 2.2);
		s119.getVariableMap().setObject("PetalLength", 5.0);
		s119.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input119 = Matrix.Factory.linkToArray(new double[] { 6.0, 2.2, 5.0, 1.5 })
				.transpose();
		s119.getVariableMap().setMatrix(INPUT, input119);
		Matrix output119 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s119.getVariableMap().setMatrix(TARGET, output119);
		s119.setLabel("Iris-virginica");
		s119.setId("iris-119");
		iris.getSampleMap().add(s119);

		Sample s120 = SampleFactory.emptySample();
		s120.getVariableMap().setObject("SepalLength", 6.9);
		s120.getVariableMap().setObject("SepalWidth", 3.2);
		s120.getVariableMap().setObject("PetalLength", 5.7);
		s120.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input120 = Matrix.Factory.linkToArray(new double[] { 6.9, 3.2, 5.7, 2.3 })
				.transpose();
		s120.getVariableMap().setMatrix(INPUT, input120);
		Matrix output120 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s120.getVariableMap().setMatrix(TARGET, output120);
		s120.setLabel("Iris-virginica");
		s120.setId("iris-120");
		iris.getSampleMap().add(s120);

		Sample s121 = SampleFactory.emptySample();
		s121.getVariableMap().setObject("SepalLength", 5.6);
		s121.getVariableMap().setObject("SepalWidth", 2.8);
		s121.getVariableMap().setObject("PetalLength", 4.9);
		s121.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input121 = Matrix.Factory.linkToArray(new double[] { 5.6, 2.8, 4.9, 2.0 })
				.transpose();
		s121.getVariableMap().setMatrix(INPUT, input121);
		Matrix output121 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s121.getVariableMap().setMatrix(TARGET, output121);
		s121.setLabel("Iris-virginica");
		s121.setId("iris-121");
		iris.getSampleMap().add(s121);

		Sample s122 = SampleFactory.emptySample();
		s122.getVariableMap().setObject("SepalLength", 7.7);
		s122.getVariableMap().setObject("SepalWidth", 2.8);
		s122.getVariableMap().setObject("PetalLength", 6.7);
		s122.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input122 = Matrix.Factory.linkToArray(new double[] { 7.7, 2.8, 6.7, 2.0 })
				.transpose();
		s122.getVariableMap().setMatrix(INPUT, input122);
		Matrix output122 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s122.getVariableMap().setMatrix(TARGET, output122);
		s122.setLabel("Iris-virginica");
		s122.setId("iris-122");
		iris.getSampleMap().add(s122);

		Sample s123 = SampleFactory.emptySample();
		s123.getVariableMap().setObject("SepalLength", 6.3);
		s123.getVariableMap().setObject("SepalWidth", 2.7);
		s123.getVariableMap().setObject("PetalLength", 4.9);
		s123.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input123 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.7, 4.9, 1.8 })
				.transpose();
		s123.getVariableMap().setMatrix(INPUT, input123);
		Matrix output123 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s123.getVariableMap().setMatrix(TARGET, output123);
		s123.setLabel("Iris-virginica");
		s123.setId("iris-123");
		iris.getSampleMap().add(s123);

		Sample s124 = SampleFactory.emptySample();
		s124.getVariableMap().setObject("SepalLength", 6.7);
		s124.getVariableMap().setObject("SepalWidth", 3.3);
		s124.getVariableMap().setObject("PetalLength", 5.7);
		s124.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input124 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.3, 5.7, 2.1 })
				.transpose();
		s124.getVariableMap().setMatrix(INPUT, input124);
		Matrix output124 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s124.getVariableMap().setMatrix(TARGET, output124);
		s124.setLabel("Iris-virginica");
		s124.setId("iris-124");
		iris.getSampleMap().add(s124);

		Sample s125 = SampleFactory.emptySample();
		s125.getVariableMap().setObject("SepalLength", 7.2);
		s125.getVariableMap().setObject("SepalWidth", 3.2);
		s125.getVariableMap().setObject("PetalLength", 6.0);
		s125.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input125 = Matrix.Factory.linkToArray(new double[] { 7.2, 3.2, 6.0, 1.8 })
				.transpose();
		s125.getVariableMap().setMatrix(INPUT, input125);
		Matrix output125 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s125.getVariableMap().setMatrix(TARGET, output125);
		s125.setLabel("Iris-virginica");
		s125.setId("iris-125");
		iris.getSampleMap().add(s125);

		Sample s126 = SampleFactory.emptySample();
		s126.getVariableMap().setObject("SepalLength", 6.2);
		s126.getVariableMap().setObject("SepalWidth", 2.8);
		s126.getVariableMap().setObject("PetalLength", 4.8);
		s126.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input126 = Matrix.Factory.linkToArray(new double[] { 6.2, 2.8, 4.8, 1.8 })
				.transpose();
		s126.getVariableMap().setMatrix(INPUT, input126);
		Matrix output126 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s126.getVariableMap().setMatrix(TARGET, output126);
		s126.setLabel("Iris-virginica");
		s126.setId("iris-126");
		iris.getSampleMap().add(s126);

		Sample s127 = SampleFactory.emptySample();
		s127.getVariableMap().setObject("SepalLength", 6.1);
		s127.getVariableMap().setObject("SepalWidth", 3.0);
		s127.getVariableMap().setObject("PetalLength", 4.9);
		s127.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input127 = Matrix.Factory.linkToArray(new double[] { 6.1, 3.0, 4.9, 1.8 })
				.transpose();
		s127.getVariableMap().setMatrix(INPUT, input127);
		Matrix output127 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s127.getVariableMap().setMatrix(TARGET, output127);
		s127.setLabel("Iris-virginica");
		s127.setId("iris-127");
		iris.getSampleMap().add(s127);

		Sample s128 = SampleFactory.emptySample();
		s128.getVariableMap().setObject("SepalLength", 6.4);
		s128.getVariableMap().setObject("SepalWidth", 2.8);
		s128.getVariableMap().setObject("PetalLength", 5.6);
		s128.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input128 = Matrix.Factory.linkToArray(new double[] { 6.4, 2.8, 5.6, 2.1 })
				.transpose();
		s128.getVariableMap().setMatrix(INPUT, input128);
		Matrix output128 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s128.getVariableMap().setMatrix(TARGET, output128);
		s128.setLabel("Iris-virginica");
		s128.setId("iris-128");
		iris.getSampleMap().add(s128);

		Sample s129 = SampleFactory.emptySample();
		s129.getVariableMap().setObject("SepalLength", 7.2);
		s129.getVariableMap().setObject("SepalWidth", 3.0);
		s129.getVariableMap().setObject("PetalLength", 5.8);
		s129.getVariableMap().setObject("PetalWidth", 1.6);
		Matrix input129 = Matrix.Factory.linkToArray(new double[] { 7.2, 3.0, 5.8, 1.6 })
				.transpose();
		s129.getVariableMap().setMatrix(INPUT, input129);
		Matrix output129 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s129.getVariableMap().setMatrix(TARGET, output129);
		s129.setLabel("Iris-virginica");
		s129.setId("iris-129");
		iris.getSampleMap().add(s129);

		Sample s130 = SampleFactory.emptySample();
		s130.getVariableMap().setObject("SepalLength", 7.4);
		s130.getVariableMap().setObject("SepalWidth", 2.8);
		s130.getVariableMap().setObject("PetalLength", 6.1);
		s130.getVariableMap().setObject("PetalWidth", 1.9);
		Matrix input130 = Matrix.Factory.linkToArray(new double[] { 7.4, 2.8, 6.1, 1.9 })
				.transpose();
		s130.getVariableMap().setMatrix(INPUT, input130);
		Matrix output130 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s130.getVariableMap().setMatrix(TARGET, output130);
		s130.setLabel("Iris-virginica");
		s130.setId("iris-130");
		iris.getSampleMap().add(s130);

		Sample s131 = SampleFactory.emptySample();
		s131.getVariableMap().setObject("SepalLength", 7.9);
		s131.getVariableMap().setObject("SepalWidth", 3.8);
		s131.getVariableMap().setObject("PetalLength", 6.4);
		s131.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input131 = Matrix.Factory.linkToArray(new double[] { 7.9, 3.8, 6.4, 2.0 })
				.transpose();
		s131.getVariableMap().setMatrix(INPUT, input131);
		Matrix output131 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s131.getVariableMap().setMatrix(TARGET, output131);
		s131.setLabel("Iris-virginica");
		s131.setId("iris-131");
		iris.getSampleMap().add(s131);

		Sample s132 = SampleFactory.emptySample();
		s132.getVariableMap().setObject("SepalLength", 6.4);
		s132.getVariableMap().setObject("SepalWidth", 2.8);
		s132.getVariableMap().setObject("PetalLength", 5.6);
		s132.getVariableMap().setObject("PetalWidth", 2.2);
		Matrix input132 = Matrix.Factory.linkToArray(new double[] { 6.4, 2.8, 5.6, 2.2 })
				.transpose();
		s132.getVariableMap().setMatrix(INPUT, input132);
		Matrix output132 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s132.getVariableMap().setMatrix(TARGET, output132);
		s132.setLabel("Iris-virginica");
		s132.setId("iris-132");
		iris.getSampleMap().add(s132);

		Sample s133 = SampleFactory.emptySample();
		s133.getVariableMap().setObject("SepalLength", 6.3);
		s133.getVariableMap().setObject("SepalWidth", 2.8);
		s133.getVariableMap().setObject("PetalLength", 5.1);
		s133.getVariableMap().setObject("PetalWidth", 1.5);
		Matrix input133 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.8, 5.1, 1.5 })
				.transpose();
		s133.getVariableMap().setMatrix(INPUT, input133);
		Matrix output133 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s133.getVariableMap().setMatrix(TARGET, output133);
		s133.setLabel("Iris-virginica");
		s133.setId("iris-133");
		iris.getSampleMap().add(s133);

		Sample s134 = SampleFactory.emptySample();
		s134.getVariableMap().setObject("SepalLength", 6.1);
		s134.getVariableMap().setObject("SepalWidth", 2.6);
		s134.getVariableMap().setObject("PetalLength", 5.6);
		s134.getVariableMap().setObject("PetalWidth", 1.4);
		Matrix input134 = Matrix.Factory.linkToArray(new double[] { 6.1, 2.6, 5.6, 1.4 })
				.transpose();
		s134.getVariableMap().setMatrix(INPUT, input134);
		Matrix output134 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s134.getVariableMap().setMatrix(TARGET, output134);
		s134.setLabel("Iris-virginica");
		s134.setId("iris-134");
		iris.getSampleMap().add(s134);

		Sample s135 = SampleFactory.emptySample();
		s135.getVariableMap().setObject("SepalLength", 7.7);
		s135.getVariableMap().setObject("SepalWidth", 3.0);
		s135.getVariableMap().setObject("PetalLength", 6.1);
		s135.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input135 = Matrix.Factory.linkToArray(new double[] { 7.7, 3.0, 6.1, 2.3 })
				.transpose();
		s135.getVariableMap().setMatrix(INPUT, input135);
		Matrix output135 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s135.getVariableMap().setMatrix(TARGET, output135);
		s135.setLabel("Iris-virginica");
		s135.setId("iris-135");
		iris.getSampleMap().add(s135);

		Sample s136 = SampleFactory.emptySample();
		s136.getVariableMap().setObject("SepalLength", 6.3);
		s136.getVariableMap().setObject("SepalWidth", 3.4);
		s136.getVariableMap().setObject("PetalLength", 5.6);
		s136.getVariableMap().setObject("PetalWidth", 2.4);
		Matrix input136 = Matrix.Factory.linkToArray(new double[] { 6.3, 3.4, 5.6, 2.4 })
				.transpose();
		s136.getVariableMap().setMatrix(INPUT, input136);
		Matrix output136 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s136.getVariableMap().setMatrix(TARGET, output136);
		s136.setLabel("Iris-virginica");
		s136.setId("iris-136");
		iris.getSampleMap().add(s136);

		Sample s137 = SampleFactory.emptySample();
		s137.getVariableMap().setObject("SepalLength", 6.4);
		s137.getVariableMap().setObject("SepalWidth", 3.1);
		s137.getVariableMap().setObject("PetalLength", 5.5);
		s137.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input137 = Matrix.Factory.linkToArray(new double[] { 6.4, 3.1, 5.5, 1.8 })
				.transpose();
		s137.getVariableMap().setMatrix(INPUT, input137);
		Matrix output137 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s137.getVariableMap().setMatrix(TARGET, output137);
		s137.setLabel("Iris-virginica");
		s137.setId("iris-137");
		iris.getSampleMap().add(s137);

		Sample s138 = SampleFactory.emptySample();
		s138.getVariableMap().setObject("SepalLength", 6.0);
		s138.getVariableMap().setObject("SepalWidth", 3.0);
		s138.getVariableMap().setObject("PetalLength", 4.8);
		s138.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input138 = Matrix.Factory.linkToArray(new double[] { 6.0, 3.0, 4.8, 1.8 })
				.transpose();
		s138.getVariableMap().setMatrix(INPUT, input138);
		Matrix output138 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s138.getVariableMap().setMatrix(TARGET, output138);
		s138.setLabel("Iris-virginica");
		s138.setId("iris-138");
		iris.getSampleMap().add(s138);

		Sample s139 = SampleFactory.emptySample();
		s139.getVariableMap().setObject("SepalLength", 6.9);
		s139.getVariableMap().setObject("SepalWidth", 3.1);
		s139.getVariableMap().setObject("PetalLength", 5.4);
		s139.getVariableMap().setObject("PetalWidth", 2.1);
		Matrix input139 = Matrix.Factory.linkToArray(new double[] { 6.9, 3.1, 5.4, 2.1 })
				.transpose();
		s139.getVariableMap().setMatrix(INPUT, input139);
		Matrix output139 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s139.getVariableMap().setMatrix(TARGET, output139);
		s139.setLabel("Iris-virginica");
		s139.setId("iris-139");
		iris.getSampleMap().add(s139);

		Sample s140 = SampleFactory.emptySample();
		s140.getVariableMap().setObject("SepalLength", 6.7);
		s140.getVariableMap().setObject("SepalWidth", 3.1);
		s140.getVariableMap().setObject("PetalLength", 5.6);
		s140.getVariableMap().setObject("PetalWidth", 2.4);
		Matrix input140 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.1, 5.6, 2.4 })
				.transpose();
		s140.getVariableMap().setMatrix(INPUT, input140);
		Matrix output140 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s140.getVariableMap().setMatrix(TARGET, output140);
		s140.setLabel("Iris-virginica");
		s140.setId("iris-140");
		iris.getSampleMap().add(s140);

		Sample s141 = SampleFactory.emptySample();
		s141.getVariableMap().setObject("SepalLength", 6.9);
		s141.getVariableMap().setObject("SepalWidth", 3.1);
		s141.getVariableMap().setObject("PetalLength", 5.1);
		s141.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input141 = Matrix.Factory.linkToArray(new double[] { 6.9, 3.1, 5.1, 2.3 })
				.transpose();
		s141.getVariableMap().setMatrix(INPUT, input141);
		Matrix output141 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s141.getVariableMap().setMatrix(TARGET, output141);
		s141.setLabel("Iris-virginica");
		s141.setId("iris-141");
		iris.getSampleMap().add(s141);

		Sample s142 = SampleFactory.emptySample();
		s142.getVariableMap().setObject("SepalLength", 5.8);
		s142.getVariableMap().setObject("SepalWidth", 2.7);
		s142.getVariableMap().setObject("PetalLength", 5.1);
		s142.getVariableMap().setObject("PetalWidth", 1.9);
		Matrix input142 = Matrix.Factory.linkToArray(new double[] { 5.8, 2.7, 5.1, 1.9 })
				.transpose();
		s142.getVariableMap().setMatrix(INPUT, input142);
		Matrix output142 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s142.getVariableMap().setMatrix(TARGET, output142);
		s142.setLabel("Iris-virginica");
		s142.setId("iris-142");
		iris.getSampleMap().add(s142);

		Sample s143 = SampleFactory.emptySample();
		s143.getVariableMap().setObject("SepalLength", 6.8);
		s143.getVariableMap().setObject("SepalWidth", 3.2);
		s143.getVariableMap().setObject("PetalLength", 5.9);
		s143.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input143 = Matrix.Factory.linkToArray(new double[] { 6.8, 3.2, 5.9, 2.3 })
				.transpose();
		s143.getVariableMap().setMatrix(INPUT, input143);
		Matrix output143 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s143.getVariableMap().setMatrix(TARGET, output143);
		s143.setLabel("Iris-virginica");
		s143.setId("iris-143");
		iris.getSampleMap().add(s143);

		Sample s144 = SampleFactory.emptySample();
		s144.getVariableMap().setObject("SepalLength", 6.7);
		s144.getVariableMap().setObject("SepalWidth", 3.3);
		s144.getVariableMap().setObject("PetalLength", 5.7);
		s144.getVariableMap().setObject("PetalWidth", 2.5);
		Matrix input144 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.3, 5.7, 2.5 })
				.transpose();
		s144.getVariableMap().setMatrix(INPUT, input144);
		Matrix output144 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s144.getVariableMap().setMatrix(TARGET, output144);
		s144.setLabel("Iris-virginica");
		s144.setId("iris-144");
		iris.getSampleMap().add(s144);

		Sample s145 = SampleFactory.emptySample();
		s145.getVariableMap().setObject("SepalLength", 6.7);
		s145.getVariableMap().setObject("SepalWidth", 3.0);
		s145.getVariableMap().setObject("PetalLength", 5.2);
		s145.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input145 = Matrix.Factory.linkToArray(new double[] { 6.7, 3.0, 5.2, 2.3 })
				.transpose();
		s145.getVariableMap().setMatrix(INPUT, input145);
		Matrix output145 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s145.getVariableMap().setMatrix(TARGET, output145);
		s145.setLabel("Iris-virginica");
		s145.setId("iris-145");
		iris.getSampleMap().add(s145);

		Sample s146 = SampleFactory.emptySample();
		s146.getVariableMap().setObject("SepalLength", 6.3);
		s146.getVariableMap().setObject("SepalWidth", 2.5);
		s146.getVariableMap().setObject("PetalLength", 5.0);
		s146.getVariableMap().setObject("PetalWidth", 1.9);
		Matrix input146 = Matrix.Factory.linkToArray(new double[] { 6.3, 2.5, 5.0, 1.9 })
				.transpose();
		s146.getVariableMap().setMatrix(INPUT, input146);
		Matrix output146 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s146.getVariableMap().setMatrix(TARGET, output146);
		s146.setLabel("Iris-virginica");
		s146.setId("iris-146");
		iris.getSampleMap().add(s146);

		Sample s147 = SampleFactory.emptySample();
		s147.getVariableMap().setObject("SepalLength", 6.5);
		s147.getVariableMap().setObject("SepalWidth", 3.0);
		s147.getVariableMap().setObject("PetalLength", 5.2);
		s147.getVariableMap().setObject("PetalWidth", 2.0);
		Matrix input147 = Matrix.Factory.linkToArray(new double[] { 6.5, 3.0, 5.2, 2.0 })
				.transpose();
		s147.getVariableMap().setMatrix(INPUT, input147);
		Matrix output147 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s147.getVariableMap().setMatrix(TARGET, output147);
		s147.setLabel("Iris-virginica");
		s147.setId("iris-147");
		iris.getSampleMap().add(s147);

		Sample s148 = SampleFactory.emptySample();
		s148.getVariableMap().setObject("SepalLength", 6.2);
		s148.getVariableMap().setObject("SepalWidth", 3.4);
		s148.getVariableMap().setObject("PetalLength", 5.4);
		s148.getVariableMap().setObject("PetalWidth", 2.3);
		Matrix input148 = Matrix.Factory.linkToArray(new double[] { 6.2, 3.4, 5.4, 2.3 })
				.transpose();
		s148.getVariableMap().setMatrix(INPUT, input148);
		Matrix output148 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s148.getVariableMap().setMatrix(TARGET, output148);
		s148.setLabel("Iris-virginica");
		s148.setId("iris-148");
		iris.getSampleMap().add(s148);

		Sample s149 = SampleFactory.emptySample();
		s149.getVariableMap().setObject("SepalLength", 5.9);
		s149.getVariableMap().setObject("SepalWidth", 3.0);
		s149.getVariableMap().setObject("PetalLength", 5.1);
		s149.getVariableMap().setObject("PetalWidth", 1.8);
		Matrix input149 = Matrix.Factory.linkToArray(new double[] { 5.9, 3.0, 5.1, 1.8 })
				.transpose();
		s149.getVariableMap().setMatrix(INPUT, input149);
		Matrix output149 = Matrix.Factory.linkToArray(new double[] { 0, 0, 1 }).transpose();
		s149.getVariableMap().setMatrix(TARGET, output149);
		s149.setLabel("Iris-virginica");
		s149.setId("iris-149");
		iris.getSampleMap().add(s149);

		result.put(TARGET, iris);
		return result;
	}
}
