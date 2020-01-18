import java.awt.Color;

public class KernelFilter {
    private static void modifyKernel(double[][] kernel, int kernelSize, double coefficient) {
        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                kernel[i][j] *= coefficient;
            }
        }
    }

    private static Color[][] getColors(Picture picture, int row, int col, int kernelSize) {
        Color[][] colors = new Color[kernelSize][kernelSize];
        int sourceRow, sourceCol;

        for (int i = 0; i < kernelSize; i++) {
            sourceRow = (row - (kernelSize / 2) + i + picture.height()) % picture.height();

            for (int j = 0; j < kernelSize; j++) {
                sourceCol = (col - (kernelSize / 2) + j + picture.width()) % picture.width();

                colors[i][j] = picture.get(sourceCol, sourceRow);
            }
        }

        return colors;
    }

    private static Color applyKernel(Color[][] colors, double[][] kernel, int kernelSize) {
        double red = 0, green = 0, blue = 0;

        for (int i = 0; i < kernelSize; i++) {
            for (int j = 0; j < kernelSize; j++) {
                red += colors[i][j].getRed() * kernel[i][j];
                green += colors[i][j].getGreen() * kernel[i][j];
                blue += colors[i][j].getBlue() * kernel[i][j];
            }
        }

        if (red < 0) { red = 0; }
        else if (red > 255) { red = 255; }

        if (green < 0) { green = 0; }
        else if (green > 255) { green = 255; }

        if (blue < 0) { blue = 0; }
        else if (blue > 255) { blue = 255; }

        return new Color((int) Math.round(red), (int) Math.round(green), (int) Math.round(blue));
    }

    private static Picture transformPicture(Picture original, double[][] kernel, int kernelSize) {
        Picture filtered = new Picture(original.width(), original.height());

        for (int row = 0; row < original.height(); row++) {
            for (int col = 0; col < original.width(); col++) {
                filtered.set(col, row, applyKernel(getColors(original, row, col, kernelSize), kernel, kernelSize));
            }
        }

        return filtered;
    }

    // Returns a new picture that applies a Gaussian blur filter to the given picture.
    public static Picture gaussian(Picture original) {
        double coefficient = 1.0 / 16.0;
        double[][] kernel = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        int kernelSize = 3;

        modifyKernel(kernel, kernelSize, coefficient);

        return transformPicture(original, kernel, kernelSize);
    }

    // Returns a new picture that applies a sharpen filter to the given picture.
    public static Picture sharpen(Picture original) {
        double[][] kernel = {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        int kernelSize = 3;

        return transformPicture(original, kernel, kernelSize);
    }

    // Returns a new picture that applies an Laplacian filter to the given picture.
    public static Picture laplacian(Picture original) {
        double[][] kernel = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
        int kernelSize = 3;

        return transformPicture(original, kernel, kernelSize);
    }

    // Returns a new picture that applies an emboss filter to the given picture.
    public static Picture emboss(Picture original) {
        double[][] kernel = {{-2, -1, 0}, {-1, 1, 1}, {0, 1, 2}};
        int kernelSize = 3;

        return transformPicture(original, kernel, kernelSize);
    }

    // Returns a new picture that applies a motion blur filter to the given picture.
    public static Picture motionBlur(Picture original) {
        double coefficient = 1.0 / 9.0;
        double[][] kernel = {
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1}
        };
        int kernelSize = 9;

        modifyKernel(kernel, kernelSize, coefficient);

        return transformPicture(original, kernel, kernelSize);
    }

    // Test client (ungraded).
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);

        picture.show();
        gaussian(picture).show();
        sharpen(picture).show();
        laplacian(picture).show();
        emboss(picture).show();
        motionBlur(picture).show();
    }
}
