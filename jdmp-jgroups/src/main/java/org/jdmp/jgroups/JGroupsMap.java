/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.jgroups.View;
import org.jgroups.blocks.ReplicatedHashMap;
import org.jgroups.blocks.ReplicatedHashMap.Notification;
import org.jgroups.conf.ConfiguratorFactory;
import org.jgroups.conf.XmlConfigurator;
import org.jgroups.util.Util;

public class JGroupsMap<K extends Serializable, V extends Serializable> implements Map<K, V>,
		Notification<K, V>, ChannelListener {

	private static String properties = "org/jdmp/jgroups/tcp.xml";

	private final int stateTimeout = 10000;

	private final ReplicatedHashMap<K, V> map;

	public JGroupsMap() throws Exception {
		this(null, null);
	}

	public JGroupsMap(String clusterName) throws Exception {
		this(createChannel(), clusterName);
	}

	private static JChannel createChannel() throws Exception {
		// PropsUDP propsUDP = new PropsUDP();
		// propsUDP.setBindAddress(InetAddress.getLocalHost().getHostAddress());

		// JChannel channel = new JChannel(new
		// PlainConfigurator(propsUDP.toString()));

		XmlConfigurator returnValue = null;
		InputStream configStream = getConfigStream(properties);
		if (configStream == null && properties.endsWith(".xml"))
			throw new FileNotFoundException(String.format(Util.getMessage("FileNotFound"),
					properties));

		if (configStream != null) {
			returnValue = XmlConfigurator.getInstance(configStream);
		}

		returnValue.getProtocolStack().get(0).getProperties().put("bind_addr", "10.20.1.20");

		System.out.println(returnValue.getProtocolStack().get(0).getProperties());

		JChannel channel = new JChannel(returnValue);
		return channel;
	}

	public JGroupsMap(JChannel channel, String clusterName) throws Exception {
		channel.addChannelListener(this);

		if (!channel.isConnected() && !channel.isConnecting()) {
			channel.connect(clusterName);
		}

		map = new ReplicatedHashMap<K, V>(channel);
		map.addNotifier(this);
		map.start(stateTimeout);
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
		System.out.println("entryRemoved:" + key);
	}

	public void entrySet(K key, V value) {
		System.out.println("entrySet:" + key);
	}

	public void viewChange(View view, List<Address> mbrs_joined, List<Address> mbrs_left) {
		System.out.println("viewChange:" + view);
	}

	public static InputStream getConfigStream(String properties) throws IOException {
		InputStream configStream = null;

		// Check to see if the properties string is the name of a file.
		try {
			configStream = new FileInputStream(properties);
		} catch (FileNotFoundException fnfe) {
			// the properties string is likely not a file
		} catch (AccessControlException access_ex) {
			// fixes http://jira.jboss.com/jira/browse/JGRP-94
		}

		// Check to see if the properties string is a URL.
		if (configStream == null) {
			try {
				configStream = new URL(properties).openStream();
			} catch (MalformedURLException mre) {
				// the properties string is not a URL
			}
		}

		// Check to see if the properties string is the name of a resource, e.g.
		// udp.xml.
		if (configStream == null && properties.endsWith("xml"))
			configStream = Util.getResourceAsStream(properties, ConfiguratorFactory.class);
		return configStream;
	}

	public void channelConnected(Channel channel) {
		System.out.println("channelConnected" + channel);
	}

	public void channelDisconnected(Channel channel) {
		System.out.println("channelDisconnected" + channel);
	}

	public void channelClosed(Channel channel) {
		System.out.println("channelClosed" + channel);
	}
}
