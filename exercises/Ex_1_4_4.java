public class Ex_1_4_4 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        int a_len = a.length;
        int temp, i;

        for (i = 0; i < a_len / 2; i++) {
            temp = a[i];
            a[i] = a[a_len - 1 - i];
            a[a_len - 1 - i] = temp;
        }

        for (i = 0; i < a_len; i++) {
            System.out.println(a[i]);
        }
    }
}
