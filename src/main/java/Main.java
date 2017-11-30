import com.szewczyk.decisiontree.data.DataProvider;
import com.szewczyk.decisiontree.data.DefaultDataProvider;
import com.szewczyk.decisiontree.data.xml.impl.DefaultDataConverter;
import com.szewczyk.decisiontree.data.xml.impl.JAXBXmlDeserializer;
import com.szewczyk.decisiontree.data.xml.impl.XmlDataSupplier;
import com.szewczyk.decisiontree.data.xml.utils.DefaultConverterUtils;
import com.szewczyk.decisiontree.logic.Attribute;
import com.szewczyk.decisiontree.logic.DefaultExamplesUtils;
import com.szewczyk.decisiontree.logic.ID3;
import com.szewczyk.decisiontree.logic.Tree;
import com.szewczyk.decisiontree.model.Examples;
import com.szewczyk.decisiontree.model.ExamplesData;
import com.szewczyk.decisiontree.utils.DecisionTreeBuilder;

import java.nio.file.Paths;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DefaultDataProvider(new XmlDataSupplier(new JAXBXmlDeserializer()), new DefaultDataConverter(new DefaultConverterUtils()));
        dataProvider.setExamplesProportion(5, 5);
        ExamplesData examplesData = dataProvider.loadExamplesFromXmlFile(Paths.get("test.xml"));
        Set<String> attributes = examplesData.getAttributes();
        Examples trainingExamples = examplesData.getTrainingExamples();

        Tree tree = DecisionTreeBuilder
                .initialize()
                .withAlgorithm(new ID3(new DefaultExamplesUtils(trainingExamples), trainingExamples))
                .withExamplesUtils(new DefaultExamplesUtils(trainingExamples))
                .withAttributes(attributes)
                .withExamples(trainingExamples).build();

        Attribute rootAttribute = tree.buildAndReturnRootAttribute();

        double accuracy = rootAttribute.verifyFor(examplesData.getValidationExamples());
        System.out.println("Tree: " + rootAttribute);

        rootAttribute.prune(0, tree.getAttributes(), rootAttribute, examplesData.getValidationExamples());
        System.out.println("Final accuracy: " + accuracy);
    }
}
