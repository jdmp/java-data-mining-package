package org.jdmp.gui;

import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;

public class Main {

	public static void main(String[] args) {
		Module m = ModuleFactory.emptyModule();
		m.showGUI();
	}

}
