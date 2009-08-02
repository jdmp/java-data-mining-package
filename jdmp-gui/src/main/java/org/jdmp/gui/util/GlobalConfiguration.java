package org.jdmp.gui.util;

import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.BasicConfigurator;

public class GlobalConfiguration implements Serializable, Map<Object, Object> {
	private static final long serialVersionUID = 2592183079000502898L;

	private static final Logger logger = Logger.getLogger(GlobalConfiguration.class.getName());

	private static final String VERSION = "0.4";

	private static final String YEAR = "2007";

	private static GlobalConfiguration globalConfiguration = null;

	private boolean saveToFile = false;

	private PropertiesConfiguration configuration = null;

	private long startTime = 0l;

	private int maxEntriesForFullMatrix = 0;

	private int maxEntriesToTranspose = 0;

	private File configFile = null;

	private File configDir = null;

	public static GlobalConfiguration getInstance() {
		if (globalConfiguration == null) {
			globalConfiguration = new GlobalConfiguration();
		}
		return globalConfiguration;
	}

	private GlobalConfiguration() {
		try {
			startTime = System.currentTimeMillis();

			System.setProperty("java.net.preferIPv4Stack", "true");
			BasicConfigurator.configure(new LogForwarder());

			String configDirName = System.getProperty("user.home") + File.separator + ".jdmp";

			configDir = new File(configDirName);
			configFile = new File(configDirName + File.separator + "jdmp.conf");

			if (configFile.exists()) {
				configuration = new PropertiesConfiguration(configFile);
				configuration.setAutoSave(true);
				configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
			} else {

				configuration = new PropertiesConfiguration();
				configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

				configuration.setHeader("JDMP Configuration File version " + VERSION);

				set("jdmp.ignoreNaN", true);
				set("jdmp.maxToolTipRows", 10);
				set("jdmp.maxToolTipCols", 10);
				set("jdmp.maxRowsToPrint", 1000);
				set("jdmp.maxColumnsToPrint", 1000);
				set("jdmp.maxEntriesForFullMatrix", 10000);
				set("jdmp.maxEntriesToTranspose", 10000);
				set("jdmp.version.label", "JDMP version " + VERSION + " by Holger Arndt " + YEAR);
				set("jdmp.datasetdir", getUserHome() + getFileSeparator() + "datasets");
				set("line.separator", System.getProperty("line.separator"));
				set("path.separator", System.getProperty("path.separator"));

				System.setProperty("proxySet", "true");

				String host = "";
				String port = "";
				String user = null;
				String password = null;
				String nonProxyHosts = null;

				String[] protocols = new String[] { "http", "https", "ftp" };
				for (String protocol : protocols) {
					System.setProperty(protocol + ".proxySet", "true");

					if (host == null)
						System.clearProperty(protocol + ".proxyHost");
					else
						System.setProperty(protocol + ".proxyHost", host);

					if (port == null)
						System.clearProperty(protocol + ".proxyPort");
					else
						System.setProperty(protocol + ".proxyPort", port);

					if (user == null)
						System.clearProperty(protocol + ".proxyUser");
					else
						System.setProperty(protocol + ".proxyUser", user);

					if (password == null)
						System.clearProperty(protocol + ".proxyPassword");
					else
						System.setProperty(protocol + ".proxyPassword", password);

					if (nonProxyHosts == null)
						System.clearProperty(protocol + ".nonProxyHosts");
					else
						System.setProperty(protocol + ".nonProxyHosts", nonProxyHosts);
				}

				if (saveToFile) {
					if (!configDir.exists()) {
						configDir.mkdirs();
					}
					configuration.setAutoSave(true);
					configuration.save(configFile);
				}

			}

		} catch (Throwable e) {
			logger.log(Level.WARNING, "could not load jdmp properties", e);
		}

	}

	public Set<Object> keySet() {
		Set<Object> keys = new HashSet<Object>();
		Iterator it = configuration.getKeys();
		while (it.hasNext()) {
			keys.add(it.next());
		}
		return keys;
	}

