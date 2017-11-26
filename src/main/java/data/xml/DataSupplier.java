package data.xml;


import data.model.raw.Data;

import java.nio.file.Path;

@FunctionalInterface
public interface DataSupplier {
    Data supplyFrom(Path path);
}
