package org.jdmp.gui;

import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;

public class Main {

	public static void main(String[] args) throws Exception {
		Module m = ModuleFactory.emptyModule();
		m.showGUI();
		DataSet ds = DataSetFactory.IRIS();
		ds.showGUI();
	}

}
