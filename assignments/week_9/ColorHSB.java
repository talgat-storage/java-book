public class ColorHSB {
    private final int h;
    private final int s;
    private final int b;

    public ColorHSB(int hue, int saturation, int brightness) {
        if (hue < 0 || hue > 359 || saturation < 0 || saturation > 100 || brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException();
        }
        h = hue;
        s = saturation;
        b = brightness;
    }

    public String toString() {
        return "(" + h + ", " + s + ", " + b + ")";
    }

    public boolean isGrayscale() {
        return s == 0 || b == 0;
    }

    public int distanceSquaredTo(ColorHSB that) {
        if (that == null) {
            throw new IllegalArgumentException();
        }

        int hueDiff = Math.min(
                (this.h - that.h) * (this.h - that.h),
                (360 - Math.abs(this.h - that.h)) * (360 - Math.abs(this.h - that.h))
        );

        return hueDiff + (this.s - that.s) * (this.s - that.s) + (this.b - that.b) * (this.b - that.b);
    }

    public static void main(String[] args) {
        int h = Integer.parseInt(args[0]);
        int s = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);

        ColorHSB originColor = new ColorHSB(h, s, b);

        String closestColorName = null;
        ColorHSB closestColor = null;

        String colorName;
        ColorHSB color;

        while (!StdIn.isEmpty()) {
            colorName = StdIn.readString();
            color = new ColorHSB(StdIn.readInt(), StdIn.readInt(), StdIn.readInt());

            if (closestColor == null || originColor.distanceSquaredTo(color) < originColor.distanceSquaredTo(closestColor)) {
                closestColorName = colorName;
                closestColor = color;
            }
        }

        if (closestColorName != null && closestColor != null) {
            StdOut.println(closestColorName + " " + closestColor);
        }
    }
}
