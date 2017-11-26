package model;

import java.util.Set;

public class ExamplesData {
    private final Examples examples;
    private final Set<String> attributes;


    public ExamplesData(Examples examples, Set<String> attributes) {
        this.examples = examples;
        this.attributes = attributes;
    }

    public Examples getExamples() {
        return examples;
    }

    public Set<String> getAttributes() {
        return attributes;
    }
}
