package com.szewczyk.decisiontree.logic;

import java.util.Set;

public interface Tree {
    Attribute buildAndReturnRootAttribute();
    Set<Attribute> getAttributes();
    String toString();
}
