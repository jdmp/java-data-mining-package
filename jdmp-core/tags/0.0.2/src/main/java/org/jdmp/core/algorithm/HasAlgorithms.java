package org.jdmp.core.algorithm;

import java.util.List;

public interface HasAlgorithms {

	public void addAlgorithm(Algorithm a);

	public void addAlgorithmListListener(AlgorithmListListener l);

	public Algorithm getAlgorithm(int pos);

	public int getAlgorithmCount();

	public List<Algorithm> getAlgorithmList();

	public int getIndexOfAlgorithm(Algorithm algorithm);

	public void removeAlgorithm(Algorithm algorithm);

	public void removeAlgorithmListListener(AlgorithmListListener l);

	public void fireAlgorithmAdded(AlgorithmListEvent e);

	public void fireAlgorithmRemoved(AlgorithmListEvent e);

	public void fireAlgorithmUpdated(AlgorithmListEvent e);
}
