package data.model.raw;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class Data {
    private Attributes attributes;

    private Examples examples;

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Examples getExamples() {
        return examples;
    }

    public void setExamples(Examples examples) {
        this.examples = examples;
    }
}
