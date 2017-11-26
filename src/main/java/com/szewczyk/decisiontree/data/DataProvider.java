package com.szewczyk.decisiontree.data;


import com.szewczyk.decisiontree.model.ExamplesData;

import java.nio.file.Path;

public interface DataProvider {
    ExamplesData loadExamplesFromXmlFile(Path path);
    void setExamplesProportion(int trainingExamples, int validationExamples);
}