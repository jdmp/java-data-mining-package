package org.jdmp.core.util;

import java.util.Timer;

public class GlobalTimer extends Timer {

	private static GlobalTimer timer = null;

	public static final GlobalTimer getInstance() {
		if (timer == null) {
			timer = new GlobalTimer();
		}
		return timer;
	}

	private GlobalTimer() {
	}

}
