package org.jdmp.gui.util;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmListEvent;
import org.jdmp.core.algorithm.AlgorithmListListener;
import org.jdmp.core.util.CoreObject;
import org.jdmp.core.util.interfaces.HasAlgorithmsAndVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableListEvent;
import org.jdmp.core.variable.VariableListListener;
import org.jdmp.gui.util.JungGraphPanel.Data;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;

public class TopologyGraphWrapper extends DirectedSparseGraph implements VariableListListener,
		AlgorithmListListener {

	public TopologyGraphWrapper(HasAlgorithmsAndVariables iTopology) {

		Vertex v = null;

		if (iTopology instanceof Algorithm) {
			Algorithm a = (Algorithm) iTopology;
			v = new DirectedSparseVertex();
			v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
			v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
			v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
			addVertex(v);
		}

		for (Algorithm a : iTopology.getAlgorithmList()) {
			v = new DirectedSparseVertex();
			v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
			v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
			v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
			addVertex(v);
		}

		for (Variable va : iTopology.getVariableList()) {
			v = new DirectedSparseVertex();
			v.setUserDatum(Data.Label, va.getLabel(), UserData.SHARED);
			v.setUserDatum(Data.JDMPObject, va, UserData.SHARED);
			v.setUserDatum(Data.Type, "Variable", UserData.SHARED);
			addVertex(v);
		}

		for (Object o1 : getVertices()) {
			for (Object o2 : getVertices()) {
				Vertex v1 = (Vertex) o1;
				Vertex v2 = (Vertex) o2;
				CoreObject u1 = (CoreObject) v1.getUserDatum(Data.JDMPObject);
				CoreObject u2 = (CoreObject) v2.getUserDatum(Data.JDMPObject);
				if (u1 instanceof Algorithm && u2 instanceof Variable) {
					Algorithm a = (Algorithm) u1;
					Variable va = (Variable) u2;
					int index = a.getIndexOfVariable(va);
					if (index >= 0) {
						Edge e = null;
						switch (a.getEdgeDirectionForVariable(index)) {
						case Algorithm.INCOMING:
							e = new DirectedSparseEdge(v2, v1);
							break;
						case Algorithm.OUTGOING:
							e = new DirectedSparseEdge(v1, v2);
							break;
						case Algorithm.BIDIRECTIONAL:
							e = new DirectedSparseEdge(v1, v2);
							Edge e2 = new DirectedSparseEdge(v2, v1);
							addEdge(e2);
							break;
						}
						if (e != null) {
							e.setUserDatum(Data.Label, a.getEdgeLabelForVariable(index),
									UserData.SHARED);
							addEdge(e);
						}
					}
				}
				if (u1 instanceof Algorithm && u2 instanceof Algorithm) {
					Algorithm a = (Algorithm) u1;
					Algorithm a2 = (Algorithm) u2;
					int index = a.getIndexOfAlgorithm(a2);
					if (index >= 0) {
						Edge e = new DirectedSparseEdge(v1, v2);
						e.setUserDatum(Data.Label, a.getEdgeLabelForAlgorithm(index),
								UserData.SHARED);
						addEdge(e);
					}
				}
			}
		}

		iTopology.addVariableListListener(this);
		iTopology.addAlgorithmListListener(this);
	}

	public void variableAdded(VariableListEvent e) {
		Variable var = (Variable) e.getData();
		Vertex v = new DirectedSparseVertex();
		v.setUserDatum(Data.Label, var.getLabel(), UserData.SHARED);
		v.setUserDatum(Data.JDMPObject, var, UserData.SHARED);
		v.setUserDatum(Data.Type, "Variable", UserData.SHARED);
		addVertex(v);
	}

	public void variableRemoved(VariableListEvent e) {

	}

	public void variableUpdated(VariableListEvent e) {
		mGraphListenerHandler.handleAdd((Edge) null);
	}

	public void algorithmAdded(AlgorithmListEvent e) {
		Algorithm a = (Algorithm) e.getData();
		Vertex v = new DirectedSparseVertex();
		v.setUserDatum(Data.Label, a.getLabel(), UserData.SHARED);
		v.setUserDatum(Data.JDMPObject, a, UserData.SHARED);
		v.setUserDatum(Data.Type, "Algorithm", UserData.SHARED);
		addVertex(v);
	}

	public void algorithmRemoved(AlgorithmListEvent e) {

	}

	public void algorithmUpdated(AlgorithmListEvent e) {
		mGraphListenerHandler.handleAdd((Edge) null);
	}

}
