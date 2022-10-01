package src;
import src.Matrix;
import src.Determinant.*;
import java.util.*;
import java.io.*;

public class Determinan {


    public static void menuDeterminan(int menu) throws IOException{
        // Prosedur untuk menjalankan menu Inverse
        // I.S sembarang, menu == 1 atau menu == 2 
        // F.S Menampilkan determinan dari matriks yang diinputkan 
        // KAMUS LOKAL
        Scanner input = new Scanner(System.in);
        Matrix inputMatrix;
        float detMatrix;
        int inputType, N;
        String file;
        // ALGORITMA
        System.out.println("Tipe input");
        System.out.println("1. Input Keyboard");
        System.out.println("2. Input File");

        System.out.print("Masukkan tipe input: ");
        inputType = input.nextInt();
        while (inputType!=1 && inputType!=2){
            System.out.println("Tipe input yang anda pilih tidak tersedia!");
            System.out.println("Tipe input");
            System.out.println("1. Input Keyboard");
            System.out.println("2. Input File");
    
            System.out.print("Masukkan tipe input: ");
            inputType = input.nextInt();
        }

        // input Matrix
        if (inputType==1){
            System.out.println("Masukkan N: ");
            N = input.nextInt(); // input dimensi
            inputMatrix = new Matrix(N, N); // input matriks
        } else{ // inputType == 2
            System.out.println("Masukkan nama file: ");
            file = input.next();      
            inputMatrix = new Matrix(file);    
        }

        // Mencari inverse
        if (menu == 1){
            detMatrix = ReduksiBaris.detReduksi(inputMatrix);
        } else { // menu == 2
            detMatrix = Kofaktor.detKofaktor(inputMatrix);
        }

        System.out.println("Determinan Matrix: " + detMatrix);
        
    }
}