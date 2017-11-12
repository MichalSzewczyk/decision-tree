package com.learning.decisiontree.data;

import java.util.Optional;

public interface XmlDeserializer {
    <T> Optional<T> deserializeData(Class<T> target, String path);
}
