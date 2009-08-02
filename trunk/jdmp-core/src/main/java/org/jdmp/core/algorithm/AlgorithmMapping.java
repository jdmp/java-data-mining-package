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

package org.jdmp.core.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.algorithm.basic.About;
import org.jdmp.core.algorithm.basic.Abs;
import org.jdmp.core.algorithm.basic.Cd;
import org.jdmp.core.algorithm.basic.ClassPath;
import org.jdmp.core.algorithm.basic.Clone;
import org.jdmp.core.algorithm.basic.Copy;
import org.jdmp.core.algorithm.basic.CreateEmptyMatrix;
import org.jdmp.core.algorithm.basic.CreateHenon;
import org.jdmp.core.algorithm.basic.CreateIris;
import org.jdmp.core.algorithm.basic.CreateLinReg;
import org.jdmp.core.algorithm.basic.CreateListMatrix;
import org.jdmp.core.algorithm.basic.CreateMLP;
import org.jdmp.core.algorithm.basic.CreateModule;
import org.jdmp.core.algorithm.basic.CreateNaiveBayes;
import org.jdmp.core.algorithm.basic.CreateSunSpotData;
import org.jdmp.core.algorithm.basic.Env;
import org.jdmp.core.algorithm.basic.Exec;
import org.jdmp.core.algorithm.basic.Exit;
import org.jdmp.core.algorithm.basic.Export;
import org.jdmp.core.algorithm.basic.Gauss;
import org.jdmp.core.algorithm.basic.Help;
import org.jdmp.core.algorithm.basic.Import;
import org.jdmp.core.algorithm.basic.Info;
import org.jdmp.core.algorithm.basic.LogisticFunction;
import org.jdmp.core.algorithm.basic.Logisticmap;
import org.jdmp.core.algorithm.basic.Mean;
import org.jdmp.core.algorithm.basic.Memory;
import org.jdmp.core.algorithm.basic.Minus;
import org.jdmp.core.algorithm.basic.Mtimes;
import org.jdmp.core.algorithm.basic.Ones;
import org.jdmp.core.algorithm.basic.Paste;
import org.jdmp.core.algorithm.basic.Plus;
import org.jdmp.core.algorithm.basic.Processors;
import org.jdmp.core.algorithm.basic.Properties;
import org.jdmp.core.algorithm.basic.Pwd;
import org.jdmp.core.algorithm.basic.Rand;
import org.jdmp.core.algorithm.basic.Randn;
import org.jdmp.core.algorithm.basic.Repeat;
import org.jdmp.core.algorithm.basic.Seed;
import org.jdmp.core.algorithm.basic.SerialExecution;
import org.jdmp.core.algorithm.basic.Show;
import org.jdmp.core.algorithm.basic.ShowLicense;
import org.jdmp.core.algorithm.basic.Sum;
import org.jdmp.core.algorithm.basic.Systemtime;
import org.jdmp.core.algorithm.basic.Tanh;
import org.jdmp.core.algorithm.basic.TanhPlusOne;
import org.jdmp.core.algorithm.basic.Threads;
import org.jdmp.core.algorithm.basic.Times;
import org.jdmp.core.algorithm.basic.Transpose;
import org.jdmp.core.algorithm.basic.Version;
import org.jdmp.core.algorithm.basic.Zeros;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

public abstract class AlgorithmMapping {

	private static Map<String, String> mapping = new HashMap<String, String>();

	static {
		mapping.put("about", About.class.getName());
		mapping.put("abs", Abs.class.getName());
		mapping.put("cd", Cd.class.getName());
		mapping.put("clone", Clone.class.getName());
		mapping.put("copy", Copy.class.getName());
		mapping.put("classpath", ClassPath.class.getName());
		mapping.put("empty", CreateEmptyMatrix.class.getName());
		mapping.put("env", Env.class.getName());
		mapping.put("exec", Exec.class.getName());
		mapping.put("exit", Exit.class.getName());
		mapping.put("export", Export.class.getName());
		mapping.put("gauss", Gauss.class.getName());
		mapping.put("help", Help.class.getName());
		mapping.put("henon", CreateHenon.class.getName());
		mapping.put("import", Import.class.getName());
		mapping.put("info", Info.class.getName());
		mapping.put("iris", CreateIris.class.getName());
		mapping.put("license", ShowLicense.class.getName());
		mapping.put("linreg", CreateLinReg.class.getName());
		mapping.put("list", CreateListMatrix.class.getName());
		mapping.put("logistic", LogisticFunction.class.getName());
		mapping.put("logmap", Logisticmap.class.getName());
		mapping.put("map", org.jdmp.core.algorithm.basic.CreateMapMatrix.class.getName());
		mapping.put("mean", Mean.class.getName());
		mapping.put("mem", Memory.class.getName());
		mapping.put("minus", Minus.class.getName());
		mapping.put("mlp", CreateMLP.class.getName());
		mapping.put("module", CreateModule.class.getName());
		mapping.put("mtimes", Mtimes.class.getName());
		mapping.put("naivebayes", CreateNaiveBayes.class.getName());
		mapping.put("ones", Ones.class.getName());
		mapping.put("paste", Paste.class.getName());
		mapping.put("plus", Plus.class.getName());
		mapping.put("processors", Processors.class.getName());
		mapping.put("properties", Properties.class.getName());
		mapping.put("pwd", Pwd.class.getName());
		mapping.put("rand", Rand.class.getName());
		mapping.put("randn", Randn.class.getName());
		mapping.put("repeat", Repeat.class.getName());
		mapping.put("sample", org.jdmp.core.algorithm.basic.CreateSample.class.getName());
		mapping.put("seed", Seed.class.getName());
		mapping.put("serial", SerialExecution.class.getName());
		mapping.put("show", Show.class.getName());
		mapping.put("sunspot", CreateSunSpotData.class.getName());
		mapping.put("sum", Sum.class.getName());
		mapping.put("time", Systemtime.class.getName());
		mapping.put("tanh", Tanh.class.getName());
		mapping.put("tanhPlus1", TanhPlusOne.class.getName());
		mapping.put("threads", Threads.class.getName());
		mapping.put("time", Systemtime.class.getName());
		mapping.put("times", Times.class.getName());
		mapping.put("transpose", Transpose.class.getName());
		mapping.put("version", Version.class.getName());
		mapping.put("zeros", Zeros.class.getName());
	}

	public static final String get(String key) {
		return mapping.get(key);
	}

	public static final ListMatrix<String> list() {
		ListMatrix<String> list = new DefaultListMatrix<String>(mapping.keySet());
		Collections.sort(list);
		return list;
	}

}
