package org.jdmp.matrix.io;

import java.io.File;

import org.jdmp.matrix.implementations.graph.DefaultGraphMatrix;
import org.jdmp.matrix.stubs.AbstractGraphMatrix;

public abstract class ImportGML {

	public static final AbstractGraphMatrix importFromGraphML(File file) {
		DefaultGraphMatrix m = new DefaultGraphMatrix();
		//m.importFromGML(file);
		return m;
	}

}
