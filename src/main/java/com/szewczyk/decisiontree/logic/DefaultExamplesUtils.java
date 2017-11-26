package com.szewczyk.decisiontree.logic;

import com.szewczyk.decisiontree.model.Example;
import com.szewczyk.decisiontree.model.Examples;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DefaultExamplesUtils {
    private final Examples examples;

    public DefaultExamplesUtils(Examples examples) {
        this.examples = examples;
    }

    int countDecisions(String attribute, String decision) {
        int count = 0;

        for (Example e : examples.getTrainingExamples()) {
            if (e.getDecisionFor(attribute).equals(decision))
                count++;
        }

        return count;
    }

    /**
     * Returns a map from each attribute name to a set of all values used in the
     * examples for that attribute.
     */
    public Map<String, Set<String>> extractDecisions() {
        Map<String, Set<String>> decisions = new HashMap<String, Set<String>>();

        for (String attribute : extractAttributes()) {
            decisions.put(attribute, extractDecisions(attribute));
        }

        return decisions;
    }

    public int countNegative(String attribute, String decision,
                             Map<String, String> attributes) {
        return countClassifier(false, attribute, decision, attributes);
    }

    public int countPositive(String attribute, String decision,
                             Map<String, String> attributes) {
        return countClassifier(true, attribute, decision, attributes);
    }

    public int countNegative(Map<String, String> attributes) {
        return countClassifier(false, attributes);
    }

    public int countPositive(Map<String, String> attributes) {
        return countClassifier(true, attributes);
    }

    public int count(String attribute, String decision, Map<String, String> attributes) {
        attributes = new HashMap(attributes);
        attributes.put(attribute, decision);

        return count(attributes);
    }

    public int count(Map<String, String> attributes) {
        int count = 0;

        System.out.println("chosen attributes: " + attributes);
//        System.out.println("examples: "+examples);

        nextExample:
        for (Example e : examples.getTrainingExamples()) {
            for (Map.Entry<String, String> attribute : attributes.entrySet())
                if (!(e.getDecisionFor(attribute.getKey()).equals(attribute.getValue())))
                    continue nextExample;

            // All of the provided attributes match the example.
            count++;
        }

        return count;
    }

    public int countClassifier(boolean classifier, Map<String, String> attributes) {
        int count = 0;


        nextExample:
        for (Example e : examples.getTrainingExamples()) {
            for (Map.Entry<String, String> attribute : attributes.entrySet())
                if (!(e.getDecisionFor(attribute.getKey()).equals(attribute.getValue())))
                    continue nextExample;

            // All of the provided attributes match the example.
            // If the example matches the classifier, then include it in the count.
            if (e.getClassifier() == classifier)
                count++;
        }

        return count;
    }

    public int countClassifier(boolean classifier, String attribute,
                               String decision, Map<String, String> attributes) {
        attributes = new HashMap(attributes);
        attributes.put(attribute, decision);

        return countClassifier(classifier, attributes);
    }

    /**
     * Returns the number of examples.
     */
    public int count() {
        return examples.getTrainingExamples().size();
    }

    /**
     * Returns a set of attribute names used in the examples.
     */
    public Set<String> extractAttributes() {
        Set<String> attributes = new HashSet<String>();

        for (Example e : examples.getTrainingExamples()) {
            attributes.addAll(e.getAttributes());
        }

        return attributes;
    }

    private Set<String> extractDecisions(String attribute) {
        Set<String> decisions = new HashSet<String>();

        for (Example e : examples.getTrainingExamples()) {
            decisions.add(e.getDecisionFor(attribute));
        }

        return decisions;
    }
}
