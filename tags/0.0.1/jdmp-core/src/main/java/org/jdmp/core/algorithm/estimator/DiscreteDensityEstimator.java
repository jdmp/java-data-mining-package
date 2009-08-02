/* ----------------------------------------------------------------------------
 * File:    DiscreteDensityEstimator.java
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

public class DiscreteDensityEstimator implements DensityEstimator {

  private double[] counts;

  private double sumOfCounts;



  public DiscreteDensityEstimator(int nmbVals, boolean laplace) {

    counts = new double[nmbVals];
    sumOfCounts = 0;
    if (laplace) {
      for (int i = 0; i < nmbVals; i++) {
        counts[i] = 1;
      }
      sumOfCounts = (double) nmbVals;
    }
  }

    public DiscreteDensityEstimator(int nmbVals, double correction) {

    counts = new double[nmbVals];
    sumOfCounts = 0;
      for (int i = 0; i < nmbVals; i++) {
        counts[i] = correction;
      }
      sumOfCounts = (double) nmbVals;
    }




  public void addValue(double val, double weight) {
    counts[(int) val] += weight;
    sumOfCounts += weight;

  }




  public double getProbability(double val) {
    if (sumOfCounts == 0) {
      return 0;
    }
    return (double) counts[(int) val] / sumOfCounts;
  }

}
