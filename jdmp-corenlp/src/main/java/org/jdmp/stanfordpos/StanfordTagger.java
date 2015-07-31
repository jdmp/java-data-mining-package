/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class StanfordTagger extends AbstractTagger {
    private static final long serialVersionUID = -2655534427624643477L;

    private final StanfordCoreNLP stanfordCoreNLP;

    private static StanfordTagger instance = null;

    public StanfordTagger() throws Exception {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        stanfordCoreNLP = new StanfordCoreNLP(props);
    }


    public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(String text) throws Exception {
        ListMatrix<ListMatrix<MapMatrix<String, Object>>> sentenceList = new DefaultListMatrix<ListMatrix<MapMatrix<String, Object>>>();

        Annotation document = new Annotation(text);

        stanfordCoreNLP.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            ListMatrix<MapMatrix<String, Object>> tokenList = new DefaultListMatrix<MapMatrix<String, Object>>();
            sentenceList.add(tokenList);

            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                MapMatrix<String, Object> tokenMap = new DefaultMapMatrix<String, Object>();
                tokenList.add(tokenMap);

                String word = token.get(CoreAnnotations.TextAnnotation.class);
                tokenMap.put("Token", word);

                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                tokenMap.put("POS", pos);

                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                tokenMap.put("NE", ne);
            }

            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);

            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
        }

        Map<Integer, CorefChain> graph = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);

        return sentenceList;
    }

    public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(Matrix input) throws Exception {
        // TODO
        return null;
    }

    public static StanfordTagger getInstance() {
        if (instance == null) {
            try {
                instance = new StanfordTagger();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

}
