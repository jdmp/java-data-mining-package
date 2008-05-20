package org.jdmp.core.algorithm;

import java.util.EventListener;

public interface AlgorithmListListener extends EventListener {

	public void algorithmAdded(AlgorithmListEvent e);

	public void algorithmRemoved(AlgorithmListEvent e);

	public void algorithmUpdated(AlgorithmListEvent e);

}
