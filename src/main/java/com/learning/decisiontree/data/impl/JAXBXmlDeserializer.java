package com.learning.decisiontree.data.impl;

import com.learning.decisiontree.data.XmlDeserializer;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.Optional;

@Slf4j
public class JAXBXmlDeserializer implements XmlDeserializer {
    @Override
    public <T> Optional<T> deserializeData(Class<T> target, String path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(target);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return Optional.of(target.cast(jaxbUnmarshaller.unmarshal(new File(path))));
        } catch (JAXBException e) {
            log.error("Failed to deserialize.", e);
            return Optional.empty();
        }

    }
}
