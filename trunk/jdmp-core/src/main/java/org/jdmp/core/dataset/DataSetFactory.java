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
import org.jdmp.core.sample.SampleFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.filematrix.FileFormat;

public abstract class DataSetFactory {

	public static DataSet emptyDataSet() {
		return new DefaultDataSet();
	}

	public static DataSet importFromMatrix(Matrix input, Matrix target) {
		DataSet ds = new DefaultDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = SampleFactory.emptySample();
			Matrix in = input.selectRows(Ret.NEW, i);
			Matrix out = target.selectRows(Ret.NEW, i);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSampleMap().add(s);
		}
		return ds;
	}

	public static DataSet importFromFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		switch (format) {
		default:
			Matrix m = Matrix.Factory.importFrom().file(file).asDenseCSV();
			return importFromMatrix(m);
		}
	}

	public static DataSet linkToFile(FileFormat format, File file, Object... parameters)
			throws IOException {
		switch (format) {
		default:
			Matrix m = Matrix.Factory.linkTo().file(file).asDenseCSV();
			return linkToMatrix(m);
		}
	}

	public static DataSet linkToMatrix(Matrix matrix) {
		// TODO: this should be improved
		return importFromMatrix(matrix);
	}

	public static DataSet importFromMatrix(Matrix matrix) {
		DataSet ds = emptyDataSet();
		ds.setLabel(matrix.getLabel());
		for (int r = 0; r < matrix.getRowCount(); r++) {
			Sample s = SampleFactory.emptySample();
			if (matrix.getRowLabel(r) != null) {
				s.setLabel(matrix.getRowLabel(r));
			}
			for (int c = 0; c < matrix.getColumnCount(); c++) {
				String label = matrix.getColumnLabel(c);
				if (label == null) {
					label = "col" + c;
				}
				s.setObject(label, matrix.getAsObject(r, c));
			}
			ds.getSampleMap().add(s);
		}
		return ds;
	}

	public static DataSet importFromMatrix(Matrix input, Matrix target, Matrix label) {
		DataSet ds = new DefaultDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = SampleFactory.emptySample();
			Matrix in = input.subMatrix(Ret.NEW, i, 0, i, input.getColumnCount() - 1);
			Matrix out = target.subMatrix(Ret.NEW, i, 0, i, target.getColumnCount() - 1);
			Matrix labelMatrix = label.subMatrix(Ret.NEW, i, 0, i, label.getColumnCount() - 1);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			s.setMatrix(Sample.LABEL, labelMatrix);
			ds.getSampleMap().add(s);
		}
		return ds;
	}

	public static DataSet linkToMatrix(Matrix input, Matrix target) {
		DataSet ds = new DefaultDataSet();
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = SampleFactory.emptySample();
			Matrix in = input.selectRows(Ret.LINK, i);
			Matrix out = target.selectRows(Ret.LINK, i);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			ds.getSampleMap().add(s);
		}
		return ds;
	}

	public static DataSet linkToMatrix(Matrix input, Matrix target, Matrix label) {
		DataSet ds = new DefaultDataSet();
		ds.getInputVariable().add(input);
		ds.getTargetVariable().add(target);
		for (int i = 0; i < input.getRowCount(); i++) {
			Sample s = SampleFactory.emptySample();
			Matrix in = input.selectRows(Ret.LINK, i);
			Matrix out = target.selectRows(Ret.LINK, i);
			Matrix labelMatrix = label.selectRows(Ret.LINK, i);
			s.setMatrix(Sample.INPUT, in);
			s.setMatrix(Sample.TARGET, out);
			s.setMatrix(Sample.LABEL, labelMatrix);
			ds.getSampleMap().add(s);
		}
		return ds;
	}

	public static DataSet HenonMap(int sampleCount, int inputLength, int predictionLength) {
		DataSet henon = DataSetFactory.labeledDataSet("Henon Map");

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

			Sample s = SampleFactory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			henon.getSampleMap().add(s);
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

	public static DataSet LogisticMap(int sampleCount, int inputLength, int predictionLength) {
		DataSet logistic = DataSetFactory.labeledDataSet("Logistic Map");

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

			Sample s = SampleFactory.labeledSample("Sample " + si);
			s.setMatrix(Sample.INPUT, input);
			s.setMatrix(Sample.TARGET, target);

			logistic.getSampleMap().add(s);
		}

		return logistic;
	}

	public static DefaultDataSet ANIMALS() {
		DefaultDataSet animals = DataSetFactory.labeledDataSet("Animals");

		Sample pigeon = SampleFactory.labeledSample("Pigeon");
		pigeon.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0 })
						.transpose());
		animals.getSampleMap().add(pigeon);

		Sample chicken = SampleFactory.labeledSample("Chicken");
		chicken.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 })
						.transpose());
		animals.getSampleMap().add(chicken);

		Sample duck = SampleFactory.labeledSample("Duck");
		duck.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1 })
						.transpose());
		animals.getSampleMap().add(duck);

		Sample goose = SampleFactory.labeledSample("Goose");
		goose.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 1 })
						.transpose());
		animals.getSampleMap().add(goose);

		Sample owl = SampleFactory.labeledSample("Owl");
		owl.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.getSampleMap().add(owl);

		Sample falcon = SampleFactory.labeledSample("Falcon");
		falcon.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.getSampleMap().add(falcon);

		Sample eagle = SampleFactory.labeledSample("Eagle");
		eagle.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0 })
						.transpose());
		animals.getSampleMap().add(eagle);

		Sample fox = SampleFactory.labeledSample("Fox");
		fox.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0 })
						.transpose());
		animals.getSampleMap().add(fox);

		Sample dog = SampleFactory.labeledSample("Dog");
		dog.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(dog);

		Sample wolf = SampleFactory.labeledSample("Wolf");
		wolf.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(wolf);

		Sample cat = SampleFactory.labeledSample("Cat");
		cat.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0 })
						.transpose());
		animals.getSampleMap().add(cat);

		Sample tiger = SampleFactory.labeledSample("Tiger");
		tiger.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(tiger);

		Sample lion = SampleFactory.labeledSample("Lion");
		lion.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(lion);

		Sample horse = SampleFactory.labeledSample("Horse");
		horse.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(horse);

		Sample zebra = SampleFactory.labeledSample("Zebra");
		zebra.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0 })
						.transpose());
		animals.getSampleMap().add(zebra);

		Sample cow = SampleFactory.labeledSample("Cow");
		cow.setMatrix(Sample.INPUT,
				Matrix.Factory.linkToArray(new double[] { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0 })
						.transpose());
		animals.getSampleMap().add(cow);

		return animals;
	}

	public static final DefaultDataSet labeledDataSet(String label) {
		DefaultDataSet ds = new DefaultDataSet();
		ds.setLabel(label);
		return ds;
	}

	public static DataSet CountActive(int number) {
		DataSet ds = labeledDataSet("Count " + number);
		double possibilites = Math.pow(2, number);
		for (int i = 0; i < possibilites; i++) {
			BitSet bits = BitSet.valueOf(new long[] { i });
			Sample sample = SampleFactory.labeledSample("" + i);
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
			ds.getSampleMap().add(sample);
		}
		return ds;
	}

	public static DataSet Linear1() {
		DataSet or = labeledDataSet("Linear1");

		Sample x0 = SampleFactory.labeledSample("0.0=0.0");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		or.getSampleMap().add(x0);

		Sample x1 = SampleFactory.labeledSample("0.1=0.1");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		or.getSampleMap().add(x1);

		Sample x2 = SampleFactory.labeledSample("0.2=0.2");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		or.getSampleMap().add(x2);

		Sample x3 = SampleFactory.labeledSample("0.3=0.3");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		or.getSampleMap().add(x3);

		Sample x4 = SampleFactory.labeledSample("0.4=0.4");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		or.getSampleMap().add(x4);

		Sample x5 = SampleFactory.labeledSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x5);

		return or;
	}

	public static DataSet Linear3() {
		DataSet or = labeledDataSet("Linear3");

		Sample x0 = SampleFactory.labeledSample("0.0=0.5");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x0);

		Sample x1 = SampleFactory.labeledSample("0.1=0.5");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x1);

		Sample x2 = SampleFactory.labeledSample("0.2=0.5");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x2);

		Sample x3 = SampleFactory.labeledSample("0.3=0.5");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x3);

		Sample x4 = SampleFactory.labeledSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x4);

		Sample x5 = SampleFactory.labeledSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x5);

		return or;
	}

	public static DataSet Linear2() {
		DataSet or = labeledDataSet("Linear2");

		Sample x0 = SampleFactory.labeledSample("0.0=0.1");
		x0.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		or.getSampleMap().add(x0);

		Sample x1 = SampleFactory.labeledSample("0.1=0.2");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		or.getSampleMap().add(x1);

		Sample x2 = SampleFactory.labeledSample("0.2=0.3");
		x2.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		or.getSampleMap().add(x2);

		Sample x3 = SampleFactory.labeledSample("0.3=0.4");
		x3.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		or.getSampleMap().add(x3);

		Sample x4 = SampleFactory.labeledSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		or.getSampleMap().add(x4);

		Sample x5 = SampleFactory.labeledSample("0.5=0.6");
		x5.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0.6 }).transpose());
		or.getSampleMap().add(x5);

		return or;
	}

	public static DataSet OR() {
		DataSet or = labeledDataSet("OR-Problem");

		Sample x000 = SampleFactory.labeledSample("00=01");
		x000.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		or.getSampleMap().add(x000);

		Sample x011 = SampleFactory.labeledSample("01=10");
		x011.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSampleMap().add(x011);

		Sample x101 = SampleFactory.labeledSample("10=10");
		x101.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSampleMap().add(x101);

		Sample x110 = SampleFactory.labeledSample("11=10");
		x110.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		or.getSampleMap().add(x110);

		return or;
	}

	public static DataSet XOR() {
		DataSet xor = labeledDataSet("XOR-Problem");

		Sample x000 = SampleFactory.labeledSample("00=01");
		x000.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.getSampleMap().add(x000);

		Sample x011 = SampleFactory.labeledSample("01=10");
		x011.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.getSampleMap().add(x011);

		Sample x101 = SampleFactory.labeledSample("10=10");
		x101.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.getSampleMap().add(x101);

		Sample x110 = SampleFactory.labeledSample("11=01");
		x110.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.getSampleMap().add(x110);

		return xor;
	}

	public static DataSet ONE() {
		DataSet one = labeledDataSet("DataSet with one sample 1->1");
		Sample x1 = SampleFactory.labeledSample("1=1");
		x1.setMatrix(Sample.INPUT, Matrix.Factory.linkToArray(new double[] { 1 }).transpose());
		x1.setMatrix(Sample.TARGET, Matrix.Factory.linkToArray(new double[] { 1 }).transpose());
		one.getSampleMap().add(x1);
		return one;
	}

	public static DataSet IRIS() throws Exception {
		DataSet ds = (DataSet) (new CreateIris().calculate().get(Sample.TARGET));
		return ds;
	}

	public static DataSet importFromURL(FileFormat fileFormat, URL url, Object... parameters)
			throws Exception {
		switch (fileFormat) {
		default:
			Matrix m = Matrix.Factory.importFrom().url(url).asDenseCSV();
			return importFromMatrix(m);
		}
	}

	public static DataSet importFromClipboard() throws IOException {
		Matrix m = Matrix.Factory.importFrom().clipboard().asDenseCSV();
		return importFromMatrix(m);
	}

	public static DataSet importFromJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		Matrix m = Matrix.Factory.importFromJDBC(type, host, port, database, sqlStatement,
				username, password);
		return importFromMatrix(m);
	}

	public static DataSet importFromJDBC(String url, String sqlStatement, String username,
			String password) {
		Matrix m = Matrix.Factory.importFromJDBC(url, sqlStatement, username, password);
		return importFromMatrix(m);
	}

	public static DataSet linkToJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password) {
		Matrix m = Matrix.Factory.linkToJDBC(type, host, port, database, sqlStatement, username,
				password);
		return linkToMatrix(m);
	}

	public static DataSet linkToJDBC(String url, String sqlStatement, String username,
			String password) {
		Matrix m = Matrix.Factory.linkToJDBC(url, sqlStatement, username, password);
		return linkToMatrix(m);
	}

}
