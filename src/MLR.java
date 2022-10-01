package src;
import java.io.*;
import java.util.Scanner;
import src.Read;
import src.SPL.*;

import src.Matrix;

public class MLR {
    
    public static float[] MLR(Matrix matrixkoef, Matrix matrixres)
    {
        // KAMUS
        int i, j, col;
        Matrix tempkoef, tempres;
        float[] result = new float[matrixkoef.getNCol()];
        
        // ALGORITMA
        float[][] x = new float[matrixkoef.getNCol() + 1][matrixkoef.getNCol() + 1]; 
        tempkoef = new Matrix(x, matrixkoef.getNCol() + 1, matrixkoef.getNCol() +1);
        x = new float[tempkoef.getNRow()][1];
        tempres = new Matrix(x, tempkoef.getNRow(), 1);
        /* Lakukan normal equation */
        for (i = 0; i < tempkoef.getNCol() ; i++)
        {
            for (j = 0; j< tempkoef.getNCol(); j++)
            {
                if (i == 0 && j == 0)
                {
                    /* n */
                    tempkoef.setElmtContent(i,j, matrixkoef.getNRow()); 
                }
                else if (i != 0 && j == 0)
                {
                    /* sigma x */
                    tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(matrixkoef, i - 1)));
                }
                else if (i == 0 && j != 0)
                {
                    /* sigma x */
                    tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(matrixkoef, j - 1)));
                }
                else if (i == j && i != 0 && j != 0)
                {
                    /* Square x */
                    tempkoef.setElmtContent(i,j, sumOfSquare(arrOfColumn(matrixkoef, i-1)));
                }
                else 
                {
                    /* perkalian x ke a dan x ke b */
                    tempkoef.setElmtContent(i, j, sumOfTwoColumn(arrOfColumn(matrixkoef, i - 1), arrOfColumn(matrixkoef, j - 1) ));
                }
                
            }
        }
        
        /* jika sudah cek apakah matriks A invertible */
        /* jika non invertible lakukan while do normal equation dengan menghapus salah satu parameter bisa dimulai dari 1 lalu 2 lalu 3  dengan membuat temporary matrix baru dengan col lama -1*/
        col = 0;
        while (!Matrix.isInvertible(tempkoef) && col < matrixkoef.getNCol())
        {
            /* kalau ga invertible, hapus 1 kolom*/
            /* Copy Matrixkoef ke tempMatKoef dan menghilangkan parameter pada ke-x */
            Matrix tempMatKoef = MinorMatKoef(matrixkoef, col);
            x = new float[tempMatKoef.getNCol() + 1][tempMatKoef.getNCol() + 1];
            tempkoef = new Matrix(x, tempMatKoef.getNCol() + 1, tempMatKoef.getNCol() +1);
            x = new float[tempkoef.getNRow()][1];
            tempres = new Matrix(x, tempkoef.getNRow(), 1);
            
            for (i = 0; i < tempkoef.getNCol() ; i++)
            {
                for (j = 0; j< tempkoef.getNCol(); j++)
                {
                    if (i == 0 && j == 0)
                    {
                        /* n */
                        tempkoef.setElmtContent(i,j, tempMatKoef.getNRow());
                    }
                    else if (i != 0 && j ==0)
                    {
                        /* sigma x */
                        tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(tempMatKoef, i - 1)));
                    }
                    else if (i == 0 && j != 0)
                    {
                        /* sigma x */
                        tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(tempMatKoef, j - 1)));
                    }
                    else if (i == j && i != 0 && j != 0)
                    {
                        /* Square x */
                        tempkoef.setElmtContent(i,j, sumOfSquare(arrOfColumn(tempMatKoef, i-1)));
                    }
                    else 
                    {
                        /* perkalian x ke a dan x ke b */
                        tempkoef.setElmtContent(i, j, sumOfTwoColumn(arrOfColumn(tempMatKoef, i - 1), arrOfColumn(tempMatKoef, j - 1) ));
                    }
                    
                }
            }
            
            col += 1;
        }
        
        /* Cari nilai H */
        for (i = 0; i < tempres.getNRow(); i++)
        {
            if (i == 0)
            {
                tempres.setElmtContent(i, 0, sumOf(arrOfColumn(matrixres, 0)));
            }
            else
            {
                tempres.setElmtContent(i, 0, sumOfTwoColumn(arrOfColumn(matrixres, 0), arrOfColumn(matrixkoef, i-1)));
            }
        }

        /* print A */
        // for (i = 0; i < tempkoef.getNRow(); i++)
        // {
        //     for (j = 0; j < tempkoef.getNCol(); j++)
        //     {
        //         System.out.print(tempkoef.getElmtContent(i, j) + " ");
        //     }
        //     System.out.println();
        // }

        // /* print H */
        // for (i = 0; i < tempres.getNRow(); i++)
        // {
        //     System.out.println(tempres.getElmtContent(i, 0));
        // }
        
        /* jika invertible lakukan b = A^-1 * H */
        
        String[] resultStr = MatrixBalikan.splybyMatrixBalikan(tempkoef, tempres);

        result = new float[resultStr.length];
        for (i = 0; i < resultStr.length; i++)
        {
            result[i] = Float.valueOf(resultStr[i]);
        }
        /* return b */
        return result;
    }
    
    
    public static float[] arrOfColumn(Matrix matrix, int j)
    {
        // KAMUS
        int i ;
        float[] result = new float[matrix.getNRow()];
        
        // ALGORITMA
        for (i = 0; i < matrix.getNRow(); i++)
        {
            result[i] = matrix.getElmtContent(i, j);
        }
        
        return result;
    }
    
    public static float sumOf(float[] arr) /* untuk satu kolom */
    {
        float sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            sum += arr[i];
        }
        return sum;
    }
    
    public static float sumOfSquare(float[] arr) /* untuk kuadratik satu kolom */
    {
        /* I.S array of Float terdefinisi */
        /* F.S mengembalikan hasil penjumlahan perkalian arr[i] dengan dirinya sendiri */
        float sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            sum += (arr[i] * arr[i]);
        }
        
        return sum;
    }
    
    public static float sumOfTwoColumn(float[] arr1, float[] arr2) /* untuk perkalian dua kolom */
    {
        /* I.S array of Float terdefinisi */
        /* F.S mengembalikan hasil penjumlahan perkalian arr1[i] dengan arr2[i] */
        float sum = 0;
        for (int i = 0; i < arr1.length; i++)
        {
            sum += (arr1[i] * arr2[i]);
        }
        
        return sum;
    }
    
    public static Matrix MinorMatKoef(Matrix matrixkoef, int col)
    {
        /* I.S matrix terdefinisi */
        /* F.S mengembalikan matriks minor dari matrixkoef dengan menghilangkan kolom ke-col */
        float[][] x = new float[matrixkoef.getNRow()][matrixkoef.getNCol() - 1];
        Matrix result = new Matrix(x,matrixkoef.getNRow(), matrixkoef.getNCol() - 1);
        int iMinor = 0, jMinor = 0;
        int i, j;
        
        for (i = 0; i < matrixkoef.getNRow(); i++)
        {
            for (j = 0; j < matrixkoef.getNCol(); j++)
            {
                if (j != col)
                {
                    result.setElmtContent(iMinor, jMinor, matrixkoef.getElmtContent(i, j));
                    jMinor += 1;
                }
            }
            iMinor += 1;
            jMinor = 0;
        }
        
        return result;
    }
    public static void menuMLR() throws IOException
    {
        // KAMUS LOKAL
        Scanner input = new Scanner(System.in);
        float[][] inputMatrixr, inputMatrixk, inputMatrix;
        int inputType, Nvar, Nrow;
        String file;
        float[] result, xtaksiran;
        float y = 0;
        Matrix inputMatrixFile;

        // ALGORITMA
        System.out.println("Tipe input");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input File");

        System.out.println("Masukkan tipe input: ");
        inputType = input.nextInt();
        while (inputType!=1 && inputType!=2){
            System.out.println("Tipe input yang anda pilih tidak tersedia!");
            System.out.println("Tipe input");
            System.out.println("1. Input Keyboard");
            System.out.println("2. Input File");
    
            System.out.print("Masukkan tipe input: ");
            inputType = input.nextInt();
        }

        // iput Matrix
        if (inputType == 1){
            System.out.println("Masukkan jumlah variabel: ");
            Nvar = input.nextInt();
            System.out.println("Masukkan jumlah sampel: ");
            Nrow = input.nextInt();
            inputMatrixk = new float[Nrow][Nvar];
            inputMatrixr = new float[Nrow][1];
            Matrix matrixkoef, matrixres;

            for (int i = 0; i < Nrow; i++){
                for (int j = 0; j < Nvar; j++){
                    System.out.println("Masukkan nilai x" + (j+1) + " pada sampel ke-" + (i+1) + ": ");
                    inputMatrixk[i][j] = input.nextFloat();
                }
                System.out.println("Masukkan nilai y pada sampel ke-" + (i+1) + ": ");
                inputMatrixr[i][0] = input.nextFloat();
            }
            matrixkoef = new Matrix(inputMatrixk, Nrow, Nvar);
            matrixres = new Matrix(inputMatrixr, Nrow, 1);
            result = MLR(matrixkoef, matrixres);
            System.out.println("Hasil Regresi Linear Berganda: ");
            System.out.print("y =");
            for (int i = 0; i < result.length; i++){
                /* Dibiarin jika ada minus cth +-1 */
                if (i == 0){
                    System.out.print(" " + result[i]);
                }
                else{
                    System.out.print(" + " + result[i] + "x" + i + 1);	
                }
            }
            System.out.println();
            System.out.println("Masukka nilai yang ingin ditaksir: ");
            xtaksiran = new float[Nvar];
            for (int i = 0; i < Nvar; i++){
                System.out.println("Masukkan nilai x" + (i+1) + ": ");
                xtaksiran[i] = input.nextFloat();
            }
            System.out.println("Hasil taksiran: ");
            for  (int i = 0; i < Nvar; i++){
                if (i == 0){
                    y += result[i];
                }
                else{
                    y += result[i] * xtaksiran[i-1];
                }
            }
            System.out.println("y = " + y);
            
        }
        else{
            try {
                System.out.println("Masukkan nama file: ");
                file = input.next();
                
                inputMatrixFile = Read.BacaFile(file);
                
                /* Inisialisasi Matriks res */
                float[][] res = new float[inputMatrixFile.getNRow()][1];
                for(int i = 0; i < inputMatrixFile.getNRow(); i++){
                    res[i][0] = inputMatrixFile.getElmtContent(i, 0);
                }
                Matrix matrixres = new Matrix(res, inputMatrixFile.getNRow(), 1);
                
                /* Inisialisasi Matriks koef */
                float[][] koef = new float[inputMatrixFile.getNRow()][inputMatrixFile.getNCol()-1];
                for(int i = 0; i < inputMatrixFile.getNRow(); i++){
                    int jkoef = 0;
                    for(int j = 0; j < inputMatrixFile.getNCol()-1; j++){
                        koef[i][jkoef] = inputMatrixFile.getElmtContent(i, j+1);
                        jkoef += 1;
                    }
                }
                Matrix matrixkoef = new Matrix(koef, inputMatrixFile.getNRow(), inputMatrixFile.getNCol()-1);
                
                result = MLR(matrixkoef, matrixres);
                
                System.out.println("Hasil Regresi Linear Berganda: ");
                System.out.print("y =");
                for (int i = 0; i < result.length; i++){
                    /* Dibiarin jika ada minus cth +-1 */
                    if (i == 0){
                        System.out.print(" " + result[i]);
                    }
                    else{
                        System.out.print(" + " + result[i] + "x" + (i));	
                    }
                }
                System.out.println();
                System.out.println("Masukka nilai yang ingin ditaksir: ");
                Nvar = matrixkoef.getNCol();
                xtaksiran = new float[Nvar];
                for (int i = 0; i < Nvar; i++){
                    System.out.println("Masukkan nilai x" + (i+1) + ": ");
                    xtaksiran[i] = input.nextFloat();
                }
                System.out.println("Hasil taksiran: ");
                for  (int i = 0; i < Nvar + 1; i++){
                    if (i == 0){
                        y += result[i];
                    }
                    else{
                        y += result[i] * xtaksiran[i-1];
                    }
                }
                System.out.println("y = " + y);
                
            } catch (Exception e) {
                System.out.println("");
            }
        }
        
    }
}


/* Referensi : https://www.statmat.net/regresi-linier-berganda/ */