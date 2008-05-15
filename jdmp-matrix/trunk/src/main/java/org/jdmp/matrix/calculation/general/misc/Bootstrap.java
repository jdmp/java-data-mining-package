package org.jdmp.matrix.calculation.general.misc;

import java.util.ArrayList;
import java.util.List;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.calculation.ObjectCalculation;
import org.jdmp.matrix.util.MathUtil;


public class Bootstrap extends ObjectCalculation {
  private static final long serialVersionUID = -5084329826465538416L;

  private int count = 0;

  private Matrix selection = null;



  public Bootstrap(Matrix m) {
    this(m, (int) m.getRowCount());
  }



  public Bootstrap(Matrix m, int count) {
    super(m);
    this.count = count;
  }



  @Override
  public Object getObject(long... coordinates) throws MatrixException {
    if (selection == null) {
      List<Integer> rows = new ArrayList<Integer>();
      for (int i = 0; i < count; i++) {
        int s = MathUtil.nextInteger(0, (int) getSource().getRowCount() - 1);
        rows.add(s);
      }
      selection = getSource().selectRows(Ret.LINK, rows);
    }
    return selection.getObject(coordinates);
  }

}
