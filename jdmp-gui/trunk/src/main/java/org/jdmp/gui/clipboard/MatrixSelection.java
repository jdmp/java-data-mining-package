package org.jdmp.gui.clipboard;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.Matrix.Format;

public class MatrixSelection implements Transferable, ClipboardOwner, Serializable {
	private static final long serialVersionUID = -8462961141636462510L;

	public static final int STRING = 0;

	public static final int IMAGE = 1;

	public static final int MATRIX = 2;

	// private static final DataFlavor[] flavors = { DataFlavor.stringFlavor,
	// new DataFlavor("image/jpeg", "JPG Image"),
	// MatrixFlavor.matrixFlavor };

	private static final DataFlavor[] flavors = { DataFlavor.stringFlavor };

	private String stringData = null;

	public MatrixSelection(Matrix matrix) throws MatrixException {
		stringData = matrix.toString(Format.CSV);
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (flavor.equals(flavors[STRING])) {
			return stringData;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	public DataFlavor[] getTransferDataFlavors() {
		return (DataFlavor[]) flavors.clone();
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		for (int i = 0; i < flavors.length; i++) {
			if (flavor.equals(flavors[i])) {
				return true;
			}
		}
		return false;
	}

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
	}

}
