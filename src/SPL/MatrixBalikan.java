package src.SPL;
import src.Inverse;
import src.Matrix;
import java.util.*;

public class MatrixBalikan {
    public static String[] splybyMatrixBalikan(Matrix matrixkoef, Matrix matrixres){
        // Fungsi mencari solusi SPL dengan metode MatrixBalikan
        // Prekondisi matrixkoef dan matrixres tak kosong
        // KAMUS LOKAL
        double[] solusi; //
        String[] solusi_string; //
        Matrix tesmatriks = new Matrix(matrixkoef);
        int i; //indeks
        //ALGORITMA
        if (matrixkoef.getNCol()>=2){
            if (Matrix.isInvertible(tesmatriks)){ // Jika punya solusi unik
                solusi = new double[matrixkoef.getNCol()];
                solusi_string = new String[matrixkoef.getNCol()];
                Matrix inverseMatrix = Inverse.getInversebyOBE(matrixkoef);
                Matrix matrixSolusi = Matrix.multiplyMatrix(inverseMatrix, matrixres);
                for (i = 0; i <= matrixSolusi.getNRow()-1;i++){
                    solusi[i] = matrixSolusi.getElmtContent(i, 0);
                }
            } else { // Jika tak punya matrix balikan
                solusi = new double[0]; //Tak punya balikan
                solusi_string = new String[0];
            }

        }
        else{ //matrixkoef.getNCol()==1
            solusi = new double[matrixkoef.getNCol()];
            solusi_string = new String[matrixkoef.getNCol()];
            if ((matrixkoef.getElmtContent(0, 0) == 0) && (matrixres.getElmtContent(0, 0)!=0)){
                solusi =  GaussJordan.getlistUndef(1); //Jika tak punya solusi
            } else if ((matrixkoef.getElmtContent(0, 0) == 0) && (matrixres.getElmtContent(0, 0)==0)){
                solusi[0] = 9999; // Mark Parametrik, Punya tak hingga solusi
            } else {
                solusi[0] = matrixres.getElmtContent(0, 0); // Punya solusi unik
            }
        }

        for (i=0;i<solusi.length;i++){
            solusi_string[i] = String.valueOf((double)solusi[i]);
        } 
        return solusi_string;
    }   
}
