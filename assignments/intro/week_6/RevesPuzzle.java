public class RevesPuzzle {
    private static void towerOfHanoi4(int n, int offset, char fromRod, char toRod, char auxRod1, char auxRod2)
    {
        if (n == 0)
            return;
        if (n == 1) {
            StdOut.println("Move disk " + (n + offset) + " from " + fromRod + " to " + toRod);
            return;
        }

        towerOfHanoi4(n - 2, offset, fromRod, auxRod1, auxRod2, toRod);
        StdOut.println("Move disk " + (n - 1 + offset) + " from " + fromRod + " to " + auxRod2);
        StdOut.println("Move disk " + (n + offset) + " from " + fromRod + " to " + toRod);
        StdOut.println("Move disk " + (n - 1 + offset) + " from " + auxRod2 + " to " + toRod);
        towerOfHanoi4(n - 2, offset, auxRod1, toRod, fromRod, auxRod2);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int k = (int) Math.rint(n + 1 - Math.sqrt(2 * n + 1));

        towerOfHanoi4(k, 0, 'A', 'B', 'C', 'D');
        towerOfHanoi4(n - k, k, 'A', 'D', 'B', 'C');
        towerOfHanoi4(k, 0, 'B', 'D', 'A', 'C');
    }
}
