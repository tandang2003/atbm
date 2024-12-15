package model.key;


import model.common.Exception;

import java.io.*;

public interface IKey<T> extends Serializable {
    default T getKey() {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    void saveToFile(File outputStream, File priDestination) throws IOException;

    void loadFromFile(File inputStream, boolean isPublicKey) throws IOException;
}
