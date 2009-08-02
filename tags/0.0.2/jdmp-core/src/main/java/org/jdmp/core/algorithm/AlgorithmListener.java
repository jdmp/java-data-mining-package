package org.jdmp.core.algorithm;

import java.util.EventListener;

public interface AlgorithmListener extends EventListener {

	public void algorithmCountIncreased(AlgorithmEvent e);

}
