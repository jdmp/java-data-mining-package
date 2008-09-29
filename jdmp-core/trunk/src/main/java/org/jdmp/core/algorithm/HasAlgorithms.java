package org.jdmp.core.algorithm;

import java.util.List;

public interface HasAlgorithms {

	public void addAlgorithm(Algorithm a);

	public Algorithm getAlgorithm(int pos);

	public int getAlgorithmCount();

	public List<Algorithm> getAlgorithmList();

	public int getIndexOfAlgorithm(Algorithm algorithm);

}
