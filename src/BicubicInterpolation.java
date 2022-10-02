package src;

// import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.border.StrokeBorder;

import src.SPL.GaussJordan;

// import src.Matrix;

// import java.util.*;

// import java.lang.Math.*;

public class BicubicInterpolation {

    public static void main(String[] args) {
        Matrix fxy, modelBic, aijMatx;
        float[][] temp;

        temp = new float[16][1];
        fxy = new Matrix(4,4);
        
        temp = new float[16][16];
        modelBic = new Matrix(temp, 16, 16);
        
        temp = new float[16][1];
        aijMatx = new Matrix(temp, 16, 1);


        modelBic = createModelBicubicMatrix();

        // modelBic.displayMatrix();

        aijMatx = createMatrixofAij(fxy);

        System.out.println("---------------------------");
        aijMatx.displayMatrix();
        
        aijMatx = Matrix.multiplyMatrix(modelBic, aijMatx);
        
        System.out.println("---------------------------");
        aijMatx.displayMatrix();


        // System.out.print("\n aaaaaaa \n");

        // modelBic.displayMatrix();
    }


    /*** MENU UNTUK BICUBIC INTERPOLATION  ***/
    public static void menuBicubicInterpolation() throws IOException {

        Scanner input = new Scanner(System.in);
        int inputType;
        int i, j, valueModel;
        float[][] tempMatx;
        Matrix fxyMatx;
        String file;
        float a, b, fabValue;

        System.out.println("Tipe input");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input File");

        System.out.print("Masukkan tipe input: ");
        inputType = input.nextInt();

        while (inputType != 1 && inputType != 2) {
            System.out.println("Tipe input yang anda pilih tidak tersedia!");
            System.out.println("Tipe input");
            System.out.println("1. Input Keyboard");
            System.out.println("2. Input File");
    
            System.out.print("Masukkan tipe input: ");
            inputType = input.nextInt();
        }

        tempMatx = new float[4][4];
        fxyMatx = new Matrix(tempMatx, 4, 4);

        if (inputType == 1) {

            System.out.println("Masukkan nilai f(-1,-1),f(0,-1),..,f(2,2).");
            
            for (i = 0; i < 4; i++) {
                for (j = 0; j < 4; j++) {
                    System.out.printf("f(%d,%d) = ", j-1,i-1);
                    valueModel = input.nextInt();
                    // System.out.print("\n");

                    fxyMatx.setElmtContent(i, j, valueModel);
                }
            }
        }
        // inputType == 2
        else { 

            System.out.print("Masukkan nama file: ");
            file = input.next();
            fxyMatx = new Matrix(file);
        }

        System.out.println("\nMasukkan nilai a dan b untuk f(a,b)");
        System.out.println("yang ingin di interpolasi.");
        System.out.println("Syarat a dan b dalam rentang 0..1");
        
        System.out.print("a = ");
        a = input.nextFloat();
        System.out.print("b = ");
        b = input.nextFloat();
        
        while ( (a < 0 || a > 1.0) || (b < 0 || b > 1.0) ) {
            System.out.println("input tidak sesuai");
            System.out.println("Syarat a dan b dalam rentang 0..1");
            
            System.out.print("a = ");
            a = input.nextFloat();
            System.out.print("b = ");
            b = input.nextFloat();
        }

        fabValue = interpolasiBicub(fxyMatx, a, b);

        System.out.printf("f(%f,%f) = %f\n\n", a, b, fabValue);

    }


    /** RUMUS FUNGSI f(x,y) **/
    public static float interpolasiBicub(Matrix fxy, float x, float y) {
        // mengembalikan nilai f(x,y) = sigma[j] sigma[i] a[i,j] * x^i * y^j
        // untuk nilai sigma[i] 0..i dan sigma[j] 0..j
        float sum;
        float[][] temp;
        Matrix matxAij;

        int itrCol;
        int i, j;

        temp = new float[16][1];
        matxAij = new Matrix(temp, 16, 1);

        matxAij = createMatrixofAij(fxy);
        matxAij.displayMatrix();

        i = 0; j = 0;
        sum = 0;

        for (itrCol = 0; itrCol < 16; itrCol++) {

            // System.out.println(j);
            i = itrCol%4;

            sum += matxAij.getElmtContent(itrCol, 0) * Math.pow(x, i) * Math.pow(y,j);
            
            j = j + (i/3);
        }


        return sum;
    }


