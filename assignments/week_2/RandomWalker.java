public class RandomWalker {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);

        int x = 0, y = 0, count = 0;
        double rand1, rand2;
        while (Math.abs(x) + Math.abs(y) != r) {
            System.out.println("(" + x + ", " + y + ")");
            rand1 = Math.random();
            rand2 = Math.random();
            if (rand1 < 0.5) {
                if (rand2 < 0.5) {
                    x += 1;
                }
                else {
                    x -= 1;
                }
            }
            else {
                if (rand2 < 0.5) {
                    y += 1;
                }
                else {
                    y -= 1;
                }
            }
            count += 1;
        }
        System.out.println("steps = " + (count - 1));
    }
}
