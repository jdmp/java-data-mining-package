package org.jdmp.jgroups;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.coordinates.CoordinateSetToLongWrapper;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.stubs.AbstractSparseDoubleMatrix;
import org.jgroups.ChannelFactory;
import org.jgroups.JChannelFactory;
import org.jgroups.View;
import org.jgroups.blocks.ReplicatedHashMap;
import org.jgroups.blocks.ReplicatedHashMap.Notification;

public class ReplicatedSparseMatrix extends AbstractSparseDoubleMatrix {
	private static final long serialVersionUID = -2470092774104421146L;

	private static int runningId = 0;

	private ChannelFactory factory = null;

	private PropsUDP props = new PropsUDP();

	private boolean persist = false;

	private ReplicatedHashMap<Coordinates, Double> values = null;

	private long[] size = null;

	private int maximumNumberOfEntries = -1;

	private double defaultValue = 0.0;

	public ReplicatedSparseMatrix(Matrix m) throws MatrixException {
		this(m.getSize());

		for (long[] c : m.allCoordinates()) {
			setAsDouble(m.getAsDouble(c), c);
		}

	}

	public ReplicatedSparseMatrix(long... size) {
		this("Matrix" + runningId++, size);
	}

	public ReplicatedSparseMatrix(String name, long... size) {
		setLabel(name);
		this.size = size;

		try {
			factory = new JChannelFactory();
			props.setBindAddress(InetAddress.getLocalHost().getHostAddress());
			values = new ReplicatedHashMap<Coordinates, Double>(name, factory, props.toString(),
					persist, 10000);
			values.addNotifier(new NotificationListener(this));
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not start matrix", e);
		}
	}

	public long[] getSize() {
		return size;
	}

	public double getDouble(long... coordinates) {
		if (values != null) {
			Double v = (Double) values.get(new Coordinates(coordinates));
			return v == null ? defaultValue : v;
		} else {
			return 0.0;
		}
	}

	@Override
	public long getValueCount() {
		return values.size();
	}

	public void setDouble(double value, long... coordinates) {
		while (maximumNumberOfEntries > 0 && values.size() > maximumNumberOfEntries) {
			values.remove(values.keySet().iterator().next());
		}
		if (Coordinates.isSmallerThan(coordinates, size)) {
			values.put(new Coordinates(coordinates), value);
		}
	}

	public Iterable<long[]> allCoordinates() {
		return new CoordinateSetToLongWrapper(values.keySet());
	}

	public boolean contains(long... coordinates) {
		return values.containsKey(new Coordinates(coordinates));
	}

}

class NotificationListener implements Notification {

	private Matrix matrix = null;

	public NotificationListener(Matrix matrix) {
		this.matrix = matrix;
	}

	public void contentsCleared() {
		matrix.notifyGUIObject();
	}

	public void contentsSet(Map new_entries) {
		matrix.notifyGUIObject();
	}

	public void entryRemoved(Serializable key) {
		matrix.notifyGUIObject();
	}

	public void entrySet(Serializable key, Serializable value) {
		matrix.notifyGUIObject();
	}

	public void viewChange(View view, Vector new_mbrs, Vector old_mbrs) {
		matrix.notifyGUIObject();
	}
}
