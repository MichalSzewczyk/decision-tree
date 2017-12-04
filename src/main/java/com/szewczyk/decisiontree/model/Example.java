package com.szewczyk.decisiontree.model;

import java.util.Map;
import java.util.Set;

public class Example {
    private final Map<String, String> attributesWithDecisions;
    private final String classifier;

    public Example(Map<String, String> attributesWithDecisions, String classifier) {
        this.attributesWithDecisions = attributesWithDecisions;
        this.classifier = classifier;
    }

    public String getDecisionFor(String attribute) {
        return attributesWithDecisions.get(attribute);
    }

    public Set<String> getAttributes() {
        return attributesWithDecisions.keySet();
    }

    public String getClassifier() {
        return classifier;
    }

    public Map<String, String> getAttributesWithDecisions() {
        return attributesWithDecisions;
    }
}
