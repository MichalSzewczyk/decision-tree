package com.szewczyk.decisiontree.data.xml.impl;

import com.szewczyk.decisiontree.data.model.raw.Attributes;
import com.szewczyk.decisiontree.data.model.raw.Data;
import com.szewczyk.decisiontree.data.xml.DataConverter;
import com.szewczyk.decisiontree.model.Example;
import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.model.ExamplesData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultDataConverter implements DataConverter {
    private final static int TEST_EXAMPLES_PROPORTION = 1;

    @Override
    public ExamplesData convertData(int trainingExamplesProportion, int validationExamplesProportion, Data data) {
        Attributes attributes = data.getAttributes();
        Set<String> convertedAttributes = new HashSet<>(Arrays.asList(attributes.getAttribute()));

        List<Example> convertedExamples =
                Stream.of(data.getExamples().getExample()).map(example ->
                        getExampleFrom(data.getAttributes().getAttribute(), example.getValue(), example.getClassifier())).collect(Collectors.toList());
        Examples examples = new Examples(convertedAttributes, convertedExamples);
        int parts = convertedExamples.size() / (trainingExamplesProportion + validationExamplesProportion + TEST_EXAMPLES_PROPORTION);
        int numberOfTestExamples = TEST_EXAMPLES_PROPORTION * parts;
        int numberOfTrainingExamples = parts * trainingExamplesProportion;
        int numberOfValidationExamples = examples.getExamples().size() - numberOfTestExamples - numberOfTrainingExamples;
        System.out.println("Running with following input sets sizes:");
        System.out.println("All examples: " + examples.getExamples().size());
        System.out.println("Test examples: " + numberOfTestExamples);
        System.out.println("Training examples: " + numberOfTrainingExamples);
        System.out.println("Validation examples: " + numberOfValidationExamples);
        List<Example> trainingExamplesList = new LinkedList<>();
        List<Example> validationExamplesList = new LinkedList<>();
        List<Example> testExamplesList = new LinkedList<>();
        for (int i = 0; i < examples.getExamples().size(); i++) {
            if (i < numberOfTrainingExamples) {
                trainingExamplesList.add(examples.getExamples().get(i));
            } else if (i < numberOfTrainingExamples + numberOfValidationExamples) {
                validationExamplesList.add(examples.getExamples().get(i));
            } else {
                testExamplesList.add(examples.getExamples().get(i));
            }
        }
        Examples trainingExamples = new Examples(convertedAttributes, trainingExamplesList);
        Examples validationExamples = new Examples(convertedAttributes, validationExamplesList);
        Examples testExamples = new Examples(convertedAttributes, testExamplesList);
        return new ExamplesData(trainingExamples, validationExamples, testExamples, convertedAttributes);
    }

    private Example getExampleFrom(String[] attributes, String[] values, String classifier) {
        if (attributes.length != values.length) {
            throw new IllegalArgumentException();
        }
        Map<String, String> attributesToValues = new HashMap<>();
        for (int i = 0; i < attributes.length; i++) {
            attributesToValues.put(attributes[i], values[i]);
        }
        return new Example(attributesToValues, Boolean.valueOf(classifier));
    }
}
