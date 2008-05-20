package org.jdmp.core.algorithm;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AlgorithmThread extends Thread {

	private static final Logger logger = Logger.getLogger(AlgorithmThread.class.getName());

	private Algorithm algorithm = null;

	public AlgorithmThread(Algorithm a) {
		this.algorithm = a;
		this.setName("AlgorithmThread: " + a.getLabel());
		this.setPriority(Thread.NORM_PRIORITY);
	}

	public void run() {
		while (!interrupted()) {
			try {
				if (algorithm.getStepsToDo() > 0) {
					long t1 = System.nanoTime();
					algorithm.call();
					long t2 = System.nanoTime();
					algorithm.setRuntime(algorithm.getRuntime() + (t2 - t1));
					algorithm.decreaseStepsToDo();
					if (algorithm.getInterval() > 0) {
						Thread.sleep(algorithm.getInterval());
					}
				} else {
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				this.interrupt();
			} catch (Exception e) {
				logger.log(Level.WARNING, "error calculating algorithm", e);
			}
		}
	}
}
