package model.key;

public class AsymmetricKey implements IKey<AsymmetricKeyHelper>{
    private AsymmetricKeyHelper key;

    public AsymmetricKey(AsymmetricKeyHelper key) {
        this.key = key;
    }

    @Override
    public AsymmetricKeyHelper getKey() {
        return key;
    }
}
