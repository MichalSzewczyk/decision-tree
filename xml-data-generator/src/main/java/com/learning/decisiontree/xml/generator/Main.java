package com.learning.decisiontree.xml.generator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        XmlDataGenerator xmlDataGenerator =
                new XmlDataGenerator(
                        "xml-data-generator\\src\\main\\resources\\classifications",
                        "xml-data-generator\\src\\main\\resources\\data");
        xmlDataGenerator
                .generateFile("src\\main\\resources\\datasets\\tic-tac-toe-data.xml");
    }
}
