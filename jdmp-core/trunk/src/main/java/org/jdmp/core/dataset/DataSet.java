package org.jdmp.core.dataset;

import java.util.List;

import org.jdmp.core.CoreObject;
import org.jdmp.core.sample.HasSampleList;
import org.jdmp.core.variable.HasVariableMap;
import org.ujmp.core.interfaces.HasMatrixList;

public interface DataSet extends CoreObject, HasVariableMap, HasSampleList, HasMatrixList {

	public List<DataSet> splitByCount(boolean shuffle, int... count);

	public List<DataSet> splitForCV(int numberOfCVSets, int idOfCVSet, long randomSeed);

	public List<DataSet> splitByPercent(boolean shuffle, double... percent);

}