    /** FUNGSI PEMBENTUK MATRIX A[i][j] **/
    public static Matrix createMatrixofAij(Matrix fxy) {
        // Mencari nilai a[i][j] untu i,j = 0,1,2,3. dari model matrix

        // KAMUS LOKAL
        Matrix fxyMatrixCol, modelMatx, resultMatx;
        String[] strAKoef;
        float[][] temp;
        int i;

        // ALGORITMA
        // inisial
        temp = new float[16][16];
        modelMatx = new Matrix(temp, 16, 16);
        
        temp = new float[16][1];
        fxyMatrixCol = new Matrix(temp, 16, 1);

        temp = new float[16][1];
        resultMatx = new Matrix(temp, 16, 1);

        // f(x,y) = Ca -> C-1f(x,y) 
        modelMatx = createModelBicubicMatrix();
        fxyMatrixCol = squareMatxToColMatx(fxy);

        strAKoef = GaussJordan.splbyGaussJordan(modelMatx, fxyMatrixCol);

        for (i = 0; i < 16; i++) {
            resultMatx.setElmtContent(i, 0, Float.valueOf(strAKoef[i]));
        }
        
        
        return resultMatx;
    }
    

    public static Matrix createModelBicubicMatrix() {
        // membuat matrix bicubic dari formula
        // x dan y terdefinisi dan memiliki selisih 4 sebagain integer
        float[][] m1;
        Matrix bicubic;
        int itrRow, itrCol;
        int x, y, i, j;

        m1 = new float[16][16];
        bicubic = new Matrix(m1, 16, 16);

        // sigma[j] sigma[i] aij x^i y^j
        // inisial
        x = -1 - 1; y = -1;
        i = 0; j =0;
        
        for (itrRow = 0; itrRow < 16; itrRow++) {

            if (x == 2) {
                x = -1;
            }
            else {
                x++;
            }

            for (itrCol = 0; itrCol < 16; itrCol++) {

                i = itrCol%4;

                bicubic.setElmtContent(itrRow, itrCol, (float) (Math.pow(x, i) * Math.pow(y, j)) );
                
                j = j + (i/3);
            }

            j = 0;
            if (x == 2) {
                y++;
            }
        }

        return bicubic;
    }

    /** OPERASI BENTUK MATRIX **/
    public static Matrix squareMatxToColMatx(Matrix matx) {
        // matx1 berubah menjadi matrix colom dengan jumlah elemen sama
        // matrix colom yaitu matrix yang hanya memiliki 1 colom

        Matrix matxCol;
        float[][] temp;
        int i, j, itrRow;

        temp = new float[matx.getNRow()*matx.getNCol()][1];
        matxCol = new Matrix(temp, matx.getNRow()*matx.getNCol(), 1);
        itrRow = 0;

        for (i = 0; i < matx.getNCol(); i++) {
            for (j = 0; j < matx.getNRow(); j++) {
                matxCol.setElmtContent(j+i+itrRow, 0, matx.getElmtContent(i, j));
            }
            itrRow += j-1;
        }

        return matxCol;
    }

    public static Matrix colMatxToSquareMatx(Matrix matx) {
        // matx1 berubah menjadi matrix square
        // precond : jumlah elemen matrix adalah n^2 dengan n = 1,2,3,..

        int incr;
        int i, j, itrRow;
        float[][] temp;
        Matrix matxSquare;

        incr = 1;

        while (incr*incr != matx.getNRow()) {
            incr += 1;
        }

        temp = new float[matx.getNRow() / incr][matx.getNRow() / incr];
        matxSquare = new Matrix(temp, matx.getNRow() / incr, matx.getNRow() / incr);

        itrRow = 0;

        for (i = 0; i < matx.getNRow() / incr; i++) {
            for (j = 0; j< matx.getNRow() / incr; j++) {
                matxSquare.setElmtContent(i, j, matx.getElmtContent(i+j+itrRow, 0));
            }
            itrRow += j-1;
        }

        return matxSquare;
    }




   

   
}

