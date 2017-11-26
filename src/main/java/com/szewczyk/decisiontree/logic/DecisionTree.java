package com.szewczyk.decisiontree.logic; /**
 *
 */

import com.szewczyk.decisiontree.model.Examples;

import java.util.*;

public class DecisionTree implements Tree {
    private Examples examples;
    private Map<String, Set<String>> decisions;
    private boolean decisionsSpecified;
    private Attribute rootAttribute;
    private final Set<Attribute> attributes;
    private DefaultExamplesUtils examplesUtils;

    private Algorithm algorithm;

    public DecisionTree(Algorithm algorithm, Examples examples, DefaultExamplesUtils examplesUtils) {
        this.algorithm = algorithm;
        this.examplesUtils = examplesUtils;
        this.examples = examples;
        this.decisions = new HashMap<>();
        this.decisionsSpecified = false;
        this.attributes = new HashSet<>();
    }

    private Attribute compileWalk(Attribute current, Map<String, String> chosenAttributes, Set<String> usedAttributes) {
        if (current.isLeaf())
            return current;

        String attributeName = current.getName();

        usedAttributes.add(attributeName);

        attributes.add(current);
        for (String decisionName : decisions.get(attributeName)) {
            chosenAttributes.put(attributeName, decisionName);

            current.addDecision(decisionName, compileWalk(algorithm.nextAttribute(chosenAttributes, usedAttributes), chosenAttributes, usedAttributes));
        }

        chosenAttributes.remove(attributeName);

        return current;
    }

    public void compile() {

        Map<String, String> chosenAttributes = new HashMap<>();
        Set<String> usedAttributes = new HashSet<>();
        if (!decisionsSpecified)
            decisions = examplesUtils.extractDecisions();
        System.out.println("creating tree for: " + chosenAttributes + " " + usedAttributes);

        rootAttribute = compileWalk(algorithm.nextAttribute(chosenAttributes, usedAttributes), chosenAttributes, usedAttributes);
    }

    public String toString() {
        compile();

        if (rootAttribute != null)
            return rootAttribute.toString();
        else
            return "";
    }

    public Attribute getRoot() {
        return rootAttribute;
    }

    @Override
    public Attribute buildAndReturnRootAttribute() {
        compile();
        return getRoot();
    }

    @Override
    public Set<Attribute> getAttributes() {
        return attributes;
    }
}
