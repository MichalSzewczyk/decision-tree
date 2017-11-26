package com.szewczyk.decisiontree.data.xml.utils;

import java.util.List;

public class DefaultConverterUtils implements ConverterUtils {

    @Override
    public <T> void splitDataOntoThreeSetsWithProportions(
            List<T> inputSet,
            List<T> firstResultSet,
            int firstProportion,
            List<T> secondResultSet,
            int secondProportion,
            List<T> thirdResultSet,
            int thirdProportion) {
        int parts = inputSet.size() / (firstProportion + secondProportion + thirdProportion);
        int numberOfFirst = firstProportion * parts;
        int numberOfSecond = secondProportion * parts;

        for (int i = 0; i < inputSet.size(); i++) {
            if (i < numberOfFirst) {
                firstResultSet.add(inputSet.get(i));
            } else if (i < numberOfFirst + numberOfSecond) {
                secondResultSet.add(inputSet.get(i));
            } else {
                thirdResultSet.add(inputSet.get(i));
            }
        }
    }
}