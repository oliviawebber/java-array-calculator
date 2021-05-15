public class Client {
    public static void main(String[] args) {

        Matrix m1 = Matrix.generateMatrix(1000);
        Matrix m2 = Matrix.generateMatrix(1000);
        m1.slowMultiply(m2);
    }
}
