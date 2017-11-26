package com.szewczyk.decisiontree.model;

import java.util.Set;

public class ExamplesData {
    private final Examples trainingExamples;
    private final Examples validationExamples;
    private final Examples testExamples;
    private final Set<String> attributes;


    public ExamplesData(Examples trainingExamples, Examples validationExamples, Examples testExamples, Set<String> attributes) {
        this.trainingExamples = trainingExamples;
        this.validationExamples = validationExamples;
        this.testExamples = testExamples;
        this.attributes = attributes;
    }

    public Examples getTrainingExamples() {
        return trainingExamples;
    }

    public Examples getValidationExamples() {
        return validationExamples;
    }

    public Examples getTestExamples() {
        return testExamples;
    }

    public Set<String> getAttributes() {
        return attributes;
    }
}
