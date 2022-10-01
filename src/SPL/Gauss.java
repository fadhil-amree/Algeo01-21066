package src.SPL;
import src.*;
import src.Determinant.*;

public class Gauss {
    public static String[] splbyGauss(Matrix matrixkoef, Matrix matrixres)
    {
        // KAMUS
        /* Lakukan OBE */
        float[] tempresult = new float[matrixkoef.getNCol()];
        String[] result;
        int i1, j1, j2, k, n, i; /*iterasi*/
        float temp;
        Matrix tempMatrixkoef = new Matrix(matrixkoef.getNRow(), matrixkoef.getNCol());

        // ALGORITMA
        /* Lakukan OBE */

        /* Lakukan perhitungan hasil */

        for (i1 = 0; i1 < tempMatrixkoef.getNRow(); i1++)
        {
            for (j1 = 0; j1 < tempMatrixkoef.getNCol(); j1++)
            {
                /* Ubah elemen jika pada elemen pertama tidak nol */
                if (tempMatrixkoef.getElmtContent(i1, j1) != 0)
                {
                    temp = tempMatrixkoef.getElmtContent(i1, j1);
                    for (j2 = 0; j2 < tempMatrixkoef.getNCol(); j2++)
                    {
                        tempMatrixkoef.setElmtContent(i1, j2, tempMatrixkoef.getElmtContent(i1, j2) / temp);
                    }
                    break;
                }
            }
        }

        /* Olah Hasil */
        /* Kasus 1 : Solusi Tunggal */
        if (tempMatrixkoef.getNCol() == tempMatrixkoef.getNRow() && DiagonalUtama1(tempMatrixkoef))
        {
            for (i = tempMatrixkoef.getNCol() - 1; i >= 0; i--)
            {
                tempresult[i] = tempMatrixkoef.getElmtContent(i, tempMatrixkoef.getNCol());
                for (j1 = tempMatrixkoef.getNCol() - 1; j1 > i; j1--)
                {
                    tempresult[i] -= tempMatrixkoef.getElmtContent(i, j1) * tempresult[j1];
                }
            }
        }

        /* Kasus 2 : Solusi Banyak */
        else if (DiagonalUtama1(tempMatrixkoef) && )
        {

        }
        /* Kasus 3 : Tidak ada Solusi */
        return null;
    }
    
    public static boolean DiagonalUtama1(Matrix matrixkoef)
    {
        // KAMUS
        int i, j;
        boolean foundNotOne = true;
        
        // ALGORITMA
        for (i = 0; i < matrixkoef.getNRow(); i++)
        {
            for (j = 0; j < matrixkoef.getNCol(); j++)
            {
                if (i == j)
                {
                    if (matrixkoef.getElmtContent(i, j) != 1)
                    {
                        foundNotOne = false;
                        break;
                    }
                }
            }
        }
        return foundNotOne;
    }

    public Matrix MatOBE(Matrix matrixkoef, Matrix matrixres)
    {
        float temp;
        
        // KAMUS

        // ALGORITMA
        while (!(ReduksiBaris.IsSegitiga(matrixkoef)))
        {
            // Tukar baris matrix yang elemen diagonalnya 0
            int i, j;
            for (i = 0; i < matrixkoef.getNCol(); i++)
            {
                if (matrixkoef.getElmtContent(i, i) == 0)
                {
                    j = i + 1;
                    while (j < matrixkoef.getNCol())
                    {
                        if (matrixkoef.getElmtContent(j, i)!= 0)
                        {
                            ReduksiBaris.tukarBaris(matrixkoef, i, j);
                        }
                        j++;
                    }
                }
            }

            // tambah baris dengan baris lainnya
            for (i = 0; i < matrix.getNCol(); i++)
            {
                for (j = 0; j < i; j++)
                {
                    // Cek apakah elemen pada matrix bernilai 0
                    if (matrix.getElmtContent(i, j) != 0)
                    {

                        // Cari KPK
                        float KPK = matrix.getElmtContent(i, j) * matrix.getElmtContent(j, j);
                        float kali = KPK / matrix.getElmtContent(i, j);

                        // kalikan baris i dengan konstanta kali
                        int col;
                        for (col = 0; col < matrix.getNCol(); col++)
                        {
                            temp = matrix.getElmtContent(i, col) * kali;
                            matrix.setElmtContent(i, col, temp);
                        }

                        float kelipatan = KPK / matrix.getElmtContent(j, j);

                        for (col = 0; col < matrix.getNCol(); col++)
                        {
                            temp = matrix.getElmtContent(i, col) - (matrix.getElmtContent(j, col) * kelipatan);
                            matrix.setElmtContent(i, col, temp);
                        }
                    }
                }
            }
        }
        return matrix;
    }

    
}
