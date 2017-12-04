package com.szewczyk.decisiontree.logic;

import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.utils.BadDecisionException;

import java.util.*;
import java.util.stream.Collectors;

public class Attribute {
    private boolean leaf;

    private String attributeName;
    private Decisions decisions;
    private String classification;

    private Attribute(boolean leaf, String attributeName, Decisions decisions, String classification) {
        this.leaf = leaf;
        this.attributeName = attributeName;
        this.decisions = decisions;
        this.classification = classification;
    }

    public static Attribute getLeafAttribute(String classification) {
        return new Attribute(true, null, new Decisions(), classification);
    }

    public static Attribute getNonLeafAttribute(String name) {
        return new Attribute(false, name, new Decisions(), null);
    }

    public String getName() {
        return attributeName;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setClassification(String classification) {
        assert (leaf);

        this.classification = classification;
    }

    public String getClassification() {
        assert (leaf);

        return classification;
    }

    String getClassificationFor(Attribute attribute) {
        Map<String, Long> attributesWithOccurrences = attribute.decisions.getMap().values().stream().collect(
                Collectors.groupingBy(Attribute::getClassification, Collectors.counting()));
        return attributesWithOccurrences.entrySet().stream().max(Comparator.comparingInt(e -> e.getValue().intValue())).orElseThrow(IllegalStateException::new).getKey();
    }

    List<Attribute> getAttributesWithLeafChildren(Set<Attribute> attributes) {
        return attributes.stream().filter(attribute -> attribute.decisions.getMap().values().stream().allMatch(value -> value.leaf)).collect(Collectors.toList());
    }

    public void prune(double epsilon, Set<Attribute> attributes, Attribute root, Examples validationData) {
        List<Attribute> attributesWithLeafChildren = attributes.stream().filter(attribute -> attribute.decisions.getMap().values().stream().allMatch(value -> value.leaf)).collect(Collectors.toList());

        while (
                attributesWithLeafChildren
                        .stream()
                        .map(attribute -> tryPruning(attribute, validationData, root, epsilon))
                        .filter(value -> value).count() > 0
                ) {

            attributesWithLeafChildren = getAttributesWithLeafChildren(attributes);
        }
    }

    private boolean tryPruning(Attribute attribute, Examples validationData, Attribute root, double epsilon) {
        double before = root.verifyFor(validationData);
        attribute.leaf = true;
        attribute.classification = getClassificationFor(attribute);
        double after = root.verifyFor(validationData);
        boolean pruned = true;
        if (before - epsilon <= after) {
            rollbackPruning(attribute);
            pruned = false;
        }
        return pruned;
    }

    public void prune(Set<Attribute> attributes, Attribute root, Examples validationData) {
        prune(0, attributes, root, validationData);
    }

    private void rollbackPruning(Attribute attribute) {
        attribute.leaf = false;
    }

    public String apply(Map<String, String> data) throws BadDecisionException {
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
                        String result = apply(example.getAttributesWithDecisions());
                        return example.getClassifier().equals(result);
                    } catch (BadDecisionException e) {
                        return false;
                    }
                }).count();

        System.out.println("Verification results: \n\tSuccessful " + numberOfSuccessful + " for all: " + verificationExamples.getExamples().size());
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
            if (e.getValue().isLeaf()) {
                b.append("classification: ").append(e.getValue().getClassification());
            }else
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
