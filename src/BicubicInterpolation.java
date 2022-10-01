package src;

import src.Inverse;
import src.Matrix;

import java.util.*;

import javax.print.StreamPrintService;

import java.lang.Math.*;

public class BicubicInterpolation {

    public static void main(String[] args) {
        Matrix fxy, modelBic;
        float[][] temp;
        float y;

        temp = new float[4][4];
        fxy = new Matrix(temp,4,4);
        
        temp = new float[16][16];
        modelBic = new Matrix(temp, 16, 16);
        modelBic = createModelBicubicMatrix();

        modelBic.displayMatrix();

        modelBic = Inverse.getInversebyAdj(modelBic);

        // System.out.print("\n\n");

        // modelBic.displayMatrix();
    }


    /*** MENU UNTUK BICUBIC INTERPOLATION  ***/
    public static void menuBicubicInterpolation(int menu) {
        
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

        x = x - 1;
        i = 0; j = 0;
        sum = 0;

        for (itrCol = 0; itrCol < 16; itrCol++) {

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
        Matrix invModel, fxyMatrixCol, tempMatx, resultMatx;
        float[][] temp;

        // ALGORITMA
        // inisial
        temp = new float[16][16];
        tempMatx = new Matrix(temp, 16, 16);
        
        temp = new float[16][16];
        invModel = new Matrix(temp, 16, 16);

        temp = new float[16][1];
        fxyMatrixCol = new Matrix(temp, 16, 1);

        temp = new float[16][1];
        resultMatx = new Matrix(temp, 16, 1);

        // f(x,y) = Ca -> C-1f(x,y) 
        tempMatx = createModelBicubicMatrix();
        invModel = Inverse.getInversebyOBE(tempMatx); // dapat invers dari rumus bicubic
        
        fxyMatrixCol = squareMatxToColMatx(fxy);
        
        resultMatx = Matrix.multiplyMatrix(invModel, fxyMatrixCol);
        
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

