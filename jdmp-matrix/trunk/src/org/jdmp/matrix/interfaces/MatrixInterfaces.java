package org.jdmp.matrix.interfaces;

import java.io.Serializable;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.calculation.CanPerformCalculations;

/**
 * This interface provides a list of all the interfaces that have to be
 * implemented by a Matrix.
 * 
 * @see Matrix
 * @author Holger Arndt
 * 
 */
public interface MatrixInterfaces extends Serializable, CoordinateFunctions, MatrixEntryGettersAndSetters,
		BasicMatrixProperties, CanPerformCalculations, CanBeReshaped, DistanceMeasures, Comparable<Matrix>, Cloneable,
		Clearable, HasAnnotation, HasLabel, HasGUIObject {

}
