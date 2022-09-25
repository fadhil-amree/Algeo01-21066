package src;

import java.util.*;

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
    public Matrix(String file){
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada file

        
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
}