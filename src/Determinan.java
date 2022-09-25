package src; 

public class Determinan {
    public static float determinan(Matrix matriks) {
        float det = matriks.content[0][0] * matriks.content[1][1] - matriks.content[0][1] * matriks.content[1][0];
        System.out.println("Determinan matriks adalah " + det);
        return 1;
    }
}