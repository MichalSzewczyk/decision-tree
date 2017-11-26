package model;

import java.util.List;
import java.util.Set;

public class Examples{
    public Set<String> getAttributes() {
        return attributes;
    }

    private final Set<String> attributes;
    private final List<Example> trainingExamples;

    public Examples(Set<String> attributes, List<Example> trainingExamples) {
        this.attributes = attributes;
        this.trainingExamples = trainingExamples;
    }

    public List<Example> getTrainingExamples() {
        return trainingExamples;
    }
}
