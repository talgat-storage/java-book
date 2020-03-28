public class RandomWalker {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);

        int x = 0, y = 0, count = 0;
        double rand;
        while (true) {
            System.out.println("(" + x + ", " + y + ")");
            if (Math.abs(x) + Math.abs(y) == r) {
                break;
            }

            rand = Math.random();
            if (rand < 0.25) {
                x -= 1;
            }
            else if (rand < 0.5) {
                x += 1;
            }
            else if (rand < 0.75) {
                y -= 1;
            }
            else {
                y += 1;
            }
            count += 1;
        }

        System.out.println("steps = " + count);
    }
}
