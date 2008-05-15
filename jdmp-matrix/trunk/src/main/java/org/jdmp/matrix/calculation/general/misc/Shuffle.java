package org.jdmp.matrix.calculation.general.misc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;


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
