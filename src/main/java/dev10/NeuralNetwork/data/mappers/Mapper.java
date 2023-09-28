package dev10.NeuralNetwork.data.mappers;

import dev10.NeuralNetwork.data.FileData;

public interface Mapper<T> {
    FileData objectToData(T object);
    T dataToObject(FileData data);
}
