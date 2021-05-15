import java.util.Random;

public class Matrix {
    int[][] matrix;
    static Random randomNumberGenerator = new Random();

    private Matrix(int size) {matrix = new int[size][size];}

    public static Matrix generateMatrix(int size) {
        Matrix m = new Matrix(size);
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                m.matrix[i][j] = randomNumberGenerator.nextInt();
            }
        }
        return m;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this.matrix.length);
        for(int i = 0; i < this.matrix.length; i++) {
            for(int j = 0; j < this.matrix.length; j++) {
                result.matrix[j][i] = this.matrix[i][j];
            }
        }
        return result;
    }

    public Matrix slowMultiply(Matrix m) {
        int size = this.matrix.length;
        Matrix result = new Matrix(size);
        Matrix transpose = this.transpose();
        for(int m1Column = 0; m1Column < size; m1Column++) {
            for(int m2Column = 0; m2Column < size; m2Column++) {
                int total = 0;
                for(int row = 0; row < size; row++) {
                    total += transpose.matrix[row][m1Column] * m.matrix[row][m2Column];
                }
                result.matrix[m1Column][m2Column] = total;
            }
        }
        return result;
    }

    public Matrix multiply(Matrix m) {
        int size = this.matrix.length;
        Matrix result = new Matrix(size);
        Matrix transpose = m.transpose();
        for(int m1Row = 0; m1Row < size; m1Row++) {
            for(int m2Row = 0; m2Row < size; m2Row++) {
                int total = 0;
                for(int col = 0; col < size; col++) {
                    total += this.matrix[m1Row][col] * transpose.matrix[m2Row][col];
                }
                result.matrix[m1Row][m2Row] = total;
            }
        }
        return result;
    }




    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.matrix.length; i++) {
            for(int j = 0; j < this.matrix.length; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
