package org.jdmp.gui.variable.actions;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdmp.gui.util.TickerSymbolUtil;
import org.jdmp.gui.variable.VariableGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.io.util.IntelligentFileReader;

public class LoadWKNAction extends VariableAction {
	private static final long serialVersionUID = -5617733019886606869L;

	private String wkn = null;

	public LoadWKNAction(JComponent c, VariableGUIObject v) {
		super(c, v);
		putValue(Action.NAME, "Load WKN...");
		putValue(Action.SHORT_DESCRIPTION, "Load WKN from yahoo finance");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.ALT_DOWN_MASK));
	}

	public LoadWKNAction(JComponent c, VariableGUIObject v, String wkn) {
		this(c, v);
		this.wkn = wkn;
	}

	public Object call() {

		IntelligentFileReader lr = null;
		PrintWriter out = null;
		HttpURLConnection connection = null;

		if (wkn == null) {

			JTable jTable = new JTable(TickerSymbolUtil.getTableModel());
			jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jTable.setRowSorter(new TableRowSorter<TableModel>(jTable.getModel()));

			JDialog dialog = new JDialog((JFrame) null, "Select stock symbol", true);

			Container contentPane = dialog.getContentPane();

			contentPane.setLayout(new BorderLayout());
			contentPane.add(new JScrollPane(jTable), BorderLayout.CENTER);

			dialog.setSize(new Dimension(600, 400));
			dialog.setResizable(false);
			if (JDialog.isDefaultLookAndFeelDecorated()) {
				boolean supportsWindowDecorations = UIManager.getLookAndFeel()
						.getSupportsWindowDecorations();
				if (supportsWindowDecorations) {
					dialog.setUndecorated(true);
					// getRootPane().setWindowDecorationStyle(style);
				}
			}
			// dialog.pack();
			// dialog.setLocationRelativeTo(parentComponent);
			WindowAdapter adapter = new WindowAdapter() {
				private boolean gotFocus = false;

				public void windowClosing(WindowEvent we) {
					// setValue(null);
				}

				public void windowGainedFocus(WindowEvent we) {
					// Once window gets focus, set initial focus
					if (!gotFocus) {
						// selectInitialValue();
						gotFocus = true;
					}
				}
			};
			dialog.addWindowListener(adapter);
			dialog.addWindowFocusListener(adapter);

			dialog.setVisible(true);
			dialog.dispose();

			wkn = (String) jTable.getRowSorter().getModel().getValueAt(jTable.getSelectedRow(), 0);
		}

		if (wkn == null)
			return null;

		wkn = wkn.toUpperCase();

		setStatus("Loading " + wkn + "...");
		try {

			setProgress(0);

			List<Matrix> mList = new ArrayList<Matrix>();

			getVariable().setSize(1, 1);

			int index = 1;
			int progress = 0;

			getVariable().setLabel(wkn);

			Calendar today = Calendar.getInstance();

			String startMonth = "00";
			String endMonth = "11";
			String startDay = "1";
			String endDay = "31";
			String update = "d"; // d:daily w:weekly m:monthly
			int countPerPage = 365;

			for (int year = today.get(Calendar.YEAR); year >= 1900; year--) {
				String urlString = "http://ichart.yahoo.com/table.csv";
				urlString += "?s=" + wkn;
				urlString += "&a=" + startMonth;
				urlString += "&b=" + startDay;
				urlString += "&c=" + year;
				urlString += "&d=" + endMonth;
				urlString += "&e=" + endDay;
				urlString += "&f=" + year;
				urlString += "&g=" + update;
				// urlString += "&y=" + startLine;
				urlString += "&z=" + countPerPage;
				urlString += "&ignore=.csv";

				URL url = new URL(urlString);

				// File dir = new File(JDMP.DATASETDIR + File.separator +
				// "stockmarket" + File.separator + wkn);
				File dir = null;

				if (!dir.exists())
					dir.mkdirs();

				File file = new File(dir.getAbsolutePath() + File.separator + wkn + "-" + year
						+ ".csv");

				String[] fields = null;

				if (file.exists() && file.length() == 0) {
					file.delete();
				}

				if (file.exists()) {
					System.out.println("get from file: " + file);
					lr = new IntelligentFileReader(file);
				} else {
					try {
						System.out.println("get from url: " + urlString);

						if (year < today.get(Calendar.YEAR))
							out = new PrintWriter(new FileOutputStream(file, false));

						connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);
						connection.setDoOutput(true);
						connection.setRequestMethod("GET");
						connection.connect();
						lr = new IntelligentFileReader(connection);
					} catch (FileNotFoundException e) {
						if (out != null) {
							out.println("Date,Open,High,Low,Close,Volume,Adj Close");
						}
					} catch (Exception e) {
						logger.log(Level.WARNING, "could not read url " + url, e);
						try {
							file.delete();
						} catch (Exception ex) {
						}
					}
				}

				String line = null;
				while (lr != null && (line = lr.readLine()) != null) {

					if (out != null)
						out.println(line);

					if (!line.startsWith("Date")) { // ignore header

						fields = line.split(",");
						String date = fields[0];
						String open = fields[1];
						String high = fields[2];
						String low = fields[3];
						String close = fields[4];
						String volume = fields[5];
						String adjclose = fields[6];
						Matrix m = MatrixFactory.zeros(1, 1);
						m.setLabel(date);
						// m.setValue(0, 0, Double.parseDouble(open));
						// m.setValue(0, 1, Double.parseDouble(high));
						// m.setValue(0, 2, Double.parseDouble(low));
						m.setDouble(Double.parseDouble(close), 0, 0);
						// m.setValue(0, 4, Double.parseDouble(volume));
						// m.setValue(0, 5, Double.parseDouble(adjclose));
						mList.add(m);
						progress++;
					}
				}

				try {
					if (lr != null) {
						lr.close();
						lr = null;
					}
				} catch (Exception e) {
				}

				try {
					if (connection != null) {
						connection.disconnect();
						connection = null;
					}
				} catch (Exception e) {
				}

				try {
					if (out != null) {
						out.close();
						out = null;
					}
				} catch (Exception e) {
				}

			}

			boolean convertToPercent = true;

			if (mList.size() == 0) {
				logger.log(Level.WARNING, "no data loaded for WKN " + wkn);
			} else {
				if (convertToPercent) {
					getVariable().setMemorySize(mList.size() - 1);
					Matrix ref = mList.get(mList.size() - 1);
					for (int i = mList.size() - 2; i >= 0; i--) {
						Matrix comp = mList.get(i);
						double r = ref.getDouble(0, 0);
						double c = comp.getDouble(0, 0);
						Matrix m = MatrixFactory.zeros(1, 1);
						m.setDouble((c - r) / r * 50, 0, 0);
						// m.setTime(index++);
						m.setLabel(ref.getLabel() + " -> " + comp.getLabel());
						getVariable().addMatrix(m);
						ref = comp;
					}
				} else {
					getVariable().setMemorySize(mList.size());
					for (int i = mList.size() - 1; i >= 0; i--) {
						Matrix m = mList.get(i);
						// m.setTime(index++);
						getVariable().addMatrix(m);
					}
				}
			}

			setStatus("Chart loaded.");
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not load chart", e);
			setStatus("Could not load chart");
			return null;
		} finally {
			setProgress(1);

			try {
				if (lr != null)
					lr.close();
			} catch (Exception e) {
			}

			try {
				if (connection != null)
					connection.disconnect();
			} catch (Exception e) {
			}

			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
			}
		}
	}
}
