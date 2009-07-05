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

package org.jdmp.core.dataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.FileFormat;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public abstract class DataSetFactory {

	public static DataSet linkToMap(Map<? extends Object, ? extends Object> dataMap) {
		SampleMapWrapper list = new SampleMapWrapper(dataMap);
		DataSet ds = emptyDataSet();
		ds.setSamples(list);
		return ds;
	}

	public static DataSet emptyDataSet() {
		return new DefaultDataSet();
	}

	public static ClassificationDataSet copyFromMatrix(Matrix input, Matrix target)
			throws MatrixException {
		ClassificationDataSet ds = new ClassificationDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			ClassificationSample s = SampleFactory.classificationSample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = target.subMatrix(Ret.NEW, i, 0, i, target.getColumnCount() - 1);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSamples().add(s);
		}
		return ds;
	}

	public static ClassificationDataSet copyFromMatrix(Matrix input, Matrix target, Matrix label)
			throws MatrixException {
		ClassificationDataSet ds = new ClassificationDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			ClassificationSample s = SampleFactory.classificationSample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = target.subMatrix(Ret.NEW, i, 0, i, target.getColumnCount() - 1);
			Matrix labelMatrix = label.subMatrix(Ret.NEW, i, 0, i, label.getColumnCount() - 1);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			s.setMatrix(Sample.LABEL, labelMatrix);
			ds.getSamples().add(s);
		}
		return ds;
	}

	public static ClassificationDataSet linkToMatrix(Matrix input, Matrix target)
			throws MatrixException {
		ClassificationDataSet ds = new ClassificationDataSet();
		ds.getInputVariable().addMatrix(input);
		ds.getTargetVariable().addMatrix(target);
		for (int i = 0; i < input.getRowCount(); i++) {
			ClassificationSample s = SampleFactory.classificationSample();
			Matrix in = input.selectRows(Ret.LINK, i);
			Matrix out = target.selectRows(Ret.LINK, i);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSamples().add(s);
		}
		return ds;
	}

	public static ClassificationDataSet linkToMatrix(Matrix input, Matrix target, Matrix label)
			throws MatrixException {
		ClassificationDataSet ds = new ClassificationDataSet();
		ds.getInputVariable().addMatrix(input);
		ds.getTargetVariable().addMatrix(target);
		for (int i = 0; i < input.getRowCount(); i++) {
			ClassificationSample s = SampleFactory.classificationSample();
			Matrix in = input.selectRows(Ret.LINK, i);
			Matrix out = target.selectRows(Ret.LINK, i);
			Matrix labelMatrix = label.selectRows(Ret.LINK, i);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			s.setMatrix(Sample.LABEL, labelMatrix);
			ds.getSamples().add(s);
		}
		return ds;
	}

	public static RegressionDataSet HenonMap(int sampleCount, int inputLength, int predictionLength) {
		RegressionDataSet henon = DataSetFactory.regressionDataSet("Henon Map");

		Random random = new Random();

		for (int si = 0; si < sampleCount; si++) {

			double q1 = random.nextDouble() * 1.26 * 2 - 1.26;
			q1 = q1 >= 1.26 ? q1 - 1.26 : q1;
			q1 = q1 < -1.26 ? q1 + 1.26 : q1;

			double q2 = random.nextDouble() * 1.26 * 2 - 1.26;
			q2 = q2 >= 1.26 ? q2 - 1.26 : q2;
			q2 = q2 < -1.26 ? q2 + 1.26 : q2;

			double q = 0;

			Matrix input = MatrixFactory.zeros(1, inputLength);
			for (int i = 0; i < inputLength; i++) {
				q = henon(q1, q2);
				input.setAsDouble(q / 2, 0, i);
				q2 = q1;
				q1 = q;
			}

			Matrix target = MatrixFactory.zeros(1, predictionLength);
			for (int i = 0; i < predictionLength; i++) {
				q = henon(q1, q2);
				target.setAsDouble(q / 2, 0, i);
				q2 = q1;
				q1 = q;
			}

			Sample s = SampleFactory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			henon.getSamples().add(s);
		}

		return henon;
	}

	private static double henon(double q1, double q2) {
		double a = 1.4;
		double b = 0.3;

		double y = 1 - a * q1 * q1 + b * q2;

		double q = y;
		q = q >= 1.26 ? q - 1.26 : q;
		q = q < -1.26 ? q + 1.26 : q;

		return q;
	}

	public static RegressionDataSet LogisticMap(int sampleCount, int inputLength,
			int predictionLength) {
		RegressionDataSet logistic = DataSetFactory.regressionDataSet("Logistic Map");

		double r = 3.82;
		Random random = new Random();

		for (int si = 0; si < sampleCount; si++) {

			double x = random.nextDouble();

			Matrix input = MatrixFactory.zeros(1, inputLength);
			for (int i = 0; i < inputLength; i++) {
				x = r * x * (1 - x);
				input.setAsDouble(x, 0, i);
			}

			Matrix target = MatrixFactory.zeros(1, predictionLength);
			for (int i = 0; i < predictionLength; i++) {
				x = r * x * (1 - x);
				target.setAsDouble(x, 0, i);
			}

			Sample s = SampleFactory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			logistic.getSamples().add(s);
		}

		return logistic;
	}

	public static final RegressionDataSet regressionDataSet(String label) {
		RegressionDataSet ds = new RegressionDataSet();
		ds.setLabel(label);
		return ds;
	}

	public static DefaultDataSet ANIMALS() throws MatrixException {
		DefaultDataSet animals = DataSetFactory.labeledDataSet("Animals");

		Sample pigeon = SampleFactory.labeledSample("Pigeon");
		pigeon.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 0 0 1 0"));
		animals.getSamples().add(pigeon);

		Sample chicken = SampleFactory.labeledSample("Chicken");
		chicken.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 0 0 0 0"));
		animals.getSamples().add(chicken);

		Sample duck = SampleFactory.labeledSample("Duck");
		duck.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 0 0 0 1"));
		animals.getSamples().add(duck);

		Sample goose = SampleFactory.labeledSample("Goose");
		goose.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 0 0 1 1"));
		animals.getSamples().add(goose);

		Sample owl = SampleFactory.labeledSample("Owl");
		owl.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.getSamples().add(owl);

		Sample falcon = SampleFactory.labeledSample("Falcon");
		falcon.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.getSamples().add(falcon);

		Sample eagle = SampleFactory.labeledSample("Eagle");
		eagle.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 1 0 1 0 0 0 0 1 1 0 1 0"));
		animals.getSamples().add(eagle);

		Sample fox = SampleFactory.labeledSample("Fox");
		fox.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 1 0 0 1 1 0 0 0 1 0 0 0"));
		animals.getSamples().add(fox);

		Sample dog = SampleFactory.labeledSample("Dog");
		dog.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 1 0 0 1 1 0 0 0 0 1 0 0"));
		animals.getSamples().add(dog);

		Sample wolf = SampleFactory.labeledSample("Wolf");
		wolf.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 1 0 0 1 1 0 1 0 1 1 0 0"));
		animals.getSamples().add(wolf);

		Sample cat = SampleFactory.labeledSample("Cat");
		cat.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"1 0 0 0 1 1 0 0 0 1 0 0 0"));
		animals.getSamples().add(cat);

		Sample tiger = SampleFactory.labeledSample("Tiger");
		tiger.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 0 1 0 1 1 0 0 0 1 1 0 0"));
		animals.getSamples().add(tiger);

		Sample lion = SampleFactory.labeledSample("Lion");
		lion.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 0 1 0 1 1 0 1 0 1 1 0 0"));
		animals.getSamples().add(lion);

		Sample horse = SampleFactory.labeledSample("Horse");
		horse.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.getSamples().add(horse);

		Sample zebra = SampleFactory.labeledSample("Zebra");
		zebra.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.getSamples().add(zebra);

		Sample cow = SampleFactory.labeledSample("Cow");
		cow.setMatrix(Sample.INPUT, MatrixFactory.importFromString(FileFormat.CSV,
				"0 0 1 0 1 1 1 0 0 0 0 0 0"));
		animals.getSamples().add(cow);

		return animals;
	}

	public static final DefaultDataSet labeledDataSet(String label) {
		DefaultDataSet ds = new DefaultDataSet();
		ds.setLabel(label);
		return ds;
	}

	public static ClassificationDataSet Linear1() {
		ClassificationDataSet or = classificationDataSet("Linear1");

		ClassificationSample x0 = SampleFactory.classificationSample("0.0=0.0");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		or.getSamples().add(x0);

		ClassificationSample x1 = SampleFactory.classificationSample("0.1=0.1");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.getSamples().add(x1);

		ClassificationSample x2 = SampleFactory.classificationSample("0.2=0.2");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.getSamples().add(x2);

		ClassificationSample x3 = SampleFactory.classificationSample("0.3=0.3");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.getSamples().add(x3);

		ClassificationSample x4 = SampleFactory.classificationSample("0.4=0.4");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.getSamples().add(x4);

		ClassificationSample x5 = SampleFactory.classificationSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x5);

		return or;
	}

	public static ClassificationDataSet Linear3() {
		ClassificationDataSet or = classificationDataSet("Linear3");

		ClassificationSample x0 = SampleFactory.classificationSample("0.0=0.5");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x0);

		ClassificationSample x1 = SampleFactory.classificationSample("0.1=0.5");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x1);

		ClassificationSample x2 = SampleFactory.classificationSample("0.2=0.5");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x2);

		ClassificationSample x3 = SampleFactory.classificationSample("0.3=0.5");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x3);

		ClassificationSample x4 = SampleFactory.classificationSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x4);

		ClassificationSample x5 = SampleFactory.classificationSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x5);

		return or;
	}

	public static ClassificationDataSet Linear2() {
		ClassificationDataSet or = classificationDataSet("Linear2");

		ClassificationSample x0 = SampleFactory.classificationSample("0.0=0.1");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.getSamples().add(x0);

		ClassificationSample x1 = SampleFactory.classificationSample("0.1=0.2");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.getSamples().add(x1);

		ClassificationSample x2 = SampleFactory.classificationSample("0.2=0.3");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.getSamples().add(x2);

		ClassificationSample x3 = SampleFactory.classificationSample("0.3=0.4");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.getSamples().add(x3);

		ClassificationSample x4 = SampleFactory.classificationSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSamples().add(x4);

		ClassificationSample x5 = SampleFactory.classificationSample("0.5=0.6");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0.6 }).transpose());
		or.getSamples().add(x5);

		return or;
	}

	public static ClassificationDataSet OR() {
		ClassificationDataSet or = classificationDataSet("OR-Problem");

		ClassificationSample x000 = SampleFactory.classificationSample("00=01");
		x000.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		or.getSamples().add(x000);

		ClassificationSample x011 = SampleFactory.classificationSample("01=10");
		x011.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSamples().add(x011);

		ClassificationSample x101 = SampleFactory.classificationSample("10=10");
		x101.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSamples().add(x101);

		ClassificationSample x110 = SampleFactory.classificationSample("11=10");
		x110.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSamples().add(x110);

		return or;
	}

	public static ClassificationDataSet XOR() {
		ClassificationDataSet xor = classificationDataSet("XOR-Problem");

		ClassificationSample x000 = SampleFactory.classificationSample("00=01");
		x000.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.getSamples().add(x000);

		ClassificationSample x011 = SampleFactory.classificationSample("01=10");
		x011.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.getSamples().add(x011);

		ClassificationSample x101 = SampleFactory.classificationSample("10=10");
		x101.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.getSamples().add(x101);

		ClassificationSample x110 = SampleFactory.classificationSample("11=01");
		x110.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.getSamples().add(x110);

		return xor;
	}

	public static ClassificationDataSet IRIS() throws MatrixException {
		StringBuilder s = new StringBuilder();
		s.append("5.1,3.5,1.4,0.2,Iris-setosa\n");
		s.append("4.9,3.0,1.4,0.2,Iris-setosa\n");
		s.append("4.7,3.2,1.3,0.2,Iris-setosa\n");
		s.append("4.6,3.1,1.5,0.2,Iris-setosa\n");
		s.append("5.0,3.6,1.4,0.2,Iris-setosa\n");
		s.append("5.4,3.9,1.7,0.4,Iris-setosa\n");
		s.append("4.6,3.4,1.4,0.3,Iris-setosa\n");
		s.append("5.0,3.4,1.5,0.2,Iris-setosa\n");
		s.append("4.4,2.9,1.4,0.2,Iris-setosa\n");
		s.append("4.9,3.1,1.5,0.1,Iris-setosa\n");
		s.append("5.4,3.7,1.5,0.2,Iris-setosa\n");
		s.append("4.8,3.4,1.6,0.2,Iris-setosa\n");
		s.append("4.8,3.0,1.4,0.1,Iris-setosa\n");
		s.append("4.3,3.0,1.1,0.1,Iris-setosa\n");
		s.append("5.8,4.0,1.2,0.2,Iris-setosa\n");
		s.append("5.7,4.4,1.5,0.4,Iris-setosa\n");
		s.append("5.4,3.9,1.3,0.4,Iris-setosa\n");
		s.append("5.1,3.5,1.4,0.3,Iris-setosa\n");
		s.append("5.7,3.8,1.7,0.3,Iris-setosa\n");
		s.append("5.1,3.8,1.5,0.3,Iris-setosa\n");
		s.append("5.4,3.4,1.7,0.2,Iris-setosa\n");
		s.append("5.1,3.7,1.5,0.4,Iris-setosa\n");
		s.append("4.6,3.6,1.0,0.2,Iris-setosa\n");
		s.append("5.1,3.3,1.7,0.5,Iris-setosa\n");
		s.append("4.8,3.4,1.9,0.2,Iris-setosa\n");
		s.append("5.0,3.0,1.6,0.2,Iris-setosa\n");
		s.append("5.0,3.4,1.6,0.4,Iris-setosa\n");
		s.append("5.2,3.5,1.5,0.2,Iris-setosa\n");
		s.append("5.2,3.4,1.4,0.2,Iris-setosa\n");
		s.append("4.7,3.2,1.6,0.2,Iris-setosa\n");
		s.append("4.8,3.1,1.6,0.2,Iris-setosa\n");
		s.append("5.4,3.4,1.5,0.4,Iris-setosa\n");
		s.append("5.2,4.1,1.5,0.1,Iris-setosa\n");
		s.append("5.5,4.2,1.4,0.2,Iris-setosa\n");
		s.append("4.9,3.1,1.5,0.1,Iris-setosa\n");
		s.append("5.0,3.2,1.2,0.2,Iris-setosa\n");
		s.append("5.5,3.5,1.3,0.2,Iris-setosa\n");
		s.append("4.9,3.1,1.5,0.1,Iris-setosa\n");
		s.append("4.4,3.0,1.3,0.2,Iris-setosa\n");
		s.append("5.1,3.4,1.5,0.2,Iris-setosa\n");
		s.append("5.0,3.5,1.3,0.3,Iris-setosa\n");
		s.append("4.5,2.3,1.3,0.3,Iris-setosa\n");
		s.append("4.4,3.2,1.3,0.2,Iris-setosa\n");
		s.append("5.0,3.5,1.6,0.6,Iris-setosa\n");
		s.append("5.1,3.8,1.9,0.4,Iris-setosa\n");
		s.append("4.8,3.0,1.4,0.3,Iris-setosa\n");
		s.append("5.1,3.8,1.6,0.2,Iris-setosa\n");
		s.append("4.6,3.2,1.4,0.2,Iris-setosa\n");
		s.append("5.3,3.7,1.5,0.2,Iris-setosa\n");
		s.append("5.0,3.3,1.4,0.2,Iris-setosa\n");
		s.append("7.0,3.2,4.7,1.4,Iris-versicolor\n");
		s.append("6.4,3.2,4.5,1.5,Iris-versicolor\n");
		s.append("6.9,3.1,4.9,1.5,Iris-versicolor\n");
		s.append("5.5,2.3,4.0,1.3,Iris-versicolor\n");
		s.append("6.5,2.8,4.6,1.5,Iris-versicolor\n");
		s.append("5.7,2.8,4.5,1.3,Iris-versicolor\n");
		s.append("6.3,3.3,4.7,1.6,Iris-versicolor\n");
		s.append("4.9,2.4,3.3,1.0,Iris-versicolor\n");
		s.append("6.6,2.9,4.6,1.3,Iris-versicolor\n");
		s.append("5.2,2.7,3.9,1.4,Iris-versicolor\n");
		s.append("5.0,2.0,3.5,1.0,Iris-versicolor\n");
		s.append("5.9,3.0,4.2,1.5,Iris-versicolor\n");
		s.append("6.0,2.2,4.0,1.0,Iris-versicolor\n");
		s.append("6.1,2.9,4.7,1.4,Iris-versicolor\n");
		s.append("5.6,2.9,3.6,1.3,Iris-versicolor\n");
		s.append("6.7,3.1,4.4,1.4,Iris-versicolor\n");
		s.append("5.6,3.0,4.5,1.5,Iris-versicolor\n");
		s.append("5.8,2.7,4.1,1.0,Iris-versicolor\n");
		s.append("6.2,2.2,4.5,1.5,Iris-versicolor\n");
		s.append("5.6,2.5,3.9,1.1,Iris-versicolor\n");
		s.append("5.9,3.2,4.8,1.8,Iris-versicolor\n");
		s.append("6.1,2.8,4.0,1.3,Iris-versicolor\n");
		s.append("6.3,2.5,4.9,1.5,Iris-versicolor\n");
		s.append("6.1,2.8,4.7,1.2,Iris-versicolor\n");
		s.append("6.4,2.9,4.3,1.3,Iris-versicolor\n");
		s.append("6.6,3.0,4.4,1.4,Iris-versicolor\n");
		s.append("6.8,2.8,4.8,1.4,Iris-versicolor\n");
		s.append("6.7,3.0,5.0,1.7,Iris-versicolor\n");
		s.append("6.0,2.9,4.5,1.5,Iris-versicolor\n");
		s.append("5.7,2.6,3.5,1.0,Iris-versicolor\n");
		s.append("5.5,2.4,3.8,1.1,Iris-versicolor\n");
		s.append("5.5,2.4,3.7,1.0,Iris-versicolor\n");
		s.append("5.8,2.7,3.9,1.2,Iris-versicolor\n");
		s.append("6.0,2.7,5.1,1.6,Iris-versicolor\n");
		s.append("5.4,3.0,4.5,1.5,Iris-versicolor\n");
		s.append("6.0,3.4,4.5,1.6,Iris-versicolor\n");
		s.append("6.7,3.1,4.7,1.5,Iris-versicolor\n");
		s.append("6.3,2.3,4.4,1.3,Iris-versicolor\n");
		s.append("5.6,3.0,4.1,1.3,Iris-versicolor\n");
		s.append("5.5,2.5,4.0,1.3,Iris-versicolor\n");
		s.append("5.5,2.6,4.4,1.2,Iris-versicolor\n");
		s.append("6.1,3.0,4.6,1.4,Iris-versicolor\n");
		s.append("5.8,2.6,4.0,1.2,Iris-versicolor\n");
		s.append("5.0,2.3,3.3,1.0,Iris-versicolor\n");
		s.append("5.6,2.7,4.2,1.3,Iris-versicolor\n");
		s.append("5.7,3.0,4.2,1.2,Iris-versicolor\n");
		s.append("5.7,2.9,4.2,1.3,Iris-versicolor\n");
		s.append("6.2,2.9,4.3,1.3,Iris-versicolor\n");
		s.append("5.1,2.5,3.0,1.1,Iris-versicolor\n");
		s.append("5.7,2.8,4.1,1.3,Iris-versicolor\n");
		s.append("6.3,3.3,6.0,2.5,Iris-virginica\n");
		s.append("5.8,2.7,5.1,1.9,Iris-virginica\n");
		s.append("7.1,3.0,5.9,2.1,Iris-virginica\n");
		s.append("6.3,2.9,5.6,1.8,Iris-virginica\n");
		s.append("6.5,3.0,5.8,2.2,Iris-virginica\n");
		s.append("7.6,3.0,6.6,2.1,Iris-virginica\n");
		s.append("4.9,2.5,4.5,1.7,Iris-virginica\n");
		s.append("7.3,2.9,6.3,1.8,Iris-virginica\n");
		s.append("6.7,2.5,5.8,1.8,Iris-virginica\n");
		s.append("7.2,3.6,6.1,2.5,Iris-virginica\n");
		s.append("6.5,3.2,5.1,2.0,Iris-virginica\n");
		s.append("6.4,2.7,5.3,1.9,Iris-virginica\n");
		s.append("6.8,3.0,5.5,2.1,Iris-virginica\n");
		s.append("5.7,2.5,5.0,2.0,Iris-virginica\n");
		s.append("5.8,2.8,5.1,2.4,Iris-virginica\n");
		s.append("6.4,3.2,5.3,2.3,Iris-virginica\n");
		s.append("6.5,3.0,5.5,1.8,Iris-virginica\n");
		s.append("7.7,3.8,6.7,2.2,Iris-virginica\n");
		s.append("7.7,2.6,6.9,2.3,Iris-virginica\n");
		s.append("6.0,2.2,5.0,1.5,Iris-virginica\n");
		s.append("6.9,3.2,5.7,2.3,Iris-virginica\n");
		s.append("5.6,2.8,4.9,2.0,Iris-virginica\n");
		s.append("7.7,2.8,6.7,2.0,Iris-virginica\n");
		s.append("6.3,2.7,4.9,1.8,Iris-virginica\n");
		s.append("6.7,3.3,5.7,2.1,Iris-virginica\n");
		s.append("7.2,3.2,6.0,1.8,Iris-virginica\n");
		s.append("6.2,2.8,4.8,1.8,Iris-virginica\n");
		s.append("6.1,3.0,4.9,1.8,Iris-virginica\n");
		s.append("6.4,2.8,5.6,2.1,Iris-virginica\n");
		s.append("7.2,3.0,5.8,1.6,Iris-virginica\n");
		s.append("7.4,2.8,6.1,1.9,Iris-virginica\n");
		s.append("7.9,3.8,6.4,2.0,Iris-virginica\n");
		s.append("6.4,2.8,5.6,2.2,Iris-virginica\n");
		s.append("6.3,2.8,5.1,1.5,Iris-virginica\n");
		s.append("6.1,2.6,5.6,1.4,Iris-virginica\n");
		s.append("7.7,3.0,6.1,2.3,Iris-virginica\n");
		s.append("6.3,3.4,5.6,2.4,Iris-virginica\n");
		s.append("6.4,3.1,5.5,1.8,Iris-virginica\n");
		s.append("6.0,3.0,4.8,1.8,Iris-virginica\n");
		s.append("6.9,3.1,5.4,2.1,Iris-virginica\n");
		s.append("6.7,3.1,5.6,2.4,Iris-virginica\n");
		s.append("6.9,3.1,5.1,2.3,Iris-virginica\n");
		s.append("5.8,2.7,5.1,1.9,Iris-virginica\n");
		s.append("6.8,3.2,5.9,2.3,Iris-virginica\n");
		s.append("6.7,3.3,5.7,2.5,Iris-virginica\n");
		s.append("6.7,3.0,5.2,2.3,Iris-virginica\n");
		s.append("6.3,2.5,5.0,1.9,Iris-virginica\n");
		s.append("6.5,3.0,5.2,2.0,Iris-virginica\n");
		s.append("6.2,3.4,5.4,2.3,Iris-virginica\n");
		s.append("5.9,3.0,5.1,1.8,Iris-virginica\n");

		Matrix m = MatrixFactory.importFromString(FileFormat.CSV, s.toString(), ",");
		Matrix input = m.selectColumns(Ret.NEW, 0, 1, 2, 3);
		Matrix output = m.selectColumns(Ret.NEW, 4).discretizeToColumns(0);

		ClassificationDataSet iris = DataSetFactory.copyFromMatrix(input.convert(ValueType.DOUBLE),
				output.convert(ValueType.DOUBLE));
		iris.setLabel("Iris flower dataset");

		for (int i = 0; i < 50; i++) {
			iris.getSamples().getElementAt(i).setLabel("Iris-setosa");
		}
		for (int i = 50; i < 100; i++) {
			iris.getSamples().getElementAt(i).setLabel("Iris-versicolor");
		}
		for (int i = 100; i < 150; i++) {
			iris.getSamples().getElementAt(i).setLabel("Iris-virginica");
		}

		return iris;
	}

	public static final ClassificationDataSet classificationDataSet(String label) {
		ClassificationDataSet ds = new ClassificationDataSet();
		ds.setLabel(label);
		return ds;
	}

	public static DataSet linkToDir(File dir, Object... parameters) throws MatrixException,
			IOException {
		return new DirDataSet(dir, parameters);
	}

	public static DataSet linkToDir(FileFormat fileFormat, File dir, Object... parameters)
			throws MatrixException, IOException {
		return new DirDataSet(fileFormat, dir, parameters);
	}

}
