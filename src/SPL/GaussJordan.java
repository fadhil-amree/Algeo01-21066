package src.SPL;
import src.*;
import java.util.*;
public class GaussJordan {
    public static boolean inListInt (int[] li, int x){
        //Fungsi mengecek apakah suatu elemen terdapat dalam list
        //KAMUS LOKAL
        boolean exist=false;
        //ALGORITMA
        for (int e:li){
            if (x==e){
                exist = true;
                break;
            }
        }
        return exist;
    }

    public static int getIndexOf(int[] li, int x){
        //Fungsi mengembalikan indeks x dalam list
        //KAMUS LOKAL
        int idx=-1;
        int i=0;
        //ALGORITMA
        for (int e:li){
            if (e==x){
                idx = i;
                break;
            }

            i++;
        }
        return idx;
    }


    public static float[] getlistUndef(int panjang){
        // fungsi mengembalikan list undef
        // KAMUS LOKAL
        int i;
        float[] listUndef = new float[panjang]; 
        // ALGORITMA
        for (i=0;i<=panjang-1;i++){
            listUndef[i] = -9999;
        }
        return listUndef;
    }

    public static String[] splbyGaussJordan(Matrix matrixkoef, Matrix matrixres){
        // Fungsi mencari solusi SPL dengan metode eliminasi gauss jordan
        // Prekondisi: matrixkoef dan matrixres tak kosong
        // KAMUS LOKAL
        float[] solusi = new float[matrixkoef.getNCol()]; 
        String[] solusi_string = new String[matrixkoef.getNCol()]; 
        String[] listParameter = new String[matrixkoef.getNCol()]; 
        String esolusi_string;
        Matrix tempMatrix = new Matrix(matrixkoef);
        Matrix resMatrix = new Matrix(matrixres);
        int i,j,k,l; //indeks
        int ctr, nParameter; //counter
        int[] idxParameter = new int[matrixkoef.getNCol()];
        float etemp,pengali,eres; //variabel elemen dan faktor pengali
        boolean found,concistent,doOBE;
        //ALGORITMA
        // eres: elemen result matriks konstanta, etemp: elemen temporary matriks koefisien 
        //Dibuat ada satu augmented matrix
        nParameter = 0; //inisialisasi nilai nParameter 
        if (matrixkoef.getNRow()>=2){
            for(i=0;i<=tempMatrix.getNRow()-1;i++)
            {   // i : indeks baris patokan
                // j : indeks baris selain baris i
                // k : indeks kolom 
                doOBE = true;

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
                    if (found){ // Jika ketemu elemen pada kolom tersebut di baris lain yang punya nilai !=0
                        for(k=0;k<=tempMatrix.getNCol()-1;k++) //swap baris
                            {
                                etemp = tempMatrix.getElmtContent(i,k);
                                eres = resMatrix.getElmtContent(i,0);
                                tempMatrix.setElmtContent(i,k,tempMatrix.getElmtContent(l,k));
                                resMatrix.setElmtContent(i,0,resMatrix.getElmtContent(l,0));
                                tempMatrix.setElmtContent(l,k,etemp);
                                resMatrix.setElmtContent(l,0,eres);
                            }
                    } else{
                        doOBE = false;
                    }
                }

                if (doOBE){
                    //Forward Phase
                    for (j=i+1;j<=tempMatrix.getNRow()-1;j++)
                    {
                        pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                        for(k=0;k<=tempMatrix.getNCol()-1;k++)
                        {   //OBE tempMatrix
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                            //OBE resMatrix
                            if (k==0){
                                resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j,k) - pengali*resMatrix.getElmtContent(i,k));
                            }
                        }
                    }

                    //Backward Phase
                    for (j=0;j<=i-1;j++)
                    {
                        pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                        for(k=0;k<=tempMatrix.getNCol()-1;k++)
                        {   //OBE tempMatrix
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                            //OBE resMatrix
                            if (k==0){
                                resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j, k) - pengali*resMatrix.getElmtContent(i,k));
                            }
                        }
                    }

                    //bentuk leading 1
                    if (tempMatrix.getElmtContent(i,i) != 1)
                    {
                        pengali = 1/tempMatrix.getElmtContent(i,i);
                        for(k=0; k<=tempMatrix.getNCol()-1; k++){
                            //Membentuk leading one
                            tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) * pengali);
                            if (k==0){
                                resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j, k) * pengali);
                            }
                        }
                    }
                }
            }
            if (Matrix.isInvertible(tempMatrix) || tempMatrix.getNRow()>tempMatrix.getNCol()){ //Punya solusi unik
                for (i=0;i<=matrixkoef.getNCol()-1;i++){
                    solusi[i] = resMatrix.getElmtContent(i, 0); // resMatriks hanya punya satu kolom
                }
            }
            else{ //not invertible dan tidak memiliki jumlah baris yang lebih banyak daripada jumlah kolom 
                concistent = true;
                i = 0;
                while (i<=tempMatrix.getNRow()-1 && concistent){
                    ctr = 0; //inisialisasi counter untuk menghitung pada baris yang semua koefnya 0
                    j = 0;
                    while (j<=tempMatrix.getNCol()-1 && ctr == 0){
                        if (tempMatrix.getElmtContent(i, j) != 0){
                            ctr += 1; // Counter bertambah jika ada elemen pada tempMatrix yang tak 0
                        }
                        j++;
                    }
                    if (ctr == 0 && resMatrix.getElmtContent(i, 0)!=0){
                        concistent = false; 
                        //Matrix tidak concistent (tak punya solusi)
                    }
                    i++;
                }

                if (!concistent){ //Jika tak ada solusi
                    solusi = getlistUndef(tempMatrix.getNCol());
                }
                else { //Jika punya tak hingga solusi
                    for (i=0;i<=matrixkoef.getNCol()-1;i++){
                        if (tempMatrix.getNRow()>=i){ //Jika indeks baris masih dalam jangkauan  
                            ctr = 0; //inisialisasi counter untuk menghitung pada baris yang semua koefnya 0
                            j = 0;
                            while (j<=tempMatrix.getNCol()-1 && ctr == 0){
                                if (tempMatrix.getElmtContent(i, j) != 0){
                                    ctr += 1; // Counter bertambah jika ada elemen pada tempMatrix yang tak 0
                                }
                                j++;
                            }
                            if (ctr == 0){ //Jika semua elemen suatu baris = 0 dan resultnya juga 0
                                solusi[i] = 9999; //Mark parametrik
                                idxParameter[nParameter] = i;
                                nParameter++;
                            } else{
                                solusi[i] = resMatrix.getElmtContent(i, 0); // resMatriks hanya punya satu kolom
                            }
                        } else{ // saat terdapat SPL dengan jumlah persamaan < jumlah variabel
                            solusi[i] = 9999; //Mark parametrik
                            idxParameter[nParameter] = i;
                            nParameter++;
                        }
                    }           
                }
            }
        } else { // matrix.nRow == 1
            solusi[0] = resMatrix.getElmtContent(0, 0);
        }
        // Konversi 
        if (Spl.isParametric(solusi)){
            //Penamaan parameter
            for (j=0;j<nParameter;j++){
                listParameter[j] = "a"+String.valueOf(idxParameter[j]);
            }

            for (i=0;i<matrixkoef.getNRow();i++){
                if (!inListInt(idxParameter, i)){ //Jika bukan parameter
                    esolusi_string = "";
                    esolusi_string += String.valueOf(resMatrix.getElmtContent(i, 0));
                    esolusi_string += " - ";
                    for(j=0;j<nParameter;j++){
                        esolusi_string += listParameter[i];
                        if (j!=nParameter-1){
                            esolusi_string += " - ";
                        }
                    }
                    solusi_string[i] = esolusi_string;
                } else {
                    solusi_string[i] = listParameter[getIndexOf(idxParameter, i)];
                }
            }
        
        } else{
            for (i=0;i<matrixkoef.getNCol();i++){
                solusi_string[i] = String.valueOf(solusi[i]);
            }
        }
        return solusi_string;
    }
    
}

