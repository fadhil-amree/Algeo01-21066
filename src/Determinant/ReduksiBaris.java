package src.Determinant;

public class ReduksiBaris {
    public boolean IsSegitiga(float[][] matrix)
    {
        // KAMUS
        int i = 0, j;
        boolean found = true;

        // ALGORITMA
        while (i < matrix[0].length && found)
        {
            j = 0;
            while (j < i)
            {
                if (matrix[i][j] != 0)
                {
                    found = false;
                }
                j++;
            }
        }
        return found;
    }

    public double detReduksi(float[][] matrix)
    {
        // KAMUS
        float tukarBaris = 0,kaliDengan = 1, hasil = 1;

        // ALGORITMA
        while (!(IsSegitiga(matrix)))
        {
            // Tukar baris matrix yang elemen diagonalnya 0
            int i, j;
            for (i = 0; i < matrix[0].length; i++)
            {
                if (matrix[i][i] == 0)
                {
                    j = i + 1;
                    while (j < matrix[0].length)
                    {
                        if (matrix[j][i] != 0)
                        {
                            tukarBaris(matrix, i, j);
                            tukarBaris++;
                        }
                        j++;
                    }
                }
            }

            // tambah baris dengan baris lainnya
            for (i = 0; i < matrix[0].length; i++)
            {
                for (j = 0; j < i; j++)
                {
                    // Cek apakah elemen pada matrix bernilai 0
                    if (matrix[i][j] != 0)
                    {

                        // Cari KPK
                        float KPK = matrix[i][j] * matrix[j][j];
                        float kali = KPK / matrix[i][j];

                        // kalikan baris i dengan konstanta kali
                        int col;
                        for (col = 0; col < matrix[0].length; col++)
                        {
                            matrix[i][col] *= kali;
                        }

                        float kelipatan = KPK / matrix[j][j];

                        for (col = 0; col < matrix[0].length; col++)
                        {
                            matrix[i][col] -= (matrix[j][col] * kelipatan);
                        }

                        kaliDengan *= matrix[j][j];
                    }
                }
            }
        }
        for (int i = 0; i < matrix[0].length; i++)
        {
            hasil *= matrix[i][i];
        }
        return (Math.pow(-1,tukarBaris) * (1/kaliDengan) * hasil);
    }

    public void tukarBaris(float[][] matrix,int i,int j)
    {
        // KAMUS
        float temp;

        // ALGORITMA
        for (int k = 0; k < matrix[0].length; k++)
        {
            temp = matrix[i][k];
            matrix[i][k] = matrix[j][k];
            matrix[j][k] = temp;
        }
    }
}
