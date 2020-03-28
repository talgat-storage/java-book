import java.util.Arrays;

public class BarChartRacer {
    public static void main(String[] args) {
        String filename = args[0];
        int maxBarsToDisplay = Integer.parseInt(args[1]);

        In file = new In(filename);

        String title = file.readLine();
        String xAxis = file.readLine();
        String source = file.readLine();
        BarChart chart = new BarChart(title, xAxis, source);

        StdDraw.setCanvasSize(1000, 700);
        StdDraw.enableDoubleBuffering();

        file.readLine();

        while (file.hasNextLine()) {
            StdDraw.clear();
            chart.reset();
            int numberOfBars = Integer.parseInt(file.readLine());
            Bar[] bars = new Bar[numberOfBars];
            for (int i = 0; i < bars.length; i++) {
                String line = file.readLine();
                String[] tokens = line.split(",");
                bars[i] = new Bar(tokens[1], Integer.parseInt(tokens[3]), tokens[4]);
                chart.setCaption(tokens[0]);
            }

            file.readLine();

            Arrays.sort(bars);

            for (int i = bars.length - 1; i >= Math.abs(bars.length - maxBarsToDisplay); i--) {
                chart.add(bars[i].getName(), bars[i].getValue(), bars[i].getCategory());
            }

            chart.draw();
            StdDraw.show();
            StdDraw.pause(1);
        }
    }
}
