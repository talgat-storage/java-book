public class ActivationFunction {
    public static double heaviside(double x) {
        double ans;

        if (x < 0) {
            ans = 0;
        }
        else if (x == 0) {
            ans = 0.5;
        }
        else if (x > 0) {
            ans = 1;
        }
        else {
            ans = Double.NaN;
        }

        return ans;
    }

    public static double sigmoid(double x) {
        return 1.0 / (1 + Math.exp(-x));
    }

    public static double tanh(double x) {
        if (x == Double.POSITIVE_INFINITY || x == Double.MAX_VALUE) {
            return 1.0;
        }
        if (x == Double.NEGATIVE_INFINITY || x == -Double.MAX_VALUE) {
            return -1.0;
        }
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }

    public static double softsign(double x) {
        if (x == Double.POSITIVE_INFINITY) {
            return 1.0;
        }
        if (x == Double.NEGATIVE_INFINITY) {
            return -1.0;
        }
        return x / (1 + Math.abs(x));
    }

    public static double sqnl(double x) {
        double ans;

        if (x <= -2) {
            ans = -1;
        }
        else if (x < 0) {
            ans = x + Math.pow(x, 2) / 4;
        }
        else if (x < 2) {
            ans = x - Math.pow(x, 2) / 4;
        }
        else if (x >= 2) {
            ans = 1;
        }
        else {
            ans = Double.NaN;
        }

        return ans;
    }

    public static void main(String[] args) {
        double x = Double.parseDouble(args[0]);

        StdOut.println("heaviside(" + x + ") = " + heaviside(x));
        StdOut.println("  sigmoid(" + x + ") = " + sigmoid(x));
        StdOut.println("     tanh(" + x + ") = " + tanh(x));
        StdOut.println(" softsign(" + x + ") = " + softsign(x));
        StdOut.println("     sqnl(" + x + ") = " + sqnl(x));
    }
}
