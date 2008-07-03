/* ----------------------------------------------------------------------------
 * File:    RunningAveragePlot.java
 * Project: jdmp-gui
 * Package: org.jdmp.gui.matrix.plot
 * ID:      $Id$
 *
 * ----------------------------------------------------------------------------
 * 
 * << short description of class >>
 *
 * ----------------------------------------------------------------------------
 *
 * Author: Andreas Naegele
 * Date:   01.04.2008
 * --------------------------------------------------------------------------*/

package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class RunningAveragePlot {

	private PlotSettings plotSettings = null;

	public RunningAveragePlot(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		try {
			long t0 = System.currentTimeMillis();

			Graphics2D g2d = (Graphics2D) g;

			double xf = plotSettings.getXFactor();
			double yf = plotSettings.getYFactor();

			if (plotSettings.getMatrixGUIObject().getRowCount() < 2) {
				return;
			}

			for (int t = 0; t < Math.min(10, plotSettings.getMatrixGUIObject().getColumnCount()); t++) {

				if (plotSettings.isShowTrace(t)) {

					long column = t;

					g2d.setStroke(plotSettings.getRunningAverageStroke());
					g2d.setColor(plotSettings.getRunningAverageLineColor());

					double xs = plotSettings.getXStepSize();

					double sum = plotSettings.getMatrixGUIObject().getDoubleValueAt(
							(long) plotSettings.getMinXValue(), column);
					double average = sum;
					double oldAverage = average;
					double firstPoint = plotSettings.getMinXValue();
					double nmbOfPoints = 1;
					long dots = 0;

					for (double xr = plotSettings.getMinXValue() + xs; xr <= plotSettings
							.getMaxXValue(); xr += xs) {
						dots++;
						long row1 = (long) (xr - xs);
						long row2 = (long) xr;
						long rowRA = (long) (firstPoint);

						double yv1 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row1,
								column);
						double yv2 = plotSettings.getMatrixGUIObject().getDoubleValueAt(row2,
								column);
						double yvRA = plotSettings.getMatrixGUIObject().getDoubleValueAt(rowRA,
								column);

						sum += yv2;
						nmbOfPoints++;
						if (nmbOfPoints > (plotSettings.getRunningAverageLength()) / xs) {
							sum = sum - yvRA;
							firstPoint += xs;
							nmbOfPoints--;
						}
						average = sum / nmbOfPoints;

						int x1 = (int) ((xr - xs) * xf);
						int x2 = (int) (xr * xf);
						x2 = (x2 == x1) ? x2++ : x2;

						int y1 = (int) (plotSettings.getHeight() - 1 - oldAverage * yf + plotSettings
								.getMinYValue()
								* yf);
						int y2 = (int) (plotSettings.getHeight() - 1 - average * yf + plotSettings
								.getMinYValue()
								* yf);

						g2d.drawLine(x1, y1, x2, y2);
						oldAverage = average;
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
