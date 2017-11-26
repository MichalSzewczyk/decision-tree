package com.szewczyk.decisiontree.data.xml.impl;

import com.szewczyk.decisiontree.data.model.raw.Attributes;
import com.szewczyk.decisiontree.data.model.raw.Data;
import com.szewczyk.decisiontree.data.xml.DataConverter;
import com.szewczyk.decisiontree.data.xml.utils.ConverterUtils;
import com.szewczyk.decisiontree.model.Example;
import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.model.ExamplesData;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultDataConverter implements DataConverter {
    private final static int TEST_EXAMPLES_PROPORTION = 1;

    private final ConverterUtils converterUtils;

    public DefaultDataConverter(ConverterUtils converterUtils) {
        this.converterUtils = converterUtils;
    }

    @Override
    public ExamplesData convertData(int trainingExamplesProportion, int validationExamplesProportion, Data data) {
        Attributes attributes = data.getAttributes();
        Set<String> convertedAttributes = new HashSet<>(Arrays.asList(attributes.getAttribute()));
        List<Example> convertedExamples = extractExamplesFromData(data);
        Examples examples = new Examples(convertedAttributes, convertedExamples);

        List<Example> trainingExamplesList = new LinkedList<>();
        List<Example> validationExamplesList = new LinkedList<>();
        List<Example> testExamplesList = new LinkedList<>();

        converterUtils.splitDataOntoThreeSetsWithProportions(
                convertedExamples,
                trainingExamplesList, trainingExamplesProportion,
                validationExamplesList, validationExamplesProportion,
                testExamplesList, TEST_EXAMPLES_PROPORTION);

        logAlgorithmInput(System.out::println, examples, testExamplesList.size(), trainingExamplesList.size(), validationExamplesList.size());
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

    private List<Example> extractExamplesFromData(Data data) {
        return Stream.of(data.getExamples().getExample()).map(example ->
                getExampleFrom(data.getAttributes().getAttribute(), example.getValue(), example.getClassifier())).collect(Collectors.toList());
    }

    void logAlgorithmInput(Consumer<String> logger, Examples examples, int numberOfTestExamples, int numberOfTrainingExamples, int numberOfValidationExamples) {
        logger.accept("Running with following input sets sizes:");
        logger.accept("All examples: " + examples.getExamples().size());
        logger.accept("Test examples: " + numberOfTestExamples);
        logger.accept("Training examples: " + numberOfTrainingExamples);
        logger.accept("Validation examples: " + numberOfValidationExamples);
    }
}
