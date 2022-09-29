package src;

import java.io.*;
import java.util.*;
import src.Determinant.*;

public class Matrix {
    private int nRow,nCol;
    private float[][] content;

    //Konstruktor input dari keyboard
    public Matrix(int nRow, int nCol){
        // Konstruktor untuk menghasilkan matriks berukuran nRow x nCol
        // I.S matrix sembarang
        // F.S matrix terdefinisi dan berukuran nRow x nCol, bisa berisi atau kosong
        // KAMUS LOKAL
        int i,j; //index
        float e; //elemen matrix
        Scanner input = new Scanner(System.in); //input
        //ALGORITMA
        this.nRow = nRow;
        this.nCol = nCol;
        this.content = new float[this.nRow][this.nCol];
        for(i=0;i<nRow;i++){ 
            for(j=0;j<nCol;j++){
                System.out.printf("Masukkan elemen baris ke-%d kolom ke-%d: ",i,j);
                e = input.nextFloat();
                this.content[i][j] = e;
            }
        }
    }

    //Konstruktor input dari file
    public Matrix(String namaFile) throws IOException{
        // Untuk nerima matriks
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada file
        File file = new File(namaFile);
        Scanner scan = new Scanner(file);

        String fileContent = "";
        while (scan.hasNextLine())
        {
            fileContent = fileContent.concat(scan.nextLine() + "\n");
        }
    } 

    //Konstruktor input berupa matriks
    public Matrix(float[][] matrix, int nRow, int nCol){
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada input 
        // KAMUS LOKAL
        int i,j; //index
        //ALGORITMA
        this.nRow = nRow;
        this.nCol = nCol;
        this.content = new float[this.nRow][this.nCol];
        for(i=0;i<this.nRow;i++){ 
            for(j=0;j<this.nCol;j++){
                this.content[i][j] = matrix[i][j];
            }
        }
    }
    //Konstruktor input berupa objek matrix
    public Matrix(Matrix matrix){
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada input (copy) 
        // KAMUS LOKAL
        int i,j; //index
        //ALGORITMA
        this.nRow = matrix.nRow;
        this.nCol = matrix.nCol;
        this.content = new float[this.nRow][this.nCol];
        for(i=0;i<this.nRow;i++){ 
            for(j=0;j<this.nCol;j++){
                this.content[i][j] = matrix.content[i][j];
            }
        }        
    }

    //Getter
    public int getNRow(){
        return this.nRow;
    }
    public int getNCol(){
        return this.nCol;
    }
    public float getElmtContent(int i, int j){
        return this.content[i][j];
    }
    public float[][] getContent(){
        return this.content;
    }

    //Setter

    public void setElmtContent(int i, int j, float x){
        this.content[i][j] = x;
    }
    public void setContent(float[][] newContent){
        this.content = newContent;
    }


    public void displayMatrix(){
        // Prosedur untuk menampilkan matrix
        // I.S matrix terdefinisi berisi nilai atau kosong
        // F.S Menampilkan matriks ke terminal dengan format
        // e1 e2 e3
        // e4 e5 e6

        //KAMUS LOKAL
        int i,j; //index
        //ALGORITMA
        for(i=0;i<this.nRow;i++){ 
            for(j=0;j<this.nCol;j++){
                if (j!= this.nCol-1){
                    System.out.printf("%.02f ", this.content[i][j]);
                } else{
                    System.out.printf("%.02f\n", this.content[i][j]);  
                }
            }
        }
    }

    public void multiplybyConstant(float k){
        // Prosedur untuk mengalikan matriks dengan suatu konstanta
        // I.S matrix terdefinisi berisi nilai atau kosong
        // F.S Setiap elemen pada matrix dikali dengan k
        //KAMUS LOKAL
        int i,j; //index
        //ALGORITMA
        for(i=0;i<this.nRow;i++){ 
            for(j=0;j<this.nCol;j++){
                this.content[i][j] *= k;
            }
        }

    }

    // Fungsi-funhgi statik yang berkenaan dengan Matrix
    // keyword statik menunjukkan bahwa ia bisa diakses tanpa harus melalui objek
    public static Matrix multiplyMatrix(Matrix m1, Matrix m2)
    {
        /* Prekondisi : Ukuran kolom  m1 = ukuran baris  m2 */
        /* Mengirim hasil perkalian matriks: salinan m1 * m2 */
        // KAMUS LOKAL
        float [][] temp = new float[m1.getNRow()][m2.getNCol()];
        int i,j,k;
        float e;
        // ALGORITMA
        for(i=0;i<=m1.getNRow()-1;i++)
        {
            for(j=0;j<=m2.getNCol()-1;j++)
            {
                e = 0;
                for (k=0;k<=m1.getNCol()-1;k++)
                {
                    e+= m1.getElmtContent(i,k)*m2.getElmtContent(k,j);
                }
                temp[i][j] = e;
            }
        }
        Matrix m3 = new Matrix(temp,m1.getNRow(),m2.getNCol());
        return m3;
    }
    public static boolean isInvertible(Matrix matrix){
        return (Kofaktor.detKofaktor(matrix)!=0);
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
        float[][] tMatrix = new float[matrix.getNRow()][matrix.getNCol()];
        int i,j;
        //ALGORITMA   
        for (i=0;i<=matrix.getNRow()-1;i++){
            for(j=0;j<=matrix.getNCol()-1;j++){
                tMatrix[i][j] = matrix.getElmtContent(j,i);
            }
        }
        Matrix transposeMatrix = new Matrix(tMatrix,matrix.getNRow(),matrix.getNCol());
        return transposeMatrix;
    }

    public static void saveHasil(boolean isMatrix){
        // write
        Scanner input = new Scanner(System.in);
        System.out.println("Masukkan nama file yang diinginkan");
        String temp = input.nextLine();
        String path = "../test/" + temp + ".txt";
        FileWriter writer = new FileWriter(path);
        // write ke txt tersebut
        writer.write();

    }
    

}