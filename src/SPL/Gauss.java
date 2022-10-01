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
        int i1, j1, j2, k, n, i, j; /*iterasi*/
        float temp;
        float[][] btemp;
        btemp = new float[matrixkoef.getNRow()][matrixkoef.getNCol()];
        Matrix tempMatrixkoef = new Matrix(btemp, matrixkoef.getNRow(), matrixkoef.getNCol());

        // ALGORITMA
        /* Lakukan OBE */ /* Benerin dulu OBE-nya mas */
        // ALGORITMA
        while (!(ReduksiBaris.IsSegitiga(matrixkoef)))
        {
            // Tukar baris matrix yang elemen diagonalnya 0
            for (i = 0; i < matrixkoef.getNRow(); i++)
            {
                if (matrixkoef.getElmtContent(i, i) == 0)
                {
                    j = i + 1;
                    while (j < matrixkoef.getNCol())
                    {
                        if (matrixkoef.getElmtContent(j, i)!= 0)
                        {
                            ReduksiBaris.tukarBaris(matrixkoef, i, j);
                            ReduksiBaris.tukarBaris(matrixres, i, j);
                        }
                        j++;
                    }
                }
            }

            // tambah baris dengan baris lainnya
            for (i = 0; i < matrixkoef.getNRow(); i++)
            {
                for (j = 0; j < i; j++)
                {
                    // Cek apakah elemen pada matrix bernilai 0
                    if (matrixkoef.getElmtContent(i, j) != 0)
                    {

                        // Cari KPK
                        float KPK = matrixkoef.getElmtContent(i, j) * matrixkoef.getElmtContent(j, j);
                        float kali = KPK / matrixkoef.getElmtContent(i, j);

                        // kalikan baris i dengan konstanta kali
                        int col;
                        for (col = 0; col < matrixkoef.getNCol(); col++)
                        {
                            temp = matrixkoef.getElmtContent(i, col) * kali;
                            matrixkoef.setElmtContent(i, col, temp);
                            temp = matrixres.getElmtContent(i, 0) * kali;
                            matrixres.setElmtContent(i, 0, temp);
                        }

                        float kelipatan = KPK / matrixkoef.getElmtContent(j, j);

                        for (col = 0; col < matrixkoef.getNCol(); col++)
                        {
                            temp = matrixkoef.getElmtContent(i, col) - (matrixkoef.getElmtContent(j, col) * kelipatan);
                            matrixkoef.setElmtContent(i, col, temp);
                            temp = matrixres.getElmtContent(i, 0) - (matrixres.getElmtContent(j, 0) * kelipatan);
                            matrixres.setElmtContent(i, 0, temp);
                        }
                    }
                }
            }
        }
        

        /* Lakukan perhitungan hasil */

        for (i1 = 0; i1 < matrixkoef.getNRow(); i1++)
        {
            for (j1 = 0; j1 < matrixkoef.getNCol(); j1++)
            {
                /* Ubah elemen jika pada elemen pertama tidak nol */
                if (matrixkoef.getElmtContent(i1, j1) != 0)
                {
                    temp = matrixkoef.getElmtContent(i1, j1);
                    for (j2 = 0; j2 < matrixkoef.getNCol(); j2++)
                    {
                        matrixkoef.setElmtContent(i1, j2, matrixkoef.getElmtContent(i1, j2) / temp);
                    }
                    matrixres.setElmtContent(i1, 0, matrixres.getElmtContent(i1, 0) / temp);
                    break;
                }
            }
        }

        /* Olah Hasil */
        /* Kasus 1 : Solusi Tunggal */
        if (matrixkoef.getNCol() == matrixkoef.getNRow() && DiagonalUtama1(matrixkoef))
        {
            for (i = matrixkoef.getNCol() - 1; i >= 0; i--)
            {
                tempresult[i] = matrixkoef.getElmtContent(i, matrixkoef.getNCol());
                for (j1 = matrixkoef.getNCol() - 1; j1 > i; j1--)
                {
                    // tempresult[i] -= matrixkoef.getElmtContent(i, j1) * tempresult[j1];
                }
            }
        }

        /* Kasus 2 : Solusi Banyak */
        // else if (DiagonalUtama1(matrixkoef) && )
        // {

        // }
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

    // public Matrix MatOBE(Matrix matrixkoef, Matrix matrixres)
    // {
    //     float temp;
        
    //     // KAMUS

    //     // ALGORITMA
    //     while (!(ReduksiBaris.IsSegitiga(matrixkoef)))
    //     {
    //         // Tukar baris matrix yang elemen diagonalnya 0
    //         int i, j;
    //         for (i = 0; i < matrixkoef.getNRow(); i++)
    //         {
    //             if (matrixkoef.getElmtContent(i, i) == 0)
    //             {
    //                 j = i + 1;
    //                 while (j < matrixkoef.getNCol())
    //                 {
    //                     if (matrixkoef.getElmtContent(j, i)!= 0)
    //                     {
    //                         ReduksiBaris.tukarBaris(matrixkoef, i, j);
    //                         ReduksiBaris.tukarBaris(matrixres, i, j);
    //                     }
    //                     j++;
    //                 }
    //             }
    //         }

    //         // tambah baris dengan baris lainnya
    //         for (i = 0; i < matrixkoef.getNRow(); i++)
    //         {
    //             for (j = 0; j < i; j++)
    //             {
    //                 // Cek apakah elemen pada matrix bernilai 0
    //                 if (matrixkoef.getElmtContent(i, j) != 0)
    //                 {

    //                     // Cari KPK
    //                     float KPK = matrixkoef.getElmtContent(i, j) * matrixkoef.getElmtContent(j, j);
    //                     float kali = KPK / matrixkoef.getElmtContent(i, j);

    //                     // kalikan baris i dengan konstanta kali
    //                     int col;
    //                     for (col = 0; col < matrixkoef.getNCol(); col++)
    //                     {
    //                         temp = matrixkoef.getElmtContent(i, col) * kali;
    //                         matrixkoef.setElmtContent(i, col, temp);
    //                         temp = matrixres.getElmtContent(i, 0) * kali;
    //                         matrixres.setElmtContent(i, 0, temp);
    //                     }

    //                     float kelipatan = KPK / matrixkoef.getElmtContent(j, j);

    //                     for (col = 0; col < matrixkoef.getNCol(); col++)
    //                     {
    //                         temp = matrixkoef.getElmtContent(i, col) - (matrixkoef.getElmtContent(j, col) * kelipatan);
    //                         matrixkoef.setElmtContent(i, col, temp);
    //                         temp = matrixres.getElmtContent(i, 0) - (matrixres.getElmtContent(j, 0) * kelipatan);
    //                         matrixres.setElmtContent(i, 0, temp);
    //                     }
    //                 }
    //             }
    //         }
    //     }
    //     return matrixkoef;
    // }

    
}
