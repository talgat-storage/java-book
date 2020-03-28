public class AudioCollage {
    public static double[] amplify(double[] a, double alpha) {
        double[] b = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            b[i] = a[i] * alpha;
        }

        return b;
    }

    public static double[] reverse(double[] a) {
        double[] b = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            b[i] = a[a.length - i - 1];
        }

        return b;
    }

    public static double[] merge(double[] a, double[] b) {
        double[] c = new double[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i];
        }

        for (int i = 0; i < b.length; i++) {
            c[a.length + i] = b[i];
        }

        return c;
    }

    public static double[] mix(double[] a, double[] b) {
        double[] c = new double[Math.max(a.length, b.length)];

        for (int i = 0; i < Math.max(a.length, b.length); i++) {
            c[i] = 0;
            if (i < a.length) {
                c[i] += a[i];
            }
            if (i < b.length) {
                c[i] += b[i];
            }
        }

        return c;
    }

    public static double[] changeSpeed(double[] a, double alpha) {
        int length = (int) (Math.floor(a.length / alpha));

        double[] b = new double[length];

        for (int i = 0; i < length; i++) {
            b[i] = a[(int) (i * alpha)];
        }

        return b;
    }

    public static void main(String[] args) {
        double[] scratch = StdAudio.read("scratch.wav");
        double[] exposure = StdAudio.read("exposure.wav");
        double[] harp = StdAudio.read("harp.wav");
        double[] singer = StdAudio.read("singer.wav");
        double[] cow = StdAudio.read("cow.wav");

        double[] harpSlowedAndRev = reverse(changeSpeed(harp, 0.5));

        double[] result;
        double[] instrumental;

        result = merge(new double[0], scratch);
        result = merge(result, exposure);
        result = merge(result, harp);
        result = merge(result, harpSlowedAndRev);

        instrumental = merge(new double[0], harpSlowedAndRev);
        instrumental = merge(instrumental, harpSlowedAndRev);
        instrumental = merge(instrumental, harpSlowedAndRev);
        instrumental = merge(instrumental, harpSlowedAndRev);

        result = merge(result, amplify(mix(instrumental, singer), 0.5));
        result = merge(result, reverse(cow));

        StdAudio.play(result);
    }
}
