package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class PlotBackground {

	private PlotSettings plotSettings = null;

	public PlotBackground(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x1 = 0;
		int y1 = 0;
		int width = plotSettings.getWidth() - 1;
		int height = plotSettings.getHeight() - 1;
		g2d.setColor(plotSettings.getPlotBackGroundColor());
		g2d.fillRect(x1, y1, width, height);
	}

}
