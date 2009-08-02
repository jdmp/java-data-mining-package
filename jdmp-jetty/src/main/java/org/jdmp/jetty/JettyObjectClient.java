/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.jetty;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JettyObjectClient {

	private URL url = null;

	public JettyObjectClient(URL url) {
		this.url = url;
	}

	public Object execute(String method, Object... parameters) throws Exception {
		OutputStream os = null;
		InputStream is = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(3000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.connect();

		os = connection.getOutputStream();
		oos = new ObjectOutputStream(os);
		oos.writeObject(method);
		oos.writeObject(parameters);
		oos.flush();
		oos.close();
		os.flush();
		os.close();

		is = connection.getInputStream();
		ois = new ObjectInputStream(is);
		Object r = ois.readObject();
		ois.close();
		is.close();

		connection.disconnect();

		return r;
	}

}
