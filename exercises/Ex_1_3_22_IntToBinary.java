public class Ex_1_3_22_IntToBinary {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int initial = n;
        String res = "";

        while (n > 0) {
            if (n % 2 == 0) {
                res = "0" + res;
            }
            else {
                res = "1" + res;
            }
            n /= 2;
        }

        System.out.println(initial + ": " + res);
    }
}
