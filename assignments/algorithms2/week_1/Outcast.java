public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        if (nouns == null || nouns.length == 0) return null;

        if (nouns.length == 1) return nouns[0];

        String res = null;
        int max = -1;

        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;

            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    dist += wordNet.distance(nouns[i], nouns[j]);
                }
            }

            if (dist > max) {
                res = nouns[i];
                max = dist;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        // Empty
    }
}
