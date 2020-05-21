package com.company;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Propagacja
{
    public static void main(String[] args)
    {
        Neurone neurone1 = new Neurone(0.86f, -0.16f, 0.28f);
        Neurone neurone2 = new Neurone(0.83f, -0.51f, -0.86f);
        Neurone neurone3 = new Neurone(0.04f, -0.43f, 0.48f);

        List trainingVectors = new ArrayList<double[]>();
        trainingVectors.add(new double[] { 1, 0, 0 });
        trainingVectors.add(new double[] { 1, 0, 1 });
        trainingVectors.add(new double[] { 1, 1, 0 });
        trainingVectors.add(new double[] { 1, 1, 1 });

        double[] expectedResults = new double[] { 0, 1, 1, 0 };

        double learningConstant = 0.5f;
        double stopCondition = 0.01f;

        Boolean[] done = new Boolean[] { false, false, false, false };

        //System.out.println("Wagi neuronu 1 " + neurone1.toString());
        //System.out.println("Wagi neuronu 2 " + neurone2.toString());
        //System.out.println("Wagi neuronu 3 " + neurone3.toString());
        int iter = 1;

        while (Arrays.asList(done).contains(false))
        {
            System.out.println("ITERACJA "+ iter +"\n");
            double totalEnergy = 0f;
            for (int i = 0; i < 4; i++)
            {
                neurone1.Inputs = (double[])trainingVectors.get(i);
                neurone1.CalculateOutput();

                neurone2.Inputs = (double[])trainingVectors.get(i);
                neurone2.CalculateOutput();

                neurone3.Inputs = new double[] { 1, neurone1.Output, neurone2.Output };
                neurone3.CalculateOutput();
                totalEnergy += (double)(Math.pow((neurone3.Output - expectedResults[i]), 2) / 2);

                System.out.println("Wektor: " + (i+1) + " wynik: " + neurone3.Output + " oczekiwany: " + expectedResults[i]);

                if (Math.abs(neurone3.Output - expectedResults[i]) < stopCondition)
                {
                    done[i] = true;
                    continue;
                } else
                {
                    done[i] = false;
                }

                System.out.println("wzór: f(net)*(1-f(net))*(d-x3(1))");
                neurone3.RealError = (neurone3.Output - expectedResults[i]) * neurone3.DerivateValue();
                System.out.println("wzór: neurone3.RealError * waga[i] * f(net)*(1-f(net))");
                neurone1.RealError = neurone3.RealError * neurone3.Weights[1] * neurone1.DerivateValue();
                neurone2.RealError = neurone3.RealError * neurone3.Weights[2] * neurone2.DerivateValue();

                neurone1.UpdateWeights(learningConstant);
                neurone2.UpdateWeights(learningConstant);
                neurone3.UpdateWeights(learningConstant);

                System.out.println("Neuron 1: " + neurone1.toString());
                System.out.println("Neuron 2: " + neurone2.toString());
                System.out.println("Neuron 3: " + neurone3.toString());
            }
            System.out.println("Energia calkowita: " + totalEnergy);
            iter++;
            System.out.println("--------------------------------\n");
        }

        System.out.println("--------------------------------\n");
        System.out.println("--------------------------------\n");

        System.out.println("\nUstalone wagi:");
        System.out.println("Neuron 1: " + neurone1.toString());
        System.out.println("Neuron 2: " + neurone2.toString());
        System.out.println("Neuron 3: " + neurone3.toString());

    }

}

class Neurone
{
    public double[] Weights;
    public double[] Inputs;
    public double Output;
    public double RealError;

    public Neurone(double weight1, double weight2, double weight3)
    {
        this.Weights = new double[] { weight1, weight2, weight3 };
    }

    // funkcja antywacji
    public double ActivationFunction(double arg)
    {
        return (double)(1 / (1 + Math.exp(-arg)));
    }

    // iloczyn skalarny
    public double Scalar(double[] vector1, double[] vector2)
    {
        double result = 0;

        for (int i = 0; i < vector1.length; i++)
        {
            result += vector1[i] * vector2[i];
        }

        return result;
    }

    // wyliczanie outputu neuronu fAktywacji(wagi * wejscia)
    public void CalculateOutput()
    {
        Output = ActivationFunction(Scalar(Weights, Inputs));
    }

    // pochodna od outputu
    public double DerivateValue()
    {
        return Output * (1 - Output);
    }

    // aktualizacja wag wg wzoru
    public void UpdateWeights(double learningConstant)
    {
        for (int i = 0; i < 3; i++)
        {
            Weights[i] -= learningConstant * RealError * Inputs[i];
        }
    }
    @Override
    public String toString()
    {
        String out = String.format(String.format("%.2f",Weights[0]) + "\n") + String.format(String.format("%.2f",Weights[1]) + "\n") + String.format(String.format("%.2f",Weights[2]) + "\n");
        return out;
    }
}
