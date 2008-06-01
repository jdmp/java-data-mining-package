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

package org.jdmp.matrix.calculation.general.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.exceptions.MatrixException;


public class Shuffle extends ObjectCalculation {
  private static final long serialVersionUID = -6935375114060680121L;

  private Matrix selection = null;



  public Shuffle(Matrix m) {
    super(m);
  }



  @Override
  public Object getObject(long... coordinates) throws MatrixException {
    if (selection == null) {
      List<Integer> rows = new ArrayList<Integer>();
      for (int i = 0; i < getSource().getRowCount(); i++) {
        rows.add(i);
      }
      Collections.shuffle(rows);
      selection = getSource().selectRows(Ret.LINK, rows);
    }
    return selection.getObject(coordinates);
  }

}
