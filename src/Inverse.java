package src;
import src.Matrix;
import src.Determinan;
import java.util.*;

public class Inverse {
    public static boolean isInvertible(Matrix matrix){
        return (Determinan.determinan(matrix)!=0);
    }

    public static Matrix getIdentityMatrix(int order){
        // Fungsi mengembalikan Sebuah matriks identitas berorde order
        // KAMUS LOKAL
        float[][] idMatrix = new float[order][order];
        int i,j;
        //ALGORITMA
        for(i=0;i<order;i++){
            idMatrix[i][i] = 1;
        }
        Matrix identityMatrix = new Matrix(idMatrix,order,order);
        return identityMatrix;
    }

    public static Matrix getInversebyOBE (Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan Inverse dari matriks tersebut

        //KAMUS LOKAL
        Matrix tempMatrix = new Matrix(matrix);
        Matrix inverseMatrix = new Matrix(getIdentityMatrix(matrix.nRow)); //inisialisasi matriks identitas
        int i,j,k,l;
        float etemp,pengali,einverse; //variabel elemen dan faktor pengali
        boolean found;

        // Proses Mereduksi dua buah matrix tetapi seolah-seolah seperti augmented matrix

        //ALGORITMA
        
        //Pengecekan apakah matriks invertible atau tidak
        if (isInvertible(tempMatrix)){

            for(i=0;i<=tempMatrix.nRow-1;i++)
            {   // i : indeks baris patokan
                // j : indeks baris selain baris i
                // k : indeks kolom 

                if (tempMatrix.content[i][i]==0) //jika elemen diagonal utama 0
                {
                    found = false;
                    l=i+1;
                    while(l<=tempMatrix.nRow-1 && !found) //cari elemen pada kolom tersebut di baris lain yang punya nilai !=0
                    {
                        if(tempMatrix.content[l][i]!=0) //jika ketemu
                        {
                            found=true;
                        }
                        else //jika tidak
                        {
                            l++;
                        }
                    }
                    for(k=0;k<=tempMatrix.nCol-1;k++) //swap baris
                    {
                        etemp = tempMatrix.content[i][k];
                        einverse = inverseMatrix.content[i][k];
                        tempMatrix.content[i][k] = tempMatrix.content[l][k];
                        inverseMatrix.content[i][k] = inverseMatrix.content[l][k];
                        tempMatrix.content[l][k] = etemp;
                        inverseMatrix.content[l][k] = einverse;
                    }
                }

                //Forward Phase
                for (j=i+1;j<=tempMatrix.nRow-1;j++)
                {
                    pengali = tempMatrix.content[j][i]/tempMatrix.content[i][i];
                    for(k=i;k<=tempMatrix.nCol-1;k++)
                    {   //OBE tempMatrix
                        tempMatrix.content[j][k] -= pengali*tempMatrix.content[i][k];
                        //OBE inverseMatrix
                        inverseMatrix.content[j][k] -= pengali*inverseMatrix.content[i][k];
                    }
                }

                //Backward Phase
                for (j=0;j<=i-1;j++)
                {
                    pengali = tempMatrix.content[j][i]/tempMatrix.content[i][i];
                    for(k=0;k<=tempMatrix.nCol-1;k++)
                    {   //OBE tempMatrix
                        tempMatrix.content[j][k] -= pengali*tempMatrix.content[i][k];
                        //OBE inverseMatrix
                        inverseMatrix.content[j][k] -= pengali*inverseMatrix.content[i][k];
                    }
                }

                //bentuk leading 1
                if (tempMatrix.content[i][i] != 1)
                {
                    pengali = 1/tempMatrix.content[i][i];
                    for(k=0; k<=tempMatrix.nCol-1; k++){
                        //Membentuk leading one
                        tempMatrix.content[j][k] *= pengali;
                        inverseMatrix.content[j][k] *= pengali;
                    }
                }
            }
            return inverseMatrix;
        }
        else{
            return matrix; //Default saat matriks tidak invertible
        }
    }

    public static Matrix getInversebyAdj(Matrix matrix){
        return matrix;
    }
}
