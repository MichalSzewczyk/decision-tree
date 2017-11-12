package com.learning.decisiontree.data.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "data")
public class Data {
    private String name;

    private Entry[] entry;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entry[] getEntry() {
        return entry;
    }

    public void setEntry(Entry[] entry) {
        this.entry = entry;
    }
}