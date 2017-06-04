package sample;
import java.math.*;

import static java.lang.StrictMath.abs;

public class Interpolation {
    private double[] xValues;
    private double[] yValues;
    private int size;


    public Interpolation(double[] xV, double[] yV, int s){
        xValues = xV;
        yValues = yV;
        size = s;
    }


    double lagrangePolynomial(double x) {
        double lagranjePol = 0;

        for(int i =0;i <size;i++){
            double basicsPol = 1;
            for(int j=0;j<size;j++){
                if(i!=j){
                    basicsPol*=(x-xValues[j])/(xValues[i]-xValues[j]);
                }

            }
            lagranjePol+= basicsPol*yValues[i];
        }
        return lagranjePol;
    }


    double newtonPonynomial(double x){

            double res=yValues[0],F,den;
            int i,j,k;
            for(i=1;i<size;i++){
                F=0;
                for(j=0;j<=i;j++){
                    den=1;
                    for(k=0;k<=i;k++){
                        if (k!=j) den*=(xValues[j]-xValues[k]);
                    }
                    F+=yValues[j]/den;
                }
                for(k=0;k<i;k++){F*=(x-xValues[k]);}
                res+=F;
            }
            return res;
    }

    double middleSqrt(double x,int basis){
        double value;
        basis++;
        double[][] xyTable = new double[2][size];
        for(int i =0;i < size;i++){
            xyTable[0][i] = xValues[i];
            xyTable[1][i] = yValues[i];

        }
        double[][] matrix = new double[basis][basis + 1];
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < basis; i++)
        {
            for (int j = 0; j < basis; j++)
            {
                double sumA = 0, sumB = 0;
                for (int k = 0; k < size; k++)
                {
                    sumA += Math.pow(xyTable[0][k], i) * Math.pow(xyTable[0][k], j);
                    sumB += xyTable[1][k] * Math.pow(xyTable[0][k],i);
                }
                matrix[i][j] = sumA;
                matrix[i][basis] = sumB;
            }
        }

       double[] res = gauss(matrix,basis);

        switch (--basis){
            case 0:{value = res[0];break;}
            case 1:{
                value = res[0] + res[1]*x;break;
            }
            case 2:{
                value = res[0] + res[1]*x + res[2]*x*x;break;
            }
            default: {value= 0;break;}
        }
        return value;
    }


    public double[] gauss(double[][] koef_arr,int size){
        double[] x = new double[size];
        double eps = 0.0001;
        double max;
        int k = 0, index;
        double[][] temp_koef = new double[size][size];
        double[] temp_values = new double[size];
        /** Here we will divide on koef/values */
        for(int i =0;i < size;i++){
            for(int j = 0;j < size;j++){
                temp_koef[i][j] = koef_arr[i][j];
            }
        }
        for(int i =0;i<size;i++){
            temp_values[i] = koef_arr[i][size];
        }
        while(k < size){
            /**  Searching for maximum*/
            max = abs(temp_koef[k][k]);
            index = k;
            for(int i =k+1;i< size ;i++){
                if(abs(temp_koef[i][k]) > max){
                    max = abs(temp_koef[i][k]);
                    index = i;
                }
            }
            if(max  < eps){
                System.out.println("We have a zero column!");
                return null;
            }

            for(int j =0;j<size;j++){
                double temp = temp_koef[k][j];
                temp_koef[k][j] = temp_koef[index][j];
                temp_koef[index][j] = temp;
            }

            double temp0 = temp_values[k];
            temp_values[k] = temp_values[index];
            temp_values[index] = temp0;
            for(int i = k;i < size;i++){
                double temp1 = temp_koef[i][k];
                if(abs(temp1) < eps) continue;
                for(int j =0;j<size; j++){
                    temp_koef[i][j] = temp_koef[i][j]/temp1;
                }
                temp_values[i] =  temp_values[i]/temp1;
                if(i == k) {continue;}
                for(int j =0;j<size;j++){
                    temp_koef[i][j] = temp_koef[i][j] - temp_koef[k][j];
                }
                temp_values[i] = temp_values[i] - temp_values[k];
            }
            k++;
        }
        for(int a = size-1;a >=0;a--)
        {
            x[a] = temp_values[a];
            for(int i =0;i<k;i++){
                temp_values[i] = temp_values[i] - temp_koef[i][a]*x[a];
            }
        }
        return x;
    }

}