package com.szewczyk.decisiontree.data.xml.utils;

import java.util.List;

public interface ConverterUtils {
    <T> void splitDataOntoThreeSetsWithProportions(
            List<T> inputSet,
            List<T> firstResultSet,
            int firstProportion,
            List<T> secondResultSet,
            int secondProportion,
            List<T> thirdResultSet,
            int thirdProportion);
}