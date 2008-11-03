/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.jgroups;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;

import org.jgroups.ChannelFactory;
import org.jgroups.JChannelFactory;
import org.jgroups.View;
import org.jgroups.blocks.ReplicatedHashMap;
import org.jgroups.blocks.ReplicatedHashMap.Notification;
import org.ujmp.core.Matrix;
import org.ujmp.core.coordinates.CoordinateSetToLongWrapper;
import org.ujmp.core.coordinates.Coordinates;
import org.ujmp.core.doublematrix.AbstractSparseDoubleMatrix;
import org.ujmp.core.exceptions.MatrixException;

public class ReplicatedSparseMatrix extends AbstractSparseDoubleMatrix {
	private static final long serialVersionUID = -2470092774104421146L;

	private static int runningId = 0;

	private ChannelFactory factory = null;

	private final PropsUDP props = new PropsUDP();

	private final boolean persist = false;

	private ReplicatedHashMap<Coordinates, Double> values = null;

	private long[] size = null;

	private final int maximumNumberOfEntries = -1;

	private final double defaultValue = 0.0;

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
			Double v = values.get(new Coordinates(coordinates));
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
