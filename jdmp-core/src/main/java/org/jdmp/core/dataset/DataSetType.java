package org.jdmp.core.dataset;

import java.io.Serializable;

public class DataSetType implements Serializable {
	private static final long serialVersionUID = -7047861470857073969L;

	public static final DataSetType NONE = new DataSetType(0, "None");

	public static final DataSetType TRAININGSET = new DataSetType(1, "TrainingSet");

	public static final DataSetType TESTSET = new DataSetType(2, "TestSet");

	public static final DataSetType VALIDATIONSET = new DataSetType(3, "ValidationSet");

	private int type = 0;

	private String name = "";

	public DataSetType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		return (name);
	}

}
