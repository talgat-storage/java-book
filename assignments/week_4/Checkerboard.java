public class Checkerboard {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if ((row + col) % 2 == 0) {
                    StdDraw.setPenColor(StdDraw.BLUE);
                }
                else {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                }

                StdDraw.filledSquare(row + 0.5, col + 0.5, 0.5);
            }
        }
    }
}
