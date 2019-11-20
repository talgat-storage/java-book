public class Ex_1_3_12_FunctionGrowth {
    public static void main(String[] args) {
        System.out.println("log n\t\t\t\tn\t\t\tn log n\t\t\t\tn^2\t\tn^3\t\t2^n");
        for (int n = 16; n <= 2048; n *= 2) {
            System.out.println(Math.log(n) + "\t" +
                            n + "\t\t\t" +
                            n * Math.log(n) + "\t" +
                            Math.pow(n, 2) + "\t" +
                            Math.pow(n, 3) + "\t" +
                            Math.pow(2, n)
                    );
        }
    }
}
