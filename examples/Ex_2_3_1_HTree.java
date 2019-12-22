public class Ex_2_3_1_HTree {
    public static void draw(int n, double size, double x, double y) {
        if (n == 0) return;

        double x0 = x - size / 2, x1 = x + size / 2;
        double y0 = y - size / 2, y1 = y + size / 2;

        StdDraw.line(x0, y, x1, y);
        StdDraw.line(x0, y0, x0, y1);
        StdDraw.line(x1, y0, x1, y1);

        StdDraw.pause(20);

        draw(n - 1, size / 2, x0, y0);
        draw(n - 1, size / 2, x0, y1);
        draw(n - 1, size / 2, x1, y1);
        draw(n - 1, size / 2, x1, y0);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        StdDraw.setScale(0, 2);
        draw(n, 1, 1, 1);
    }
}
