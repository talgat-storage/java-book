public class Clock {
    private int h;
    private int m;

    public Clock(int hours, int minutes) {
        setInstanceVariables(hours, minutes);
    }

    public Clock(String s) {
        if (s.length() != 5 || !s.substring(2, 3).equals(":")) {
            throw new IllegalArgumentException();
        }

        h = Integer.parseInt(s.substring(0, 2));
        m = Integer.parseInt(s.substring(3, 5));

        setInstanceVariables(h, m);
    }

    private void setInstanceVariables(int hours, int minutes) {
        if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException();
        }

        this.h = hours;
        this.m = minutes;
    }

    public String toString() {
        return String.format("%02d:%02d", h, m);
    }

    public boolean isEarlierThan(Clock that) {
        return this.h < that.h || (this.h == that.h && this.m < that.m);
    }

    public void tic() {
        this.toc(1);
    }

    public void toc(int mDelta) {
        if (mDelta < 0) {
            throw new IllegalArgumentException();
        }

        int newM = (this.m + mDelta) % 60;
        int hDelta = (this.m + mDelta) / 60;
        int newH = (this.h + hDelta) % 24;

        setInstanceVariables(newH, newM);
    }

    public static void main(String[] args) {
        Clock current = new Clock(9, 0);
        Clock finish = new Clock(18, 0);

        current.tic();
        current.toc(89);

        StdOut.println(current);

        if (finish.isEarlierThan(current)) {
            StdOut.println("Work");
        }
    }
}
