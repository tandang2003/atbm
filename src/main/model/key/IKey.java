package main.model.key;

import main.model.common.Exception;

public interface IKey<T> {
    default T getKey() {
        throw new RuntimeException(Exception.UNSUPPORTED_METHOD);
    }

//    void genKey();

//    void loadKey(T key);
}
