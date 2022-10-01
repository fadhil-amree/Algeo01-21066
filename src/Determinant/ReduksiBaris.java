package src.Determinant;

import src.Matrix;

public class ReduksiBaris {
    private static float tukarBaris = 0,kaliDengan = 1, hasil = 1, temp;
    public static boolean IsSegitiga(Matrix matrix)
    {
        // KAMUS
        int i = 0, j;
        boolean found = true;

        // ALGORITMA
        while (i < matrix.getNCol() && found)
        {
            j = 0;
            while (j < i)
            {
                if (matrix.getElmtContent(i, j) != 0)
                {
                    found = false;
                }
                j++;
            }
        }
        return found;
    }

    public static Matrix MatReduksi(Matrix matrix)
    {
        
        // KAMUS

        // ALGORITMA
        while (!(IsSegitiga(matrix)))
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
                            tukarBaris(matrix, i, j);
                            tukarBaris++;
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

                        kaliDengan *= matrix.getElmtContent(j, j);
                    }
                }
            }
        }
        return matrix;
    }

    public static float detReduksi(Matrix matrix)
    {
        matrix = MatReduksi(matrix);
        for (int i = 0; i < matrix.getNCol(); i++)
        {
            hasil *= matrix.getElmtContent(i, i);
        }
        return (float) (Math.pow(-1,tukarBaris) * (1/kaliDengan) * hasil);
    }

    public static void tukarBaris(Matrix matrix,int i,int j)
    {
        // KAMUS
        float temp;

        // ALGORITMA
        for (int k = 0; k < matrix.getNCol(); k++)
        {
            temp = matrix.getElmtContent(i, k);
            matrix.setElmtContent(i, k, matrix.getElmtContent(j, k)) ;
            matrix.setElmtContent(j, k, temp);
        }
    }
}
