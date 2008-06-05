package org.jdmp.core.dataset;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jdmp.core.matrix.wrappers.DataSetInputMatrixWrapper;
import org.jdmp.core.sample.Attribute;
import org.jdmp.core.sample.BasicSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.collections.DefaultMatrixList;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.io.ExportMatrix;

public class BasicDataSet extends AbstractDataSet {
	private static final long serialVersionUID = -2887879051530049677L;

	private Matrix inputMatrix = null;

	protected MatrixList matrixList = null;

	public BasicDataSet(Variable v) {
		this(v.getLabel());
		for (Matrix m : v.getMatrixList()) {
			BasicSample s = new BasicSample(m);
			addSample(s);
		}
	}

	public BasicDataSet(String label) {
		super(label);
	}

	public BasicDataSet() {
		super();
	}

	public final Set<Attribute> getAttributes() {
		SortedSet<Attribute> features = new TreeSet<Attribute>();
		for (Sample s : getSampleList()) {
			features.addAll(((BasicSample) s).getAttributes());
		}
		return features;
	}

	public final int getFeatureCount() {
		return getAttributes().size();
	}

	public BasicSample getSample(int pos) {
		return (BasicSample) super.getSample(pos);
	}

	public final void standardize(int dimension) throws MatrixException {
		getInputMatrix().standardize(Ret.ORIG, dimension, true);
	}

	public final void center(int dimension) throws MatrixException {
		getInputMatrix().center(Ret.ORIG, dimension, true);
	}

	public final Matrix getInputMatrix() {
		if (inputMatrix == null) {
			inputMatrix = new DataSetInputMatrixWrapper(this);
		}
		return inputMatrix;
	}

	public MatrixList getMatrixList() {
		if (matrixList == null) {
			matrixList = new DefaultMatrixList();
			matrixList.add(getInputMatrix());
		}
		return matrixList;
	}

	public final List<BasicSample> getBasicSampleList() {
		return Arrays.asList(getSampleList().toArray(new BasicSample[0]));
	}

	public final String toCSV() throws MatrixException, IOException {
		return ExportMatrix.toString(Format.CSV, this.getInputMatrix());
	}

	public final void exportToCSV(File file) throws MatrixException, IOException {
		ExportMatrix.toFile(Format.CSV, file, this.getInputMatrix());
	}

}
