package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Traces {

	private PlotSettings plotSettings = null;

	public Traces(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		try {
			long t0 = System.currentTimeMillis();

			Graphics2D g2d = (Graphics2D) g;

			double xf = plotSettings.getXFactor();
			double yf = plotSettings.getYFactor();

			for (int t = 0; t < Math.min(10, plotSettings.getMatrixGUIObject().getColumnCount()); t++) {

				if (plotSettings.isShowTrace(t)) {

					long column = t;

					g2d.setStroke(plotSettings.getTraceStroke(t));
					g2d.setColor(plotSettings.getTraceColor(t));

					double xs = plotSettings.getXStepSize();

					long dots = 0;
					for (double xr = plotSettings.getMinXValue() + xs; xr <= plotSettings.getMaxXValue(); xr += xs) {
						dots++;
						long row1 = (long) (xr - xs);
						long row2 = (long) xr;

						double yv1 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row1, column);
						double yv2 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row2, column);

						int x1 = (int) ((xr - xs) * xf);
						int x2 = (int) (xr * xf);
						x2 = (x2 == x1) ? x2++ : x2;

						int y1 = (int) (plotSettings.getHeight() - 1 - yv1 * yf + plotSettings.getMinYValue() * yf);
						int y2 = (int) (plotSettings.getHeight() - 1 - yv2 * yf + plotSettings.getMinYValue() * yf);

						g2d.drawLine(x1, y1, x2, y2);
					}

				}

				long t1 = System.currentTimeMillis();
				if (t1 - t0 > plotSettings.getTimeLimit()) {
					return;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
