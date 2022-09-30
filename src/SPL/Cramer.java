package src.SPL;
import src.Matrix;
import src.Determinant.*;

public class Cramer {
    public float[] splbyCramer(Matrix matrixkoef,Matrix matrixres)
    {
        // KAMUS
        int i, j, n; /*iterasi*/
        Matrix temp = new Matrix(matrixkoef.getNRow(),matrixkoef.getNCol()); /* temp untuk mencari determinan kecilnya */
        float[] result = new float[matrixkoef.getNCol()]; /*array untuk menampung hasil*/
        float detUtama; /* determinan Matrix utama */

        // ALGORITMA
        /* Cari determinan utama */
        detUtama = Kofaktor.detKofaktor(matrixkoef);

        if (detUtama != 0)
        {
        for (n = 0; n < matrixkoef.getNCol() ; n++)
        {
            for (i = 0; i < matrixkoef.getNRow() ; i++)
            {
                for (j = 0; j < matrixkoef.getNCol() - 1; j++)
                {
                    if (j == n)
                    {
                        // kalau j == n, maka masukkan nilai-nilai matrix res ke temp

                        temp.setElmtContent(i, j, matrixres.getElmtContent(i, 0));
                    }
                    else
                    {
                        temp.setElmtContent(i, j, matrixkoef.getElmtContent(i, j));
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
