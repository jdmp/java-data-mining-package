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

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.WordShapeClassifier;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.DefaultMapMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

import java.io.*;
import java.util.*;

public class StanfordTagger extends AbstractTagger {
    private static final long serialVersionUID = -2655534427624643477L;

    private StanfordCoreNLP stanfordCoreNLP;

    private CRFClassifier<CoreLabel> crf;

    public StanfordTagger() throws Exception {
        //   Properties props = new Properties();
        //   props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        //   stanfordCoreNLP = new StanfordCoreNLP(props);
    }

    public StanfordTagger(File file) throws Exception {
        crf = new CRFClassifier<CoreLabel>(new SeqClassifierFlags());
        crf.loadClassifierNoExceptions(file);
    }

    public StanfordTagger(String filename) throws Exception {
        this(new File(filename));
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


    public void train(ListMatrix<ListMatrix<MapMatrix<String, String>>> listMatrix) throws Exception {
        List<List<CoreLabel>> sentenceList = new ArrayList<List<CoreLabel>>();
        for (ListMatrix<MapMatrix<String, String>> innerList : listMatrix) {
            List<CoreLabel> tokenList = new ArrayList<CoreLabel>();
            sentenceList.add(tokenList);
            for (MapMatrix<String, String> mapMatrix : innerList) {
                CoreLabel l = new CoreLabel();
                l.set(CoreAnnotations.TextAnnotation.class, mapMatrix.getAsString("Token"));
                l.set(CoreAnnotations.AnswerAnnotation.class, mapMatrix.getAsString("Class"));
                tokenList.add(l);
            }
        }

        SeqClassifierFlags flags = new SeqClassifierFlags();
        flags.maxLeft = 3;
        flags.useClassFeature = true;
        flags.useWord = true;
        flags.maxNGramLeng = 6;
        flags.usePrev = true;
        flags.useNext = true;
        flags.useDisjunctive = true;
        flags.useSequences = true;
        flags.usePrevSequences = true;
        flags.useTypeSeqs = true;
        flags.useTypeSeqs2 = true;
        flags.useTypeySequences = true;
        flags.wordShape = WordShapeClassifier.WORDSHAPECHRIS2;

        flags.useNGrams = true;
        crf = new CRFClassifier<CoreLabel>(flags);
        crf.train(sentenceList, null);
    }


    public CoreLabelListMatrix tagCRF(ListMatrix<MapMatrix<String, String>> text) throws Exception {
        List<CoreLabel> list = new ArrayList<CoreLabel>();
        for (MapMatrix<String, String> m : text) {
            CoreLabel l = new CoreLabel();
            l.set(CoreAnnotations.TextAnnotation.class, m.getAsString("Token"));
            list.add(l);
        }
        List<CoreLabel> result = crf.classify(list);
        return new CoreLabelListMatrix(result);
    }

    public CoreLabelListListMatrix tagCRF(String text) throws Exception {
        List<List<CoreLabel>> result = crf.classify(text);
        return new CoreLabelListListMatrix(result);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = "";

        ListMatrix<ListMatrix<String>> originalText = StanfordTokenizer.getInstance().tokenize(text);
        ListMatrix<ListMatrix<MapMatrix<String, String>>> trainingText = new DefaultListMatrix<ListMatrix<MapMatrix<String, String>>>();

        StanfordTagger tagger = null;

        for (ListMatrix<String> sentence : originalText) {

            ListMatrix<MapMatrix<String, String>> tokenSequence = new DefaultListMatrix<MapMatrix<String, String>>();

            for (String word : sentence) {
                MapMatrix<String, String> token = new DefaultMapMatrix<String, String>();
                token.put("Token", word);
                tokenSequence.add(token);
            }

            if (tagger != null) {
                tokenSequence = tagger.tagCRF(tokenSequence);
            }


            trainingText.add(tokenSequence);
            for (MapMatrix<String, String> token : tokenSequence) {
                token.put("Class", "O");
            }

            for (int i = 0; i < tokenSequence.size(); i++) {
                MapMatrix<String, String> token = tokenSequence.get(i);
                String word = token.getAsString("Token");
                String c = token.getAsString("Class");
                String a = token.getAsString("Answer");
                System.out.println(i + ": " + word + " (" + c + ") " + a);
            }

            System.out.print("Enter number: ");

            String input = null;
            while (!"".equals(input)) {
                input = br.readLine();
                if (!"".equals(input)) {
                    int number = Integer.parseInt(input);
                    if (number < sentence.size()) {
                        System.out.print("Enter tag: ");
                        input = br.readLine();
                        MapMatrix<String, String> token = tokenSequence.get(number);
                        token.put("Class", input);
                        for (int i = 0; i < tokenSequence.size(); i++) {
                            token = tokenSequence.get(i);
                            String word = token.getAsString("Token");
                            String c = token.getAsString("Class");
                            String a = token.getAsString("Answer");
                            System.out.println(i + ": " + word + " (" + c + ") " + a);
                        }
                    }
                }
            }


            tagger = new StanfordTagger();
            tagger.train(trainingText);
            CoreLabelListMatrix tmpTokenSequence = tagger.tagCRF(tokenSequence);

            for (int i = 0; i < tmpTokenSequence.size(); i++) {
                MapMatrix<String, String> token = tmpTokenSequence.get(i);
                String word = token.getAsString("Token");
                String c = token.getAsString("Class");
                String a = token.getAsString("Answer");
                System.out.println(i + ": " + word + " (" + c + ") " + a);
            }

            System.out.println("---------------------------------------");

        }
    }


}
