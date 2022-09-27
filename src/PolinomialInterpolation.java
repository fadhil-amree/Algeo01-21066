package src;
import src.Matrix;
import src.SPL.*;
import java.util.*;
import java.lang.Math;

public class PolinomialInterpolation {
    public static Matrix setOfTitikToMatrix (float[][] setOfTitik,int nRow){
        // Fungsi untuk mengembalikan hasil SPL yang terbentuk dari kumpulan titik
        // KAMUS LOKAL
        Matrix augmented;
        float[][] augMatrix = new float[nRow][nRow+1]; 
        int i,j;
        double e;
        // ALGORITMA

        for (i=0;i<=nRow-1;i++){
            for (j=0;j<=nRow-1;j++){
                e = Math.pow(setOfTitik[i][0],j);
                augMatrix[i][j] = (float)e; // Elemen matrixcoef
            }
            augMatrix[i][nRow] = setOfTitik[i][1]; // Elemen matrixres
        }

        augmented = new Matrix(augMatrix,nRow,nRow+1); // Membuat objek matrix
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

    public static float estimateY(float[] koefisien, int length, float X){
        // Fungsi mengembalikan taksiran suatu input x terhadap polinom interpolasi
        // KAMUS LOKAL
        double Y;
        int i;
        // ALGORITMA
        Y=0;
        for (i=0;i<= length-1;i++){
            Y += koefisien[i]*Math.pow(X,i);
        }
        return (float)Y;
    }

    public static void displayPolinom(float[] koefisien, int length){
        // Prosedur untuk menampilkan polinomial interpolasi
        // I.S Koefisien terdefinisi
        // F.S Menampilkan Polinom Interpolasi dengan format:
        //      a0 + a1x + a2x^2 + ... +anx^n 
        // KAMUS LOKAL
        String polinom;
        int i;
        // ALGORITMA
        polinom = "P(x) = ";
        for (i=0;i<=length-1;i++){
            if (i == 0){
                polinom += Double.toString((double)koefisien[i]);
            } else if(i == 1){
                polinom += (Double.toString((double)koefisien[i])+"x");
            } else{
                polinom += (Double.toString((double)koefisien[i])+"x"+"^"+ String.valueOf(i));
            }
            if (i!= length-1){
                polinom += "+ ";
            }
        }

        System.out.println(polinom);
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
        augmented = setOfTitikToMatrix(setOfTitik, n+1);
            // Mencari koefisien dari polinomial Interpolasi
        func = getPolinomialCoef(augmented);

        // Menampilkan Polinomial Interpolasi
        System.out.println("Polinomial Interpolasi");
        displayPolinom(func,n+1);
        
        // Menampilkan taksiran output dari x saat menjadi input polinomial interpolasi
            // Mencari nilai taksiran
        Y = estimateY(func, n+1 ,X);
            //Menampilkan
        System.out.printf("P(%.04f) = %.04f\n",X,Y);
    }

}
