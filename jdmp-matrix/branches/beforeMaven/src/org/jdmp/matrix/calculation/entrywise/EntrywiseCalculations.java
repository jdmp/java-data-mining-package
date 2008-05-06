package org.jdmp.matrix.calculation.entrywise;

import org.jdmp.matrix.calculation.entrywise.basic.BasicEntrywiseCalculations;
import org.jdmp.matrix.calculation.entrywise.creators.CreatorCalculations;
import org.jdmp.matrix.calculation.entrywise.hyperbolic.HyperbolicCalculations;
import org.jdmp.matrix.calculation.entrywise.misc.MiscEntrywiseCalculations;
import org.jdmp.matrix.calculation.entrywise.replace.ReplaceCalculations;
import org.jdmp.matrix.calculation.entrywise.rounding.RoundingCalculations;
import org.jdmp.matrix.calculation.entrywise.trigonometric.TrigonometricCalculations;

public interface EntrywiseCalculations extends BasicEntrywiseCalculations, RoundingCalculations,
		HyperbolicCalculations, TrigonometricCalculations, CreatorCalculations, ReplaceCalculations,
		MiscEntrywiseCalculations {

}
