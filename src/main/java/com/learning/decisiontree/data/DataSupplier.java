package com.learning.decisiontree.data;

import com.learning.decisiontree.data.model.Data;

@FunctionalInterface
public interface DataSupplier {
    Data supplyFrom(String path);
}