	public String getString(String name) {
		return configuration.getString(name);
	}

	public int getInteger(String name) {
		return configuration.getInt(name);
	}

	public boolean getBoolean(String name) {
		return configuration.getBoolean(name);
	}

	private void set(String name, Object value) {
		configuration.addProperty(name, value);
	}

	public long getStartTime() {
		return startTime;
	}

	public String getVersionLabel() {
		return getString("jdmp.version.label");
	}

	public String getDataSetDir() {
		return getString("jdmp.datasetdir");
	}

	public String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}

	public String getFileSeparator() {
		return System.getProperty("file.separator");
	}

	public String getJavaClassPath() {
		return System.getProperty("java.class.path");
	}

	public String getJavaClassVersion() {
		return System.getProperty("java.class.version");
	}

	public String getJavaHome() {
		return System.getProperty("java.home");
	}

	public String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	public String getJavaVendorURL() {
		return System.getProperty("java.vendor.url");
	}

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public String getLineSeparator() {
		return System.getProperty("line.separator");
	}

	public String getOperatingSystemArchitecture() {
		return System.getProperty("os.arch");
	}

	public String getOperatingSystemName() {
		return System.getProperty("os.name");
	}

	public String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	public String getUserDir() {
		return System.getProperty("user.dir");
	}

	public String getUserHome() {
		return System.getProperty("user.home");
	}

	public String getUserName() {
		return System.getProperty("user.name");
	}

	public int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	public long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	public long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	public long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	public boolean isIgnoreNaN() {
		return (Boolean) getBoolean("jdmp.ignoreNaN");
	}

	public int getMaxToolTipRows() {
		return (Integer) getInteger("jdmp.maxToolTipRows");
	}

	public int getMaxToolTipCols() {
		return (Integer) getInteger("jdmp.maxToolTipCols");
	}

	public int getMaxRowsToPrint() {
		return getInteger("jdmp.maxRowsToPrint");
	}

	public int getMaxColumnsToPrint() {
		return getInteger("jdmp.maxColumnsToPrint");
	}

	public int getMaxEntriesForFullMatrix() {
		if (maxEntriesForFullMatrix == 0) {
			maxEntriesForFullMatrix = getInteger("jdmp.maxEntriesForFullMatrix");
		}
		return maxEntriesForFullMatrix;
	}

	public int getMaxEntriesToTranspose() {
		if (maxEntriesToTranspose == 0) {
			maxEntriesToTranspose = getInteger("jdmp.maxEntriesToTranspose");
		}
		return maxEntriesToTranspose;
	}

	public int getScreenWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public int getScreenHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}

	public int getScreenResolution() {
		return Toolkit.getDefaultToolkit().getScreenResolution();
	}

	public boolean isSaveToFile() {
		return saveToFile;
	}

	public void setSaveToFile(boolean saveToFile) {
		this.saveToFile = saveToFile;

		try {

			if (saveToFile) {
				if (!configDir.exists()) {
					configDir.mkdirs();
				}

				configuration.setAutoSave(true);
				configuration.save(configFile);
			} else {
				configuration.setAutoSave(false);
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save configuration", e);
		}
	}

	public Object get(String string) {
		return configuration.getProperty(string);
	}

	public void clear() {
		configuration.clear();
	}

	public boolean containsKey(Object key) {
		return configuration.containsKey("" + key);
	}

	public boolean containsValue(Object value) {
		return false;
	}

	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return null;
	}

	public Object get(Object key) {
		return configuration.getProperty("" + key);
	}

	public boolean isEmpty() {
		return configuration.isEmpty();
	}

	public Object put(Object key, Object value) {
		configuration.setProperty("" + key, value);
		return null;
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
	}

	public Object remove(Object key) {
		configuration.clearProperty("" + key);
		return null;
	}

	public int size() {
		return keySet().size();
	}

	public Collection<Object> values() {
		return null;
	}

	public String getLocalIP() {
		return "192.168.11.80";
	}

}
