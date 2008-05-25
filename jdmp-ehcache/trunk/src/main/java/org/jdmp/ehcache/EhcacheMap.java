package org.jdmp.ehcache;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.event.RegisteredEventListeners;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

public class EhcacheMap<K, V> implements Map<K, V>, Serializable, Flushable, Closeable {
	private static final long serialVersionUID = -2405059234958626645L;

	private int maxElementsInMemory = 100;

	private int maxElementsOnDisk = 100000;

	private boolean overflowToDisk = true;

	private boolean eternal = true;

	private boolean diskPersistent = true;

	private RegisteredEventListeners registeredEventListeners = null;

	private int timeToLiveSeconds = 100000;

	private int timeToIdleSeconds = 120;

	private int diskExpiryThreadIntervalSeconds = 300;

	private MemoryStoreEvictionPolicy memoryStoreEvictionPolicy = MemoryStoreEvictionPolicy.LFU;

	private String diskStorePath = null;

	private transient CacheManager manager = null;

	private transient Cache cache = null;

	public EhcacheMap() throws IOException {
		this("cache" + System.nanoTime());
	}

	public EhcacheMap(String name) throws IOException {
		this(name, null);
	}

	public EhcacheMap(String name, File path) throws IOException {
		System.setProperty("net.sf.ehcache.enableShutdownHook", "true");

		if (path == null) {
			path = File.createTempFile("ehcache" + System.nanoTime(), "tmp");
			path.delete();
			path.mkdir();
		}

		diskStorePath = path.getAbsolutePath();

		Cache c = new Cache(name, maxElementsInMemory, memoryStoreEvictionPolicy, overflowToDisk, diskStorePath,
				eternal, timeToLiveSeconds, timeToIdleSeconds, diskPersistent, diskExpiryThreadIntervalSeconds,
				registeredEventListeners);
		getCacheManager().addCache(c);
		cache = getCacheManager().getCache(name);
	}

	public String getPath() {
		if (diskStorePath == null) {
			diskStorePath = System.getProperty("java.io.tmpdir");
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
			diskStoreConfigurationParameter.setPath(getPath());
			config.addDiskStore(diskStoreConfigurationParameter);
			config.setDefaultCacheConfiguration(cacheconfig);
			manager = new CacheManager(config);
		}
		return manager;
	}

	public void clear() {
		cache.flush();
	}

	public boolean containsKey(Object key) {
		return cache.isKeyInCache(key);
	}

	public boolean containsValue(Object value) {
		return cache.isValueInCache(value);
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return null;
	}

	public V get(Object key) {
		Element e = cache.get(key);
		if (e != null) {
			return (V) e.getValue();
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return cache.getSize() == 0;
	}

	public Set<K> keySet() {
		return new HashSet<K>(cache.getKeys());
	}

	public V put(K key, V value) {
		Element e = new Element(key, value);
		cache.put(e);
		return null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			V value = m.get(key);
			put(key, value);
		}
	}

	public V remove(Object key) {
		cache.remove(key);
		return null;
	}

	public int size() {
		return cache.getSize();
	}

	public Collection<V> values() {
		return null;
	}

	public void finalize() {
		CacheManager.getInstance().removeCache(cache.getName());
	}

	public void flush() throws IOException {
		cache.flush();
	}

	public void close() throws IOException {
		cache.dispose();
		manager.removeCache(cache.getName());
	}

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		String cacheName = (String) s.readObject();
		diskStorePath = (String) s.readObject();
		System.out.println(cacheName);
		cache = getCacheManager().getCache(cacheName);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(cache.getName());
		s.writeObject(diskStorePath);
	}

}
