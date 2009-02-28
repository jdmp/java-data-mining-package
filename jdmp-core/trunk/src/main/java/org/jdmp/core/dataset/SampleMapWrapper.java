package org.jdmp.core.dataset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.swing.AbstractListModel;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.jdmp.core.util.ObservableList;
import org.ujmp.core.collections.SoftHashMap;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.util.StringUtil;

public class SampleMapWrapper<V> extends AbstractListModel implements ObservableList<Sample>,
		Wrapper<Map<? extends Object, V>> {
	private static final long serialVersionUID = -699242401200223218L;

	private Map<? extends Object, V> map = null;

	private Map<Sample, Integer> sampleToIndex = null;

	private Map<Integer, Sample> indexToSample = null;

	public SampleMapWrapper(Map<? extends Object, V> map) {
		this.map = map;
		sampleToIndex = new SoftHashMap<Sample, Integer>();
		indexToSample = new SoftHashMap<Integer, Sample>();
	}

	@Override
	public synchronized void clear() {
		int size = map.size();
		sampleToIndex.clear();
		indexToSample.clear();
		map.clear();
		fireIntervalRemoved(this, 0, size - 1);
	}

	@Override
	public Sample getElementAt(int index) {
		Sample sample = indexToSample.get(index);
		if (sample != null) {
			return sample;
		}
		int i = 0;
		for (Object k : map.keySet()) {
			if (i++ == index) {
				V v = map.get(k);
				Sample s = SampleFactory.createFromObject(v);
				s.setId(StringUtil.convert(k));
				indexToSample.put(i, s);
				sampleToIndex.put(s, i);
				return s;
			}
		}
		return null;
	}

	@Override
	public int indexOf(Sample value) {
		Integer index = sampleToIndex.get(value);
		if (index != null) {
			return index;
		}
		int i = 0;
		for (Object k : map.keySet()) {
			V v = map.get(k);
			if (value.equals(v)) {
				Sample s = SampleFactory.createFromObject(v);
				s.setId(StringUtil.convert(k));
				indexToSample.put(i, s);
				sampleToIndex.put(s, i);
				return i;
			}
			i++;
		}
		return -1;
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Collection<Sample> toCollection() {
		throw new MatrixException("not implemented");
	}

	@Override
	public int getSize() {
		return map.size();
	}

	@Override
	public Iterator<Sample> iterator() {
		return new Iterator<Sample>() {

			int index = 0;

			@Override
			public boolean hasNext() {
				return index < map.size();
			}

			@Override
			public Sample next() {
				return getElementAt(index++);
			}

			@Override
			public void remove() {
				throw new MatrixException("not implemented");
			}
		};
	}

	@Override
	public Map<? extends Object, V> getWrappedObject() {
		return map;
	}

	@Override
	public synchronized void setWrappedObject(Map<? extends Object, V> object) {
		int size = map.size();
		indexToSample.clear();
		sampleToIndex.clear();
		this.map = object;
		fireContentsChanged(this, 0, size - 1);
	}

	@Override
	public synchronized void add(Sample value) {
		throw new MatrixException("use put to add values");
	}

	@Override
	public void addAll(Collection<Sample> values) {
		throw new MatrixException("use put to add values");
	}

	@Override
	public synchronized boolean remove(Sample value) {
		throw new MatrixException("not yet implemented");
	}

}
