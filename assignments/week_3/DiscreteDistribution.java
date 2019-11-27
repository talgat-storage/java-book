public class DiscreteDistribution {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int[] sums = new int[args.length];

        sums[0] = 0;
        for (int i = 1; i < sums.length; i++) {
            sums[i] = sums[i - 1] + Integer.parseInt(args[i]);
        }

        for (int i = 0; i < m; i++) {
            int r =  (int) (Math.random() * sums[sums.length - 1]);
            int j;
            for (j = 0; j < sums.length; j++) {
                if (sums[j] > r) {
                    break;
                }
            }
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
