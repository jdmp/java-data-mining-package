package org.jdmp.gui.matrix.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.ujmp.core.util.GnuPlot;

public class GnuPlotPanel extends JPanel {
	private static final long serialVersionUID = -241038814283185885L;

	private MatrixGUIObject matrix = null;

	public GnuPlotPanel(MatrixGUIObject m) {
		this.matrix = m;
		setLayout(new FlowLayout());

		add(new JButton(new XYPlotAction()));
		add(new JButton(new ScatterPlotAction()));
	}

	class ScatterPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4837137928213709856L;

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GnuPlot.getInstance().scatterPlot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class XYPlotAction extends AbstractAction {
		private static final long serialVersionUID = -4928348084073744818L;

		public XYPlotAction() {
			super("XY Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				GnuPlot.getInstance().plot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
