package org.jdmp.gui.matrix.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.util.Octave;

public class OctavePanel extends JPanel {
	private static final long serialVersionUID = -894693635404746973L;

	private MatrixGUIObject matrix = null;

	public OctavePanel(MatrixGUIObject m) {
		this.matrix = m;

		setLayout(new FlowLayout());

		add(new JButton(new MatrixPlotAction()));
		add(new JButton(new XYPlotAction()));
		add(new JButton(new ScatterPlotAction()));
	}

	class MatrixPlotAction extends AbstractAction {
		private static final long serialVersionUID = -4928348084073744818L;

		public MatrixPlotAction() {
			super("Matrix Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(matrix.getMatrix());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class XYPlotAction extends AbstractAction {

		public XYPlotAction() {
			super("XY Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	class ScatterPlotAction extends AbstractAction {
		private static final long serialVersionUID = 4837137928213709856L;

		public ScatterPlotAction() {
			super("Scatter Plot");
		}

		public void actionPerformed(ActionEvent e) {
			try {
				Octave.getInstance().plot(matrix.getMatrix().selectColumns(Ret.NEW, 0),
						matrix.getMatrix().selectColumns(Ret.NEW, 1), "x");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
