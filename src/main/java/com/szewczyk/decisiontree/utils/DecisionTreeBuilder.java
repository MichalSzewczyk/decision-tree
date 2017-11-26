package utils;

import logic.Algorithm;
import logic.DecisionTree;
import logic.DefaultExamplesUtils;
import logic.Tree;
import model.Examples;

import java.util.Set;

import static java.util.Objects.isNull;

public class DecisionTreeBuilder {
    private Algorithm algorithm;
    private Set<String> attributes;
    private Examples examples;
    private DefaultExamplesUtils examplesUtils;

    private DecisionTreeBuilder() {
    }

    public static DecisionTreeBuilder initialize() {
        return new DecisionTreeBuilder();
    }

    public Tree build() {

        if (isNull(algorithm) || isNull(attributes) || isNull(examples) || isNull(examplesUtils)) {
            throw new IllegalStateException("Tree builder not initialized properly.");
        }
        return new DecisionTree(algorithm, examples, examplesUtils);
    }

    public DecisionTreeBuilder withAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public DecisionTreeBuilder withAttributes(Set<String> attributes) {
        this.attributes = attributes;
        return this;
    }

    public DecisionTreeBuilder withExamples(Examples examples) {
        this.examples = examples;
        return this;
    }

    public DecisionTreeBuilder withExamplesUtils(DefaultExamplesUtils examplesUtils) {
        this.examplesUtils = examplesUtils;
        return this;
    }
}
