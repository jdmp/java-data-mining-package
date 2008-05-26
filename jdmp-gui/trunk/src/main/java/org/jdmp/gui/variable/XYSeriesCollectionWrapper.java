package org.jdmp.gui.variable;

import org.jdmp.core.variable.Variable;
import org.jdmp.matrix.Coordinates;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.data.Range;
import org.jfree.data.xy.XYSeriesCollection;

public class XYSeriesCollectionWrapper extends XYSeriesCollection {
	private static final long serialVersionUID = 963347559253591538L;

	private static final int MAXTRACES = 10;

	private Variable variable = null;

	public XYSeriesCollectionWrapper(Variable v) {
		this.variable = v;
		if (Coordinates.product(v.getSize()) == 0) {
			System.out.println("size not definied");
		}

		int size = (int) Math.min(Coordinates.product(v.getSize()), MAXTRACES);
		for (int i = 0; i < size; i++) {
			addSeries(new XYSeriesWrapper(variable, i));
		}
	}

	public void setRange(Range range) {
		int size = Math.min(getSeriesCount(), MAXTRACES);
		for (int i = 0; i < size; i++) {
			((XYSeriesWrapper) getSeries(i)).setRange(range);
		}
	}

	public ValueMarker getMeanMarker(int series) {
		return ((XYSeriesWrapper) getSeries(series)).getMeanMarker();
	}

	public IntervalMarker getStandardDeviationMarker(int series) {
		return ((XYSeriesWrapper) getSeries(series)).getStandardDeviationMarker();
	}

	public IntervalMarker getMinMaxMarker(int series) {
		return ((XYSeriesWrapper) getSeries(series)).getMinMaxMarker();
	}
}
