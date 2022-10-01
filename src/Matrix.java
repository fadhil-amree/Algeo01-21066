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
        // KAMUS LOKAL
        String[][] MatrixString; //matriks string berasal dari File
        String data; //data yang dibaca dari file
        String[] IsiData; //data yang sudah di split
        int row, col, i, j; //index
        
        //ALGORITMA
        /* Inisialisasi */
        MatrixString = new String[9999][9999];
        
        /* Membaca file */
        try {
            String temp = "../test/input/"+namaFile;
            File isiFile = new File(temp);
            Scanner myReader = new Scanner(isiFile);

            row = 0; /* hitung baris */
            col = 0; /* hitung kolom */

            /* Iterasi untuk membaca isi file */
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                IsiData = data.split("");
                for (col = 0; col < IsiData.length; col++) {
                    MatrixString[row][col] = IsiData[col];
                }
                row++;
                col = IsiData.length;
            }
            myReader.close();
            
            /* Inisialisasi ukuran matriks */
            float MatrixHasil[][] = new float[row][col];
            
            /* Mengisi matrix hasil dengan casting hasil matrix dari matrix string */
            for (i = 0; i < row; i++) {
                for (j = 0; j < col; j++) {
                    MatrixHasil[i][j] = Float.valueOf(MatrixString[i][j]);
                }
            }

            /* Output Matrix hasil akhir */
            for(i=0;i<row;i++){
                for(j=0;j<col;j++){
                    System.out.print(MatrixHasil[i][j] + " ");
                }
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan");
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

    public static void saveHasil(Matrix matrix, String namaFile) throws IOException{
        // namaFile sudah dalam .txt
        // write hasil dalam Matrix

        //KAMUS LOKAL
        int i,j;
        String text;

        //ALGORITMA
        String path = "../test/hasil/"+namaFile; 
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        
        for (i = 0 ; i < matrix.getNRow(); i++)
        {
            text = "";
            for (j = 0 ; j < matrix.getNCol(); j++)
            {
                if (j != matrix.getNCol()-1)
                {
                    text += String.valueOf(matrix.getElmtContent(i,j)) + " ";
                }
                else
                {
                    text += String.valueOf(matrix.getElmtContent(i,j));
                }
            }
            bw.write(text);
            if (i != matrix.getNRow()-1)
            {
                bw.newLine();
            }
        }
        bw.close();
    }
    
    public static void saveHasil(float[] array, String namaFile) throws IOException{
        // namaFile sudah dalam .txt
        // write hasil dalam array
        
        //KAMUS LOKAL
        int i;
        String text = "";
    
        //ALGORITMA
        String path = "../test/hasil/"+namaFile; 
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);
    
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (i = 0 ; i < array.length; i++)
        {
            if (i != array.length-1)
            {
                text += String.valueOf(array[i]) + " ";
            }
            else
            {
                text += String.valueOf(array[i]);
            }
        }
        bw.write(text); 
        bw.close();
    }

    public static Matrix[] splitAugmented(Matrix augmented){
        // Fungsi untuk mengembalikan list of Matrix yang berisi matrixkoef dan matrixre
        // KAMUS LOKAL
        Matrix[] listMatrix = new Matrix[2];
        float[][] matrixk, matrixr;
        Matrix matrixkoef, matrixres;
        int i,j;
        // ALGORITMA
        matrixk = new float[augmented.getNRow()][augmented.getNCol()-1];
        matrixr = new float[augmented.getNRow()][1];
        for (i=0;i<augmented.getNRow();i++){
            for(j=0;j<augmented.getNCol();j++){
                if (j == augmented.getNCol()-1){
                    matrixr[i][j] = augmented.getElmtContent(i, augmented.getNCol()-1);
                }
                if (j != augmented.getNCol()-1){
                    matrixk[i][j] = augmented.getElmtContent(i, j);
                }
            }
        }
        matrixkoef = new Matrix(matrixk,augmented.getNRow(),augmented.getNCol()-1);
        matrixres = new Matrix(matrixr, augmented.getNRow(), 1);
        listMatrix[0] = matrixkoef;
        listMatrix[1] = matrixres;
        return listMatrix;
    }
    
    public static Matrix[] splitAugmentedMLR(Matrix augmented)
    {
        // Fungsi untuk mengembalikan list of Matrix yang berisi matrixkoef dan matrixre
        // KAMUS LOKAL
        Matrix[] listMatrix = new Matrix[2];
        float[][] matrixk, matrixr;
        Matrix matrixkoef, matrixres;
        int i,j;
        // ALGORITMA
        matrixk = new float[augmented.getNRow()][augmented.getNCol()-1];
        matrixr = new float[augmented.getNRow()][1];
        for (i=0;i<augmented.getNRow();i++){
            for(j=0;j<augmented.getNCol()-1;j++){
                if (j == 0){
                    matrixr[i][j] = augmented.getElmtContent(i, augmented.getNCol()-1);
                } 
                if (j != 0){
                    matrixk[i][j-1] = augmented.getElmtContent(i, j);
                }
            }
        }
        matrixkoef = new Matrix(matrixk,augmented.getNRow(),augmented.getNCol()-1);
        matrixres = new Matrix(matrixr, augmented.getNRow(), 1);
        listMatrix[0] = matrixkoef;
        listMatrix[1] = matrixres;
        return listMatrix;   
    }
    
}