/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import org.ujmp.core.Matrix;
import org.ujmp.core.enums.DBType;
import org.ujmp.core.filematrix.FileFormat;

public interface DataSetFactory {

	public ListDataSet emptyDataSet();

	public ListDataSet importFromFile(FileFormat format, File file, Object... parameters)
			throws IOException;

	public ListDataSet linkToFile(FileFormat format, File file, Object... parameters)
			throws IOException;

	public ListDataSet linkToInput(Matrix input);

	public ListDataSet linkToInputAndLabels(Matrix input, Matrix labels);

	public ListDataSet linkToInputAndTarget(Matrix input, Matrix target);

	public ListDataSet linkToInputTargetAndLabel(Matrix input, Matrix target, Matrix label);

	public ListDataSet HenonMap(int sampleCount, int inputLength, int predictionLength);

	public ListDataSet LogisticMap(int sampleCount, int inputLength, int predictionLength);

	public AbstractListDataSet ANIMALS();

	public AbstractListDataSet labeledDataSet(String label);

	public ListDataSet CountActive(int number);

	public ListDataSet Linear1();

	public ListDataSet Linear3();

	public ListDataSet Linear2();

	public ListDataSet OR();

	public ListDataSet XOR();

	public ListDataSet ONE();

	public ListDataSet IRIS();

	public ListDataSet MNISTTrain() throws IOException;

	public ListDataSet MNISTTest() throws IOException;

	public ListDataSet importFromURL(FileFormat fileFormat, URL url, Object... parameters)
			throws Exception;

	public ListDataSet importFromClipboard() throws IOException;

	public ListDataSet importFromJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password);

	public ListDataSet importFromJDBC(String url, String sqlStatement, String username,
			String password);

	public ListDataSet linkToJDBC(DBType type, String host, int port, String database,
			String sqlStatement, String username, String password);

	public ListDataSet linkToJDBC(String url, String sqlStatement, String username, String password);

}
