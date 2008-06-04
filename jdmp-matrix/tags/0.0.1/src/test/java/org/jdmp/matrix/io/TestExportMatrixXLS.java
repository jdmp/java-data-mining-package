/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.jdmp.matrix.io;

import java.io.File;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.Format;
import org.jdmp.matrix.exceptions.MatrixException;

public class TestExportMatrixXLS extends AbstractExportMatrixTest {

	public Format getFormat() {
		return Format.XLS;
	}

	public void testExportToFile() throws Exception {

		try {
			Class.forName("org.jdmp.jexcelapi.DenseExcelMatrix2D");
		} catch (ClassNotFoundException e) {
			return;
		}

		super.testExportToFile();

	}

	public void testExportToStream() throws Exception {
		try {
			super.testExportToStream();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToWriter() throws Exception {
		try {
			super.testExportToWriter();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToClipboard() throws Exception {
		try {
			super.testExportToClipboard();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	public void testExportToString() throws Exception {
		try {
			super.testExportToString();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

}
