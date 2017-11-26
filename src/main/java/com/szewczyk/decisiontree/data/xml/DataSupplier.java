package com.szewczyk.decisiontree.data.xml;


import com.szewczyk.decisiontree.data.model.raw.Data;

import java.nio.file.Path;

@FunctionalInterface
public interface DataSupplier {
    Data supplyFrom(Path path);
}
