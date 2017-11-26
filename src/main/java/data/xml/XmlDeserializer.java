package data.xml;

import java.nio.file.Path;
import java.util.Optional;

public interface XmlDeserializer {
    <T> Optional<T> deserializeData(Class<T> target, Path path);
}
