package com.werow.web;

public class test {
    public static void main(String[] args) {
        int n = 3002;
        int result1 = resolve1(n);
        System.out.println("result1 = " + result1);

        String[] a = {"A", "B", "C", "D", "E", "F"};
        String[] b = {"X", "Y", "Z"};
        String[] c = resolve2(a, b);
        StringBuilder result2 = new StringBuilder();
        for (String s : c) {
            result2.append(s);
            result2.append(" ");
        }
        System.out.println("result2 = " + result2);

        int[] A = {10, 100, 50, 50, 40, 40, 40};
        int result3 = resolve3(A);
        System.out.println("result3 = " + result3);
    }

    private static int resolve1(int n) {
        int sum = 0;
        while (n / 10 > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum + n;
    }

    private static String[] resolve2(String[] a, String[] b) {
        String[] c = new String[a.length * 2];
        for (int i = 0; i < a.length * 2; i++) {
            if (i % 2 == 0) {
                c[i] = a[i / 2];
            } else {
                c[i] = b[(i / 2) % b.length];
            }
        }
        return c;
    }

    private static int resolve3(int[] a) {
        int max = 0;
        int min = 100;
        int sum = 0;
        for (int i : a) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
            sum += i;
        }
        double average = (sum - max - min) / (a.length - 2);
        return (int) Math.ceil(average);
    }
}
