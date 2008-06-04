package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class ZeroAxis {

    private PlotSettings plotSettings = null;

    public ZeroAxis(PlotSettings plotSettings) {
        this.plotSettings = plotSettings;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double yf = plotSettings.getYFactor();
        int x1 = 0;
        int x2 = plotSettings.getWidth() - 1;
        int y1 = (int) (plotSettings.getHeight() - 1 + plotSettings.getMinYValue() * yf);       
        int y2 = y1;
        
        g2d.setStroke(plotSettings.getZeroAxisStroke());
        g2d.setColor(plotSettings.getZeroAxisColor());
        g2d.drawLine(x1, y1, x2, y2);
    }

}
