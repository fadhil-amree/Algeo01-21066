package src.SPL;
import src.Matrix;
import src.Determinant.*;

public class Cramer {
    public float[] splbyCramer(Matrix matrix)
    {
        // KAMUS
        int i, j, n; /*iterasi*/
        Matrix temp = new Matrix(matrix.getNRow() - 1,matrix.getNCol()); /* temp untuk mencari determinan kecilnya */
        float[] result = new float[matrix.getNCol() - 1]; /*array untuk menampung hasil*/
        float detUtama; /* determinan Matrix utama */

        // ALGORITMA
        /* Cari determinan utama */
        for (i = 0; i < matrix.getNRow() ; i++)
        {
            for (j = 0; j < matrix.getNCol() - 1; j++)
            {
                temp.setElmtContent(i, j, matrix.getElmtContent(i, j));
            }
        }
        detUtama = Kofaktor.detKofaktor(temp);

        if (detUtama != 0)
        {
        for (n = 0; n < matrix.getNCol() ; n++)
        {
            for (i = 0; i < matrix.getNRow() ; i++)
            {
                for (j = 0; j < matrix.getNCol() - 1; j++)
                {
                    if (j == n)
                    {
                        temp.setElmtContent(i, j, matrix.getElmtContent(i, matrix.getNCol() - 1));
                    }
                    else
                    {
                        temp.setElmtContent(i, j, matrix.getElmtContent(i, j));
                    }
                }
            }
            result[n] = Kofaktor.detKofaktor(temp) / detUtama;
        }
    } else 
    {
        result = null; // gaada
    }
    
    return result;
    }

}
