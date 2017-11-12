package com.learning.decisiontree.data.impl;

import com.learning.decisiontree.data.DataSupplier;
import com.learning.decisiontree.data.XmlDeserializer;
import com.learning.decisiontree.data.model.Data;

public class XmlDataSupplier implements DataSupplier {
    private final XmlDeserializer xmlDeserializer;

    public XmlDataSupplier(XmlDeserializer xmlDeserializer) {
        this.xmlDeserializer = xmlDeserializer;
    }


    @Override
    public Data supplyFrom(String path) {
        return xmlDeserializer
                .deserializeData(Data.class, path)
                .orElseThrow(IllegalStateException::new);
    }
}
