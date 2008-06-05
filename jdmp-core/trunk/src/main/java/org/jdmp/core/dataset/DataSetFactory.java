package org.jdmp.core.dataset;

import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.WeightedSample;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.exceptions.MatrixException;

public abstract class DataSetFactory {

	public static BasicDataSet ANIMALS() throws MatrixException {
		BasicDataSet animals = new BasicDataSet("Animals");

		WeightedSample pigeon = new WeightedSample("Pigeon");
		pigeon.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 1 0"));
		animals.addSample(pigeon);

		WeightedSample chicken = new WeightedSample("Chicken");
		chicken.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 0 0"));
		animals.addSample(chicken);

		WeightedSample duck = new WeightedSample("Duck");
		duck.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 0 1"));
		animals.addSample(duck);

		WeightedSample goose = new WeightedSample("Goose");
		goose.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 0 0 1 1"));
		animals.addSample(goose);

		WeightedSample owl = new WeightedSample("Owl");
		owl.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(owl);

		WeightedSample falcon = new WeightedSample("Falcon");
		falcon.setInputMatrix(MatrixFactory.importFromString("1 0 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(falcon);

		WeightedSample eagle = new WeightedSample("Eagle");
		eagle.setInputMatrix(MatrixFactory.importFromString("0 1 0 1 0 0 0 0 1 1 0 1 0"));
		animals.addSample(eagle);

		WeightedSample fox = new WeightedSample("Fox");
		fox.setInputMatrix(MatrixFactory.importFromString("0 1 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(fox);

		WeightedSample dog = new WeightedSample("Dog");
		dog.setInputMatrix(MatrixFactory.importFromString("0 1 0 0 1 1 0 0 0 0 1 0 0"));
		animals.addSample(dog);

		WeightedSample wolf = new WeightedSample("Wolf");
		wolf.setInputMatrix(MatrixFactory.importFromString("0 1 0 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(wolf);

		WeightedSample cat = new WeightedSample("Cat");
		cat.setInputMatrix(MatrixFactory.importFromString("1 0 0 0 1 1 0 0 0 1 0 0 0"));
		animals.addSample(cat);

		WeightedSample tiger = new WeightedSample("Tiger");
		tiger.setInputMatrix(MatrixFactory.importFromString("0 0 1 0 1 1 0 0 0 1 1 0 0"));
		animals.addSample(tiger);

		WeightedSample lion = new WeightedSample("Lion");
		lion.setInputMatrix(MatrixFactory.importFromString("0 0 1 0 1 1 0 1 0 1 1 0 0"));
		animals.addSample(lion);

		WeightedSample horse = new WeightedSample("Horse");
		horse.setInputMatrix(MatrixFactory.importFromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(horse);

		WeightedSample zebra = new WeightedSample("Zebra");
		zebra.setInputMatrix(MatrixFactory.importFromString("0 0 1 0 1 1 1 1 0 0 1 0 0"));
		animals.addSample(zebra);

		WeightedSample cow = new WeightedSample("Cow");
		cow.setInputMatrix(MatrixFactory.importFromString("0 0 1 0 1 1 1 0 0 0 0 0 0"));
		animals.addSample(cow);

		return animals;
	}

	public static ClassificationDataSet Linear1() {
		ClassificationDataSet or = new ClassificationDataSet("Linear1");

		ClassificationSample x0 = new ClassificationSample("0.0=0.0");
		x0.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.1");
		x1.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.2");
		x2.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.3");
		x3.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.4");
		x4.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet Linear3() {
		ClassificationDataSet or = new ClassificationDataSet("Linear3");

		ClassificationSample x0 = new ClassificationSample("0.0=0.5");
		x0.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.5");
		x1.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.5");
		x2.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.5");
		x3.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.5");
		x5.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet Linear2() {
		ClassificationDataSet or = new ClassificationDataSet("Linear2");

		ClassificationSample x0 = new ClassificationSample("0.0=0.1");
		x0.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.0 }).transpose());
		x0.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		or.addSample(x0);

		ClassificationSample x1 = new ClassificationSample("0.1=0.2");
		x1.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.1 }).transpose());
		x1.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		or.addSample(x1);

		ClassificationSample x2 = new ClassificationSample("0.2=0.3");
		x2.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.2 }).transpose());
		x2.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		or.addSample(x2);

		ClassificationSample x3 = new ClassificationSample("0.3=0.4");
		x3.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.3 }).transpose());
		x3.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		or.addSample(x3);

		ClassificationSample x4 = new ClassificationSample("0.4=0.5");
		x4.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.4 }).transpose());
		x4.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		or.addSample(x4);

		ClassificationSample x5 = new ClassificationSample("0.5=0.6");
		x5.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0.5 }).transpose());
		x5.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0.6 }).transpose());
		or.addSample(x5);

		return or;
	}

	public static ClassificationDataSet OR() {
		ClassificationDataSet or = new ClassificationDataSet("OR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		or.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setInputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=10");
		x110.setInputMatrix(MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		or.addSample(x110);

		return or;
	}

	public static ClassificationDataSet XOR() {
		ClassificationDataSet xor = new ClassificationDataSet("XOR-Problem");

		ClassificationSample x000 = new ClassificationSample("00=01");
		x000.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0, 0 }).transpose());
		x000.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x000);

		ClassificationSample x011 = new ClassificationSample("01=10");
		x011.setInputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		x011.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x011);

		ClassificationSample x101 = new ClassificationSample("10=10");
		x101.setInputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		x101.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 1, 0 }).transpose());
		xor.addSample(x101);

		ClassificationSample x110 = new ClassificationSample("11=01");
		x110.setInputMatrix(MatrixFactory.linkToArray(new double[] { 1, 1 }).transpose());
		x110.setDesiredOutputMatrix(MatrixFactory.linkToArray(new double[] { 0, 1 }).transpose());
		xor.addSample(x110);

		return xor;
	}

}
