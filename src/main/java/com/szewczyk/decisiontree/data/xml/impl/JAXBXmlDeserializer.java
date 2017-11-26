package com.szewczyk.decisiontree.data.xml.impl;


import com.szewczyk.decisiontree.data.xml.XmlDeserializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.util.Optional;

public class JAXBXmlDeserializer implements XmlDeserializer {
    @Override
    public <T> Optional<T> deserializeData(Class<T> target, Path path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(target);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return Optional.of(target.cast(jaxbUnmarshaller.unmarshal(path.toFile())));
        } catch (JAXBException e) {
            return Optional.empty();
        }

    }
}
