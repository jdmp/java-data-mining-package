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

package org.jdmp.matrix;

import java.io.Serializable;

import org.jdmp.matrix.annotation.HasAnnotation;
import org.jdmp.matrix.calculation.CanPerformCalculations;
import org.jdmp.matrix.interfaces.BasicMatrixProperties;
import org.jdmp.matrix.interfaces.CanBeReshaped;
import org.jdmp.matrix.interfaces.Clearable;
import org.jdmp.matrix.interfaces.CoordinateFunctions;
import org.jdmp.matrix.interfaces.DistanceMeasures;
import org.jdmp.matrix.interfaces.HasGUIObject;
import org.jdmp.matrix.interfaces.HasLabel;
import org.jdmp.matrix.interfaces.MatrixEntryGettersAndSetters;
import org.jdmp.matrix.io.ExportInterface;

/**
 * <code>Matrix</code> is the main class for storing any type of data. You
 * have to choose the suitable implementation for your needs, e.g.
 * <code>DefaultFullDoubleMatrix2D</code> to store double values or
 * DefaultGenericMatrix if you want to specify the object type.
 * 
 * 
 * @author Holger Arndt
 * @version $Revision$
 * @date $Date$
 * 
 * @log $Log$
 * 
 */
public interface Matrix extends Serializable, ExportInterface, CoordinateFunctions,
		MatrixEntryGettersAndSetters, BasicMatrixProperties, CanPerformCalculations, CanBeReshaped,
		DistanceMeasures, Comparable<Matrix>, Cloneable, Clearable, HasAnnotation, HasLabel, HasGUIObject {

	/**
	 * Defines the object types that can be stored in a Matrix. Different matrix
	 * implementation might exist for each type. Use EntryType.OBJECT for other
	 * object types.
	 */
	public enum EntryType {
		GENERIC, BOOLEAN, BYTE, CHARACTER, SHORT, INTEGER, LONG, FLOAT, DOUBLE, STRING, DATE, OBJECT
	};

	public enum AnnotationTransfer {
		NONE, LINK, COPY
	};

	public static final int Y = 0;

	public static final int X = 1;

	public static final int Z = 2;

	public static final int ROW = 0;

	public static final int COLUMN = 1;

	public static final int ALL = 0x7fffffff;

	public static final int NONE = -1;

	/**
	 * Import and export formats that are supported.
	 */
	public enum Format {
		CSV, TXT, M, MAT, HTML, MTX, XLS, OBJ, SER, GraphML, TEX, WAV, BMP, TIFF, PLT, JPEG, PDF, PNG, XML, AML, ARFF, ATT, LOG, NET, XRFF
	};

	public Matrix clone();

}
