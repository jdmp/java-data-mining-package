package org.jdmp.core.algorithm;

public class AlgorithmSpeedThread extends Thread {

	private Algorithm algorithm = null;

	private int lastCount = 0;

	public AlgorithmSpeedThread(Algorithm algorithm) {
		this.setName(this.getClass().getSimpleName() + ": " + algorithm.getLabel());
		this.algorithm = algorithm;
		this.setPriority(Thread.MIN_PRIORITY);
	}

	public void run() {
		while (!interrupted()) {
			try {
				sleep(1000);
				algorithm.setCallsPerSecond(algorithm.getCount() - lastCount);
				lastCount = algorithm.getCount();
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}
}
