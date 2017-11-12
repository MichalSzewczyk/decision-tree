package com.learning.decisiontree.application;

import com.learning.decisiontree.data.DataSupplier;
import com.learning.decisiontree.data.impl.JAXBXmlDeserializer;
import com.learning.decisiontree.data.impl.XmlDataSupplier;

public class CompositionRoot {
    private static final String DATA_FILE_PATH = "src\\\\main\\\\resources\\\\datasets\\\\tic-tac-toe-data.xml";
    private final DataSupplier dataSupplier;
    public CompositionRoot(){
        dataSupplier = new XmlDataSupplier(new JAXBXmlDeserializer());
    }

    public void start(){
        Data inputData = dataSupplier.supplyFrom(DATA_FILE_PATH);

    }
}
