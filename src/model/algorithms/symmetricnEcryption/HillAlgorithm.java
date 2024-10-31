package model.algorithms.symmetricnEcryption;

public class HillAlgorithm implements ITest<int[][]> {
    private int[][] key;


    @Override
    public void loadKey(int[][]... inputKey) {
        key = inputKey[0];

    }

    public static void main(String[] args) {
        HillAlgorithm hillAlgorithm = new HillAlgorithm();
        hillAlgorithm.loadKey(new int[][]{{1, 2}});
    }

}
