package com.szewczyk.decisiontree.logic;

import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.utils.BadDecisionException;

import java.util.Map;

public class Attribute {
    private boolean leaf;

    private String attributeName;
    private Decisions decisions;
    private boolean classification;

    public Attribute(boolean classification) {
        this.leaf = true;
        this.classification = classification;
        this.decisions = new Decisions();
        this.attributeName = null;
    }

    public Attribute(String name) {
        this.leaf = false;
        this.attributeName = name;
        this.decisions = new Decisions();
    }

    public String getName() {
        return attributeName;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setClassification(boolean classification) {
        assert (leaf);

        this.classification = classification;
    }

    public boolean getClassification() {
        assert (leaf);

        return classification;
    }

    public boolean apply(Map<String, String> data) throws BadDecisionException {
        if (isLeaf())
            return getClassification();

        Attribute nextAttribute = decisions.apply(data.get(attributeName));
        return nextAttribute.apply(data);
    }

    public double verifyFor(Examples verificationExamples) {
        long numberOfSuccessful = verificationExamples.getExamples()
                .stream()
                .filter(example -> {
                    try {
                        boolean result = apply(example.getAttributesWithDecisions());
                        System.out.println(example.getClassifier() + " = " + result);
                        return example.getClassifier() == result;
                    } catch (BadDecisionException e) {
                        return false;
                    }
                }).count();

        System.out.println("Successful " + numberOfSuccessful + " for all: " + verificationExamples.getExamples().size());
        return (double) numberOfSuccessful / verificationExamples.getExamples().size();
    }

    public void addDecision(String decision, Attribute attribute) {
        assert (!leaf);

        decisions.put(decision, attribute);
    }

    public String toString() {
        StringBuffer b = new StringBuffer();

        for (Map.Entry<String, Attribute> e : decisions.getMap().entrySet()) {
            b.append(getName());
            b.append(" -> ");
            if (e.getValue().isLeaf())
                b.append(e.getValue().getClassification());
            else
                b.append(e.getValue().getName());
            b.append(" [label=\"");
            b.append(e.getKey());
            b.append("\"]\n");

            b.append(e.getValue().toString());
        }

        return b.toString();
    }

    public Map<String, Attribute> getDecisions() {
        return decisions.getMap();
    }
}
