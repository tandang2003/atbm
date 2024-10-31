package model.key;

public class MatrixKey implements IKey<int[][]> {
    private int[][] key;
    public MatrixKey(int[][] key) {
        this.key = key;
    }
    public MatrixKey() {
    }

    @Override
    public int[][] getKey() {
        return key;
    }

    @Override
    public void genKey() {

    }

}
