package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class YAxis {

	private PlotSettings plotSettings = null;

	public YAxis(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x1 = 0;
		int y1 = plotSettings.getHeight() - 1;
		int x2 = 0;
		int y2 = 0;
		g2d.setStroke(plotSettings.getAxisStroke());
		g2d.setColor(plotSettings.getAxisColor());
		g2d.drawLine(x1, y1, x2, y2);
	}

}
