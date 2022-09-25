package src; 

public class Determinan {
    public void determinan(float[][] matriks) {
        float det = matriks[0][0] * matriks[1][1] - matriks[0][1] * matriks[1][0];
        System.out.println("Determinan matriks adalah " + det);
    }
}