package org.jdmp.gui.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.Action;
import javax.swing.JComponent;

import org.ujmp.core.interfaces.GUIObject;

public class PasteAction extends ObjectAction {
	private static final long serialVersionUID = -4029079211237756552L;

	public PasteAction(JComponent c, GUIObject o) {
		super(c, o);
		putValue(Action.NAME, "Paste");
		putValue(Action.SHORT_DESCRIPTION, "Paste from clipboard");
	}

	public Object call() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(getObject());
		String s;
		try {
			s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
		} catch (Exception ex) {
			s = ex.toString();
		}
		System.out.println(s);
		return null;
	}

}
