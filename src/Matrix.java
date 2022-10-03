package src;

import java.io.*;
import java.util.*;

import javax.xml.transform.Source;

import src.Determinant.*;

public class Matrix {
    private int nRow,nCol;
    private double[][] content;

    //Konstruktor input dari keyboard
    public Matrix(int nRow, int nCol){
        // Konstruktor untuk menghasilkan matriks berukuran nRow x nCol
        // I.S matrix sembarang
        // F.S matrix terdefinisi dan berukuran nRow x nCol, bisa berisi atau kosong
        // KAMUS LOKAL
        int i,j; //index
        double e; //elemen matrix
        Scanner input = new Scanner(System.in); //input
        //ALGORITMA
        this.nRow = nRow;
        this.nCol = nCol;
        this.content = new double[this.nRow][this.nCol];
        for(i=0;i<nRow;i++){ 
            for(j=0;j<nCol;j++){
                System.out.printf("Masukkan elemen baris ke-%d kolom ke-%d: ",i,j);
                e = input.nextDouble();
                this.content[i][j] = e;
            }
        }
    }


    //Konstruktor input berupa matriks
    public Matrix(double[][] matrix, int nRow, int nCol){
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada input 
        // KAMUS LOKAL
        int i,j; //index
        //ALGORITMA
        this.nRow = nRow;
        this.nCol = nCol;
        this.content = new double[this.nRow][this.nCol];
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
        this.content = new double[this.nRow][this.nCol];
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
    public double getElmtContent(int i, int j){
        return this.content[i][j];
    }
    public double[][] getContent(){
        return this.content;
    }

    //Setter

    public void setElmtContent(int i, int j, double x){
        this.content[i][j] = x;
    }
    public void setContent(double[][] newContent){
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

    public void multiplybyConstant(double k){
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
        double [][] temp = new double[m1.getNRow()][m2.getNCol()];
        int i,j,k;
        double e;
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
        if (matrix.getNRow() == matrix.getNCol()){
            return (ReduksiBaris.detReduksi(matrix)!=0);
        } else{
            return false;
        }
    }

    public static Matrix getIdentityMatrix(int order){
        // Fungsi mengembalikan Sebuah matriks identitas berorde order
        // KAMUS LOKAL
        double[][] idMatrix = new double[order][order];
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
        double[][] undefMatrix = new double[order][order];
        int i,j;
        //ALGORITMA
        for(i=0;i<order;i++){
            for(j=0;j<order;j++){
            undefMatrix[i][j] = -9999;
            }
        }
        Matrix undefinedMatrix = new Matrix(undefMatrix,order,order);
        return undefinedMatrix;
    }

    public static Matrix getTransposeMatrix(Matrix matrix){
        // Fungsi yang menerima sebuah matriks lalu mengembalikan Transpose dari matriks tersebut
        // KAMUS LOKAL
        double[][] tMatrix = new double[matrix.getNRow()][matrix.getNCol()];
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

    public static void saveHasil(Matrix matrix, String namaFile) throws Exception{
        // namaFile sudah dalam .txt
        // write hasil dalam Matrix

        //KAMUS LOKAL
        int i,j;
        String text;

        //ALGORITMA
        String path = ".\\test\\hasil\\"+namaFile; 
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
    
    public static void saveHasil(double[] array, String namaFile) throws IOException{
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
        double[][] matrixk, matrixr;
        Matrix matrixkoef, matrixres;
        int i,j;
        // ALGORITMA
        matrixk = new double[augmented.getNRow()][augmented.getNCol()-1];
        matrixr = new double[augmented.getNRow()][1];
        for (i=0;i<augmented.getNRow();i++){
            for(j=0;j<augmented.getNCol();j++){
                if (j == augmented.getNCol()-1){
                    matrixr[i][0] = augmented.getElmtContent(i, augmented.getNCol()-1);
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
        double[][] matrixk, matrixr;
        Matrix matrixkoef, matrixres;
        int i,j;
        // ALGORITMA
        matrixk = new double[augmented.getNRow()][augmented.getNCol()-1];
        matrixr = new double[augmented.getNRow()][1];
        for (i=0;i<augmented.getNRow();i++){
            for(j=0;j<augmented.getNCol();j++){
                if (j == 0){
                    matrixr[i][0] = augmented.getElmtContent(i, 0);
                } 
                else{
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

    public static boolean isEqualMatrix(Matrix m1, Matrix m2){
        // Fungsi untuk mengecek apakah dua buah matriks sama, jika iya mengembalikan true, jika tidak mengembalikan false
        // KAMUS LOKAL
        int i,j;
        boolean equal = true; 
        // ALGORITMA
        if ((m1.getNRow()!=m2.getNRow()) || (m1.getNCol()!=m2.getNCol())){
            equal = false;
        } else { //ukuran sama
            i =0;
            while (i<m1.getNRow() && equal){
                j = 0;
                while (j < m2.getNCol() && equal){
                    if (m1.getElmtContent(i, j)!=m2.getElmtContent(i, j)){
                        equal = false;
                    }
                    j++;
                }
                i++;
            }
        }

        return equal;
    }
    
}