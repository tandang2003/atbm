package model.key;

public class SignKey implements IKey {
    private SignKeyHelper key;

    public SignKey(SignKeyHelper key) {
        this.key = key;
    }

    @Override
    public SignKeyHelper getKey() {
        return key;
    }
}
