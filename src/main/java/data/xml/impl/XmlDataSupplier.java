package data.xml.impl;

import data.model.raw.Data;
import data.xml.DataSupplier;
import data.xml.XmlDeserializer;

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
