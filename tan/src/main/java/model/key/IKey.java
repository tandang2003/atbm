package model.key;


import model.common.Exception;

import java.io.Serializable;

public interface IKey<T> extends Serializable {
    default T getKey() {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

//    void genKey();

//    void loadKey(T key);
}
