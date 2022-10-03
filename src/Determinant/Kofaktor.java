package src.Determinant;
import java.lang.Math;
import src.Matrix;

public class Kofaktor 
{
    // Mencari Matrix dengan metode Ekspansi Kofaktor

    //KAMUS

    //ALGORITMA

    public static double detKofaktor(Matrix matrix)
    {
        double result = 0;
        int i;
        if (matrix.getNCol() > 2)
        {
            for (i=0;i<matrix.getNCol();i++)
            {
                result += matrix.getElmtContent(0, i) * (Math.pow(-1,i)) * detKofaktor(getMinor(matrix,i));
            }
        }
        else
        {
            result = matrix.getElmtContent(0, 0) * matrix.getElmtContent(1, 1) - matrix.getElmtContent(0, 1) * matrix.getElmtContent(1, 0);
        }
        return result;
    }

    public static Matrix getMinor(Matrix matrix, int col)
    {
        double[][] temp;
        temp = new double[matrix.getNRow()-1][matrix.getNCol()-1];
        Matrix minor = new Matrix(temp, matrix.getNRow()-1,matrix.getNCol()-1);
        
        int iMinor = 0, jMinor = 0;
        int i, j;

        for (i = 1; i < matrix.getNRow(); i++)
        {
            for (j = 0; j < matrix.getNCol(); j++)
            {
                if (j != col)
                {
                    minor.setElmtContent(iMinor,jMinor,matrix.getElmtContent(i, j));
                    jMinor++;
                }
            }
            iMinor++;
            jMinor = 0;
        }
        return minor;
    }
}
