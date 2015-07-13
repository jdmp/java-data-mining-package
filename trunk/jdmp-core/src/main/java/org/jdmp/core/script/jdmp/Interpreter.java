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

package org.jdmp.core.script.jdmp;

import java.io.PushbackReader;
import java.io.StringReader;

import org.jdmp.core.module.Module;
import org.jdmp.core.script.Result;
import org.jdmp.core.script.jdmp.lexer.Lexer;
import org.jdmp.core.script.jdmp.node.Start;
import org.jdmp.core.script.jdmp.parser.Parser;

public class Interpreter {

	private Module module = null;

	public Interpreter(Module module) {
		this.module = module;
	}

	public Result execute(String s) {
		try {
			if (!s.endsWith(";")) {
				s = s + ";";
			}

			Translation translation = new Translation(module);
			StringReader sr = new StringReader(s);
			Parser p = new Parser(new Lexer(new PushbackReader(sr, 1024)));

			Start tree = p.parse();
			tree.apply(translation);

			return translation.getResult();

		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null) {
				return new Result("Error", e.getCause());
			} else {
				return new Result("Unparseable expression", e);
			}
		}
	}

}
