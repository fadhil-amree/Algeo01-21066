package src.SPL;
import src.Matrix;
import src.Inverse;
public class GaussJordan {
    public static float[] getlistUndef(int panjang){
        // fungsi mengembalikan list undef
        // KAMUS LOKAL
        int i;
        float[] listUndef = new float[panjang]; 
        // ALGORITMA
        for (i=0;i<=panjang-1;i++){
            listUndef[i] = -999;
        }
        return listUndef;
    }

    public static float[] splbyGaussJordan(Matrix matrixkoef, Matrix matrixres){
        // Fungsi mencari solusi SPL dengan metode eliminasi gauss jordan
        // KAMUS LOKAL
        float[] solusi = new float[matrixkoef.getNCol()]; //
        Matrix tempMatrix = new Matrix(matrixkoef);
        Matrix resMatrix = new Matrix(matrixres);
        int i,j,k,l; //indeks
        int ctr; //counter
        float etemp,pengali,eres; //variabel elemen dan faktor pengali
        boolean found,concistent;
        //ALGORITMA
        // eres: elemen result matriks konstanta, etemp: elemen temporary matriks koefisien 
        //Dibuat ada satu augmented matrix
        if (matrixkoef.getNRow()>=2){
            for(i=0;i<=tempMatrix.getNRow()-1;i++)
            {   // i : indeks baris patokan
                // j : indeks baris selain baris i
                // k : indeks kolom 

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
                    for(k=0;k<=tempMatrix.getNCol()-1;k++) //swap baris
                    {
                        etemp = tempMatrix.getElmtContent(i,k);
                        eres = resMatrix.getElmtContent(i,k);
                        tempMatrix.setElmtContent(i,k,tempMatrix.getElmtContent(l,k));
                        resMatrix.setElmtContent(i,k,resMatrix.getElmtContent(l,k));
                        tempMatrix.setElmtContent(l,k,etemp);
                        resMatrix.setElmtContent(l,k,eres);
                    }
                }

                //Forward Phase
                for (j=i+1;j<=tempMatrix.getNRow()-1;j++)
                {
                    pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                    for(k=0;k<=tempMatrix.getNCol()-1;k++)
                    {   //OBE tempMatrix
                        tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                        //OBE inverseMatrix
                        resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j,k) - pengali*resMatrix.getElmtContent(i,k));
                    }
                }

                //Backward Phase
                for (j=0;j<=i-1;j++)
                {
                    pengali = tempMatrix.getElmtContent(j,i)/tempMatrix.getElmtContent(i,i);
                    for(k=0;k<=tempMatrix.getNCol()-1;k++)
                    {   //OBE tempMatrix
                        tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) - pengali*tempMatrix.getElmtContent(i,k));
                        //OBE inverseMatrix
                        resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j, k) - pengali*resMatrix.getElmtContent(i,k));
                    }
                }

                //bentuk leading 1
                if (tempMatrix.getElmtContent(i,i) != 1)
                {
                    pengali = 1/tempMatrix.getElmtContent(i,i);
                    for(k=0; k<=tempMatrix.getNCol()-1; k++){
                        //Membentuk leading one
                        tempMatrix.setElmtContent(j,k, tempMatrix.getElmtContent(j, k) * pengali);
                        resMatrix.setElmtContent(j,k, resMatrix.getElmtContent(j, k) * pengali);
                    }
                }

            }
            if (Inverse.isInvertible(tempMatrix)){ //Punya solusi unik
                for (i=0;i<=matrixkoef.getNCol()-1;i++){
                    solusi[i] = resMatrix.getElmtContent(i, 0); // resMatriks hanya punya satu kolom
                }
                return solusi;
            }
            else{ //not invertible
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
                    return getlistUndef(tempMatrix.getNCol());
                }
                else { //Jika punya tak hingga solusi
                    return solusi;
                }
            }
        } else { // matrix.nRow < 2
            solusi[0] = resMatrix.getElmtContent(0, 0);
            return solusi;
        }

    }         
}

