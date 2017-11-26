package com.szewczyk.decisiontree.data;

import com.szewczyk.decisiontree.data.xml.DataConverter;
import com.szewczyk.decisiontree.data.xml.DataSupplier;
import com.szewczyk.decisiontree.model.ExamplesData;

import java.nio.file.Path;

public class DefaultDataProvider implements DataProvider {
    private final DataSupplier dataSupplier;
    private final DataConverter dataConverter;

    public DefaultDataProvider(DataSupplier dataSupplier, DataConverter dataConverter) {
        this.dataSupplier = dataSupplier;
        this.dataConverter = dataConverter;
    }

    @Override
    public ExamplesData loadExamplesFromXmlFile(Path path) {
        return dataConverter.convertData(dataSupplier.supplyFrom(path));
    }
}