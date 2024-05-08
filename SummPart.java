package ru.itis.inf304.lab8.integral;

public class SummPart extends Thread {
    private double a;
    private double b;
    private double sum = 0;
    public static final int N = 100;

    public SummPart(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getPartSum() {
        return sum;
    }

    private Double func(Double x) {
        return Math.sin(x*x);
    }

    @Override
    public void run() {
        double h = (b - a) / N;
        for (int i = 0; i < N; ++ i) {
            sum += h * func(a + i * h + h/2);
        }
        Main.addtoTotalSum(sum);
    }
}