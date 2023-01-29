package com.example.effective.item6;

public class Sum {

    private static long sum() {
        long sum = 0L; // wrapper 사용 여부에 따라 성능차이 auto boxing이 생기는 경우 반복만큼 인스턴스가 생성됨
        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }
        return sum;
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        long x = sum();
        long end = System.nanoTime();
        System.out.println((end - start) / 1_000_000 + " ms.");
        System.out.println(x);
    }
}
