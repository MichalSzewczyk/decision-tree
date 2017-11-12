package com.learning.decisiontree.xml.generator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class XmlDataGenerator {
    private String classificationsFilePath;
    private String dataFilePath;


    public XmlDataGenerator(String classificationsFilePath, String dataFilePath) {
        this.classificationsFilePath = classificationsFilePath;
        this.dataFilePath = dataFilePath;
    }

    void generateFile(String resultFilePath) throws IOException {
        List<String> classesList = getListOfLines(classificationsFilePath);
        List<String> dataList = getListOfLines(dataFilePath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("<data>\n" +
                        "    <name>Tic-Tac-Toe Endgame</name>\n");
        dataList.stream().map(str -> List.of(str.split(","))).forEach(list -> {
            stringBuilder.append("    <entry>\n");
            for (int i = 0; i < list.size(); i++) {
                stringBuilder
                        .append("" + "        <attribute>\n" + "            <name>")
                        .append(classesList.get(i)).append("</name>\n")
                        .append("            <value>")
                        .append(list.get(i))
                        .append("</value>\n")
                        .append("        </attribute>\n");

            }
            stringBuilder.append("    </entry>\n");
        });

        stringBuilder.append(
                "</data>");

        Path path = Paths.get(resultFilePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(stringBuilder.toString());
        }
    }

    private static List<String> getListOfLines(String source) throws IOException {
        return Files.lines(Paths.get(source)).collect(Collectors.toList());
    }
}
