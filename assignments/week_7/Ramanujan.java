public class Ramanujan {
    public static boolean isRamanujan(long n) {
        long firstCube, secondCube;
        int count = 0;

        for (int i = 1; i <= Math.cbrt(n / 2.0); i++) {
            firstCube = i * i * i;
            secondCube = n - firstCube;

            if (Math.cbrt(secondCube) % 1 == 0) {
                count += 1;
            }
        }

        return count > 1;
    }

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);

        StdOut.println(isRamanujan(n));
    }
}
