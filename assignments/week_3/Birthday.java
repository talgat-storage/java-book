public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        int[] peopleCountFreq = new int[2 * n];
        for (int trial = 0; trial < trials; trial++) {
//            System.out.println("Trial: " + trial);

            boolean[] birthdays = new boolean[n];

            int peopleCount = 0;
            while (true) {
                peopleCount += 1;
                int r = (int) (Math.random() * n);
//                System.out.println("Birthday: " + r);

                if (birthdays[r]) {
                    break;
                }
                birthdays[r] = true;
            }

//            System.out.println("Stopped after " + peopleCount + " person(s)");

            peopleCountFreq[peopleCount - 1] += 1;
        }

        int totalPeopleCount = 0;
        for (int i = 0; i < 2 * n; i++) {
            totalPeopleCount += peopleCountFreq[i];

            System.out.println((i + 1) + "\t" + peopleCountFreq[i] + "\t" + (double) totalPeopleCount / trials);
            if (2 * totalPeopleCount >= trials) {
                break;
            }
        }
    }
}
