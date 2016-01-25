/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

package org.jdmp.stanfordpos;

import edu.stanford.nlp.ling.CoreLabel;
import org.ujmp.core.listmatrix.AbstractListMatrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

import java.util.List;


public class CoreLabelListMatrix extends AbstractListMatrix<MapMatrix<String, String>> {
    private static final long serialVersionUID = 5210805150750331628L;

    private final List<CoreLabelMapMatrix> list = new DefaultListMatrix<CoreLabelMapMatrix>();


    public CoreLabelListMatrix(List<CoreLabel> data) {
        for (CoreLabel l : data) {
            list.add(new CoreLabelMapMatrix(l));
        }
    }

    @Override
    public MapMatrix<String, String> get(int i) {
        return list.get(i);
    }

    @Override
    protected boolean addToList(MapMatrix<String, String> stringStringMapMatrix) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected void addToList(int i, MapMatrix<String, String> stringStringMapMatrix) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected MapMatrix<String, String> removeFromList(int i) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected boolean removeFromList(Object o) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected MapMatrix<String, String> setToList(int i, MapMatrix<String, String> stringStringMapMatrix) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected void clearList() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public int size() {
        return list.size();
    }
}
