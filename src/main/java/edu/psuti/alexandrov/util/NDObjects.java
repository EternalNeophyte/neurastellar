package edu.psuti.alexandrov.util;

import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;

public final class NDObjects {

    private NDObjects() {
        throw new AssertionError("Not supposed to be instantiated");
    }

    public static NDList shapeToNDList(Shape shape, NDManager manager) {
        var ndArray = manager.create(shape, DataType.INT32);
        return new NDList(ndArray);
    }
}
