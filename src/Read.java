package src;

import java.io.*;
import java.util.*;
import src.Matrix.*;

public class Read {
  public static Matrix BacaFile(String namaFile) throws Exception {
    String[][] MatrixStr;
    String[] IsiData;
    String data;
    MatrixStr = new String[9999][9999];
    int row = 9999, col = 9999, i, j;

    try {
      String temp = ".\\test\\input\\" + namaFile;
      File file = new File(temp);
      BufferedReader br = new BufferedReader(new FileReader(file));

      i = 0;

      if ((data = br.readLine()) != null) {

        IsiData = data.split(" ");

        for (j = 0; j < IsiData.length; j++) {
          MatrixStr[i][j] = IsiData[j];
        }

        i++;
        col = IsiData.length;

        while ((data = br.readLine()) != null) {

          IsiData = data.split(" ");

          for (j = 0; j < IsiData.length; j++) {
            MatrixStr[i][j] = IsiData[j];
          }

          i++;
        }

        /** KASUS KHUSUS BICUBIC INTERPOLATION FILE **/
        if (IsiData.length < col) {
          for (j = IsiData.length; j < col; j++) {
            MatrixStr[i - 1][j] = "0";
          }
        }

      }

      row = i;

      double[][] Matrixhasil = new double[row][col];
      for (i = 0; i < row; i++) {
        for (j = 0; j < col; j++) {

          Matrixhasil[i][j] = Double.valueOf(MatrixStr[i][j]);
          }

      }

      Matrix M = new Matrix(Matrixhasil, row, col);

      return M;

    } catch (FileNotFoundException e) {
      System.out.println("Tidak ditemukan File tersebut.");
      return null;
    }
  }
}

/* https://www.geeksforgeeks.org/different-ways-reading-text-file-java/ */