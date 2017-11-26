package com.szewczyk.decisiontree.specification;

import com.szewczyk.decisiontree.data.xml.utils.ConverterUtils;
import com.szewczyk.decisiontree.data.xml.utils.DefaultConverterUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ApplicationSpecification {
    @Test
    public void shouldProperlySplitDataIntoThreeSubsetsBasedOnPassedProportions() {
        //GIVEN
        int anyInputDataNumber = 100;
        ConverterUtils dataConverter = new DefaultConverterUtils();
        List<Integer> anyInputData = generateRandomData(anyInputDataNumber);
        int anyFirstProportion = 10;
        List<Integer> firstResult = new LinkedList<>();
        int anySecondProportion = 10;
        List<Integer> secondResult = new LinkedList<>();
        int anyThirdProportion = 80;
        List<Integer> thirdResult = new LinkedList<>();

        //WHEN
        dataConverter.splitDataOntoThreeSetsWithProportions(
                anyInputData,
                firstResult, anyFirstProportion,
                secondResult, anySecondProportion ,
                thirdResult, anyThirdProportion);

        //THEN
        assertTrue(firstResult.size() == 10);
    }

    private List<Integer> generateRandomData(int amount) {
        Random rand = new Random();
        return rand.ints().boxed().limit(amount).collect(Collectors.toList());
    }
}