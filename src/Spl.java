package src;
import java.io.IOException;
import java.util.*;
import src.Matrix;
import src.SPL.*;   

public class Spl {
    static float parametric = 9999, undef = -9999;

    public static boolean isEmpty(String[] solusi){
        // Mengembalikan true jika solusi kosong
        // KAMUS LOKAL
        int length = 0;
        // ALGORITMA
        for (String x: solusi){
            length++;
        }
        return (length == 0);
    }

    public static boolean isUndef(String[] solusi){
        // Mengembalikan true, jika tidak ada solusi
        // KAMUS LOKAL
        boolean no_solution = false;
        int i = 0;
        // ALGORITMA
        while(!no_solution && i < solusi.length){
            if (solusi[i].equals("-9999.0")){
                no_solution = true;
            }
            i++;
        }
        return no_solution;
    }

    public static boolean isParametric(float[] solusi){
        // Mengembalikan true jika solusi parametric
        // KAMUS LOKAL
        boolean parametric = false;
        // ALGROITMA
        for(float x: solusi){
            if (x == 9999){
                parametric = true;
                break;
            }
        }
        return parametric;
    }

    public static void menuSPL(int menu) throws Exception{
        // Prosedur untuk menjalankan menu SPL
        // I.S Sembarang, menu == 1 or menu == 2 or menu == 3 or menu == 4
        // F.S Menampilkan solusi SPL dari input user

        // KAMUS LOKAL
        Scanner input = new Scanner(System.in);
        int inputType, nRow, nCol,i;
        String[] solusi;
        String file;
        Matrix augmented,matrixkoef,matrixres;
        Matrix[] listMatrix = new Matrix[2];
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

        if (inputType == 1){
            System.out.println("Matriks Augmented");
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
            System.out.println("Matrixkoef: ");
            matrixkoef = new Matrix(nRow, nCol-1);
            System.out.println("Matrixres: ");
            matrixres = new Matrix(nRow,1); 
            System.out.println("Matrixkoef: ");
            matrixkoef.displayMatrix();
            System.out.println("Matrixres: ");
            matrixres.displayMatrix();
            System.out.println("solusi: ");
            solusi = new String[nCol-1];

        } else{ // inputType == 2
            // Input file
            System.out.print("Masukkan nama file: ");
            file = input.next();
            augmented = Read.BacaFile(file);
            listMatrix = Matrix.splitAugmented(augmented);
            matrixkoef = listMatrix[0];
            matrixres = listMatrix[1];
            solusi = new String[matrixkoef.getNCol()];
        }

        switch (menu){
            case 1: solusi = Gauss.splbyGauss(matrixkoef, matrixres);
                    break;
            case 2: solusi = GaussJordan.splbyGaussJordan(matrixkoef, matrixres);
                    break;
            case 3: solusi = MatrixBalikan.splybyMatrixBalikan(matrixkoef, matrixres);
                    break;
            case 4: solusi = Cramer.splbyCramer(matrixkoef, matrixres); //menu == 4
                    break;
        }

        // Penanganan Kasus solusi
        if (isEmpty(solusi)){
            if (menu == 3){
                System.out.println("Matriks tidak punya balikan!");
            } else if (menu == 4 && isEmpty(solusi)){
                System.out.println("Determinan Matriks = 0");
            } 
        }else{
            if (isUndef(solusi)){
                System.out.println("SPL tidak mempunyai solusi yang memenuhi");
            }
            else { // Jika solusi ada, bisa parametrik, bisa unik
                for (i=0; i<= matrixkoef.getNCol() - 1;i++){
                    System.out.printf("x[%d]: %s\n",i,solusi[i]);
                }
                System.out.println("Apakah Anda ingin menyimpan solusi [y/n]?");
                String response = input.next();
                while (!response.equals("y") && !response.equals("n")){
                    System.out.println("Input tidak valid!");
                    System.out.println("Apakah Anda ingin menyimpan solusi [y/n]?");
                    response = input.next();
                }
                if (response.equals("y")){
                    System.out.print("Masukkan nama file: ");
                    file = input.next();
                    Write.saveHasil(solusi, file);
                }
            }
        }
    }
}

