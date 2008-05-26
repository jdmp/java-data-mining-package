package org.jdmp.gui.matrix.plot;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ListSelectionModel;

public class Selection {

	private PlotSettings plotSettings = null;

	public Selection(PlotSettings plotSettings) {
		this.plotSettings = plotSettings;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		ListSelectionModel lsm = plotSettings.getMatrixGUIObject().getRowSelectionModel();
		int min = lsm.getMinSelectionIndex();
		int max = lsm.getMaxSelectionIndex();

		if (min != -1 && max != -1) {
			double xf = plotSettings.getXFactor();

			int y1 = 0;
			int y2 = plotSettings.getHeight() - 1;
			int h = y2 - y1;

			int x1 = (int) (min * xf);
			int x2 = (int) (max * xf);
			int w = x2 - x1;

			g2d.setColor(plotSettings.getSelectionLineColor());
			g2d.drawLine(x1, y1, x1, y2);
			g2d.drawLine(x2, y1, x2, y2);
			g2d.setColor(plotSettings.getSelectionColor());
			if (w != 0) {
				g2d.fillRect(x1, y1, w, h);
			}
		}

	}

}
