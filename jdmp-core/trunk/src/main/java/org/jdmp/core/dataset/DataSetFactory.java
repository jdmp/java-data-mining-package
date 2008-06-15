package org.jdmp.core.dataset;

import java.io.IOException;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.exceptions.MatrixException;

public abstract class DataSetFactory {

	public static BasicDataSet ANIMALS() throws MatrixException {
		BasicDataSet animals = new BasicDataSet("Animals");

		Sample pigeon = SampleFactory.labeledSample("Pigeon");
		pigeon.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 1 0"));
		animals.addSample(pigeon);

		Sample chicken = SampleFactory.labeledSample("Chicken");
		chicken
				.setMatrix(Sample.INPUT, MatrixFactory
						.importFromString("1 0 0 1 0 0 0 0 1 0 0 0 0"));
		animals.addSample(chicken);

		Sample duck = SampleFactory.labeledSample("Duck");
		duck.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 0 1"));
		animals.addSample(duck);

		Sample goose = SampleFactory.labeledSample("Goose");
		goose.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 1 1"));
		animals.addSample(goose);

		Sample owl = SampleFactory.labeledSample("Owl");
		owl.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(owl);

		Sample falcon = SampleFactory.labeledSample("Falcon");
		falcon.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(falcon);

		Sample eagle = SampleFactory.labeledSample("Eagle");
		eagle.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 1 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(eagle);

		Sample fox = SampleFactory.labeledSample("Fox");
		fox.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 1 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(fox);

		Sample dog = SampleFactory.labeledSample("Dog");
		dog.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 1 0 0 1 1 0 0 0 0 1 0 0"));
		animals.addSample(dog);

		Sample wolf = SampleFactory.labeledSample("Wolf");
		wolf.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 1 0 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(wolf);

		Sample cat = SampleFactory.labeledSample("Cat");
		cat.setMatrix(Sample.INPUT, MatrixFactory.importFromString("1 0 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(cat);

		Sample tiger = SampleFactory.labeledSample("Tiger");
		tiger.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 0 1 0 1 1 0 0 0 1 1 0 0"));
		animals.addSample(tiger);

		Sample lion = SampleFactory.labeledSample("Lion");
		lion.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 0 1 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(lion);

		Sample horse = SampleFactory.labeledSample("Horse");
		horse.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(horse);

		Sample zebra = SampleFactory.labeledSample("Zebra");
		zebra.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(zebra);

		Sample cow = SampleFactory.labeledSample("Cow");
		cow.setMatrix(Sample.INPUT, MatrixFactory.importFromString("0 0 1 0 1 1 1 0 0 0 0 0 0"));
		animals.addSample(cow);

		return animals;
	}

	public static ClassificationDataSet Linear1() {
		ClassificationDataSet or = new ClassificationDataSet("Linear1");

		ClassificationSample x0 = new ClassificationSample("0.0=0.0");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.1");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.2");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.3");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.4");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet Linear3() {
		ClassificationDataSet or = new ClassificationDataSet("Linear3");

		ClassificationSample x0 = new ClassificationSample("0.0=0.5");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.5");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.5");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.5");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet Linear2() {
		ClassificationDataSet or = new ClassificationDataSet("Linear2");

		ClassificationSample x0 = new ClassificationSample("0.0=0.1");
		x0.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.2");
		x1.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.3");
		x2.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.4");
		x3.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.6");
		x5.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.6 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet OR() {
		ClassificationDataSet or = new ClassificationDataSet("OR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		or.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=10");
		x110.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x110);

		return or;
	}

	public static ClassificationDataSet XOR() {
		ClassificationDataSet xor = new ClassificationDataSet("XOR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=01");
		x110.setMatrix(Sample.INPUT, MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x110);

		return xor;
	}

	public static ClassificationDataSet IRIS() throws IOException {
		StringBuffer s = new StringBuffer();
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

		Matrix m = MatrixFactory.importFromString(Format.CSV, s.toString());
		Matrix input = m.selectColumns(Ret.NEW, 0, 1, 2, 3);
		Matrix output = m.selectColumns(Ret.NEW, 4).discretizeToColumns(0);

		ClassificationDataSet iris = ClassificationDataSet.copyFromMatrix(input, output);
		iris.setLabel("Iris flower dataset");

		for (int i = 0; i < 50; i++) {
			iris.getSample(i).setLabel("Iris-setosa");
		}
		for (int i = 50; i < 100; i++) {
			iris.getSample(i).setLabel("Iris-versicolor");
		}
		for (int i = 100; i < 150; i++) {
			iris.getSample(i).setLabel("Iris-virginica");
		}

		return iris;
	}

}
