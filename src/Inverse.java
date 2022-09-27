package src;
import src.Matrix;
import src.Determinant.*;
import java.util.*;

public class Inverse {
    public static Matrix getInversebyOBE (Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan Inverse dari matriks tersebut dengan metode Operasi Baris ELementer
        // Prekondisi : matrix adalah matriks persegi
        //KAMUS LOKAL
        Matrix tempMatrix = new Matrix(matrix);
        Matrix inverseMatrix = new Matrix(Matrix.getIdentityMatrix(matrix.getNRow())); //inisialisasi matriks identitas
        int i,j,k,l;
        float etemp,pengali,einverse; //variabel elemen dan faktor pengali
        boolean found;

        // Proses Mereduksi dua buah matrix tetapi seolah-seolah seperti augmented matrix

        //ALGORITMA
        if (matrix.getNRow()>=2){
            //Pengecekan apakah matriks invertible atau tidak
            if (Matrix.isInvertible(tempMatrix)){

                for(i=0;i<=tempMatrix.getNRow()-1;i++)
                {   // i : indeks baris patokan
                    // j : indeks baris selain baris i
                    // k : indeks kolom 

                    if (tempMatrix.getElmtContent(i,i)==0) //jika elemen diagonal utama 0
                    {
                        found = false;
                        l=i+1;
                        while(l<=tempMatrix.getNRow()-1 && !found) //cari elemen pada kolom tersebut di baris lain yang punya nilai !=0
                        {
                            if(tempMatrix.getElmtContent(l,i)!=0) //jika ketemu
                            {
                                found=true;
                            }
                            else //jika tidak
                            {
                                l++;
                            }
                        }
                        for(k=0;k<=tempMatrix.getNCol()-1;k++) //swap baris
                        {
                            etemp = tempMatrix.getElmtContent(i,k);
                            einverse = inverseMatrix.getElmtContent(i,k);
                            tempMatrix.setElmtContent(i,k,tempMatrix.getElmtContent(l,k));
                            inverseMatrix.setElmtContent(i,k,inverseMatrix.getElmtContent(l,k));
                            tempMatrix.setElmtContent(l,k,etemp);
                            inverseMatrix.setElmtContent(l,k,einverse);
                        }
                    }

                    //Forward Phase
                    for (j=i+1;j<=tempMatrix.getNRow()-1;j++)
                    {
                        pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                        for(k=0;k<=tempMatrix.getNCol()-1;k++)
                        {   //OBE tempMatrix
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                            //OBE inverseMatrix
                            inverseMatrix.setElmtContent(j,k, inverseMatrix.getElmtContent(j,k) - pengali*inverseMatrix.getElmtContent(i,k));
                        }
                    }

                    //Backward Phase
                    for (j=0;j<=i-1;j++)
                    {
                        pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                        for(k=0;k<=tempMatrix.getNCol()-1;k++)
                        {   //OBE tempMatrix
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                            //OBE inverseMatrix
                            inverseMatrix.setElmtContent(j,k, inverseMatrix.getElmtContent(j, k) - pengali*inverseMatrix.getElmtContent(i,k));
                        }
                    }

                    //bentuk leading 1
                    if (tempMatrix.getElmtContent(i,i) != 1)
                    {
                        pengali = 1/tempMatrix.getElmtContent(i,i);
                        for(k=0; k<=tempMatrix.getNCol()-1; k++){
                            //Membentuk leading one
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) * pengali);
                            inverseMatrix.setElmtContent(j,k, inverseMatrix.getElmtContent(j, k) * pengali);
                        }
                    }

                }
                return inverseMatrix;
            }
            else{ //not invertible
                return Matrix.getUndefMatrix(matrix.getNRow()); //Default saat matriks tidak invertible
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
        float[][] cofactorMatrix = new float[matrix.getNRow()][matrix.getNCol()];
        int i,j,k,l,bm,km; //indeks
        float det, c;
        // ALGORITMA
        //i,k: indeks baris
        //j,l: indeks kolom
        //bm: indeks baris minor
        //km: indeks kolom minor

        if (matrix.getNRow() >= 2){

            if (Matrix.isInvertible(matrix)){
                if (matrix.getNRow()>2){
                    for(i=0;i<=matrix.getNRow()-1;i++){
                        for(j=0;j<=matrix.getNCol()-1;j++){
                            minor = new float[matrix.getNRow()-1][matrix.getNCol()-1]; //Inisiasi matriks minor 
                            bm =0;
                            for(k=0;k<=matrix.getNRow()-1;k++){
                                km = 0;
                                
                                for(l=0;l<=matrix.getNCol()-1;l++){
                                    if (k!=i && l!=j){ //Mengambil elemen yang termasuk minor i,j
                                        minor[bm][km] = matrix.getElmtContent(k,l);
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
                    Matrix kofaktorMatrix = new Matrix(cofactorMatrix, matrix.getNRow(), matrix.getNCol());
                    Matrix inverseMatrix = Matrix.getTransposeMatrix(kofaktorMatrix);
                    det = Kofaktor.detKofaktor(matrix.getContent()); //menghitung determinan matriks
                    inverseMatrix.multiplybyConstant(1/det); // 1/det * adj(matriks)
                    return inverseMatrix;
                } else { 
                    // matrix.nRow == 2
                    cofactorMatrix[0][0] = matrix.getElmtContent(1,1);
                    cofactorMatrix[1][1] = matrix.getElmtContent(0,0);
                    cofactorMatrix[0][1] = (-1) * matrix.getElmtContent(0,1);
                    cofactorMatrix[1][0] = (-1) * matrix.getElmtContent(1,0);
                    det = Kofaktor.detKofaktor(matrix.getContent()); //menghitung determinan matriks
                    Matrix inverseMatrix = new Matrix(cofactorMatrix, matrix.getNRow(),matrix.getNCol());
                    inverseMatrix.multiplybyConstant(1/det); // 1/det * adj(matriks)
                    return inverseMatrix;           
                }
            } else{ // not invertible
                return Matrix.getUndefMatrix(matrix.getNRow());
            }
        } else { //matrix.nRow < 2
            return matrix;
        }
    }
    
}
