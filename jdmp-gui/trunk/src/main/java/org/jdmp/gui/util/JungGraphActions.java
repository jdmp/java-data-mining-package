package org.jdmp.gui.util;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import org.jdmp.gui.actions.PanelActions;
import org.jdmp.gui.util.JungGraphPanel.GraphLayout;
import org.jdmp.matrix.io.ExportJPEG;
import org.jdmp.matrix.io.ExportPDF;

public class JungGraphActions extends JPopupMenu {

	private static final long serialVersionUID = -2307893165969916295L;

	JungGraphPanel jungGraphPanel = null;

	public JungGraphActions(JungGraphPanel m) {
		this.jungGraphPanel = m;

		JMenu panelMenu = new JMenu("This Panel");
		for (JComponent c : new PanelActions(m, null)) {
			panelMenu.add(c);
		}
		this.add(panelMenu);

		JMenu layoutMenu = new JMenu("Layout");
		layoutMenu.add(frLayoutAction);
		layoutMenu.add(kkLayoutAction);
		layoutMenu.add(iSomLayoutAction);
		layoutMenu.add(springLayoutAction);
		layoutMenu.add(circleLayoutAction);
		this.add(layoutMenu);
		this.add(toggleEdgesAction);
		this.add(toggleEdgeLabelsAction);
		this.add(toggleVertexLabelsAction);
		this.add(new JSeparator());
		this.add(exportToJpgAction);
		this.add(exportToPdfAction);
		this.add(new JSeparator());
		this.add(refreshAction);
	}

	public final Action frLayoutAction = new AbstractAction("FR Layout") {
		private static final long serialVersionUID = 3149916178777567323L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.FRLayout);
		}
	};

	public final Action iSomLayoutAction = new AbstractAction("ISOM Layout") {
		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.ISOMLayout);
		}
	};

	public final Action kkLayoutAction = new AbstractAction("KK Layout") {
		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.KKLayout);
		}
	};

	public final Action springLayoutAction = new AbstractAction("Spring Layout") {
		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.SpringLayout);
		}
	};

	public final Action circleLayoutAction = new AbstractAction("Circle Layout") {
		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.switchLayout(GraphLayout.CircleLayout);
		}
	};

	public final Action refreshAction = new AbstractAction("Refresh") {
		private static final long serialVersionUID = -8057389215808050942L;

		public void actionPerformed(ActionEvent e) {
			GraphicsExecutor.scheduleUpdate(jungGraphPanel);
		}
	};

	public final Action exportToPdfAction = new AbstractAction("Export to PDF...", UIManager.getIcon("JDMP.icon.pdf")) {
		private static final long serialVersionUID = -7413294854080175036L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.exportToPDF(ExportPDF.selectFile());
		}
	};

	public final Action exportToJpgAction = new AbstractAction("Export to JPG...", UIManager.getIcon("JDMP.icon.image")) {
		private static final long serialVersionUID = 2903870037000412488L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.exportToJPEG(ExportJPEG.selectFile());
		}
	};

	public final Action toggleEdgesAction = new AbstractAction("Toggle Edges") {
		private static final long serialVersionUID = -7632767332831157590L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.setShowEdges(!jungGraphPanel.isShowEdges());
		}
	};

	public final Action toggleEdgeLabelsAction = new AbstractAction("Toggle Edge Labels") {
		private static final long serialVersionUID = 5043606502712307760L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.setShowEdgeLabels(!jungGraphPanel.isShowEdgeLabels());
		}
	};

	public final Action toggleVertexLabelsAction = new AbstractAction("Toggle Vertex Labels") {
		private static final long serialVersionUID = -8736147166116311565L;

		public void actionPerformed(ActionEvent e) {
			jungGraphPanel.setShowVertexLabels(!jungGraphPanel.isShowVertexLabels());
		}
	};

}
