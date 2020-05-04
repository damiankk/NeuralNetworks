package com.company;

public class Perceptron {

    public static void main(String[] args) {

        double[] x1 = new double[] {1.0,0.0,0.0};
        double[] x2 = new double[] {1.0,0.0,1.0};
        double[] x3 = new double[] {1.0,1.0,0.0};
        double[] x4 = new double[] {1.0,1.0,1.0};

        double[] d = new double[] {0.0,0.0,1.0,0.0};

        double[] w1 = new double[] {1.0,1.0,1.0};
        double p;

        //punkt a) ωk+1 = ωk + ρ(dk - yk)xk
        //(i) ρ = 1 oraz ω1 = (1, 1, 1)

        p = 1;
        perceptronA(x1,x2,x3,x4,w1,d,p);

        //(ii) ρ = 0.1 oraz ω1 = (-0.12, 0.4, 0.65)

        p = 0.1;
        w1[0] = -0.12;
        w1[1] = 0.4;
        w1[2] = 0.65;
        perceptronA(x1,x2,x3,x4,w1,d,p);

        //punkt b ωk+1 = ωk + c( suma źle rozpoznanych
        //wektorów przez wagę ωk)

        w1[0] = 1;
        w1[1] = 1;
        w1[2] = 1;
        double c = 1.0;
        perceptronB(x1,x2,x3,x4,w1,d,c);

    }

    public static void perceptronB(double[] x1, double[] x2, double[] x3, double[] x4,
                                   double[] w1, double[] d, double c){

        //sprawdzamy dla w1


        double[] x1t = new double[3]; x1t = x1;
        double[] x2t = new double[3]; x2t = x2;
        double[] x3t = new double[3]; x3t = x3;
        double[] x4t = new double[3]; x4t = x4;




        double[] w2 = xRecon(x1,x2,x3,x4,w1,d,c);
        double[] w3 = xRecon(x1,x2,x3,x4,w2,d,c);
        double[] w4 = xRecon(x1,x2,x3,x4,w3,d,c);


    }

    public static double[] xRecon (double[] x1, double[] x2, double[] x3, double[] x4,
                                   double[] w, double[] d, double c){
        //sprawdzic ktore wektory sa zle rozpoznane przez wage i z jakim znakiem je wstawic
        //mam liste 4 wartosci, 0 oznacza wektor dobrze rozpoznany,
        // -1 zle rozpoznany z minusem, 1 zle rozpoanny z plusem i mnoze je prezz wektory
        double[] result = new double[4];
        result[0] = vectorSign(sigmFunc(matrixMult(x1,w)),d[0]);
        result[1] = vectorSign(sigmFunc(matrixMult(x2,w)),d[0]);
        result[2] = vectorSign(sigmFunc(matrixMult(x3,w)),d[0]);
        result[3] = vectorSign(sigmFunc(matrixMult(x4,w)),d[0]);

        x1 = matrixMult(result[0],x1);
        x2 = matrixMult(result[1],x2);
        x3 = matrixMult(result[2],x3);
        x4 = matrixMult(result[3],x4);

        result = matrixAdd(w,matrixMult(c,matrixAdd(matrixAdd(x1,x2),matrixAdd(x3,x4))));

        return result;

    }

    public static double vectorSign (double y, double d){
        //funkcja zwraca znak wektora
        if (y == d) {
            return 0;
        } else if (y == 0 && d == 1){
            return 1;
        } else return -1;
    }

    public static void perceptronA(double[] x1, double[] x2, double[] x3, double[] x4,
                                   double[] w1, double[] d, double p){

        double[] y = new double[4];

        //y1
        y[0] = sigmFunc(matrixMult(w1, x1));
        double[] w2 = matrixAdd(w1, matrixMult((p*(d[0]-y[0])),x1));
        //y2
        y[1] = sigmFunc(matrixMult(w2, x2));
        double[] w3 = matrixAdd(w2, matrixMult((p*(d[1]-y[1])),x2));
        //y3
        y[2] = sigmFunc(matrixMult(w3, x3));
        double[] w4 = matrixAdd(w3, matrixMult((p*(d[2]-y[2])),x3));

        y[3] = sigmFunc(matrixMult(w4, x4));
        double[] w5 = matrixAdd(w4, matrixMult((p*(d[3]-y[3])),x4));

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




}
