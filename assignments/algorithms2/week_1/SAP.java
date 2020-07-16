import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SAP {
    private final Digraph graph;

    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException();

        graph = new Digraph(G);
    }

    private void verifyVertices(Iterable<Integer> it) {
        for (Integer id : it) {
            if (id == null || id < 0 || id >= graph.V()) throw new IllegalArgumentException();
        }
    }

    private int[] getAncestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();

        verifyVertices(v);
        verifyVertices(w);

        boolean[] markedFromA = new boolean[graph.V()];
        int[] distFromA = new int[graph.V()];
        Queue<Integer> q = new LinkedList<Integer>();

        for (int id : v) {
            markedFromA[id] = true;
            q.offer(id);
        }
        while (!q.isEmpty()) {
            int id = q.poll();

            for (int adj : graph.adj(id)) {
                if (!markedFromA[adj]) {
                    markedFromA[adj] = true;
                    distFromA[adj] = distFromA[id] + 1;
                    q.offer(adj);
                }
            }
        }

        boolean[] markedFromB = new boolean[graph.V()];
        int[] distFromB = new int[graph.V()];
        int minDist = -1;
        int ancestor = -1;
        for (int id : w) {
            markedFromB[id] = true;
            q.offer(id);
        }
        while (!q.isEmpty()) {
            int id = q.poll();

            if (markedFromA[id]) {
                if (minDist == -1 || distFromA[id] + distFromB[id] < minDist) {
                    minDist = distFromA[id] + distFromB[id];
                    ancestor = id;
                }
            }

            for (int adj : graph.adj(id)) {
                if (!markedFromB[adj]) {
                    markedFromB[adj] = true;
                    distFromB[adj] = distFromB[id] + 1;
                    q.offer(adj);
                }
            }
        }

        return new int[]{ ancestor, minDist };
    }

    public int length(int v, int w) {
        List<Integer> vList = new ArrayList<Integer>();
        List<Integer> wList = new ArrayList<Integer>();

        vList.add(v);
        wList.add(w);

        return length(vList, wList);
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return getAncestor(v, w)[1];
    }

    public int ancestor(int v, int w) {
        List<Integer> vList = new ArrayList<Integer>();
        List<Integer> wList = new ArrayList<Integer>();

        vList.add(v);
        wList.add(w);

        return ancestor(vList, wList);
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return getAncestor(v, w)[0];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
