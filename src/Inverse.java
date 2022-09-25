package src;
import src.Matrix;
import src.Determinant.*;
import java.util.*;

public class Inverse {
    public static boolean isInvertible(Matrix matrix){
        return (Kofaktor.detKofaktor(matrix.content)!=0);
    }

    public static Matrix getIdentityMatrix(int order){
        // Fungsi mengembalikan Sebuah matriks identitas berorde order
        // KAMUS LOKAL
        float[][] idMatrix = new float[order][order];
        int i;
        //ALGORITMA
        for(i=0;i<order;i++){
            idMatrix[i][i] = 1;
        }
        Matrix identityMatrix = new Matrix(idMatrix,order,order);
        return identityMatrix;
    }

    public static Matrix getUndefMatrix(int order){
        // Fungsi mengembalikan Sebuah matriks undefine berorde order
        // KAMUS LOKAL
        float[][] undefMatrix = new float[order][order];
        int i,j;
        //ALGORITMA
        for(i=0;i<order;i++){
            for(j=0;j<order;j++){
            undefMatrix[i][i] = -999;
            }
        }
        Matrix undefinedMatrix = new Matrix(undefMatrix,order,order);
        return undefinedMatrix;
    }

    public static Matrix getTransposeMatrix(Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan Transpose dari matriks tersebut
        // KAMUS LOKAL
        float[][] tMatrix = new float[matrix.nRow][matrix.nCol];
        int i,j;
        //ALGORITMA   
        for (i=0;i<=matrix.nRow-1;i++){
            for(j=0;j<=matrix.nCol-1;j++){
                tMatrix[i][j] = matrix.content[j][i];
            }
        }
        Matrix transposeMatrix = new Matrix(tMatrix,matrix.nRow,matrix.nCol);
        return transposeMatrix;
    }

    public static Matrix getInversebyOBE (Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan Inverse dari matriks tersebut dengan metode Operasi Baris ELementer
        // Prekondisi : matrix adalah matriks persegi
        //KAMUS LOKAL
        Matrix tempMatrix = new Matrix(matrix);
        Matrix inverseMatrix = new Matrix(getIdentityMatrix(matrix.nRow)); //inisialisasi matriks identitas
        int i,j,k,l;
        float etemp,pengali,einverse; //variabel elemen dan faktor pengali
        boolean found;

        // Proses Mereduksi dua buah matrix tetapi seolah-seolah seperti augmented matrix

        //ALGORITMA
        if (matrix.nRow>=2){
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
                        for(k=0;k<=tempMatrix.nCol-1;k++)
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
            else{ //not invertible
                return getUndefMatrix(matrix.nRow); //Default saat matriks tidak invertible
            }
        } else { // matrix.nRow < 2
            return matrix;
        }
    }

    public static Matrix getInversebyAdj(Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan inverse dari matriks tersebut dengan metode Adjoin
        // Prekondisi : matrix adalah matriks persegi
        // KAMUS LOKAL
        float[][] minor; //matriks minor
        float[][] cofactorMatrix = new float[matrix.nRow][matrix.nCol];
        int i,j,k,l,bm,km; //indeks
        float det, c;
        // ALGORITMA
        //i,k: indeks baris
        //j,l: indeks kolom
        //bm: indeks baris minor
        //km: indeks kolom minor

        if (matrix.nRow >= 2){

            if (isInvertible(matrix)){
                if (matrix.nRow>2){
                    for(i=0;i<=matrix.nRow-1;i++){
                        for(j=0;j<=matrix.nCol-1;j++){
                            minor = new float[matrix.nRow-1][matrix.nCol-1]; //Inisiasi matriks minor 
                            bm =0;
                            for(k=0;k<=matrix.nRow-1;k++){
                                km = 0;
                                
                                for(l=0;l<=matrix.nCol-1;l++){
                                    if (k!=i && l!=j){ //Mengambil elemen yang termasuk minor i,j
                                        minor[bm][km] = matrix.content[k][l];
                                        km++; // kolom minor bergeser
                                    }
                                }
                                if (k!= i){
                                    bm++;
                                }
                            }
                            det = Kofaktor.detKofaktor(minor); // Menghitung determinan dari matriks minor
                            if ((i+j)%2==0){ //Menentukan tanda cofactor
                                c = 1;
                            } else{
                                c = -1;
                            }
                            c *= det; //Menghitung nilai cofactor
                            
                            // Menambahkan elemen matriks cofactor
                            cofactorMatrix[i][j] = c;
                        }
                    }
                    Matrix kofaktorMatrix = new Matrix(cofactorMatrix, matrix.nRow, matrix.nCol);
                    Matrix inverseMatrix = getTransposeMatrix(kofaktorMatrix);
                    det = Kofaktor.detKofaktor(matrix.content); //menghitung determinan matriks
                    inverseMatrix.multiplybyConstant(1/det); // 1/det * adj(matriks)
                    return inverseMatrix;
                } else { 
                    // matrix.nRow == 2
                    cofactorMatrix[0][0] = matrix.content[1][1];
                    cofactorMatrix[1][1] = matrix.content[0][0];
                    cofactorMatrix[0][1] = (-1) * matrix.content[0][1];
                    cofactorMatrix[1][0] = (-1) * matrix.content[1][0];
                    det = Kofaktor.detKofaktor(matrix.content); //menghitung determinan matriks
                    Matrix inverseMatrix = new Matrix(cofactorMatrix, matrix.nRow,matrix.nCol);
                    inverseMatrix.multiplybyConstant(1/det); // 1/det * adj(matriks)
                    return inverseMatrix;           
                }
            } else{ // not invertible
                return getUndefMatrix(matrix.nRow);
            }
        } else { //matrix.nRow < 2
            return matrix;
        }
    }
        public static void main (String[] args){
        int nRow, nCol;
        Scanner input = new Scanner(System.in);

        do{ 
            System.out.print("Masukkan Banyak Baris: ");
            nRow =  input.nextInt();
            System.out.println("");
        } while (nRow<0);
        do{ 
            System.out.print("Masukkan Banyak Kolom: ");
            nCol =  input.nextInt();
            System.out.println("");
        } while (nCol<0);
        Matrix matrix = new Matrix(nRow, nCol); 
        System.out.println("Matriks: ");
        matrix.displayMatrix();
        System.out.println("Inverse Matrix: ");

        Matrix inverseMatrix = getInversebyAdj(matrix);
        inverseMatrix.displayMatrix();
    }
}
