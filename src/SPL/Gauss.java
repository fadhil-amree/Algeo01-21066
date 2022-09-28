package src.SPL;
import src.Matrix;
import src.Determinant.*;

public class Gauss {
    float[] result;
    public void splbyGauss(Matrix matrix)
    {
        // KAMUS
        Matrix matriks = MatReduksi(matrix);
        int i1, j1, j2, k, n; /*iterasi*/
        float temp;

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
}
