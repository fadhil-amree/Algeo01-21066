package src;
import src.Matrix;

public class MLR {
    public float[] MLR(Matrix matrixkoef, Matrix matrixres)
    {
        // KAMUS
        int i, j, col;
        Matrix tempkoef, tempres;
        float[] result = new float[matrixkoef.getNCol()];

        // ALGORITMA
        float[][] x = new float[matrixkoef.getNCol() + 1][matrixkoef.getNCol() + 1]; 
        tempkoef = new Matrix(x, matrixkoef.getNCol() + 1, matrixkoef.getNCol() +1);
        x = new float[tempkoef.getNRow()][1];
        tempres = new Matrix(x, tempkoef.getNRow(), 1);
        /* Lakukan normal equation */
        for (i = 0; i < tempkoef.getNCol() ; i++)
        {
            for (j = 0; j< tempkoef.getNCol(); j++)
            {
                if (i == 0 && j == 0)
                {
                    /* n */
                    tempkoef.setElmtContent(i,j, matrixkoef.getNRow()); 
                }
                else if (i != 0 && j == 0)
                {
                    /* sigma x */
                    tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(matrixkoef, i - 1)));
                }
                else if (i == 0 && j != 0)
                {
                    /* sigma x */
                    tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(matrixkoef, j - 1)));
                }
                else if (i == j && i != 0 && j != 0)
                {
                    /* Square x */
                    tempkoef.setElmtContent(i,j, sumOfSquare(arrOfColumn(matrixkoef, i-1)));
                }
                else 
                {
                    /* perkalian x ke a dan x ke b */
                    tempkoef.setElmtContent(i, j, sumOfTwoColumn(arrOfColumn(matrixkoef, i - 1), arrOfColumn(matrixkoef, j - 1) ));
                }

            }
        }

        /* jika sudah cek apakah matriks A invertible */
        /* jika non invertible lakukan while do normal equation dengan menghapus salah satu parameter bisa dimulai dari 1 lalu 2 lalu 3  dengan membuat temporary matrix baru dengan col lama -1*/
        col = 0;
        while (!Matrix.isInvertible(tempkoef) && col < matrixkoef.getNCol())
        {
            /* kalau ga invertible, hapus 1 kolom*/
            /* Copy Matrixkoef ke tempMatKoef dan menghilangkan parameter pada ke-x */
            Matrix tempMatKoef = MinorMatKoef(matrixkoef, col);
            x = new float[tempMatKoef.getNCol() + 1][tempMatKoef.getNCol() + 1];
            tempkoef = new Matrix(x, tempMatKoef.getNCol() + 1, tempMatKoef.getNCol() +1);
            x = new float[tempkoef.getNRow()][1];
            tempres = new Matrix(x, tempkoef.getNRow(), 1);
            
            for (i = 0; i < tempkoef.getNCol() ; i++)
            {
                for (j = 0; j< tempkoef.getNCol(); j++)
                {
                    if (i == 0 && j == 0)
                    {
                        /* n */
                        tempkoef.setElmtContent(i,j, tempMatKoef.getNRow());
                    }
                    else if (i != 0 && j ==0)
                    {
                        /* sigma x */
                        tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(tempMatKoef, i - 1)));
                    }
                    else if (i == 0 && j != 0)
                    {
                        /* sigma x */
                        tempkoef.setElmtContent(i,j, sumOf(arrOfColumn(tempMatKoef, j - 1)));
                    }
                    else if (i == j && i != 0 && j != 0)
                    {
                        /* Square x */
                        tempkoef.setElmtContent(i,j, sumOfSquare(arrOfColumn(tempMatKoef, i-1)));
                    }
                    else 
                    {
                        /* perkalian x ke a dan x ke b */
                        tempkoef.setElmtContent(i, j, sumOfTwoColumn(arrOfColumn(tempMatKoef, i - 1), arrOfColumn(tempMatKoef, j - 1) ));
                    }

                }
            }
            
            col += 1;
        }

        /* Cari nilai H */
        for (i = 0; i < tempres.getNRow(); i++)
        {
            if (i == 0)
            {
                tempres.setElmtContent(i, 0, sumOf(arrOfColumn(matrixres, 0)));
            }
            else
            {
                tempres.setElmtContent(i, 0, sumOfTwoColumn(arrOfColumn(matrixres, 0), arrOfColumn(matrixkoef, i-1)));
            }
        }

        /* jika invertible lakukan b = A^-1 * H */

        result = arrOfColumn(Matrix.multiplyMatrix(Inverse.getInversebyAdj(tempkoef), tempres), 0) ;

        /* return b */
        return result;
    }


    public float[] arrOfColumn(Matrix matrix, int j)
    {
        // KAMUS
        int i ;
        float[] result = new float[matrix.getNCol()];

        // ALGORITMA
        for (i = 0; i < matrix.getNRow(); i++)
        {
            result[i] = matrix.getElmtContent(i, j);
        }

        return result;
    }

    public float sumOf(float[] arr) /* untuk satu kolom */
    {
        float sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            sum += arr[i];
        }
        return sum;
    }

    public float sumOfSquare(float[] arr) /* untuk kuadratik satu kolom */
    {
        /* I.S array of Float terdefinisi */
        /* F.S mengembalikan hasil penjumlahan perkalian arr[i] dengan dirinya sendiri */
        float sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            sum += (arr[i] * arr[i]);
        }
        
        return sum;
    }

    public float sumOfTwoColumn(float[] arr1, float[] arr2) /* untuk perkalian dua kolom */
    {
        /* I.S array of Float terdefinisi */
        /* F.S mengembalikan hasil penjumlahan perkalian arr1[i] dengan arr2[i] */
        float sum = 0;
        for (int i = 0; i < arr1.length; i++)
        {
            sum += (arr1[i] * arr2[i]);
        }
        
        return sum;
    }

    public Matrix MinorMatKoef(Matrix matrixkoef, int col)
    {
        /* I.S matrix terdefinisi */
        /* F.S mengembalikan matriks minor dari matrixkoef dengan menghilangkan kolom ke-col */
        float[][] x = new float[matrixkoef.getNRow()][matrixkoef.getNCol() - 1];
        Matrix result = new Matrix(x, matrixkoef.getNRow(), matrixkoef.getNCol() - 1);
        int iMinor = 0, jMinor = 0;
        int i, j;

        for (i = 0; i < matrixkoef.getNRow(); i++)
        {
            for (j = 0; j < matrixkoef.getNCol(); j++)
            {
                if (j != col)
                {
                    result.setElmtContent(iMinor, jMinor, matrixkoef.getElmtContent(i, j));
                    jMinor += 1;
                }
            }
            iMinor += 1;
            jMinor = 0;
        }

        return result;
    }
}


/* Referensi : https://www.statmat.net/regresi-linier-berganda/ */