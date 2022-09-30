package src;
import java.util.*;
import src.Matrix;
import src.SPL.*;

public class Spl {
    static float parametric = 9999, undef = -9999;

    public static boolean isEmpty(float[] solusi){
        // Mengembalikan true jika solusi kosong
        // KAMUS LOKAL
        int length = 0;
        // ALGORITMA
        for (float x: solusi){
            length++;
        }
        return (length == 0);
    }


    public static boolean isUndef(float[] solusi){
        // Mengembalikan true, jika tidak ada solusi
        // KAMUS LOKAL
        boolean no_solution = true;
        // ALGORITMA
        for (float x :solusi){
            if (x != undef){
                no_solution = false;
                break;
            }
        }
        return no_solution;
    }
    public static boolean isParametric(float[] solusi){
        // Mengembalikan true, jika tidak ada solusi
        // KAMUS LOKAL
        boolean parametric_solution = false;
        // ALGORITMA
        for (float x :solusi){
            if (x == parametric){
                parametric_solution = true;
                break;
            }
        }
        return parametric_solution;
    }

    public static void menuSPL(int menu){
        // Prosedur untuk menjalankan menu SPL
        // I.S Sembarang, menu == 1 or menu == 2 or menu == 3 or menu == 4
        // F.S Menampilkan solusi SPL dari input user

        // KAMUS LOKAL
        Scanner input = new Scanner(System.in);
        int inputType, nRow, nCol,i;
        float[] solusi;
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
            solusi = new float[nCol-1];

        } else{ // inputType == 2
            // Input file
            System.out.print("Masukkan nama file: ");
            file = input.next();
            augmented = new Matrix(file);
            listMatrix = Matrix.splitAugmented(augmented);
            matrixkoef = listMatrix[0];
            matrixres = listMatrix[1];
        }

        switch (menu){
            case 1: solusi = Gauss.splbyGauss(matrixkoef, matrixres);
                    break;
            case 2: solusi = GaussJordan.splbyGaussJordan(matrixkoef, matrixres);
                    break;
            case 3: solusi = MatrixBalikan.splybyMatrixBalikan(matrixkoef, matrixres);
                    break;
            default: solusi = Cramer.splbyCramer(matrixkoef, matrixres); //menu == 4
                    break;
        }

        // Penanganan Kasus solusi
        if (isUndef(solusi)){
            if (menu == 3 && isEmpty(solusi)){
                System.out.println("Matriks tidak punya balikan!");
            } else if (menu == 4 && isEmpty(solusi)){
                System.out.println("Determinan Matriks = 0");
            } else {
                System.out.println("SPL tidak mempunyai solusi");
            }
        } else{
            if (isParametric(solusi)){
            }
            else { // Jika solusi unik
                for (i=0; i<=nCol-2;i++){
                    System.out.printf("x[%d]: %.02f\n",i,solusi[i]);
                }
            }
        }
    }
}
