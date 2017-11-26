package com.szewczyk.decisiontree.data.xml;

import com.szewczyk.decisiontree.data.model.raw.Data;
import com.szewczyk.decisiontree.model.ExamplesData;

@FunctionalInterface
public interface DataConverter {
    ExamplesData convertData(Data data);
}