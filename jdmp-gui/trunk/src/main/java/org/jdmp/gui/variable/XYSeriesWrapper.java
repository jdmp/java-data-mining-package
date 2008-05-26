package org.jdmp.gui.variable;

import java.awt.Color;
import java.util.Map;
import java.util.WeakHashMap;

import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableEvent;
import org.jdmp.core.variable.VariableListener;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.StringUtil;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class XYSeriesWrapper extends XYSeries implements VariableListener {
	private static final long serialVersionUID = 2493663877511719452L;

	private static final int MAXITEMS = 3000;

	private Map<Integer, XYDataItem> values = new WeakHashMap<Integer, XYDataItem>();

	private Variable variable = null;

	private ValueMarker meanMarker = null;

	private IntervalMarker standardDeviationMarker = null;

	private IntervalMarker minMaxMarker = null;

	private int number = 0;

	private int stepsize = 1;

	private int start = 0;

	public XYSeriesWrapper(Variable v, int number) {
		super("TimeSeries " + number);
		this.number = number;
		this.variable = v;
		meanMarker = new MeanMarkerForVariable(variable, number);
		standardDeviationMarker = new StandardDeviationMarkerForVariable(variable, number);
		minMaxMarker = new MinMaxMarkerForVariable(variable, number);
		variable.addVariableListener(this);
		stepsize = (int) Math.ceil((double) variable.getMatrixCount() / (double) MAXITEMS);
	}

	public void setRange(Range range) {
		double length = range.getLength();
		start = (int) Math.floor(range.getLowerBound());
		stepsize = (int) Math.ceil(length / MAXITEMS);
	}

	@Override
	public XYDataItem getDataItem(int index) {
		int id = start + index * stepsize;
		if (id >= variable.getMatrixCount()) {
			return new XYDataItem(id, 0.0);
		}

		Matrix matrix = variable.getMatrix(id);
		double value = 0.0;
		try {
			value = matrix.getDouble(number % matrix.getRowCount(), number / matrix.getRowCount());
		} catch (MatrixException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XYDataItem xyDataItem = values.get(id);
		if (xyDataItem == null) {
			xyDataItem = new XYDataItem(id, value);
			values.put(id, xyDataItem);
		} else {
			xyDataItem.setY(value);
		}

		return xyDataItem;
	}

	@Override
	public int getItemCount() {
		if (variable.getMatrixCount() > MAXITEMS) {
			return MAXITEMS;
		}
		return variable.getMatrixCount();
	}

	@Override
	public int indexOf(Number x) {
		return (Integer) x;
	}

	public void valueChanged(VariableEvent e) {
		if (System.currentTimeMillis() % 10 == 0)
			fireSeriesChanged();
	}

	public ValueMarker getMeanMarker() {
		return meanMarker;
	}

	public IntervalMarker getStandardDeviationMarker() {
		return standardDeviationMarker;
	}

	public IntervalMarker getMinMaxMarker() {
		return minMaxMarker;
	}

}

class StandardDeviationMarkerForVariable extends IntervalMarker {
	private static final long serialVersionUID = 4093403885413441600L;

	private int number = 0;

	private Variable variable = null;

	public StandardDeviationMarkerForVariable(Variable v, int number) {
		super(0, 0);
		this.variable = v;
		this.number = number;
		setPaint(new Color(255, 100, 100, 60));
	}

	@Override
	public double getEndValue() {
		try {
			return variable.getMeanMatrix().getDouble(0, number)
					+ variable.getStandardDeviationMatrix().getDouble(0, number);
		} catch (MatrixException e) {
			return 0.0;
		}
	}

	@Override
	public double getStartValue() {
		try {
			return variable.getMeanMatrix().getDouble(0, number)
					- variable.getStandardDeviationMatrix().getDouble(0, number);
		} catch (MatrixException e) {
			return 0.0;
		}
	}

}

class MinMaxMarkerForVariable extends IntervalMarker {

	private int number = 0;

	private Variable variable = null;

	public MinMaxMarkerForVariable(Variable v, int number) {
		super(0, 0);
		this.variable = v;
		this.number = number;
		setPaint(new Color(255, 200, 200, 50));
	}

	@Override
	public double getEndValue() {
		try {
			return variable.getMaxMatrix().getDouble(0, number);
		} catch (MatrixException e) {
			return 0.0;
		}
	}

	@Override
	public double getStartValue() {
		try {
			return variable.getMinMatrix().getDouble(0, number);
		} catch (MatrixException e) {
			return 0.0;
		}
	}

}

class MeanMarkerForVariable extends ValueMarker {
	private static final long serialVersionUID = 7345423855597100653L;

	private int number = 0;

	private Variable variable = null;

	public MeanMarkerForVariable(Variable v, int number) {
		super(0);
		this.variable = v;
		this.number = number;
		setPaint(new Color(200, 0, 0, 128));
		setLabelAnchor(RectangleAnchor.TOP);
		setLabelTextAnchor(TextAnchor.TOP_RIGHT);
	}

	@Override
	public String getLabel() {
		try {
			return StringUtil.format(variable.getMeanMatrix().getDouble(0, number));
		} catch (MatrixException e) {
			return "";
		}
	}

	@Override
	public double getValue() {
		try {
			return variable.getMeanMatrix().getDouble(0, number);
		} catch (MatrixException e) {
			return 0.0;
		}
	}

	@Override
	public void setValue(double arg0) {
	}

}