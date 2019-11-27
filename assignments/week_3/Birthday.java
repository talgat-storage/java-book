public class Birthday {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        float total_success_count = 0;
        int number_of_people = 1;
        while (2 * total_success_count < trials) {
            int success_count = 0;
            for (int i = 0; i < trials; i++) {
                boolean[] in_room = new boolean[n];
                for (int j = 0; j < number_of_people; j++) {
                    int r = (int) (Math.random() * n);
                    if (in_room[r]) {
                        if (j == number_of_people - 1) {
                            success_count += 1;
                        }
                        break;
                    }
                    in_room[r] = true;
                }
            }
            total_success_count += success_count;

            System.out.println(number_of_people + "\t" + success_count + "\t" + total_success_count / trials);

            number_of_people += 1;
        }
    }
}
