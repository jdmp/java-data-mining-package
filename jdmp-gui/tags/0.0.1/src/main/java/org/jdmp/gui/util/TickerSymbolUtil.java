package org.jdmp.gui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jdmp.matrix.io.util.IntelligentFileReader;

public class TickerSymbolUtil {

	private static final String TICKERSYMBOLSRESOURCE = "resources/tickersymbols.txt";

	private static final Logger logger = Logger.getLogger(TickerSymbolUtil.class.getName());

	private static Map<String, String> symbolMap = null;

	private static List<String> symbolList = null;

	public static final Map<String, String> getSymbolMap() {
		if (symbolMap == null)
			createSymbolMap();
		return symbolMap;
	}

	public static final List<String> getSymbolList() {
		if (symbolList == null)
			symbolList = new ArrayList<String>(getSymbolMap().keySet());
		return symbolList;
	}

	private static final void createSymbolMap() {
		try {
			IntelligentFileReader lr = new IntelligentFileReader(TICKERSYMBOLSRESOURCE);

			symbolMap = new HashMap<String, String>();

			String line = null;
			while ((line = lr.readLine()) != null) {
				String[] fields = line.split("\t");
				String name = fields[0];
				String symbol = fields[1];
				symbolMap.put(symbol, name);
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not load ticker symbols", e);
		}
	}

	public static final TableModel getTableModel() {
		return new AbstractTableModel() {
			private static final long serialVersionUID = 0L;

			public int getColumnCount() {
				return 2;
			}

			@Override
			public String getColumnName(int column) {
				switch (column) {
				case 0:
					return "Symbol";
				case 1:
					return "Name";
				}
				return null;
			}

			public int getRowCount() {
				return getSymbolMap().size();
			}

			public Class<?> getColumnClass(int columnIndex) {
				return String.class;
			}

			public Object getValueAt(int rowIndex, int columnIndex) {
				switch (columnIndex) {
				case 0:
					return getSymbolList().get(rowIndex);
				case 1:
					return getSymbolMap().get(getSymbolList().get(rowIndex));
				}
				return null;
			}
		};
	}
}
