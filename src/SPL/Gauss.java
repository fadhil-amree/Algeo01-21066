package src.SPL;
import src.*;
import src.Determinant.*;

public class Gauss {
    public static int getIdxOne(Matrix matrixkoef, int row)
    {
        int col = 0;
        while (col < matrixkoef.getNCol() - 1 && !(matrixkoef.getElmtContent(row, col) == 1))
        {
            col++;
        }
        return col;
    }
    public static boolean isRowOnlyContainOne(Matrix matrixkoef, int row)
    {
        int countOne = 0;
        for (int j = 0 ; j < matrixkoef.getNCol(); j++)
        {
            if (matrixkoef.getElmtContent(row, j) == 1)
            {
                countOne++;
            }
        }
        return (countOne == 1);
    }
    public static boolean isAllColZero(Matrix M, int col) {
        int i;
        for (i = 0; i < M.getNRow(); i++) {
            if (M.getElmtContent(i, col) != 0) {
                return false;
            }
        }
        return true;
    }
    public static Matrix hapusRowMatKoef(Matrix matrixkoef, int row){
        float[][] iTemp = new float[row + 1][matrixkoef.getNCol()];
        Matrix tempMatrix = new Matrix(iTemp,row + 1, matrixkoef.getNCol());
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < matrixkoef.getNCol(); j++) {
                tempMatrix.setElmtContent(i, j, matrixkoef.getElmtContent(i, j));
            }
        }
        return tempMatrix;
    }
    
    public static Matrix hapusRowMatRes(Matrix matrixres, int row)
    {
        float[][] iTemp = new float[row + 1][1];
        Matrix tempMatrix = new Matrix(iTemp, row + 1, 1);
        for (int i = 0; i <= row; i++) {
            for (int j = 0; j < matrixres.getNCol(); j++) {
                tempMatrix.setElmtContent(i, j, matrixres.getElmtContent(i, j));
            }
        }
        return tempMatrix;
    }

    public static void subtractOfRowMatrix(Matrix matrix, int row1, int row2, float pengali)
    {
        // Mengembalikan hasil pengurangan baris row1 dengan row2
        // KAMUS LOKAL
        int i;
        float temp;
        // ALGORITMA
        for (i = 0; i < matrix.getNCol(); i++)
        {
            temp = matrix.getElmtContent(row1, i) - (pengali * matrix.getElmtContent(row2, i));
            matrix.setElmtContent(row1, i, temp);
        }
    }

    public static boolean isBarisKoefNol(Matrix matrixkoef, int i)
    {
        boolean isNol = true;
        for (int j = 0; j < matrixkoef.getNCol(); j++)
        {
            if (matrixkoef.getElmtContent(i, j) != 0)
            {
                isNol = false;
            }
        }
        return isNol;
    }

    public static boolean isBarisResNol(Matrix matrixres, int i)
    {

        return (matrixres.getElmtContent(i, 0) == 0);
    }

    public static boolean isElDiagonal0 (Matrix matrixkoef, int row)
    {
        return (matrixkoef.getElmtContent(row, row) == 0);
    }
    
    public static void TukarBaris(Matrix matrixkoef, Matrix matrixres, int row)
    {
        /* I.S matrixkoef dan matrixres terdefinisi */
        /* F.S matrixkoef dan matrixres ditukar barisnya */
        
        // KAMUS
        int col = row, i, j;
        float temp;
        
        // ALGORITMA
        i = row + 1;
        while(i < matrixkoef.getNCol() && isElDiagonal0(matrixkoef, row))
        {
            if (matrixkoef.getElmtContent(i, col) != 0)
            {
                for (j = 0; j < matrixkoef.getNCol(); j++)
                {
                    temp = matrixkoef.getElmtContent(row, j);
                    matrixkoef.setElmtContent(row, j, matrixkoef.getElmtContent(i, j));
                    matrixkoef.setElmtContent(i, j, temp);
                }
                temp = matrixres.getElmtContent(row, 0);
                matrixres.setElmtContent(row, 0, matrixres.getElmtContent(i, 0));
                matrixres.setElmtContent(i, 0, temp);
            }
            i++;
        }
    }
    
    public static boolean IsSegitigaBawahZero(Matrix matrixkoef)
    {
        /* I.S matrix terdefinisi */
        /* F.S mengembalikan true jika matrix segitiga bawahnya 0 semua */
        
        // KAMUS
        int i, j, n; /*iterasi*/
        boolean isZero = true; /*boolean untuk mengecek apakah matrix segitiga bawahnya 0 semua*/
        
        // ALGORITMA
        /* Cek apakah matrix segitiga bawahnya 0 semua */
        n = matrixkoef.getNRow();
        i = 0;
        while (isZero && i < n) {
            j = 0;
            while (isZero && j < i) {
                if (matrixkoef.getElmtContent(i, j) != 0) {
                    isZero = false;
                }
                j++;
            }
            i++;
        }
        return isZero;
    }

    public static boolean isDiagonalOne(Matrix matrixkoef)
    {
        /* I.S matrix terdefinisi */
        /* F.S mengembalikan true jika elemen diagonal matrix bernilai 1 */
        
        // KAMUS
        int i, n; /*iterasi*/
        boolean isOne = true; /*boolean untuk mengecek apakah elemen diagonal matrix bernilai 1*/

        // ALGORITMA
        /* Cek apakah elemen diagonal matrix bernilai 1 */
        for (i = 0; i < matrixkoef.getNRow(); i++) {
            if (matrixkoef.getElmtContent(i, i) != 1) {
                isOne = false;
            }
        }

        return isOne;
    }
    
    public static String[] splbyGauss(Matrix matrixkoef, Matrix matrixres)
    {
        /* I.S matrixkoef dan matrixres terdefinisi */
        /* F.S result hasil dalam string */

        // KAMUS
        int i, j, n, row, col; /*iterasi*/
        String[] resultStr = new String[matrixkoef.getNCol()]; /*array untuk menampung hasil dalam string*/
        float[] resultFloat = new float[matrixkoef.getNCol()]; /*array untuk menampung hasil dalam float*/
        float pembagi, pengurang; /*variabel untuk mencari KPK*/

        // ALGORITMA
        /* Cari hasil */
        i = 0;
        while (!IsSegitigaBawahZero(matrixkoef)) {
            row = i;
            /* tukar baris */
            while (isElDiagonal0(matrixkoef, row) && row < matrixkoef.getNRow())
            {
                TukarBaris(matrixkoef, matrixres, row);
                row++;
            }

            /* Cari  OBE*/
            j = 0;
            while (j < i)
            {
                if(matrixkoef.getElmtContent(i, j) != 0) // ini belum tau
                {
                    pembagi = matrixkoef.getElmtContent(i, j);
                    pengurang = matrixkoef.getElmtContent(j, j);
                    for (col = 0; col < matrixkoef.getNCol(); col++)
                    {
                        matrixkoef.setElmtContent(i, col, (matrixkoef.getElmtContent(i, col) * pengurang) - (matrixkoef.getElmtContent(j, col) * pembagi));
                    }
                    for (col = 0; col < matrixres.getNCol(); col++)
                    {
                        matrixres.setElmtContent(i, col, (matrixres.getElmtContent(i, col) * pengurang) - (matrixres.getElmtContent(j, col) * pembagi));
                    }     
                }
                j++;
            }
            
            /* cari leading one */
            j = 0;
            pembagi = 0;
            while(j < matrixkoef.getNCol())
            {
                if (matrixkoef.getElmtContent(i, j) != 0)
                {
                    pembagi = matrixkoef.getElmtContent(i, j);
                    break;
                }
                j++;
            }
            while (pembagi != 0 && j < matrixkoef.getNCol())
            {
                matrixkoef.setElmtContent(i, j, matrixkoef.getElmtContent(i, j) / pembagi);
                j++;
            }
            if (pembagi != 0)
            {
                matrixres.setElmtContent(i, 0, matrixres.getElmtContent(i, 0) / pembagi);
            }

            /* tambah baris dengan baris lainnya */


            i += 1;
        }


        /* Cari hasil */
        /* Solusi 1 : Solusi Unik*/
        if (isDiagonalOne(matrixkoef) && matrixkoef.getNCol() == matrixkoef.getNRow())
        {
            for (i = matrixkoef.getNRow() - 1; i >= 0; i--)
            {
                resultFloat[i] = matrixres.getElmtContent(i, 0);
                j = i + 1;
                while(j < matrixkoef.getNCol())
                {
                    resultFloat[i] -= matrixkoef.getElmtContent(i, j) * resultFloat[j];
                    j++;
                }
            }

            for (i = 0; i < matrixkoef.getNCol(); i++)
            {
                resultStr[i] = String.valueOf(resultFloat[i]);
            }
        }
        /* Solusi 2 : Solusi Banyak tipe 1*/ 
        else if(isDiagonalOne(matrixkoef) && matrixkoef.getNCol() != matrixkoef.getNRow()) {
            /* inisialisasi string parameter */
            String a = "a";
            int itemp, jtemp;
            int batasbawahParam = matrixkoef.getNRow();
            int batasatasParam = matrixkoef.getNCol() - 1;
            
            /* inisialisasi matriks untuk menyimpan hasil */
            /* Sususan Mtemp = new float[nNonParam][nParam] */
            float[][] dumpMtemp = new float[matrixkoef.getNRow()][matrixkoef.getNCol() - matrixkoef.getNRow() + 1];
            Matrix Mtemp = new Matrix(dumpMtemp, matrixkoef.getNRow(), matrixkoef.getNCol() - matrixkoef.getNRow() + 1);

            /* Masukkan semua variable yang berupa parameter */
            i  = resultStr.length - 1;
            while (i >= batasbawahParam)
            {
                resultStr[i] = a + String.valueOf(i + 1);
                i--;
            }

            /* Kalkulasikan variable yang berupa hasil */
            itemp = Mtemp.getNRow() - 1; // batas atas Mtemp
            /* Hasil ini adalah hasil yang bilangan float */
            while (itemp >= 0)
            {
                Mtemp.setElmtContent(itemp, 0, matrixres.getElmtContent(itemp, 0));
                j = matrixkoef.getNCol() - 1;
                jtemp = Mtemp.getNCol() - 1;
                while (j >= batasbawahParam && jtemp > 0)
                {
                    /* Isikan koefisien pada matriks" dengan minusnya */
                    Mtemp.setElmtContent(itemp, jtemp, -(matrixkoef.getElmtContent(itemp, j)));
                    jtemp--;
                    j--;
                }
                itemp -= 1;
            }

            /* Cari hasil */
            for (i = Mtemp.getNRow() - 1; i >= 0; i--)
            {
                j = i + 1;
                while(j < Mtemp.getNRow())
                {
                    subtractOfRowMatrix(Mtemp, i, j, matrixkoef.getElmtContent(i, j));
                    j++;
                }
            }
            
            
            /* Masukkan hasil ke dalam resultStr */
            i = 0;
            String tempStr;
            while (i < Mtemp.getNRow())
            {
                tempStr = "";
                for(j = 0; j < Mtemp.getNCol(); j++)
                {
                    if (Mtemp.getElmtContent(i, j) != 0)
                    {
                        if (j == 0)
                        {
                            tempStr += String.valueOf(Mtemp.getElmtContent(i,j));
                        }
                        else
                        {
                                tempStr +=  " + " + String.valueOf(Mtemp.getElmtContent(i,j)) + " " + resultStr[j + batasbawahParam - 1];
                        }
                    }
                }
                resultStr[i] = tempStr;
                i++;
            }
        } else {
            i = matrixkoef.getNRow() - 1;
            while (isBarisKoefNol(matrixkoef, i) && isBarisResNol(matrixres, i) && (i >= 0))
            {
                i -= 1;
            }
            if (isBarisKoefNol(matrixkoef, i) && !isBarisResNol(matrixres, i))
            {
                resultFloat = GaussJordan.getlistUndef(resultFloat.length);
                for (i = 0; i < resultFloat.length; i++)
                {
                    resultStr[i] = String.valueOf(resultFloat[i]);
                }
            } else {
                /* Banyak solusi tipe 2 ada persamaan yang sebenarnya adalah scaling dari persamaan lainnya*/
                matrixkoef = hapusRowMatKoef(matrixkoef, i);
                matrixres = hapusRowMatRes(matrixres, i);

                /* Cari hasil */
                 /* inisialisasi string parameter */
            String a = "a";
            int itemp, jtemp;
            int batasbawahParam = matrixkoef.getNRow();
            int batasatasParam = matrixkoef.getNCol() - 1;
            
            /* inisialisasi matriks untuk menyimpan hasil */
            /* Sususan Mtemp = new float[nNonParam][nParam] */
            float[][] dumpMtemp = new float[matrixkoef.getNRow()][matrixkoef.getNCol() - matrixkoef.getNRow() + 1];
            Matrix Mtemp = new Matrix(dumpMtemp, matrixkoef.getNRow(), matrixkoef.getNCol() - matrixkoef.getNRow() + 1);
            
            /* Simpan identitas variable yang bukan parameter */
            for (i = matrixkoef.getNRow() - 1; i >= 0; i--)
            {
                if (isRowOnlyContainOne(matrixkoef, i))
                {
                    resultFloat[getIdxOne(matrixkoef, i)] = matrixres.getElmtContent(i, 0);
                }
            }

            /* Masukkan semua variable yang berupa parameter */
            i  = resultStr.length - 1;
            while (i >= batasbawahParam)
            {
                resultStr[i] = a + String.valueOf(i + 1);
                i--;
            }

            /* Kalkulasikan variable yang berupa hasil */
            itemp = Mtemp.getNRow() - 1; // batas atas Mtemp
            /* Hasil ini adalah hasil yang bilangan float */
            while (itemp >= 0)
            {
                Mtemp.setElmtContent(itemp, 0, matrixres.getElmtContent(itemp, 0));
                j = matrixkoef.getNCol() - 1;
                jtemp = Mtemp.getNCol() - 1;
                while (j >= batasbawahParam && jtemp > 0)
                {
                    /* Isikan koefisien pada matriks" dengan minusnya */
                    Mtemp.setElmtContent(itemp, jtemp, -(matrixkoef.getElmtContent(itemp, j)));
                    jtemp--;
                    j--;
                }
                itemp -= 1;
            }

            /* Cari hasil */
            for (i = Mtemp.getNRow() - 1; i >= 0; i--)
            {
                j = i + 1;
                while(j < Mtemp.getNRow())
                {
                    subtractOfRowMatrix(Mtemp, i, j, matrixkoef.getElmtContent(i, j));
                    j++;
                }
            }
            
            
            /* Masukkan hasil ke dalam resultStr */
            i = 0;
            String tempStr;
            while (i < Mtemp.getNRow())
            {
                tempStr = "";
                for(j = 0; j < Mtemp.getNCol(); j++)
                {
                    if (Mtemp.getElmtContent(i, j) != 0)
                    {
                        if (j == 0)
                        {
                            tempStr += String.valueOf(Mtemp.getElmtContent(i,j));
                        }
                        else
                        {
                                tempStr +=  " + " + String.valueOf(Mtemp.getElmtContent(i,j)) + " " + resultStr[j + batasbawahParam - 1];
                        }
                    }
                }
                resultStr[i] = tempStr;
                i++;
            }
            }
        }


        return resultStr;
    }
}
