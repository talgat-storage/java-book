public class BandMatrix {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (Math.abs(col - row) > width) {
                    System.out.print("0  ");
                }
                else {
                    System.out.print("*  ");
                }
            }
            System.out.println();
        }
    }
}
