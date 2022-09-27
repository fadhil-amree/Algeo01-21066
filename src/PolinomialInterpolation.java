package src;
import src.Matrix;
import src.SPL.*;
import java.util.*;

public class PolinomialInterpolation {
    public static Matrix setOfTitikToMatrix (float[][] setOfTitik){
        // Fungsi untuk mengembalikan hasil SPL yang terbentuk dari kumpulan titik
        // KAMUS LOKAL
        Matrix augmented;
        // ALGORITMA

        return augmented;
    }


    public static float[] getPolinomialCoef(Matrix matrix){
        // Fungsi untuk mengembalikan list koefisien dari polinomial interpolasi
        // Elemen koefisien terurut sesuai derajat dari x
        // [a0,a1,...an]

        // KAMUS LOKAL
        Matrix matrixres, matrixkoef;
        float[] koefisien = new float[matrix.getNRow()];
        float[][] coefMatrix = new float[matrix.getNRow()][matrix.getNCol()-1];
        float[][] resMatrix = new float[matrix.getNRow()][1];
        int i,j;
        // ALGORITMA
        // Membentuk matrixkoef
        for (i=0;i<=matrix.getNRow()-1;i++){
            for (j=0;j<=matrix.getNCol()-2;j++){
                coefMatrix[i][j] = matrix.getElmtContent(i, j);
            }
        }
        matrixkoef = new Matrix(coefMatrix,matrix.getNRow(),matrix.getNCol()-1);

        // Membentuk matrixres
        for (i=0;i<=matrix.getNRow()-1;i++){
            resMatrix[i][0] = matrix.getElmtContent(i, matrix.getNCol()-1);
        }
        matrixres = new Matrix(resMatrix,matrix.getNRow(),1);

        // Mencari nilai koefisien 
        koefisien = GaussJordan.splbyGaussJordan(matrixkoef, matrixres);
        return koefisien;
    }

    public static float estimateY(float[] koefisien, float X){
        // Fungsi mengembalikan taksiran suatu input x terhadap polinom interpolasi
        // KAMUS LOKAL
        float Y;
        // ALGORITMA
        Y=0;
        return Y;
    }

    public static void displayPolinom(float[] koefisien){
        // Prosedur untuk menampilkan polinomial interpolasi
        // I.S Koefisien terdefinisi
        // F.S Menampilkan Polinom Interpolasi dengan format:
        //      a0 + a1x + a2x^2 + ... +anx^n 
    }



    public static void getPolinomialInterpolation(){
        // Prosedur untuk menjalankan menu Polinomial Interpolation
        // I.S Sembarang
        // F.S Menampilkan Polinom Interpolasi dan taksiran nilai fungsi pada x yang diberikan
    
        // KAMUS LOKAL
        Scanner input = new Scanner(System.in);
        int n,i,j;
        float xi,yi, X, Y;
        float[] func;
        float[][] setOfTitik;
        Matrix augmented;

        // ALGORITMA
        System.out.print("Masukkan derajat Polinomial(n): ");
        n = input.nextInt();
        setOfTitik = new float [n+1][2]; //Alokasi memori
        //Input
        for (i=0;i<=n;i++){
            // Input nilai xi,yi
            System.out.printf("Masukkan x%d: ",i);
            xi = input.nextFloat();
            System.out.printf("Masukkan y%d: ",i);
            yi = input.nextFloat();

            // Menyimpan xi,yi ke dalam matriks titik
            setOfTitik[i][0] = xi;
            setOfTitik[i][1] = yi;
        }

        // input nilai yang akan ditaksir
        System.out.print("Masukkan X: ");
        X = input.nextFloat();

        // Membentuk Polinom Interpolasi
            // Membentuk Augmented Matrix
        augmented = setOfTitikToMatrix(setOfTitik);
            // Mencari koefisien dari polinomial Interpolasi
        func = getPolinomialCoef(augmented);

        // Menampilkan Polinomial Interpolasi
        displayPolinom(func);
        
        // Menampilkan taksiran output dari x saat menjadi input polinomial interpolasi
            // Mencari nilai taksiran
        Y = estimateY(func, X);
            //Menampilkan
        System.out.printf("P(%.04f) = %.04f\n",X,Y);
    }
}
