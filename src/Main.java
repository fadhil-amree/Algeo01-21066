package src;
import java.util.*;
import java.io.*;
import src.Spl;
import src.Determinant.*;
import src.SPL.*;
;

public class Main {
        
        public static void  main (String[] args) throws Exception {
                int nRow, nCol, menu; //deklarasi jumlah baris dan kolom pada matriks
                Scanner input = new Scanner(System.in); //inisiasi variabel untuk melakukan input
                boolean runProgram = true;
                
        while (runProgram){
            System.out.println("MENU");
            System.out.println("1. Sistem Persamaan Linier");
            System.out.println("2. Determinan");
            System.out.println("3. Matriks Balikan");
            System.out.println("4. Interpolasi Polinom");
            System.out.println("5. Interpolasi Bicubic");
            System.out.println("6. Regresi Linier Berganda");
            System.out.println("7. Keluar");
            System.out.println("");
            
            System.out.print("Masukkan Pilihan Anda: ");
            menu = input.nextInt();
            
            //Template
            switch (menu){
                    case 1:
                    System.out.println("1. Metode Eliminasi Gauss");
                    System.out.println("2. Metode Eliminasi Gauss-Jordan");
                    System.out.println("3. Metode Matriks Balikan");
                    System.out.println("4. Kaidah Cramer");
                    System.out.println("");
                    
                    System.out.print("Masukkan Pilihan Anda: ");
                    menu = input.nextInt();
                        
                    switch (menu){
                            case 1: System.out.println("Menyelesaikan SPL dengan Metode Eliminasi Gauss");
                                    Spl.menuSPL(1);
                            break;
                            case 2: System.out.println("Menyelesaikan SPL dengan Metode Eliminasi Gauss-Jordan");
                                    Spl.menuSPL(2);
                            break;
                            case 3: System.out.println("Menyelesaikan SPL dengan Metode Matriks Balikan");
                                    Spl.menuSPL(3);
                            // handle IsBalikan
                            break;
                            case 4: System.out.println("Menyelesaikan SPL dengan Kaidah Cramer");
                                    Spl.menuSPL(4);
                            // handle IsSquare jika getNCol - 1 == getNRow
                            // handle IsBalikan
                            break;
                            default: System.out.println("Pilihan Anda tidak terdapat pada menu!");
                            break;
                        }
                        break;
                        case 2:
                        // handle dengan IsSquare
                        System.out.println("1. Metode Reduksi Baris");
                        System.out.println("2. Metode Ekspansi Kofaktor"); 
                        System.out.println("");
                        
                        System.out.print("Masukkan Pilihan Anda: ");
                        menu = input.nextInt();     
                        
                        switch(menu){
                                case 1: System.out.println("Mencari Determinan Matriks dengan Metode Reduksi Baris");
                                        Determinan.menuDeterminan(1);
                                break;
                                case 2: System.out.println("Mencari Determinan Matriks dengan Metode Ekspansi Kofaktor");
                                        Determinan.menuDeterminan(2);
                                break;
                                default: System.out.println("Pilihan Anda tidak terdapat pada menu!");
                                break;
                        }
                        break;
                        case 3: 
                        // handle dengan isInvertible
                        System.out.println("1. Metode Reduksi Baris");
                        System.out.println("2. Metode Adjoin"); 
                        System.out.println("");
                        
                        System.out.print("Masukkan Pilihan Anda: ");
                        menu = input.nextInt();      
                        
                        switch(menu){
                                case 1: System.out.println("Mencari Matriks Balikan dengan Metode Reduksi Baris");
                                        Inverse.menuInverse(1);
                                break;
                                case 2: System.out.println("Mencari Matriks Balikan dengan Metode Ekspansi Kofaktor");
                                        Inverse.menuInverse(2);
                                break;
                                default: System.out.println("Pilihan Anda tidak terdapat pada menu!");
                                break;
                        }
                        break;     
                        case 4: 
                        System.out.println("Melakukan Interpolasi Polinom");
                        PolinomialInterpolation.menuPolinomialInterpolation();
                        break;
                        case 5:
                        System.out.println("Melakukan Interpolasi Bicubic");
                                BicubicInterpolation.menuBicubicInterpolation();
                        break;
                        case 6: 
                        System.out.println("Melakukan Regresi Linier Berganda");
                        MLR.menuMLR();
                        break;
                        case 7:
                        System.out.println("Keluar dari Program");
                        runProgram = false;
                        break;
                        default: System.out.println("Pilihan Anda tidak terdapat pada menu!");
                        break;
                }
        }
}

}
