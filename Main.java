package ru.itis.inf304.lab8.integral;
import java.util.ArrayList;
import java.util.List;
public class Main {

    private static double totalSum = 0.0;

    public static void main(String[] args) throws InterruptedException {
        int count = Runtime.getRuntime().availableProcessors();
        System.out.println("Counts of streams: " + count);
        double sp = Math.sqrt(Math.PI);
        double h = sp/count;

        runWithOutSynchronization(count, h);

        runWithSynchronization(count, h);
    }



    private static void runWithOutSynchronization(int count, double h) throws InterruptedException{
        List<SummPart> thList = new ArrayList<>();
        double startTime = System.nanoTime();

        for (int i = 0; i < count; i++) {
            thList.add(new SummPart(i * h, (i + 1) *h));
        }
        thList.forEach(Thread::start);

        for (SummPart thread : thList){
            thread.join();
        }

        double endTime = System.nanoTime();
        double result = thList.stream().mapToDouble(SummPart::getPartSum).sum();

        System.out.println("Result without synchronization: " + result + " : " + (endTime - startTime));
    }

    private static void runWithSynchronization(int count, double h) throws InterruptedException{
        totalSum = 0.0;
        List<SummPart> thList = new ArrayList<>();
        double startTime = System.nanoTime();

        for (int i = 0; i < count; i++) {
            thList.add(new SummPart(i * h, (i + 1) *h));
        }
        thList.forEach(Thread::start);

        for (SummPart thread : thList){
            thread.join();
        }

        double endTime = System.nanoTime();

        System.out.println("Result with synchronization: " + totalSum + " : " + (endTime - startTime));
    }


    public static synchronized void addtoTotalSum(double partSum) {
        totalSum += partSum;
    }
}
