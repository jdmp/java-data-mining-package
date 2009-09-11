package org.jdmp.core.util;

import java.io.File;

import org.ujmp.core.util.UJMPSettings;

public class JDMPSettings extends UJMPSettings {

	private static String datasetFolder = ".";

	static {
		try {
			datasetFolder = System.getProperty("user.home") + File.separator + ".jdmp"
					+ File.separator + "datasets";
			if (!new File(datasetFolder).exists()) {
				new File(datasetFolder).mkdirs();
			}
		} catch (Throwable t) {
		}
	}

	public static String getDataSetFolder() {
		return datasetFolder;
	}
}
