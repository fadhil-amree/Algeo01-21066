package src;

// import src.Inverse;
import src.Matrix;

import java.util.*;
import java.lang.Math.*;

public class BicubicInterpolation {

    public static void main(String[] args) {
        
        float[][] tempfloatmat;
        Matrix matx1;
        int i, j;
        int x,y;
        
        tempfloatmat = new float[16][16];
        matx1 = new Matrix(tempfloatmat, 16, 16);
        matx1.displayMatrix();
        
        // sigma[j] sigma[i] aij xi, yj -
        // x = -2; y = -1;
/* 
        for (j = 0; j < 16; j++) {
            
            if (x == 2) {
                x = -1;
            }
            else {
                x++;
            }
            // System.out.print(j+" ");
            // System.out.print("("+x+","+y+")");
            // System.out.print("\n");
            // System.out.print("(j=" + j%4 + ") ");
            for (i = 0; i < 16; i++) {
                matx1.setElmtContent( i, j, (float) (Math.pow(x,i%4)*Math.pow(y,j%4)) );
                // System.out.print( (float) (Math.pow(x,i%4)*Math.pow(y,j%4)) + " ");
                // System.out.print( xyFunctionValue(x, i%4, y, j%4) + " ");
                // System.out.print("(" + i%4 + ") ");
            }
            // System.out.print("\n");
            if (x == 2) {
                y++; 
            }
            
        }

        System.out.print((float) ( Math.pow(0,0) * Math.pow(0, 0)));
 */
        matx1 = createModelBicubicMatrix(-1,-1);
        matx1.displayMatrix();

    }


    /*** MENU UNTUK BICUBIC INTERPOLATION  ***/
    public static void menuBicubicInterpolation(int menu) {
        
    }


    /** RUMUS FUNGSI **/
    public static float functionValue(Matrix a) {
        // mengembalikan nilai f(x,y) = sigma[j] sigma[i] a[i,j] * x^i * y^j
        // untuk nilai sigma[i] 0..i dan sigma[j] 0..j
        int i, j, x, y;
        double sum;

        sum = 0;

        x = -2; y = -1;
        
        for (j = 0; j < 16; j++) {

            if (x == 2) {
                x = -1;
            }
            else {
                x++;
            }

            for (i = 0; i < 16; i++) {
                sum = a.getElmtContent(i,0)*Math.pow(x,i%4)*Math.pow(y,j%4);
            }

            if (y == 2) {
                y = -1;
            }
            else if (x == 2) {
                y++; 
            }

        }

        return (float) sum;
    }

    public static Matrix createMatrixofAij(Matrix fxy) {


        Matrix matxBicub, fxyMatrixCol, tempMatx, resultMatx;
        float[][] temp;

        temp = new float[16][16];
        tempMatx = new Matrix(temp, 16, 16);
        
        temp = new float[16][16];
        matxBicub = new Matrix(temp, 16, 16);

        temp = new float[16][1];
        fxyMatrixCol = new Matrix(temp, 16, 1);

        temp = new float[16][1];
        resultMatx = new Matrix(temp, 16, 1);

        tempMatx = createModelBicubicMatrix(-1, -1);
        matxBicub = Inverse.getInversebyOBE(tempMatx); // dapat invers dari rumus bicubic
        
        fxyMatrixCol = squareMatxToColMatx(fxy);
        
        resultMatx = Matrix.multiplyMatrix(fxyMatrixCol, matxBicub);

        // kali matrix matxBicub dengan fxyMatrixCol

        // dapat matrix Aij
        
        return resultMatx;
    }
    
    public static Matrix squareMatxToColMatx(Matrix matx) {
        // I.S : matx1 terdefinisi
        // F.S : matx1 berubah menjadi matrix colom dengan jumlah elemen sama
        // matrix colom yaitu matrix yang hanya memiliki 1 colom

        Matrix m;
        // float[][] temp;
        int i, j, itr1;

        // temp = new float[matx.getNRow()*matx.getNCol()][1];
        m = new Matrix(matx.getNRow()*matx.getNCol(), 1);
        itr1 = 0;

        for (j = 0; j < matx.getNCol(); j++) {
            for (i = 0; i < matx.getNRow(); i++) {
                m.setElmtContent(i+j+itr1, 0, matx.getElmtContent(i, j));
            }
            itr1 += i-1;
        }

        return m;
    }

    public static Matrix createModelBicubicMatrix(double x, double y) {
        // membuat matrix bicubic dari formula
        // x dan y terdefinisi dan memiliki selisih 4 sebagain integer
        float[][] m1;
        Matrix bicubic;
        int i, j;

        m1 = new float[16][16];
        bicubic = new Matrix(m1, 16, 16);

        // sigma[j] sigma[i] aij xi, yj -
        x = x - 1;

        for (j = 0; j < 16; j++) {

            if (x == 2) {
                x = -1;
            }
            else {
                x++;
            }

            for (i = 0; i < 16; i++) {
                bicubic.setElmtContent((int) i, (int) j, (float) (Math.pow(x,i%4)*Math.pow(y,j%4)));
            }

            if (y == 2) {
                y = -1;
            }
            else if (x == 2) {
                y++; 
            }

        }

        return bicubic;
    }



   

   
}

