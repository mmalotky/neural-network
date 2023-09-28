package dev10.NeuralNetwork.data;

import dev10.NeuralNetwork.data.mappers.Mapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class FileRepository<T> {
    String pathFormat;
    Mapper<T> mapper;

    public List<String> getSavedIds() throws DataAccessException {
        List<String> saveList = new ArrayList<>();

        String filePath = pathFormat.substring(0, pathFormat.length() - 7);
        File saves = new File(filePath);
        if(saves.exists()) {
            if(saves.listFiles() == null) {
                return saveList;
            }
            else {
                saveList = Arrays.stream(Objects.requireNonNull(saves.listFiles()))
                        .map(File::getName)
                        .map(s -> s.substring(0, s.length() - 4))
                        .toList();
            }
        }
        else {
            throw new DataAccessException("File not found at: " + filePath, new FileNotFoundException());
        }
        return saveList;
    }

    public void save(T object, String id) throws DataAccessException {
        String filePath = String.format(pathFormat, id);
        FileData data = mapper.objectToData(object);
        try(PrintWriter writer = new PrintWriter(filePath)) {
            for(String line : data.getLines()) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            File file = new File(filePath);
            try {
                if(!file.createNewFile()) {
                    throw new IOException();
                }
            } catch (IOException ex) {
                throw new DataAccessException("Error creating new file at: " + filePath, ex);
            }
            save(object, id);
        }
    }

    public T load(String id) throws DataAccessException {
        String filePath = String.format(pathFormat, id);
        T object;
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            FileData data = new FileData();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                data.addLine(line);
            }
            object = mapper.dataToObject(data);
        } catch (Exception e) {
            throw new DataAccessException("Error accessing file at: " + filePath, e);
        }
        return object;
    }

    public boolean delete(String id) {
        String filePath = String.format(pathFormat, id);
        File networkFile = new File(filePath);
        return networkFile.delete();
    }

    void buildFileTree() throws DataAccessException {
        String[] tree = pathFormat.split("/");
        String url = "";
        for(String branch : tree) {
            if(branch.contains("%s")) break;
            url+=branch;
            File folder = new File(url);
            if(!folder.exists() && !folder.mkdir()) throw new DataAccessException(String.format("Could not make file at %s", url));
            url+="/";
        }
    }
}
