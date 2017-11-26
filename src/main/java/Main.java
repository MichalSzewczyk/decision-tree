import com.szewczyk.decisiontree.data.DataProvider;
import com.szewczyk.decisiontree.data.DefaultDataProvider;
import com.szewczyk.decisiontree.data.xml.impl.DefaultDataConverter;
import com.szewczyk.decisiontree.data.xml.impl.JAXBXmlDeserializer;
import com.szewczyk.decisiontree.data.xml.impl.XmlDataSupplier;
import com.szewczyk.decisiontree.utils.BadDecisionException;
import com.szewczyk.decisiontree.logic.DefaultExamplesUtils;
import com.szewczyk.decisiontree.logic.ID3;
import com.szewczyk.decisiontree.logic.Tree;
import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.model.ExamplesData;
import com.szewczyk.decisiontree.utils.DecisionTreeBuilder;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DefaultDataProvider(new XmlDataSupplier(new JAXBXmlDeserializer()), new DefaultDataConverter());
        ExamplesData examplesData = dataProvider.loadExamplesFromXmlFile(Paths.get("test.xml"));
        Set<String> attributes = examplesData.getAttributes();
        Examples examples = examplesData.getExamples();

        Tree tree = DecisionTreeBuilder
                .initialize()
                .withAlgorithm(new ID3(new DefaultExamplesUtils(examples), examples))
                .withExamplesUtils(new DefaultExamplesUtils(examples))
                .withAttributes(attributes)
                .withExamples(examples).build();

        Map<String, String> example_case = new HashMap<>();
        example_case.put("Outlook", "Overcast");
        example_case.put("Temperature", "Hot");
        example_case.put("Humidity", "High");
        example_case.put("Wind", "Strong");

        try {
            boolean classifier = tree.buildAndReturnRootAttribute().apply(example_case);
            System.out.println("Classification of passed data is: "+classifier);
        } catch (BadDecisionException e) {
            System.out.println("Unable to get decision for data.");
        }
    }
}
