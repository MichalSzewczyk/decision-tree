package com.szewczyk.decisiontree.model;

import java.util.List;
import java.util.Set;

public class Examples{
    public Set<String> getAttributes() {
        return attributes;
    }

    private final Set<String> attributes;
    private final List<Example> examples;

    public Examples(Set<String> attributes, List<Example> examples) {
        this.attributes = attributes;
        this.examples = examples;
    }

    public List<Example> getExamples() {
        return examples;
    }
}
