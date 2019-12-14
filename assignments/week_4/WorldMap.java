public class WorldMap {
    public static void main(String[] args) {
        int width = StdIn.readInt();
        int height = StdIn.readInt();

        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        while (!StdIn.isEmpty()) {
            StdIn.readString();

            int verticesCount = StdIn.readInt();
            double[] xCoords = new double[verticesCount];
            double[] yCoords = new double[verticesCount];

            for (int i = 0; i < verticesCount; i++) {
                xCoords[i] = StdIn.readDouble();
                yCoords[i] = StdIn.readDouble();
            }

            StdDraw.polygon(xCoords, yCoords);
        }

        StdDraw.show();
    }
}
