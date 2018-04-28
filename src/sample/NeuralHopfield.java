package sample;

public class NeuralHopfield {
    private int[][] matrix;

    public NeuralHopfield(int size) {
        matrix = new int[size][size];
    }

    public void learn(int[] inputSet) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                }
                else {
                    matrix[i][j] += inputSet[i] * inputSet[j];
                }
            }
        }
    }

    public int[] execute(int[] inputSet)
    {
        int[] result = new int[inputSet.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i] += matrix[i][j] * inputSet[j];
            }
        }

        for (int i = 0; i < result.length; i++) {
            result[i] = sigma(result[i]);
        }

        return result;
    }

    private int sigma(int value)
    {
        if (value >= 0) {
            return 1;
        }

        return -1;
    }

}
