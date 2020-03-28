public class Divisors {
    public static int gcd(int a, int b) {
        int temp;

        if (a == 0 && b == 0) {
            return 0;
        }

        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            temp = a;
            a = b;
            b = temp % b;
        }

        return a;
    }

    public static int lcm(int a, int b) {
        if (a == 0 && b == 0) {
            return 0;
        }
        return (Math.abs(a) / gcd(a, b)) * Math.abs(b);
    }

    public static boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    public static int totient(int n) {
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (areRelativelyPrime(i, n)) {
                count += 1;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        StdOut.println("gcd(" + a + ", " + b + ") = " + gcd(a, b));
        StdOut.println("lcm(" + a + ", " + b + ") = " + lcm(a, b));
        StdOut.println("areRelativelyPrime(" + a + ", " + b + ") = " + areRelativelyPrime(a, b));
        StdOut.println("totient(" + a + ") = " + totient(a));
        StdOut.println("totient(" + b + ") = " + totient(b));
    }
}
