import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.PriorityQueue;

public class SeamCarver {
    private static final double MAX_ENERGY = 1000.0;
    private Picture picture;
    private double[][] pixelEnergy = null;
    private int pictureWidth;
    private int pictureHeight;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException();

        this.picture = new Picture(picture);

        pictureWidth = this.picture.width();
        pictureHeight = this.picture.height();
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return pictureWidth;
    }

    public int height() {
        return pictureHeight;
    }

    private boolean isPixelValid(int x, int y) {
        return x >= 0 && x < this.width() && y >= 0 && y < this.height();
    }

    public double energy(int x, int y) {
        if (!isPixelValid(x, y)) throw new IllegalArgumentException();

        if (x == 0 || x == this.width() - 1 || y == 0 || y == this.height() - 1) {
            return MAX_ENERGY;
        }

        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color up = picture.get(x, y + 1);
        Color down = picture.get(x, y - 1);

        int deltaX = (right.getRed() - left.getRed()) * (right.getRed() - left.getRed()) +
                (right.getGreen() - left.getGreen()) * (right.getGreen() - left.getGreen()) +
                (right.getBlue() - left.getBlue()) * (right.getBlue() - left.getBlue());
        int deltaY = (up.getRed() - down.getRed()) * (up.getRed() - down.getRed()) +
                (up.getGreen() - down.getGreen()) * (up.getGreen() - down.getGreen()) +
                (up.getBlue() - down.getBlue()) * (up.getBlue() - down.getBlue());

        return Math.sqrt(deltaX + deltaY);
    }

    private void initializeEnergy() {
        this.pixelEnergy = new double[this.width()][this.height()];

        for (int x = 0; x < this.width(); x++) {
            for (int y = 0; y < this.height(); y++) {
                pixelEnergy[x][y] = this.energy(x, y);
            }
        }
    }

    private void removeSeam(int[] seam, boolean isVertical) {
        if (seam == null) throw new IllegalArgumentException();

        if (isVertical ? seam.length != this.height() : seam.length != this.width())
            throw new IllegalArgumentException();

        if (isVertical ? this.width() <= 1 : this.height() <= 1) throw new IllegalArgumentException();

        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i] - seam[i - 1]) > 1) throw new IllegalArgumentException();
        }

        if (this.pixelEnergy == null) initializeEnergy();

        Picture newPicture = isVertical ?
                new Picture(this.width() - 1, this.height()) :
                new Picture(this.width(), this.height() - 1);

        if (isVertical) {
            for (int y = 0; y < this.height(); y++) {
                for (int x = 0; x < this.width(); x++) {
                    if (x == seam[y]) continue;
                    else if (x < seam[y]) {
                        newPicture.set(x, y, picture.get(x, y));
                    }
                    else {
                        newPicture.set(x - 1, y, picture.get(x, y));
                    }
                }
            }
        }
        else {
            for (int x = 0; x < this.width(); x++) {
                for (int y = 0; y < this.height(); y++) {
                    if (y == seam[x]) continue;
                    else if (y < seam[x]) {
                        newPicture.set(x, y, picture.get(x, y));
                    }
                    else {
                        newPicture.set(x, y - 1, picture.get(x, y));
                    }
                }
            }
        }

        picture = newPicture;

        pictureWidth = picture.width();
        pictureHeight = picture.height();

        pixelEnergy = null;
    }

    public void removeVerticalSeam(int[] seam) {
        removeSeam(seam, true);
    }

    public void removeHorizontalSeam(int[] seam) {
        removeSeam(seam, false);
    }

    private int getPixelSum(int x, int y) {
        return y * this.width() + x;
    }

    private int getXofPixelSum(int sum) {
        return sum % this.width();
    }

    private int getYofPixelSum(int sum) {
        return sum / this.width();
    }

    private int[] findSeam(int root, boolean isVertical) {
        int xRoot = isVertical ? root : 0;
        int yRoot = isVertical ? 0 : root;
        int rootSum = getPixelSum(xRoot, yRoot);

        double[] energyMap = new double[this.width() * this.height()];
        int[] edgeTo = new int[this.width() * this.height()];
        PriorityQueue<Integer> pq = new PriorityQueue<>((Integer p, Integer q) ->
                Double.compare(energyMap[p], energyMap[q]));

        for (int i = 0; i < energyMap.length; i++) {
            energyMap[i] = Double.POSITIVE_INFINITY;
        }

        if (this.pixelEnergy == null) initializeEnergy();

        energyMap[rootSum] = this.pixelEnergy[xRoot][yRoot];
        pq.offer(rootSum);

        while (!pq.isEmpty()) {
            int pixelSum = pq.poll();
            int x = getXofPixelSum(pixelSum);
            int y = getYofPixelSum(pixelSum);

            if (isVertical && y == this.height() - 1) {
                pq.offer(pixelSum);
                break;
            }
            else if (!isVertical && x == this.width() - 1) {
                pq.offer(pixelSum);
                break;
            }

            int[][] candidatePixels;
            if (isVertical) {
                candidatePixels = new int[][]{{x - 1, y + 1}, {x, y + 1}, {x + 1, y + 1}};
            }
            else {
                candidatePixels = new int[][]{{x + 1, y - 1}, {x + 1, y}, {x + 1, y + 1}};
            }

            for (int[] candidatePixel : candidatePixels) {
                int candidateX = candidatePixel[0];
                int candidateY = candidatePixel[1];

                if (!isPixelValid(candidateX, candidateY)) continue;

                int candidateSum = getPixelSum(candidateX, candidateY);

                double candidateEnergy = energyMap[pixelSum] + this.pixelEnergy[candidateX][candidateY];

                if (energyMap[candidateSum] == Double.POSITIVE_INFINITY || candidateEnergy < energyMap[candidateSum]) {
                    energyMap[candidateSum] = candidateEnergy;
                    edgeTo[candidateSum] = pixelSum;
                    pq.offer(candidateSum);
                }
            }
        }

        int[] seam = isVertical ? new int[this.height()] : new int[this.width()];
        int pixelSum = pq.poll();

        for (int index = isVertical ? this.height() - 1 : this.width() - 1; index >= 0; index--) {
            int pixelX = getXofPixelSum(pixelSum);
            int pixelY = getYofPixelSum(pixelSum);

            seam[index] = isVertical ? pixelX : pixelY;

            pixelSum = edgeTo[pixelSum];
        }

        return seam;
    }

    private double getSeamEnergy(int[] seam, boolean isVertical) {
        double energy = 0.0;

        if (this.pixelEnergy == null) initializeEnergy();

        for (int i = 0; i < seam.length; i++) {
            if (isVertical) {
                energy += this.pixelEnergy[seam[i]][i];
            }
            else {
                energy += this.pixelEnergy[i][seam[i]];
            }
        }

        return energy;
    }

    private int[] findSeam(boolean isVertical) {
        int[] minSeam = null;
        double minEnergy = Double.POSITIVE_INFINITY;

        for (int root = 0; isVertical ? root < this.width() : root < this.height(); root++) {
            int[] seam = findSeam(root, isVertical);

            double seamEnergy = getSeamEnergy(seam, isVertical);

            if (seamEnergy < minEnergy) {
                minEnergy = seamEnergy;
                minSeam = seam;
            }
        }

        return minSeam;
    }

    public int[] findVerticalSeam() {
        return findSeam(true);
    }

    public int[] findHorizontalSeam() {
        return findSeam(false);
    }

    public static void main(String[] args) {
        String filename = args[0];

        Picture picture = new Picture(filename);

        StdOut.println("Width: " + picture.width() + ", height: " + picture.height());

        SeamCarver seamCarver = new SeamCarver(picture);

        int[] seam = seamCarver.findVerticalSeam();

        double seamEnergy = seamCarver.getSeamEnergy(seam, true);

        System.out.println("Seam energy: " + seamEnergy);

        for (int i = 0; i < seam.length; i++) {
            System.out.print("(" + seam[i] + ", " + i + ") -> ");
        }
        System.out.println();

        seamCarver.removeVerticalSeam(new int[]{ 3, 4, 3, 2, 1 });
    }
}
