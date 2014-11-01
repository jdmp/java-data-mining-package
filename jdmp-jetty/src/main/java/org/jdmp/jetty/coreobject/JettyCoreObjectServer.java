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

package org.jdmp.jetty.coreobject;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.ujmp.core.interfaces.CoreObject;

public class JettyCoreObjectServer {

	private Server server = null;

	private CoreObject object = null;

	private int port = 8888;

	public JettyCoreObjectServer(CoreObject o, int port) {
		this.object = o;
		this.port = port;
	}

	public boolean isConnected() {
		return server != null && server.isRunning();
	}

	public void start() throws Exception {
		server = new Server(port);

		ContextHandlerCollection contexts = new ContextHandlerCollection();
		server.setHandler(contexts);

		ContextHandler context = new ContextHandler();
		context.setContextPath("/");
		context.setResourceBase(".");
		context.setClassLoader(Thread.currentThread().getContextClassLoader());
		server.setHandler(context);
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

}
