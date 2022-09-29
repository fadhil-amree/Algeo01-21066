package src.SPL;
import src.Matrix;
import src.Determinant.*;

public class Gauss {
    float[] result;
    public void splbyGauss(Matrix matrix)
    {
        // KAMUS
        /* Lakukan OBE */
        int i1, j1, j2, k, n; /*iterasi*/
        float temp;
        matrix = MatOBE(matrix);  // OBE

        // ALGORITMA
        for (i1 = 0; i1 < matrix.getNRow(); i1++)
        {
            for (j1 = 0; j1 < matrix.getNCol(); j1++)
            {
                /* Ubah elemen jika pada elemen pertama tidak nol */
                if (matrix.getElmtContent(i1, j1) != 0)
                {
                    temp = matrix.getElmtContent(i1, j1);
                    for (j2 = 0; j2 < matrix.getNCol(); j2++)
                    {
                        matrix.setElmtContent(i1, j2, matrix.getElmtContent(i1, j2) / temp);
                    }
                    break;
                }
            }
        }

        /* Olah Hasil */
        /* Kasus 1 : Solusi Tunggal */
        /* Kasus 2 : Solusi Banyak */
        /* Kasus 3 : Tidak ada Solusi */

    }

    public Matrix MatOBE(Matrix matrix)
    {
        float temp;
        
        // KAMUS

        // ALGORITMA
        while (!(ReduksiBaris.IsSegitiga(matrix)))
        {
            // Tukar baris matrix yang elemen diagonalnya 0
            int i, j;
            for (i = 0; i < matrix.getNCol(); i++)
            {
                if (matrix.getElmtContent(i, i) == 0)
                {
                    j = i + 1;
                    while (j < matrix.getNCol())
                    {
                        if (matrix.getElmtContent(j, i)!= 0)
                        {
                            ReduksiBaris.tukarBaris(matrix, i, j);
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
