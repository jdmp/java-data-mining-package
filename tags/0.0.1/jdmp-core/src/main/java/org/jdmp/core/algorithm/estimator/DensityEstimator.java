/* ----------------------------------------------------------------------------
 * File:    DensityEstimator.java
 * Project: jdmp-core
 * Package: org.jdmp.core.algorithm.estimator
 * ID:      $Id$
 *
 * ----------------------------------------------------------------------------
 * 
 * << short description of class >>
 *
 * ----------------------------------------------------------------------------
 *
 * Author: Andreas Naegele
 * Date:   07.05.2008
 * --------------------------------------------------------------------------*/

package org.jdmp.core.algorithm.estimator;

public interface DensityEstimator {

  void addValue(double val, double weight);



  double getProbability(double val);

}
