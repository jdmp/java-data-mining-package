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

package org.jdmp.core.dataset;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.BitSet;
import java.util.Random;

import org.jdmp.core.algorithm.basic.CreateIris;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.filematrix.FileFormat;

public class DefaultDataSetFactory extends AbstractDataSetFactory {

	public ListDataSet emptyDataSet() {
		return new DefaultDataSet();
	}

	public ListDataSet importFromFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		switch (format) {
		default:
			Matrix m = Matrix.Factory.importFrom().file(file).asDenseCSV();
			return linkToInput(m);
		}
	}

	public ListDataSet linkToFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		switch (format) {
		default:
			Matrix m = Matrix.Factory.linkTo().file(file).asDenseCSV();
			return linkToInput(m);
		}
	}

	public ListDataSet linkToInput(Matrix input) {
		ListDataSet ds = new MatrixDataSet(input);
		return ds;
	}

	public ListDataSet linkToInputAndLabels(Matrix input, Matrix labels) {
		ListDataSet ds = new MatrixDataSet(input);
		ds.setMatrix("Labels", labels);
		return ds;
	}

	public ListDataSet linkToInputAndTarget(Matrix input, Matrix target) {
		ListDataSet ds = new MatrixDataSet(input);
		ds.setMatrix(DataSet.TARGET, target);
		return ds;
	}

	public ListDataSet linkToInputTargetAndLabel(Matrix input, Matrix target, Matrix labels) {
		ListDataSet ds = new MatrixDataSet(input);
		ds.setMatrix("Labels", labels);
		ds.setMatrix(DataSet.TARGET, target);
		return ds;
	}

	public ListDataSet HenonMap(int sampleCount, int inputLength, int predictionLength) {
		ListDataSet henon = ListDataSet.Factory.labeledDataSet("Henon Map");

		Random random = new Random();

		for (int si = 0; si < sampleCount; si++) {

			double q1 = random.nextDouble() * 1.26 * 2 - 1.26;
			q1 = q1 >= 1.26 ? q1 - 1.26 : q1;
			q1 = q1 < -1.26 ? q1 + 1.26 : q1;

			double q2 = random.nextDouble() * 1.26 * 2 - 1.26;
			q2 = q2 >= 1.26 ? q2 - 1.26 : q2;
			q2 = q2 < -1.26 ? q2 + 1.26 : q2;

			double q = 0;

			Matrix input = Matrix.Factory.zeros(1, inputLength);
			for (int i = 0; i < inputLength; i++) {
				q = henon(q1, q2);
				input.setAsDouble(q / 2, 0, i);
				q2 = q1;
				q1 = q;
			}

			Matrix target = Matrix.Factory.zeros(1, predictionLength);
			for (int i = 0; i < predictionLength; i++) {
				q = henon(q1, q2);
				target.setAsDouble(q / 2, 0, i);
				q2 = q1;
				q1 = q;
			}

			Sample s = Sample.Factory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			henon.add(s);
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

	public ListDataSet LogisticMap(int sampleCount, int inputLength, int predictionLength) {
		ListDataSet logistic = ListDataSet.Factory.labeledDataSet("Logistic Map");

		double r = 3.82;
		Random random = new Random();

		for (int si = 0; si < sampleCount; si++) {

			double x = random.nextDouble();

			Matrix input = Matrix.Factory.zeros(1, inputLength);
			for (int i = 0; i < inputLength; i++) {
				x = r * x * (1 - x);
				input.setAsDouble(x, 0, i);
			}

			Matrix target = Matrix.Factory.zeros(1, predictionLength);
			for (int i = 0; i < predictionLength; i++) {
				x = r * x * (1 - x);
				target.setAsDouble(x, 0, i);
			}

			Sample s = Sample.Factory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			logistic.add(s);
		}

		return logistic;
	}

	public AbstractDataSet ANIMALS() {
		AbstractDataSet animals = ListDataSet.Factory.labeledDataSet("Animals");

		Sample pigeon = Sample.Factory.labeledSample("Pigeon");
		pigeon.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0 })
						.transpose());
		animals.add(pigeon);

		Sample chicken = Sample.Factory.labeledSample("Chicken");
		chicken.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 })
						.transpose());
		animals.add(chicken);

		Sample duck = Sample.Factory.labeledSample("Duck");
		duck.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 })
						.transpose());
		animals.add(duck);

		Sample goose = Sample.Factory.labeledSample("Goose");
		goose.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1 })
						.transpose());
		animals.add(goose);

		Sample owl = Sample.Factory.labeledSample("Owl");
		owl.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.add(owl);

		Sample falcon = Sample.Factory.labeledSample("Falcon");
		falcon.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.add(falcon);

		Sample eagle = Sample.Factory.labeledSample("Eagle");
		eagle.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.add(eagle);

		Sample fox = Sample.Factory.labeledSample("Fox");
		fox.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0 })
						.transpose());
		animals.add(fox);

		Sample dog = Sample.Factory.labeledSample("Dog");
		dog.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0 })
						.transpose());
		animals.add(dog);

		Sample wolf = Sample.Factory.labeledSample("Wolf");
		wolf.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0 })
						.transpose());
		animals.add(wolf);

		Sample cat = Sample.Factory.labeledSample("Cat");
		cat.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0 })
						.transpose());
		animals.add(cat);

		Sample tiger = Sample.Factory.labeledSample("Tiger");
		tiger.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0 })
						.transpose());
		animals.add(tiger);

		Sample lion = Sample.Factory.labeledSample("Lion");
		lion.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0 })
						.transpose());
		animals.add(lion);

		Sample horse = Sample.Factory.labeledSample("Horse");
		horse.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0 })
						.transpose());
		animals.add(horse);

		Sample zebra = Sample.Factory.labeledSample("Zebra");
		zebra.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0 })
						.transpose());
		animals.add(zebra);

		Sample cow = Sample.Factory.labeledSample("Cow");
		cow.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 })
						.transpose());
		animals.add(cow);

		return animals;
	}

	public final AbstractDataSet labeledDataSet(String label) {
		AbstractDataSet ds = new DefaultDataSet();
		ds.setLabel(label);
		return ds;
	}

	public ListDataSet CountActive(int number) {
		ListDataSet ds = labeledDataSet("Count " + number);
		double possibilites = Math.pow(2, number);
		for (int i = 0; i < possibilites; i++) {
			BitSet bits = BitSet.valueOf(new long[] { i });
			Sample sample = Sample.Factory.labeledSample("" + i);
			Matrix input = Matrix.Factory.zeros(1, number);
			Matrix target = Matrix.Factory.zeros(1, number + 1);
			int count = 0;
			for (int j = 0; j < number; j++) {
				if (bits.get(j)) {
					input.setAsDouble(1, 0, j);
					count++;
				}
			}
			target.setAsDouble(1, 0, count);
			sample.setMatrix(Sample.INPUT, input);
			sample.setMatrix(Sample.TARGET, target);
			ds.add(sample);
		}
		return ds;
	}

	public ListDataSet Linear1() {
		ListDataSet or = labeledDataSet("Linear1");

		Sample x0 = Sample.Factory.labeledSample("0.0=0.0");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		or.add(x0);

		Sample x1 = Sample.Factory.labeledSample("0.1=0.1");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		or.add(x1);

		Sample x2 = Sample.Factory.labeledSample("0.2=0.2");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		or.add(x2);

		Sample x3 = Sample.Factory.labeledSample("0.3=0.3");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		or.add(x3);

		Sample x4 = Sample.Factory.labeledSample("0.4=0.4");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		or.add(x4);

		Sample x5 = Sample.Factory.labeledSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x5);

		return or;
	}

	public ListDataSet Linear3() {
		ListDataSet or = labeledDataSet("Linear3");

		Sample x0 = Sample.Factory.labeledSample("0.0=0.5");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x0);

		Sample x1 = Sample.Factory.labeledSample("0.1=0.5");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x1);

		Sample x2 = Sample.Factory.labeledSample("0.2=0.5");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x2);

		Sample x3 = Sample.Factory.labeledSample("0.3=0.5");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x3);

		Sample x4 = Sample.Factory.labeledSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x4);

		Sample x5 = Sample.Factory.labeledSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x5);

		return or;
	}

	public ListDataSet Linear2() {
		ListDataSet or = labeledDataSet("Linear2");

		Sample x0 = Sample.Factory.labeledSample("0.0=0.1");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		or.add(x0);

		Sample x1 = Sample.Factory.labeledSample("0.1=0.2");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		or.add(x1);

		Sample x2 = Sample.Factory.labeledSample("0.2=0.3");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		or.add(x2);

		Sample x3 = Sample.Factory.labeledSample("0.3=0.4");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		or.add(x3);

		Sample x4 = Sample.Factory.labeledSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.add(x4);

		Sample x5 = Sample.Factory.labeledSample("0.5=0.6");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.6 }).transpose());
		or.add(x5);

		return or;
	}

	public ListDataSet OR() {
		ListDataSet or = labeledDataSet("OR-Problem");

		Sample x000 = Sample.Factory.labeledSample("00=01");
		x000.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		or.add(x000);

		Sample x011 = Sample.Factory.labeledSample("01=10");
		x011.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.add(x011);

		Sample x101 = Sample.Factory.labeledSample("10=10");
		x101.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.add(x101);

		Sample x110 = Sample.Factory.labeledSample("11=10");
		x110.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.add(x110);

		return or;
	}

	public ListDataSet XOR() {
		ListDataSet xor = labeledDataSet("XOR-Problem");

		Sample x000 = Sample.Factory.labeledSample("00=01");
		x000.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.add(x000);

		Sample x011 = Sample.Factory.labeledSample("01=10");
		x011.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.add(x011);

		Sample x101 = Sample.Factory.labeledSample("10=10");
		x101.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.add(x101);

		Sample x110 = Sample.Factory.labeledSample("11=01");
		x110.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.add(x110);

		return xor;
	}

	public ListDataSet ONE() {
		ListDataSet one = labeledDataSet("DataSet with one sample 1->1");
		Sample x1 = Sample.Factory.labeledSample("1=1");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1 }).transpose());
		one.add(x1);
		return one;
	}

	public ListDataSet IRIS() {
		ListDataSet ds = (ListDataSet) (new CreateIris().calculate().get(Sample.TARGET));
		return ds;
	}

	public ListDataSet importFromURL(FileFormat fileFormat, URL url, Object... parameters)
			throws Exception {
		switch (fileFormat) {
		default:
			Matrix m = Matrix.Factory.importFrom().url(url).asDenseCSV();
			return linkToInput(m);
		}
	}

	public ListDataSet importFromClipboard() throws IOException {
		Matrix m = Matrix.Factory.importFrom().clipboard().asDenseCSV();
		return linkToInput(m);
	}

	public ListDataSet importFromJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		Matrix m = Matrix.Factory.importFromJDBC(type, host, port, database, sqlStatement,
				username, password);
		return linkToInput(m);
	}

	public ListDataSet importFromJDBC(String url, String sqlStatement, String username,
			String password) {
		Matrix m = Matrix.Factory.importFromJDBC(url, sqlStatement, username, password);
		return linkToInput(m);
	}

	public ListDataSet linkToJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		Matrix m = Matrix.Factory.linkToJDBC(type, host, port, database, sqlStatement, username,
				password);
		return linkToInput(m);
	}

	public ListDataSet linkToJDBC(String url, String sqlStatement, String username, String password) {
		Matrix m = Matrix.Factory.linkToJDBC(url, sqlStatement, username, password);
		return linkToInput(m);
	}

}
