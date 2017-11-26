package com.szewczyk.decisiontree.data;

import com.szewczyk.decisiontree.data.xml.DataConverter;
import com.szewczyk.decisiontree.data.xml.DataSupplier;
import com.szewczyk.decisiontree.model.ExamplesData;

import java.nio.file.Path;

public class DefaultDataProvider implements DataProvider {
    private final DataSupplier dataSupplier;
    private final DataConverter dataConverter;
    private int trainingExamples;
    private int validationExamples;

    public DefaultDataProvider(DataSupplier dataSupplier, DataConverter dataConverter) {
        this.dataSupplier = dataSupplier;
        this.dataConverter = dataConverter;
        this.trainingExamples = 5;
        this.validationExamples = 1;
    }

    @Override
    public ExamplesData loadExamplesFromXmlFile(Path path) {
        return dataConverter.convertData(trainingExamples, validationExamples, dataSupplier.supplyFrom(path));
    }

    @Override
    public void setExamplesProportion(int trainingExamples, int validationExamples) {
        this.trainingExamples = trainingExamples;
        this.validationExamples = validationExamples;
    }
}