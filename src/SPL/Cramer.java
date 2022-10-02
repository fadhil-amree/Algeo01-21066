package src.SPL;
import src.*;
import src.Determinant.*;

public class Cramer {
    public static String[] splbyCramer(Matrix matrixkoef,Matrix matrixres)
    {
        // KAMUS
        int i, j, n; /*iterasi*/
        float[][] btemp;
        btemp = new float[matrixkoef.getNRow()][matrixkoef.getNCol()];
        Matrix temp = new Matrix(btemp,matrixkoef.getNRow(),matrixkoef.getNCol()); /* temp untuk mencari determinan kecilnya */
        String[] result = new String[matrixkoef.getNCol()]; /*array untuk menampung hasil*/
        float detUtama; /* determinan Matrix utama */
        float detKecil; /* determinan Matrix kecil */   

        // ALGORITMA
        /* Cari determinan utama */
        detUtama = Kofaktor.detKofaktor(matrixkoef);

        if (detUtama != 0)
        {
            for (i = 0; i < matrixkoef.getNCol(); i++)
            {
                /* Copy matrix koefisien ke temp */
                for (j = 0; j < matrixkoef.getNRow(); j++)
                {
                    for (n = 0; n < matrixkoef.getNCol(); n++)
                    {
                        temp.setElmtContent(j, n, matrixkoef.getElmtContent(j, n));
                    }
                }

                /* Ganti kolom ke-i dengan matrix hasil */
                for (j = 0; j < matrixres.getNRow(); j++)
                {
                    temp.setElmtContent(j, i, matrixres.getElmtContent(j, 0));
                }

                /* Cari determinan kecil */
                detKecil = Kofaktor.detKofaktor(temp);

                /* Cari hasil */
                result[i] = String.valueOf(detKecil/detUtama);
            }
        }
        else
        {
            result = new String[0];
        }
        return result;
    } 
    }
