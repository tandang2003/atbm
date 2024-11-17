package model.key;

public class HashKey implements IKey<HashKeyHelper> {
    private HashKeyHelper key;

    public HashKey() {

    }

    @Override
    public HashKeyHelper getKey() {
        return key;
    }
}
