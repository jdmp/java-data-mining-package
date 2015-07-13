/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class JettyObjectHandler extends AbstractHandler {

	private final Object object;

	public JettyObjectHandler(Object o) {
		this.object = o;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		InputStream is = request.getInputStream();

		String methodName = null;
		Object[] parameters = null;
		Object result = null;

		// read parameters
		try {
			ObjectInputStream ois = new ObjectInputStream(is);
			methodName = (String) ois.readObject();
			parameters = (Object[]) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		is.close();

		// execute method
		try {
			Class<?>[] classes = new Class<?>[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				classes[i] = parameters[i].getClass();
			}

			Method method = null;
			for (Method m : object.getClass().getMethods()) {
				if (m.getParameterTypes().length == parameters.length && m.getName().equals(methodName)) {
					method = m;
					break;
				}
			}

			if (method == null) {
				throw new RuntimeException("method not found: " + methodName + Arrays.asList(parameters));
			}

			result = method.invoke(object, parameters);

		} catch (Exception e) {
			e.printStackTrace();
		}

		OutputStream os = response.getOutputStream();

		// write result
		try {
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(result);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		os.flush();
		os.close();
	}

}
