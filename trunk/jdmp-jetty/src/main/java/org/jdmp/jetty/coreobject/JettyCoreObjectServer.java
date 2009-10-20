/*
 * Copyright (C) 2008-2009 by Holger Arndt
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

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.module.Module;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.jetty.JettyObjectServlet;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class JettyCoreObjectServer {

	private Server server = null;

	private JDMPCoreObject object = null;

	private int port = 8888;

	public JettyCoreObjectServer(JDMPCoreObject o, int port) {
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

		Context root = new Context(contexts, "/", Context.SESSIONS);
		root.addServlet(new ServletHolder(new JettyCoreObjectServlet(object)), "/*");

		Context io = new Context(contexts, "/io", Context.SESSIONS);
		io.addServlet(new ServletHolder(new JettyObjectServlet(object)), "/*");

		if (object instanceof Module) {
			Context console = new Context(contexts, "/console",
					Context.SESSIONS);
			console.addServlet(new ServletHolder(new JettyConsoleServlet(
					(Module) object)), "/*");
		}

		if (object instanceof HasVariables) {
			Context list = new Context(contexts, "/variables", Context.SESSIONS);
			list.addServlet(new ServletHolder(new JettyCoreObjectListServlet(
					((HasVariables) object).getVariables())), "/*");
			Context listio = new Context(contexts, "/io/variables",
					Context.SESSIONS);
			listio.addServlet(new ServletHolder(new JettyObjectServlet(
					((HasVariables) object).getVariables())), "/*");
		}

		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

}
