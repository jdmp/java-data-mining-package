package org.jdmp.core.matrix.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.jdmp.core.util.GlobalTimer;
import org.ujmp.core.Matrix;
import org.ujmp.core.matrices.stubs.AbstractMapMatrix;

public class MatrixRunningThreads extends AbstractMapMatrix {
	private static final long serialVersionUID = -6988423129848472319L;

	private static MatrixRunningThreads matrix = null;

	private MatrixRunningThreads() {
		GlobalTimer.getInstance().schedule(new TimerTask() {

			@Override
			public void run() {
				matrix.notifyGUIObject();

			}
		}, 1000, 1000);
	}

	public static Matrix getInstance() {
		if (matrix == null) {
			matrix = new MatrixRunningThreads();
		}
		return matrix;
	}

	@Override
	public Map getMap() {
		return ThreadMap.getInstance();
	}

}

class ThreadMap implements Map<Object, Object> {

	private static ThreadMap threadMap = null;

	public static ThreadMap getInstance() {
		if (threadMap == null) {
			threadMap = new ThreadMap();
		}
		return threadMap;
	}

	public void clear() {
	}

	public boolean containsKey(Object key) {
		return Thread.getAllStackTraces().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return Thread.getAllStackTraces().containsValue(value);
	}

	public Set<java.util.Map.Entry<Object, Object>> entrySet() {
		return Collections.emptySet();
	}

	public Object get(Object key) {
		return Arrays.asList(Thread.getAllStackTraces().get(key));
	}

	public boolean isEmpty() {
		return Thread.getAllStackTraces().isEmpty();
	}

	public Set<Object> keySet() {
		return new HashSet<Object>(Thread.getAllStackTraces().keySet());
	}

	public Object put(Object key, Object value) {
		return null;
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
	}

	public Object remove(Object key) {
		return null;
	}

	public int size() {
		return Thread.getAllStackTraces().size();
	}

	public Collection<Object> values() {
		return new ArrayList<Object>(Thread.getAllStackTraces().values());
	}

}