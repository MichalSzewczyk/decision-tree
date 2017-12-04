package com.szewczyk.decisiontree.logic;

import com.szewczyk.decisiontree.model.Examples;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class ID3 implements Algorithm {
    private Examples examples;
    private final DefaultExamplesUtils examplesUtils;

    public ID3(DefaultExamplesUtils examplesUtils, Examples examples) {
        this.examplesUtils = examplesUtils;
        this.examples = examples;
    }

    Optional<String> allMatchesOneClassification(Set<String> classifications, Map<String, String> chosenAttributes) {
        int i = 0;
        String result = null;
        for (String classification : classifications) {
            if (examplesUtils.countPositive(classification, chosenAttributes) > 0) {
                i++;
                result = classification;
            }
            if (i == 2) {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(result);
    }

    Optional<String> getWithHighestMatch(Set<String> classifications, Map<String, String> chosenAttributes) {
        int max = Integer.MIN_VALUE;
        String result = null;
        for (String classification : classifications) {
            int actual = examplesUtils.countPositive(classification, chosenAttributes);
            if (actual > max) {
                max = actual;
                result = classification;
            }
        }
        return Optional.ofNullable(result);
    }

    public Attribute nextAttribute(Set<String> classifications, Map<String, String> chosenAttributes, Set<String> usedAttributes) {
        double currentGain;
        double bestGain = 0.0;
        String bestAttribute = "";
        Optional<String> result = allMatchesOneClassification(classifications, chosenAttributes);

        if (result.isPresent()) {
            return Attribute.getLeafAttribute(result.get());
        }

        remainingAttributes(usedAttributes).size();


        for (String attribute : remainingAttributes(usedAttributes)) {
            currentGain = informationGain(classifications, attribute, chosenAttributes);

            if (currentGain > bestGain) {
                bestAttribute = attribute;
                bestGain = currentGain;
            }
        }
        System.out.println("best gain " + bestGain);
        if (bestGain == 0.0) {
            String classifier = getWithHighestMatch(classifications, chosenAttributes).orElseThrow(IllegalStateException::new);

            return Attribute.getLeafAttribute(classifier);
        } else {

            return Attribute.getNonLeafAttribute(bestAttribute);
        }
    }

    private Set<String> remainingAttributes(Set<String> usedAttributes) {
        Set<String> result = examplesUtils.extractAttributes();
        result.removeAll(usedAttributes);
        return result;
    }

    private double entropy(Set<String> classifiers, Map<String, String> specifiedAttributes) {
        double totalExamples = examplesUtils.count();
        double entropy = 0;
        for (String classifier : classifiers) {
            double examplesMatchingClassifier = examplesUtils.countPositive(classifier, specifiedAttributes);
            entropy += -nlog2(examplesMatchingClassifier / totalExamples);
        }

        return entropy;
    }

    private double entropy(Set<String> classifiers, String attribute, String decision, Map<String, String> specifiedAttributes) {
        double totalExamples = examplesUtils.count(attribute, decision, specifiedAttributes);

        double entropy = 0;
        for (String classifier : classifiers) {
            double examplesMatchingClassifier = examplesUtils.countPositive(classifier, attribute, decision, specifiedAttributes);
            entropy += -nlog2(examplesMatchingClassifier / totalExamples);
        }

        return entropy;
    }

    private double informationGain(Set<String> classifiers, String attribute, Map<String, String> specifiedAttributes) {
        double sum = entropy(classifiers, specifiedAttributes);

        double examplesCount = examplesUtils.count(specifiedAttributes);

        if (examplesCount == 0)
            return sum;

        Map<String, Set<String>> decisions = examplesUtils.extractDecisions();

        for (String decision : decisions.get(attribute)) {
            double entropyPart = entropy(classifiers, attribute, decision, specifiedAttributes);
            double decisionCount = examplesUtils.countDecisions(attribute, decision);

            sum += -(decisionCount / examplesCount) * entropyPart;
        }

        return sum;
    }

    private double nlog2(double value) {
        if (value == 0)
            return 0;

        return value * Math.log(value) / Math.log(2);
    }
}
