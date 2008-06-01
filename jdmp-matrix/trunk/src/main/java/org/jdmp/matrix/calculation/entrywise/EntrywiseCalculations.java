/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

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
