package model.key;


import model.common.Exception;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public interface IKey<T> extends Serializable {
    default T getKey() {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

    void saveToFile(DataOutputStream outputStream) throws IOException;

    void loadFromFile(DataInputStream inputStream) throws IOException;
}
