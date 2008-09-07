package org.jdmp.ehcache;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.ujmp.core.exceptions.MatrixException;

public class EhcacheMap<K, V> implements Map<K, V>, Flushable, Closeable {
	private static final long serialVersionUID = -2405059234958626645L;

	private int maxElementsInMemory = 1000;

	private int maxElementsOnDisk = Integer.MAX_VALUE - 1;

	private boolean overflowToDisk = true;

	private boolean eternal = true;

	private boolean diskPersistent = true;

	private transient File diskStorePath = null;

	private transient String name = null;

	private transient RegisteredEventListeners registeredEventListeners = null;

	private int timeToLiveSeconds = Integer.MAX_VALUE - 1;

	private int timeToIdleSeconds = 120;

	private int diskSpoolBufferSizeMB = 16;

	private int diskExpiryThreadIntervalSeconds = 300;

	private MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LFU;

	private transient CacheManager manager = null;

	private transient Cache cache = null;

	private BootstrapCacheLoader bootstrapCacheLoader = null;

	public EhcacheMap() throws IOException {
		this(null);
	}

	public EhcacheMap(String name) throws IOException {
		this(name, null);
	}

	public EhcacheMap(String name, File path) throws IOException {
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");
		this.diskStorePath = path;
		this.name = name;
	}

	public String getName() {
		if (name == null) {
			name = "ehcache" + System.nanoTime();
		}
		return name;
	}

	public File getPath() {
		if (diskStorePath == null) {
			try {
				diskStorePath = File.createTempFile(getName(), ".tmp");
				diskStorePath.delete();
				diskStorePath.mkdir();
				diskStorePath.deleteOnExit();
				new File(diskStorePath + System.getProperty("file.separator") + getName() + ".data")
						.deleteOnExit();
				new File(diskStorePath + System.getProperty("file.separator") + getName()
						+ ".index").deleteOnExit();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return diskStorePath;
	}

	private CacheManager getCacheManager() {
		if (manager == null) {

			Configuration config = new Configuration();
			CacheConfiguration cacheconfig = new CacheConfiguration();
			cacheconfig.setDiskExpiryThreadIntervalSeconds(diskExpiryThreadIntervalSeconds);
			cacheconfig.setDiskPersistent(diskPersistent);
			cacheconfig.setEternal(eternal);
			cacheconfig.setMaxElementsInMemory(maxElementsInMemory);
			cacheconfig.setMaxElementsOnDisk(maxElementsOnDisk);
			cacheconfig.setMemoryStoreEvictionPolicyFromObject(memoryStoreEvictionPolicy);
			cacheconfig.setOverflowToDisk(overflowToDisk);
			cacheconfig.setTimeToIdleSeconds(timeToIdleSeconds);
			cacheconfig.setTimeToLiveSeconds(timeToLiveSeconds);

			DiskStoreConfiguration diskStoreConfigurationParameter = new DiskStoreConfiguration();
			diskStoreConfigurationParameter.setPath(getPath().getAbsolutePath());
			config.addDiskStore(diskStoreConfigurationParameter);
			config.setDefaultCacheConfiguration(cacheconfig);
			manager = new CacheManager(config);
		}
		return manager;
	}

	private Cache getCache() {
		if (cache == null) {
			Cache c = new Cache(getName(), maxElementsInMemory, memoryStoreEvictionPolicy,
					overflowToDisk, getPath().getAbsolutePath(), eternal, timeToLiveSeconds,
					timeToIdleSeconds, diskPersistent, diskExpiryThreadIntervalSeconds,
					registeredEventListeners, bootstrapCacheLoader, maxElementsOnDisk,
					diskSpoolBufferSizeMB);
			getCacheManager().addCache(c);
			cache = getCacheManager().getCache(getName());
		}
		return cache;
	}

	public void clear() {
		getCache().flush();
	}

	public boolean containsKey(Object key) {
		return getCache().isKeyInCache(key);
	}

	public boolean containsValue(Object value) {
		return getCache().isValueInCache(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new MatrixException("not implemented");
	}

	public V get(Object key) {
		Element e = getCache().get(key);
		if (e != null) {
			return (V) e.getValue();
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return getCache().getSize() == 0;
	}

	public Set<K> keySet() {
		return new HashSet<K>(getCache().getKeys());
	}

	public V put(K key, V value) {
		Element e = new Element(key, value);
		getCache().put(e);
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V value = m.get(key);
			put(key, value);
		}
	}

	public V remove(Object key) {
		getCache().remove(key);
		return null;
	}

	public int size() {
		return getCache().getSize();
	}

	public Collection<V> values() {
		throw new MatrixException("not implemented");
	}

	public void finalize() {
		getCacheManager().removeCache(getCache().getName());
	}

	public void flush() throws IOException {
		getCache().flush();
	}

	public void close() throws IOException {
		getCache().dispose();
		getCacheManager().removeCache(getCache().getName());
	}
}
