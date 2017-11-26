package com.szewczyk.decisiontree.logic; /**
 *
 */

import com.szewczyk.decisiontree.model.Examples;

import java.util.Map;
import java.util.Set;


public class ID3 implements Algorithm {
    private Examples examples;
    private final DefaultExamplesUtils examplesUtils;

    public ID3(DefaultExamplesUtils examplesUtils, Examples examples) {
        this.examplesUtils = examplesUtils;
        this.examples = examples;
    }

    public Attribute nextAttribute(Map<String, String> chosenAttributes, Set<String> usedAttributes) {
        double currentGain = 0.0, bestGain = 0.0;
        String bestAttribute = "";
        int positive = examplesUtils.countPositive(chosenAttributes);
        int negative = examplesUtils.countNegative(chosenAttributes);
        System.out.println("negative, positive: "+negative+" "+positive);

        if (positive == 0)
            return new Attribute(false);
        else if (negative == 0)
            return new Attribute(true);


                remainingAttributes(usedAttributes).size();


        for (String attribute : remainingAttributes(usedAttributes)) {
            currentGain = informationGain(attribute, chosenAttributes);

            if (currentGain > bestGain) {
                bestAttribute = attribute;
                bestGain = currentGain;
            }
        }
        System.out.println("best gain "+bestGain);
        if (bestGain == 0.0) {
            boolean classifier = examplesUtils.countPositive(chosenAttributes) > 0;

            return new Attribute(classifier);
        } else {

            return new Attribute(bestAttribute);
        }
    }

    private Set<String> remainingAttributes(Set<String> usedAttributes) {
        Set<String> result = examplesUtils.extractAttributes();
        result.removeAll(usedAttributes);
        return result;
    }

    private double entropy(Map<String, String> specifiedAttributes) {
        double totalExamples = examplesUtils.count();
        double positiveExamples = examplesUtils.countPositive(specifiedAttributes);
        double negativeExamples = examplesUtils.countNegative(specifiedAttributes);

        return -nlog2(positiveExamples / totalExamples) -
                nlog2(negativeExamples / totalExamples);
    }

    private double entropy(String attribute, String decision, Map<String, String> specifiedAttributes) {
        double totalExamples = examplesUtils.count(attribute, decision, specifiedAttributes);
        double positiveExamples = examplesUtils.countPositive(attribute, decision, specifiedAttributes);
        double negativeExamples = examplesUtils.countNegative(attribute, decision, specifiedAttributes);

        return -nlog2(positiveExamples / totalExamples) -
                nlog2(negativeExamples / totalExamples);
    }

    private double informationGain(String attribute, Map<String, String> specifiedAttributes) {
        double sum = entropy(specifiedAttributes);

        double examplesCount = examplesUtils.count(specifiedAttributes);

        if (examplesCount == 0)
            return sum;

        Map<String, Set<String>> decisions = examplesUtils.extractDecisions();

        for (String decision : decisions.get(attribute)) {
            double entropyPart = entropy(attribute, decision, specifiedAttributes);
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
