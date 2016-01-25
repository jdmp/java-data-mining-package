/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.complete;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ org.jdmp.complete.TestPlugins.class, org.jdmp.core.AllTests.class, org.jdmp.gui.AllTests.class,
		org.jdmp.bsh.AllTests.class, org.jdmp.jetty.AllTests.class, org.jdmp.liblinear.AllTests.class,
		org.jdmp.libsvm.AllTests.class, org.jdmp.lucene.AllTests.class, org.jdmp.mallet.AllTests.class,
		org.jdmp.stanfordpos.AllTests.class, org.jdmp.weka.AllTests.class })
public class AllTests {
}