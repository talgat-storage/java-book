public class ShannonEntropy {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);

        double[] results = new double[m + 1];
        int totalCount = 0;
        double answer = 0;

        while (!StdIn.isEmpty()) {
            int num = StdIn.readInt();
            results[num] += 1;
            totalCount += 1;
        }

        for (int i = 1; i <= m; i++) {
            if (results[i] != 0) {
                results[i] = results[i] / totalCount;

                results[i] = 0 - results[i] * Math.log(results[i]) / Math.log(2);

                answer += results[i];
            }
        }

        StdOut.printf("%.4f\n", answer);
    }
}
