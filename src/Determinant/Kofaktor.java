package src.Determinant;
import java.lang.Math;

public class Kofaktor 
{
    // Mencari Matrix dengan metode Ekspansi Kofaktor

    //KAMUS

    //ALGORITMA

    public static float detKofaktor(float[][] matrix)
    {
        float result = 0;
        int i,j;
        if (matrix[0].length > 2)
        {
            for (i=0;i<matrix[0].length;i++)
            {
                result += matrix[0][i] * (Math.pow(-1,i)) * detKofaktor(getMinor(matrix,i));
            }
        }
        else
        {
            result = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        return result;
    }

    public static float[][] getMinor(float[][] matrix, int col)
    {
        float[][] minor = new float[matrix[0].length-1][matrix[0].length-1];
        
        int iMinor = 0, jMinor = 0;
        int i, j;

        for (i = 1; i < matrix[0].length; i++)
        {
            for (j = 0; j < matrix[0].length; j++)
            {
                if (j != col)
                {
                    minor[iMinor][jMinor] = matrix[i][j];
                    jMinor++;
                }
            }
            iMinor++;
            jMinor = 0;
        }
        return minor;
    }
}
