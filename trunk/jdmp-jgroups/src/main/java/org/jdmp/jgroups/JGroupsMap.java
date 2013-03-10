/*
 * Copyright (C) 2008-2013 by Holger Arndt
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
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.ChannelException;
import org.jgroups.ChannelFactory;
import org.jgroups.JChannelFactory;
import org.jgroups.View;
import org.jgroups.blocks.ReplicatedHashMap;
import org.jgroups.blocks.ReplicatedHashMap.Notification;

public class JGroupsMap<K extends Serializable, V extends Serializable> implements Map<K, V>,
		Notification<K, V> {

	private ChannelFactory channelFactory = null;

	private String props = null;

	private final boolean persist = false;

	private String name = null;

	private final int stateTimeout = 10000;

	private ReplicatedHashMap<K, V> map = null;

	public JGroupsMap() throws UnknownHostException, ChannelException {
		this(null);
	}

	public JGroupsMap(String name) throws UnknownHostException, ChannelException {
		this.name = name;

		if (this.channelFactory == null) {
			this.channelFactory = new JChannelFactory();
		}
		if (this.props == null) {
			PropsUDP propsUDP = new PropsUDP();
			propsUDP.setBindAddress(InetAddress.getLocalHost().getHostAddress());
			this.props = propsUDP.toString();
		}
		if (this.name == null) {
			this.name = "jgroupsmap" + System.nanoTime();
		}

		this.map = new ReplicatedHashMap<K, V>(name, channelFactory, props, persist, stateTimeout);
		this.map.addNotifier(this);
	}

	
	public void clear() {
		map.clear();
	}

	
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	
	public V get(Object key) {
		return map.get(key);
	}

	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	
	public Set<K> keySet() {
		return map.keySet();
	}

	
	public V put(K key, V value) {
		return map.put(key, value);
	}

	
	public void putAll(Map<? extends K, ? extends V> m) {
		map.putAll(m);
	}

	
	public V remove(Object key) {
		return map.remove(key);
	}

	
	public int size() {
		return map.size();
	}

	
	public Collection<V> values() {
		return map.values();
	}

	
	public void contentsCleared() {

	}

	
	public void contentsSet(Map<K, V> new_entries) {

	}

	
	public void entryRemoved(K key) {

	}

	
	public void entrySet(K key, V value) {

	}

	
	public void viewChange(View view, Vector<Address> new_mbrs, Vector<Address> old_mbrs) {

	}

}
