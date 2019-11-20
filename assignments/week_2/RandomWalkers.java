public class RandomWalkers {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        double sum = 0;
        for (int trial = 0; trial < trials; trial++) {
            int x = 0, y = 0, count = 0;
            double rand;
            while (Math.abs(x) + Math.abs(y) != r) {
                rand = Math.random();
                if (rand < 0.25) {
                    x += 1;
                }
                else if (rand < 0.5) {
                    x -= 1;
                }
                else if (rand < 0.75) {
                    y += 1;
                }
                else {
                    y -= 1;
                }
                count += 1;
            }
            sum += count - 1;
        }

        System.out.println("average number of steps = " + sum / trials);
    }
}
