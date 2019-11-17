public class Ex_1_2_24 {
    public static void main(String[] args) {
        double initial = Double.parseDouble(args[0]);
        double interest = Double.parseDouble(args[1]);
        double years = Double.parseDouble(args[2]);

        System.out.println(initial * Math.exp(interest * years));
    }
}
