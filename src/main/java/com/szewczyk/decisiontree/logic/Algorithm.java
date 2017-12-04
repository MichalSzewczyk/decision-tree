package com.szewczyk.decisiontree.logic;
import java.util.Map;
import java.util.Set;


public interface Algorithm {
  Attribute nextAttribute(Set<String> classifications, Map<String, String> chosenAttributes, Set<String> usedAttributes);
}
