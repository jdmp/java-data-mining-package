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

package org.jdmp.matrix.implementations.io;

import java.io.File;
import java.io.IOException;

import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseStringMatrix2D;
import org.jdmp.matrix.util.MathUtil;
import org.jdmp.matrix.util.SeekableLineInputStream;


public class CSVMatrix extends AbstractDenseStringMatrix2D {
  private static final long serialVersionUID = 6025235663309962730L;

  private String separator = "\t";

  private int columnCount = 0;

  private SeekableLineInputStream sli = null;



  public CSVMatrix(String file) throws IOException {
    this(new File(file));
  }



  public CSVMatrix(File file) throws IOException {
    sli = new SeekableLineInputStream(file);
    for (int i = 0; i < 100; i++) {
      String line = sli.readLine(MathUtil.nextInteger(0, sli.getLineCount()));
      int c = line.split(separator).length;
      if (c > columnCount) {
        columnCount = c;
      }
    }
  }



  public long[] getSize() {
    return new long[] { sli.getLineCount(), columnCount };
  }



  public String getString(long... coordinates) throws MatrixException {
    try {
      String line = sli.readLine((int) coordinates[ROW]);
      String fields[] = line.split(separator);
      if (fields.length > columnCount) {
        columnCount = fields.length;
      }
      if (coordinates[COLUMN] < fields.length) {
        return fields[(int) coordinates[COLUMN]];
      }
    } catch (Exception e) {
      throw new MatrixException(e);
    }
    return null;
  }
}
