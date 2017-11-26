package data;


import model.ExamplesData;

import java.nio.file.Path;

public interface DataProvider {
    ExamplesData loadExamplesFromXmlFile(Path path);
}