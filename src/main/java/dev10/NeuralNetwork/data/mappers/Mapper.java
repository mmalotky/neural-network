package dev10.NeuralNetwork.data.mappers;

import dev10.NeuralNetwork.data.FileData;

/**
 * Convert objects to dataFiles and vice-versa
 * @param <T> Object type for conversions
 */
public interface Mapper<T> {
    /**
     * Convert an object to data file format
     * @param object Object to be converted
     * @return DataFile object containing object data
     */
    FileData objectToData(T object);

    /**
     * Convert data file info into the object type
     * @param data DataFile object containing object data
     * @return Converted Object
     */
    T dataToObject(FileData data);
}
