package data.xml.impl;

import data.model.raw.Attributes;
import data.model.raw.Data;
import data.xml.DataConverter;
import model.Example;
import model.Examples;
import model.ExamplesData;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultDataConverter implements DataConverter {
    @Override
    public ExamplesData convertData(Data data) {
        Attributes attributes = data.getAttributes();
        Set<String> convertedAttributes = new HashSet<>(Arrays.asList(attributes.getAttribute()));

        List<Example> convertedExamples =
                Stream.of(data.getExamples().getExample()).map(example ->
                        getExampleFrom(data.getAttributes().getAttribute(), example.getValue(), example.getClassifier())).collect(Collectors.toList());
        Examples examples = new Examples(convertedAttributes, convertedExamples);
        return new ExamplesData(examples, convertedAttributes);
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
