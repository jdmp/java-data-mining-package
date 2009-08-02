package org.jdmp.gui.module;

import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleListEvent;
import org.jdmp.core.module.ModuleListListener;
import org.jdmp.gui.util.JungGraphPanel.Data;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;

public class ModuleListGraphWrapper extends DirectedSparseGraph implements ModuleListListener {

	public ModuleListGraphWrapper(HasModules iModules) {

		for (Module i : iModules.getModuleList()) {
			Vertex v = new DirectedSparseVertex();
			v.setUserDatum(Data.Label, i.getLabel(), UserData.SHARED);
			v.setUserDatum(Data.JDMPObject, i, UserData.SHARED);
			addVertex(v);
		}

		// for (RowColumn rc : matrix) {
		// double value = matrix.getValue(rc);
		// if (value != 0) {
		// Vertex v1 = getVertex(rc.getRow());
		// Vertex v2 = getVertex(rc.getColumn());
		// if (v1 != null && v2 != null) {
		// if (numEdges() < MAXEDGES) {
		// Edge e = new DirectedSparseEdge(v1, v2);
		// e.addUserDatum("label", matrix.getValue(rc), UserData.SHARED);
		// e.addUserDatum("rowcolumn", rc, UserData.SHARED);
		// e.addUserDatum("value", matrix.getValue(rc), UserData.SHARED);
		// addEdge(e);
		// }
		// }
		// }
		// }

		iModules.addModuleListListener(this);
	}

	public void moduleAdded(ModuleListEvent e) {
		Module i = (Module) e.getData();
		Vertex v = new DirectedSparseVertex();
		v.setUserDatum(Data.Label, i.getLabel(), UserData.SHARED);
		v.setUserDatum(Data.JDMPObject, i, UserData.SHARED);
		addVertex(v);
	}

	public void moduleRemoved(ModuleListEvent e) {
		Module i = (Module) e.getData();
		for (Object o : getVertices().toArray()) {
			if (o != null) {
				Vertex v = (Vertex) o;
				if (i.equals(v.getUserDatum(Data.JDMPObject))) {
					removeVertex(v);
				}
			}
		}
	}

	public void moduleUpdated(ModuleListEvent e) {
		mGraphListenerHandler.handleAdd((Edge) null);
	}

}
