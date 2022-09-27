package src;

import src.Inverse;
import src.Matrix;

import java.util.*;
import java.lang.Math.*;

public class BicubicInterpolation {

    private static int xyFunctionValue (int i, int j, int x, int y){
        // mengembalikan nilai x^i*y^j
        return ((int)Math.pow(x,i)*(int)Math.pow(y,j))
    }

    private static float functionValue(int i, int j, float x, float y, Matrix a) {
        // mengembalikan nilai f(x,y) = sigma[j] sigma[i] a[i,j] * x^i * y^j
        // untuk nilai sigma[i] 0..i dan sigma[j] 0..j
        int itrX = 0, itrY = 0;
        float sum;
        
        for (itrY = 0; itrY <= i; itrY++) {
            for (itrX = 0; itrX <= j; itrX++) {
                sum = (float) (a.getElmtContent(i, j) * Math.pow(x,i) * Math.pow(y, j));
            }
        }
    }

    

}
