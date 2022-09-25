package src;

import java.util.*;

public class Matrix {
    int nRow,nCol;
    float[][] content;

    //Konstruktor input dari keyboard
    Matrix(int nRow, int nCol){
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
    Matrix(String file){
        // I.S Matrix sembarang
        // F.S matrix terdefinisi sesuai matrix yang ada pada file

        
    } 

    //Konstruktor input berupa matriks
    Matrix(float[][] matrix, int nRow, int nCol){
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
    //Konstruktor input beruba objek matrix
    Matrix(Matrix matrix){
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

    //Konstruktor input beruba objek matrix
    Matrix(Matrix matrix){
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

    void displayMatrix(){
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
}