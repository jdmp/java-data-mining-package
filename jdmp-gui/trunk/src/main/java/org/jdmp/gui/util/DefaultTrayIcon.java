package org.jdmp.gui.util;

import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jdmp.gui.actions.TaskQueue;

public class DefaultTrayIcon extends TrayIcon implements ActionListener {

	private static Image image = Toolkit.getDefaultToolkit().getImage("jdmp16.png");

	public DefaultTrayIcon() {
		super(image);
		setToolTip("JDMP");

		TaskQueue.addActionListener(this);

		MouseListener mouseListener = new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					// Workspace.getInstance().showFrame();
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}
		};

		PopupMenu popup = new PopupMenu();

		MenuItem exitItem = new MenuItem("Exit");
		ActionListener exitListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// new ExitAction().call();
			}
		};
		exitItem.addActionListener(exitListener);
		popup.add(exitItem);

		setPopupMenu(popup);

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayMessage("Action Event", "An Action Event Has Been Peformed!", TrayIcon.MessageType.INFO);
			}
		};

		setImageAutoSize(true);
		addActionListener(actionListener);
		addMouseListener(mouseListener);

	}

	public void actionPerformed(ActionEvent e) {
		displayMessage("New task added", e.getActionCommand(), TrayIcon.MessageType.INFO);
	}

}
