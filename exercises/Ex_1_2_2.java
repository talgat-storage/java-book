public class Ex_1_2_2 {
    public static void main(String[] args) {
        double degrees = Double.parseDouble(args[0]);
        double radians = Math.toRadians(degrees);

        double sin = Math.sin(radians);
        double cos = Math.cos(radians);

        System.out.println(sin * sin + cos * cos);
    }
}
