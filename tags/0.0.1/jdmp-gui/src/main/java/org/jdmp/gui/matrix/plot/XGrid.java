package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class XGrid {

	private PlotSettings plotSettings = null;

	public XGrid(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int y1 = plotSettings.getHeight() - 1;
		int y2 = 0;
		g2d.setStroke(plotSettings.getXGridStroke());
		g2d.setColor(plotSettings.getXGridColor());
		double xf = plotSettings.getXFactor();
		double xgs = plotSettings.getXGridStepSize();
		for (double x = plotSettings.getMinXValue(); x < plotSettings.getMaxXValue(); x += xgs) {
			int x1 = (int) (x * xf);
			int x2 = x1;
			g2d.drawLine(x1, y1, x2, y2);
		}
	}

}
