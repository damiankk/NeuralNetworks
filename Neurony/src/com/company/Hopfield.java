package com.company;

import java.util.ArrayList;
import java.util.List;

public class Hopfield {


    public static void main(String[] args) {

        double[][] weight = new double [][] { { 0, (-2.0 / 3.0), (2.0 / 3.0) }, { (-2.0 / 3.0), 0, (-2.0 / 3.0) }, { (2.0 / 3.0), (-2.0 / 3.0), 0 } };
        double[][] neuralStim = new double[3][];

        List<double[]> neuralStates = new ArrayList<double[]>();
        neuralStates.add(new double[] { 1, 1, 1 });
        neuralStates.add(new double[] { 1, 1, -1 });
        neuralStates.add(new double[] { 1, -1, 1 });
        neuralStates.add(new double[] { -1, 1, 1 });
        neuralStates.add(new double[] { -1, -1, 1 });
        neuralStates.add(new double[] { -1, 1, -1 });
        neuralStates.add(new double[] { 1, -1, -1 });
        neuralStates.add(new double[] { -1, -1, -1 });

        List<String> fixedPoints = new ArrayList<String>();

        neuralStim[1] = new double[] { 1, 1, 1 };



        for (double [] state : neuralStates){
            System.out.println("---------------------------------");
            System.out.println("Wektor poczatkowy " + Print(state));


            int i = 0;
            int neuron = i;

            while (true){

                System.out.println("--------------- t" + (i + 1) + " ------------------");

                neuron = i % 3;
                double result = MatrixMult(state, weight, neuron);
                System.out.println("U" + (neuron+1) + "(" + (i+1) + ") = " + String.format("%.2f",result));
                state[neuron] = sigmFunc(result);
                System.out.println("V(" + (i+1) + ") = " + Print(state));
                neuralStim[neuron] = new double[3];

                for(int j = 0; j < 3; j++){
                    neuralStim[neuron][j] = state[j];
                }

                if (neuron == 2 && Compare(neuralStim[0], neuralStim[1]) && Compare(neuralStim[1], neuralStim[2])){
                    System.out.println("Punkt staly wynosi: " + Print(state));
                    if (!fixedPoints.contains(Print(state))){
                        fixedPoints.add(Print(state));
                    }
                    break;
                }
                i++;

            }


        }


        System.out.println("---------------------------------");
        System.out.println("Siec posiada nastepujace punkty stale: ");
        for (int i=0; i<fixedPoints.size(); i++){
            System.out.println((fixedPoints.get(i)));
        }


    }

    public static double sigmFunc(double a){
        // sigm function 1, t>0; -1, t<=0;
        if (a > 0){
            return 1;
        } else return -1;
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


    public static double MatrixMult(double[] vector, double[][] matrix, int lineNum)
    {
        double result = 0;

        for (int i = 0; i < vector.length; i++)
        {
            result += vector[i] * matrix[lineNum][i];
        }

        return result;
    }

    public static boolean Compare(double[] vector1, double[] vector2){

        if (vector1 == null || vector2 == null || vector1.length != vector2.length){
            return false;
        }
        for (int i = 0; i < vector1.length; i++){
            if (vector1[i] != vector2[i]){
                return false;
            }
        }
        return true;
    }

}
