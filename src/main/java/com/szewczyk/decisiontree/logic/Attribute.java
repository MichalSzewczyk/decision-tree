package com.szewczyk.decisiontree.logic;

import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.utils.BadDecisionException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    boolean getClassificationFor(Attribute attribute) {
        long positiveClassifiers = attribute.decisions.getMap().values().stream().filter(Attribute::getClassification).count();
        long negativeClassifiers = attribute.decisions.getMap().values().size() - positiveClassifiers;
        return positiveClassifiers > negativeClassifiers;
    }

    public void prune(double epsilon, Set<Attribute> attributes, Attribute root, Examples validationData) {
        System.out.println("Performing pruning.");
        double before = root.verifyFor(validationData);
        System.out.println("Attributes for prunning: "+attributes);
        List<Attribute> attributesWithLeafChildren = attributes.stream().filter(attribute -> attribute.decisions.getMap().values().stream().allMatch(value -> value.leaf)).collect(Collectors.toList());

        attributesWithLeafChildren.forEach(attribute -> {
            attribute.leaf = true;
            attribute.classification = getClassificationFor(attribute);
        });

        double after = root.verifyFor(validationData);
        System.out.println("Accuracy before pruning: " + before + " and after: " + after);
        if (before - epsilon > after) {
            System.out.println("Rolling back pruning");
            rollbackPruning(attributesWithLeafChildren);
        }
    }

    public void prune(Set<Attribute> attributes, Attribute root, Examples validationData) {
        prune(0, attributes, root, validationData);
    }

    private void rollbackPruning(List<Attribute> attributesWithLeafChildren) {
        attributesWithLeafChildren.forEach(attribute -> attribute.leaf = false);
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
