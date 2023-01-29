package item6;

public class Sum {
    private static long autoBoxingSum() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }

    private static long noneAutoBoxingSum() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("=== autoboxing ===");
        long start = System.nanoTime();
        long x = autoBoxingSum();
        long end = System.nanoTime();
        System.out.println((end - start) / 1_000_000. + " ms.");
        System.out.println(x);

        System.out.println("=== none autoboxing ===");
        long start2 = System.nanoTime();
        long x2 = noneAutoBoxingSum();
        long end2 = System.nanoTime();
        System.out.println((end2 - start2) / 1_000_000. + " ms.");
        System.out.println(x2);
    }
}
