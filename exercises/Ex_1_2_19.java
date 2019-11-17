public class Ex_1_2_19 {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        double r = Math.random();
        System.out.println(Math.round(a + r * (b - a)));
    }
}
