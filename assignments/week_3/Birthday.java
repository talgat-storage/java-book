public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        boolean[] inRoom = new boolean[n];
        int i, j, k, r;

        double totalSuccessCount = 0;
        int numberOfPeople = 1;
        while (2 * totalSuccessCount < trials) {
            int successCount = 0;
            for (i = 0; i < trials; i++) {
                for (j = 0; j < inRoom.length; j++) {
                    inRoom[j] = false;
                }
                for (k = 0; k < numberOfPeople; k++) {
                    r = (int) (Math.random() * n);
                    if (inRoom[r]) {
                        if (k == numberOfPeople - 1) {
                            successCount += 1;
                        }
                        break;
                    }
                    inRoom[r] = true;
                }
            }

            totalSuccessCount += successCount;

            System.out.println(numberOfPeople + "\t" + successCount + "\t" + totalSuccessCount / trials);

            numberOfPeople += 1;

            if (numberOfPeople > n * 128) {
                break;
            }
        }
    }
}
