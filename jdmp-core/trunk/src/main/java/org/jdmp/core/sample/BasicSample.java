package org.jdmp.core.sample;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jdmp.core.variable.DefaultVariable;
import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.util.StringUtil;

public class BasicSample extends AbstractSample {
	private static final long serialVersionUID = -3649758882404748630L;

	public static final int INPUT = 0;

	public BasicSample(Matrix m) {
		this(m.getLabel());
		setInputMatrix(m);
	}

	public BasicSample(String label) {
		super(label);
	}

	public BasicSample() {
		super();
	}

	public BasicSample clone() {
		BasicSample s = new BasicSample();
		s.setInputMatrix(getInputMatrix().clone());
		return s;
	}

	public Variable getInputVariable() {
		Variable v = getVariable(INPUT);
		if (v == null) {
			v = new DefaultVariable("Input", 1);
			setVariable(INPUT, v);
		}
		return v;
	}

	public Matrix getInputMatrix() {
		return getInputVariable().getMatrix();
	}

	public int getAttributeCount() {
		Matrix im = getInputMatrix();
		if (im != null) {
			return (int) im.getColumnCount();
		} else {
			return 0;
		}
	}

	public Set<Attribute> getAttributes() {
		SortedSet<Attribute> attributes = new TreeSet<Attribute>();
		Matrix im = getInputMatrix();
		if (im != null) {
			for (int i = 0; i < im.getColumnCount(); i++) {
				Object o = im.getColumnObject(i);
				if (o == null) {
					attributes.add(new Attribute("Attribute " + i, false, 0));
				} else if (o instanceof Attribute) {
					attributes.add((Attribute) o);
				} else {
					attributes.add(new Attribute(StringUtil.format(o), false, 0));
				}
			}
		}
		return attributes;
	}

	public void divideInput(double d) throws MatrixException {
		getInputVariable().divideBy(d);
	}

	public void setInputMatrix(Matrix input) {
		getInputVariable().addMatrix(input);
	}

	public Matrix getMatrix() {
		return getInputMatrix();
	}

}
