package org.example;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static ArrayBlockingQueue<String> queue1 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queue2 = new ArrayBlockingQueue<>(100);
    public static ArrayBlockingQueue<String> queue3 = new ArrayBlockingQueue<>(100);
    public static String A = "";
    public static String B = "";
    public static String C = "";
    public static int maxA = 0;
    public static int maxB = 0;
    public static int maxC = 0;

    public static void main(String[] args) throws InterruptedException {

        String text = generateText("abc", 100000);

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    queue1.put(text);
                    queue2.put(text);
                    queue3.put(text);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        Thread tread1 = new Thread(() -> {
            for (String s : queue1) {
                int countA = 0;
                for (char element : s.toCharArray()) {
                    if (element == 'a') countA++;
                }
                if (countA > maxA) {
                    maxA = countA;
                    A = s;
                }
            }
        });

        Thread tread2 = new Thread(() -> {
            for (String s : queue2) {
                int countB = 0;
                for (char element : s.toCharArray()) {
                    if (element == 'b') countB++;
                }
                if (countB > maxB) {
                    maxB = countB;
                    B = s;
                }
            }
        });

        Thread tread3 = new Thread(() -> {
            for (String s : queue3) {
                int countC = 0;
                for (char element : s.toCharArray()) {
                    if (element == 'c') countC++;
                }
                if (countC > maxC) {
                    maxC = countC;
                    C = s;
                }
            }


        });
        tread1.start();
        tread2.start();
        tread3.start();

        tread1.join();
        tread2.join();
        tread3.join();

        System.out.println(maxA);
        System.out.println(maxB);
        System.out.println(maxC);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}