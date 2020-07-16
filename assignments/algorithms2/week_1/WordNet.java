import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private final List<String> synsets = new ArrayList<String>();
    private final Map<String, List<Integer>> wordToSynsetIds = new HashMap<String, List<Integer>>();
    private final Digraph graph;
    private final SAP sap;

    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In in = new In(synsets);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            this.synsets.add(synset);
            String[] words = synset.split(" ");
            for (String word : words) {
                if (!wordToSynsetIds.containsKey(word)) {
                    wordToSynsetIds.put(word, new ArrayList<Integer>());
                }
                wordToSynsetIds.get(word).add(id);
            }
        }

        graph = new Digraph(this.synsets.size());

        in = new In(hypernyms);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int idx = Integer.parseInt(fields[i]);

                graph.addEdge(id, idx);
            }
        }

        if (!isDAG()) throw new IllegalArgumentException();

        sap = new SAP(graph);
    }

    private boolean isDAG() {
        return !isCyclic() && isRooted();
    }

    private boolean isCyclic() {
        boolean[] marked = new boolean[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i] && isCyclicDfs(i, marked, new boolean[graph.V()])) {
                return true;
            }
        }

        return false;
    }

    private boolean isCyclicDfs(int idx, boolean[] marked, boolean[] trace) {
        marked[idx] = true;
        trace[idx] = true;

        for (int adj : graph.adj(idx)) {
            if (!marked[adj]) {
                if (isCyclicDfs(adj, marked, trace)) {
                    return true;
                }
            }
            else if (trace[adj]) {
                return true;
            }
        }

        trace[idx] = false;

        return false;
    }

    private boolean isRooted() {
        int count = 0;

        for (int id = 0; id < graph.V(); id++) {
            if (graph.outdegree(id) == 0 && graph.indegree(id) != 0) {
                count += 1;
            }
        }

        return count == 1;
    }

    public Iterable<String> nouns() {
        return wordToSynsetIds.keySet();
    }

    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();

        return wordToSynsetIds.containsKey(word);
    }

    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        if (!wordToSynsetIds.containsKey(nounA) || !wordToSynsetIds.containsKey(nounB))
            throw new IllegalArgumentException();

        List<Integer> a = wordToSynsetIds.get(nounA);
        List<Integer> b = wordToSynsetIds.get(nounB);

        return sap.length(a, b);
    }

    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null) throw new IllegalArgumentException();

        if (!wordToSynsetIds.containsKey(nounA) || !wordToSynsetIds.containsKey(nounB))
            throw new IllegalArgumentException();

        List<Integer> a = wordToSynsetIds.get(nounA);
        List<Integer> b = wordToSynsetIds.get(nounB);

        return synsets.get(sap.ancestor(a, b));
    }

    public static void main(String[] args) {
        // Empty
    }
}
