package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {


    public static void main(String[] args) {

        List wektoryTrenujace = new ArrayList<double[]>();
        wektoryTrenujace.add(new double[] {1.0,0.0,0.0});
        wektoryTrenujace.add(new double[] {1.0,0.0,1.0});
        wektoryTrenujace.add(new double[] {1.0,1.0,0.0});
        wektoryTrenujace.add(new double[] {1.0,1.0,1.0});

        double[] d = new double[] {0.0,0.0,1.0,0.0};

        double[] w1 = new double[] {1.0,1.0,1.0};
        double p;

        System.out.println("Wagi początkowe: " + Print(w1) + "\n");

        //punkt a) ωk+1 = ωk + ρ(dk - yk)xk
        //(i) ρ = 1 oraz ω1 = (1, 1, 1)

        p = 1;

        System.out.println("Algorytm Preceptronu: \n a) (i)");

        perceptronA(wektoryTrenujace,w1,d,p);

        System.out.println("--------------------------------\n");

        //(ii) ρ = 0.1 oraz ω1 = (-0.12, 0.4, 0.65)

        p = 0.1;
        w1[0] = -0.12;
        w1[1] = 0.4;
        w1[2] = 0.65;

        System.out.println("Algorytm Preceptronu: \n a) (ii)");

        perceptronA(wektoryTrenujace,w1,d,p);

        System.out.println("--------------------------------\n");

        //punkt b ωk+1 = ωk + c( suma źle rozpoznanych
        //wektorów przez wagę ωk)

        w1[0] = 1;
        w1[1] = 1;
        w1[2] = 1;
        double c = 1.0;

        System.out.println("Algorytm Preceptronu: \n b)");

        perceptronB(wektoryTrenujace,w1,d,c);

    }

    public static void perceptronB(List<double[]> wektoryTrenujace,
                                   double[] w, double[] d, double c){
        //sprawdzic ktore wektory sa zle rozpoznane przez wage i z jakim znakiem je wstawic
        //mam liste 4 wartosci, 0 oznacza wektor dobrze rozpoznany,
        // -1 zle rozpoznany z minusem, 1 zle rozpoanny z plusem i mnoze je prezz wektory



        /*

        double[] w2 = xRecon(wektoryTrenujace,w1,d,c);
        double[] w3 = xRecon(wektoryTrenujace,w2,d,c);
        double[] w4 = xRecon(wektoryTrenujace,w3,d,c);
        */

        double[] result = new double[4];
        double[] sumOfWec = new double[4];
        boolean repeat;

        //y1
        int iteracje = 0;
        do {
            Arrays.fill(sumOfWec, 0.0);
            repeat = false;
            iteracje++;
            System.out.println("Iteracja: " + iteracje);
            for (int i=0; i< wektoryTrenujace.size();i++){
                result[i] = vectorSign(sigmFunc(matrixMult(wektoryTrenujace.get(i),w)),d[i]);
                sumOfWec = matrixAdd(sumOfWec, matrixMult(result[i],wektoryTrenujace.get(i)));
                if (result[i] != 0){
                    System.out.println("Wektor trenujacy x" + (i+1) + " zostal zle rozpoznany przez wage " + Print(w));
                    repeat = true;
                }

            }
            w = matrixAdd(w,matrixMult(c,sumOfWec));
            System.out.println("Nowa waga: " + Print(w) + "\n");
            if (iteracje> 40)
            {
                System.out.println("Wagi nie stabilizują się po 40 iteracjach.");
                return;
            }
        } while (repeat);

        System.out.println("Wagi ustabilizowaly sie po " + iteracje + " iteracji/ach.\nWaga wynosi " +  Print(w));



    }


    public static double vectorSign (double y, double d){
        //funkcja zwraca znak wektora
        if (y == d) {
            return 0;
        } else if (y == 0 && d == 1){
            return 1;
        } else return -1;
    }

    public static void perceptronA(List<double[]> wektoryTrenujace,double[] w, double[] d, double p){

        double[] y = new double[4];


        //y1
        int iteracje = 0;
        do {
            iteracje++;
            for (int i=0; i< wektoryTrenujace.size();i++){
                y[i] = sigmFunc(matrixMult(w, wektoryTrenujace.get(i)));
                w = matrixAdd(w, matrixMult((p * (d[i] - y[i])), wektoryTrenujace.get(i)));
                System.out.println("Wektor: x" + i + ": " + Print(wektoryTrenujace.get(i)) + ", waga: " + Print(w) + "\n");
            }
            if (iteracje> 40)
            {
                System.out.println("Wagi nie stabilizują się po 40 iteracjach.");
                return;
            }
        } while (!Arrays.equals(d,y));

        System.out.println("Wagi ustabilizowaly sie po " + iteracje + " iteracji/ach.\nWaga wynosi " +  Print(w));

    }

    public static double matrixMult(double[] m1, double[] m2){
        double result = m1[0] * m2[0] + m1[1] * m2[1] + m1[2] * m2[2];
        return result;
    }

    public static double[] matrixMult(double m1, double[] m2){
        double[] result = new double[3];
        result[0] = m1 * m2[0];
        result[1] = m1 * m2[1];
        result[2] = m1 * m2[2];
        return result;
    }

    public static double[] matrixAdd(double[] m1, double[] m2){
        double[] result = new double[3];
        result[0] = m1[0] + m2[0];
        result[1] = m1[1] + m2[1];
        result[2] = m1[2] + m2[2];
        return result;
    }

    public static double sigmFunc(double a){
        // sigm function 1, t>0; 0, t<=0;
        if (a > 0){
            return 1;
        } else return 0;
    }

    public static String Print(double[] list)
    {
        StringBuilder builder = new StringBuilder("[ ");
        for (double item : list)
        {
            builder.append(String.format("%.2f",item) + ", ");
        }
        builder.deleteCharAt(builder.length() - 2);
        builder.append("]");
        return builder.toString();
    }





}
