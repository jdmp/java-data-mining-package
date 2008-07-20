package org.jdmp.gui.util;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jdmp.gui.actions.TaskQueue;
import org.ujmp.core.interfaces.GUIObject;

public class StatusBar extends JPanel {
	private static final long serialVersionUID = -92341245296146976L;

	private JLabel taskStatus = new JLabel();

	private JLabel objectStatus = new JLabel();

	private GUIObject object = null;

	private JProgressBar jProgressBar = new JProgressBar();

	private JProgressBar memoryUsage = new JProgressBar();

	// private Timer timer = null;

	public StatusBar(GUIObject o) {
		this.object = o;
		this.setPreferredSize(new Dimension(1000, 30));
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new GridBagLayout());

		taskStatus.setPreferredSize(new Dimension(200, 30));
		taskStatus.setMinimumSize(new Dimension(200, 30));

		add(objectStatus, new GridBagConstraints(0, 0, 1, 1, 0.2, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		add(taskStatus, new GridBagConstraints(2, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		add(memoryUsage, new GridBagConstraints(3, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

		jProgressBar.setStringPainted(false);
		jProgressBar.setMinimum(0);
		jProgressBar.setMaximum(1000);
		jProgressBar.setValue(1000);
		jProgressBar.setVisible(false);

		objectStatus.setBorder(BorderFactory.createEtchedBorder());
		taskStatus.setBorder(BorderFactory.createEtchedBorder());
		jProgressBar.setBorder(BorderFactory.createEtchedBorder());
		memoryUsage.setBorder(BorderFactory.createEtchedBorder());

		memoryUsage.setMinimumSize(new Dimension(50, 30));

		add(jProgressBar, new GridBagConstraints(1, 0, 1, 1, 0.8, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));

	}

	public void start() {
		stop();
		// timer = new Timer("Toolbar Timer for " + object.getLabel());
		// timer.schedule(new UpdateTask(this), 1000, // initial delay
		// 1000); // subsequent rate
	}

	public void stop() {
		// if (timer != null) {
		// timer.cancel();
		// timer = null;
		// }
	}

	public void setTaskString(String s) {
		taskStatus.setText(s);
	}

	public void setObjectString(String s) {
		objectStatus.setText(s);
	}

	public void setProgress(Double progress) {
		if (progress == null) {
			jProgressBar.setValue(0);
			jProgressBar.setIndeterminate(true);
			jProgressBar.setVisible(true);
		} else if (progress == 1.0) {
			jProgressBar.setValue(1000);
			jProgressBar.setVisible(false);
		} else {
			int value = (int) (progress * jProgressBar.getMaximum());
			jProgressBar.setIndeterminate(false);
			jProgressBar.setValue(value);
			jProgressBar.setVisible(true);
		}
	}

	public void updateMemoryUsage() {
		int max = (int) Runtime.getRuntime().maxMemory() / 1048576;
		int total = (int) Runtime.getRuntime().totalMemory() / 1048576;
		int free = (int) Runtime.getRuntime().freeMemory() / 1048576;
		int used = total - free;
		memoryUsage.setMaximum(0);
		memoryUsage.setMaximum(max);
		memoryUsage.setValue(used);
		memoryUsage.setToolTipText("" + used + "MB of " + max + "MB used");
	}

	public GUIObject getObject() {
		return object;
	}

	class UpdateTask extends TimerTask {

		private StatusBar statusBar = null;

		public UpdateTask(StatusBar statusBar) {
			this.statusBar = statusBar;
		}

		public void run() {
			statusBar.setTaskString(TaskQueue.getStatus());
			// statusBar.setToolTipText(getObject().getToolTipText());
			statusBar.setObjectString("" + getObject());
			statusBar.setProgress(TaskQueue.getProgress());
			statusBar.updateMemoryUsage();
		}

	}

}
