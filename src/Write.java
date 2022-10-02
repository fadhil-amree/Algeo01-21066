package src;
import java.io.*;
import src.Matrix;

public class Write {
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
    
    public static void saveHasil(String[] array, String namaFile) throws Exception{
        // namaFile sudah dalam .txt
        // write hasil dalam array
        
        //KAMUS LOKAL
        int i;
        String text = "";
    
        //ALGORITMA
        String path = ".\\test\\hasil\\"+namaFile; 
        File fout = new File(path);
        FileOutputStream fos = new FileOutputStream(fout);
    
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (i = 0 ; i < array.length; i++)
        {
            text = String.valueOf(array[i]);
            bw.write(text); 
            if (i != array.length-1)
            {
                bw.newLine();
            }
        }
        bw.close();
    }

}
