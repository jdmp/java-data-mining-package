package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class YGrid {

	private PlotSettings plotSettings = null;

	public YGrid(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x1 = plotSettings.getWidth() - 1;
		int x2 = 0;
		g2d.setStroke(plotSettings.getYGridStroke());
		g2d.setColor(plotSettings.getYGridColor());
		double yf = plotSettings.getYFactor();
		double ygs = plotSettings.getYGridStepSize();
		for (double y = plotSettings.getMinYValue(); y < plotSettings.getMaxYValue(); y += ygs) {
			int y1 = (int) (plotSettings.getHeight() - 1 - y * yf + plotSettings.getMinYValue() * yf);
			int y2 = y1;
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
}
