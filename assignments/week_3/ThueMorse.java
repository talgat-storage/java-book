public class ThueMorse {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int power_of_two = 1;
        while (power_of_two < n) power_of_two *= 2;

        int[] sequence = new int[power_of_two];

        for (int i = 1; i < power_of_two; i *= 2) {
            for (int j = i; j < 2 * i; j++) {
                if (sequence[j - i] == 0) {
                    sequence[j] = 1;
                }
            }
        }

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (sequence[row] == sequence[col]) {
                    System.out.print("+ ");
                }
                else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }
}
