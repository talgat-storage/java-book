public class TrinomialDP {
    public static long trinomial(int n, int k) {
        if (k < -n || k > n) {
            return 0;
        }

        long[][] dp = new long[n + 1][2 * n + 1];

        dp[0][n] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= 2 * n; j++) {
                if (j - 1 >= 0) {
                    dp[i][j] += dp[i - 1][j - 1];
                }
                dp[i][j] += dp[i - 1][j];
                if (j + 1 <= 2 * n) {
                    dp[i][j] += dp[i - 1][j + 1];
                }
            }
        }

        return dp[n][n + k];
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        StdOut.println(trinomial(n, k));
    }
}
