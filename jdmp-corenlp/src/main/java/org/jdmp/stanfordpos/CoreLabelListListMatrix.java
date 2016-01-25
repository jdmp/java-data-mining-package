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
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

import java.util.List;

public class CoreLabelListListMatrix extends AbstractListMatrix<ListMatrix<MapMatrix<String, String>>> {
    private static final long serialVersionUID = 4851748235738540331L;

    private final List<List<CoreLabel>> data;

    public CoreLabelListListMatrix(List<List<CoreLabel>> data) {
        this.data = data;
    }

    @Override
    public ListMatrix<MapMatrix<String, String>> get(int i) {
        return new CoreLabelListMatrix(data.get(i));
    }

    @Override
    protected boolean addToList(ListMatrix<MapMatrix<String, String>> mapMatrixes) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected void addToList(int i, ListMatrix<MapMatrix<String, String>> mapMatrixes) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected ListMatrix<MapMatrix<String, String>> removeFromList(int i) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected boolean removeFromList(Object o) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected ListMatrix<MapMatrix<String, String>> setToList(int i, ListMatrix<MapMatrix<String, String>> mapMatrixes) {
        throw new RuntimeException("not implemented");
    }

    @Override
    protected void clearList() {
        throw new RuntimeException("not implemented");
    }

    @Override
    public int size() {
        return data.size();
    }
}
