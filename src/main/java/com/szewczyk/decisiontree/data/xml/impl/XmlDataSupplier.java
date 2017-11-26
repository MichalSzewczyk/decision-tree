package com.szewczyk.decisiontree.data.xml.impl;

import com.szewczyk.decisiontree.data.model.raw.Data;
import com.szewczyk.decisiontree.data.xml.DataSupplier;
import com.szewczyk.decisiontree.data.xml.XmlDeserializer;

import java.nio.file.Path;

public class XmlDataSupplier implements DataSupplier {
    private final XmlDeserializer xmlDeserializer;

    public XmlDataSupplier(XmlDeserializer xmlDeserializer) {
        this.xmlDeserializer = xmlDeserializer;
    }

    @Override
    public Data supplyFrom(Path path) {
        return xmlDeserializer
                .deserializeData(Data.class, path)
                .orElseThrow(IllegalStateException::new);
    }
}
