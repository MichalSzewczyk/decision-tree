package data.xml;

import data.model.raw.Data;
import model.ExamplesData;

@FunctionalInterface
public interface DataConverter {
    ExamplesData convertData(Data data);
}