public class Ex_1_5_3_Average {
    public static void main(String[] args) {
        double sum = 0.0;
        int n = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            sum += x;
            n += 1;
        }
        StdOut.println("Average is " + sum / n);
    }
}
