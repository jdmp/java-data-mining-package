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

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import org.ujmp.core.mapmatrix.AbstractMapMatrix;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CoreLabelMapMatrix extends AbstractMapMatrix<String, String> {
    private static final long serialVersionUID = 959256729449686013L;

    private final Map<String, String> map = new TreeMap<String, String>();

    public CoreLabelMapMatrix(CoreLabel coreLabel) {
        for (Class c : coreLabel.keySet()) {
            if (CoreAnnotations.ValueAnnotation.class == c) {
                map.put("Value", coreLabel.getString(CoreAnnotations.ValueAnnotation.class));
            } else if (CoreAnnotations.TextAnnotation.class == c) {
                map.put("Token", coreLabel.getString(CoreAnnotations.TextAnnotation.class));
            } else if (CoreAnnotations.OriginalTextAnnotation.class == c) {
                map.put("OriginalText", coreLabel.getString(CoreAnnotations.OriginalTextAnnotation.class));
            } else if (CoreAnnotations.CharacterOffsetBeginAnnotation.class == c) {
                map.put("CharacterOffsetBegin", String.valueOf(coreLabel.get(CoreAnnotations.CharacterOffsetBeginAnnotation.class)));
            } else if (CoreAnnotations.CharacterOffsetEndAnnotation.class == c) {
                map.put("CharacterOffsetEnd", String.valueOf(coreLabel.get(CoreAnnotations.CharacterOffsetEndAnnotation.class)));
            } else if (CoreAnnotations.PositionAnnotation.class == c) {
                map.put("CharacterOffsetEnd", coreLabel.getString(CoreAnnotations.PositionAnnotation.class));
            } else if (CoreAnnotations.BeforeAnnotation.class == c) {
                map.put("Before", coreLabel.getString(CoreAnnotations.BeforeAnnotation.class));
            } else if (CoreAnnotations.ShapeAnnotation.class == c) {
                map.put("Shape", coreLabel.getString(CoreAnnotations.ShapeAnnotation.class));
            } else if (CoreAnnotations.GoldAnswerAnnotation.class == c) {
                map.put("GoldAnswer", coreLabel.getString(CoreAnnotations.GoldAnswerAnnotation.class));
            } else if (CoreAnnotations.AnswerAnnotation.class == c) {
                map.put("Answer", coreLabel.getString(CoreAnnotations.AnswerAnnotation.class));
            } else if (CoreAnnotations.AfterAnnotation.class == c) {
                map.put("After", coreLabel.getString(CoreAnnotations.AfterAnnotation.class));
            } else {
                throw new RuntimeException("unknown key class: " + c);
            }
        }
    }

    @Override
    protected void clearMap() {
        map.clear();
    }

    @Override
    protected String removeFromMap(Object o) {
        return map.remove(o);
    }

    @Override
    protected String putIntoMap(String key, String value) {
        return map.put(key, value);
    }

    public int size() {
        return map.size();
    }

    public String get(Object key) {
        return map.get(key);
    }

    public Set<String> keySet() {
        return map.keySet();
    }
}
