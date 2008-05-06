package org.jdmp.matrix.util;

public abstract class JDMPSettings {

	private static double tolerance = Math.pow(10.0, -6.0);

	private static int systemOutBufferSize = 1024 * 1024;

	private static int systemErrBufferSize = 1024 * 1024;

	private static int numberOfThreads = 1;

	/**
	 * How many rows should be returned maximally for <code>toString()</code>
	 * If the <code>Matrix</code> is bigger, three dots (<code>...</code>)
	 * will be returned for the remaining rows.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxRowsToPrint = 1000;

	/**
	 * How many columns should be returned maximally for <code>toString()</code>.
	 * If the <code>Matrix</code> is bigger, three dots (<code>...</code>)
	 * will be returned for the remaining columns.
	 * 
	 * 
	 * @default 1000
	 */
	private static long maxColumnsToPrint = 1000;

	/**
	 * How many rows should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining
	 * rows.
	 * 
	 * 
	 * @default 10
	 */
	private static long maxToolTipRows = 10;

	/**
	 * How many columns should be returned maximally for
	 * <code>getToolTipText()</code>. If the <code>Matrix</code> is bigger,
	 * three dots (<code>...</code>) will be returned for the remaining
	 * columns.
	 * 
	 * @default 10
	 */
	private static long maxToolTipCols = 10;

	private static RingBufferWriter out = null;

	private static RingBufferWriter err = null;

	static {
		out = new RingBufferWriter(systemOutBufferSize);
		err = new RingBufferWriter(systemErrBufferSize);

		System.setOut(new TeeStream(System.out, out));
		System.setErr(new TeeStream(System.err, err));

		// Set the number of threads to use for expensive calculations. If the
		// machine has only one cpu, only one thread is used. For more than one
		// core, the number of threads is higher.
		numberOfThreads = Runtime.getRuntime().availableProcessors();

		/**
		 * Registers handlers for the signals SIGCONT (18), SIGCHLD (17) and
		 * SIGUSR2 (12). When one of these signals is sent to the application,
		 * the graphical user interface will be displayed.
		 */
		JDMPSignalHandler.initialize();
	}

	public static void initialize() {
	}

	public static String getSystemOut() {
		return out.toString();
	}

	public static String getSystemErr() {
		return err.toString();
	}

	public static int getNumberOfThreads() {
		return numberOfThreads;
	}

	public static void setNumberOfThreads(int numberOfThreads) {
		JDMPSettings.numberOfThreads = numberOfThreads;
	}

	public static double getTolerance() {
		return tolerance;
	}

	public static void setTolerance(double tolerance) {
		JDMPSettings.tolerance = tolerance;
	}

	public static long getMaxColumnsToPrint() {
		return maxColumnsToPrint;
	}

	public static void setMaxColumnsToPrint(long maxColumnsToPrint) {
		JDMPSettings.maxColumnsToPrint = maxColumnsToPrint;
	}

	public static long getMaxRowsToPrint() {
		return maxRowsToPrint;
	}

	public static void setMaxRowsToPrint(long maxRowsToPrint) {
		JDMPSettings.maxRowsToPrint = maxRowsToPrint;
	}

	public static long getMaxToolTipCols() {
		return maxToolTipCols;
	}

	public static void setMaxToolTipCols(long maxToolTipCols) {
		JDMPSettings.maxToolTipCols = maxToolTipCols;
	}

	public static long getMaxToolTipRows() {
		return maxToolTipRows;
	}

	public static void setMaxToolTipRows(long maxToolTipRows) {
		JDMPSettings.maxToolTipRows = maxToolTipRows;
	}

}
