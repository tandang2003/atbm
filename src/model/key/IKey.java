package model.key;

public interface IKey<T> {
    T getKey();
    void genKey();

//    void loadKey(T key);
}
