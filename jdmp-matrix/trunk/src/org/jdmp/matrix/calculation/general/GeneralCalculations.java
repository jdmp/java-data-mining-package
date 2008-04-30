package org.jdmp.matrix.calculation.general;

import org.jdmp.matrix.calculation.general.misc.MiscGeneralCalculations;
import org.jdmp.matrix.calculation.general.missingvalues.MissingValueCalculations;
import org.jdmp.matrix.calculation.general.solving.SolvingCalculations;
import org.jdmp.matrix.calculation.general.statistical.StatisticalCalculations;

public interface GeneralCalculations extends StatisticalCalculations, SolvingCalculations, MissingValueCalculations,
		MiscGeneralCalculations {

}
