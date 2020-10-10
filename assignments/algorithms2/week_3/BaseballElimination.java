import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class BaseballElimination {
    private final int n;
    private final List<String> teamNames = new ArrayList<>();
    private final Map<String, Integer> teamIndices = new HashMap<>();

    private final int[] w;
    private final int[] l;
    private final int[] r;
    private final int[][] g;

    private final int source, target;

    private final Graph graph;

    public BaseballElimination(String filename) {
        In in = new In(filename);

        this.n = Integer.parseInt(in.readLine());

        this.w = new int[this.n];
        this.l = new int[this.n];
        this.r = new int[this.n];
        this.g = new int[this.n][this.n];

        for (int i = 0; i < n; i++) {
            String line = in.readLine();

            String[] tokens = line.trim().split("\\s+");

            teamNames.add(tokens[0]);
            teamIndices.put(tokens[0], i);

            w[i] = Integer.parseInt(tokens[1]);
            l[i] = Integer.parseInt(tokens[2]);
            r[i] = Integer.parseInt(tokens[3]);

            for (int j = 0; j < n; j++) {
                g[i][j] = Integer.parseInt(tokens[j + 4]);
            }
        }

        source = (n + 1) * n;
        target = (n + 1) * n + 1;

        graph = new Graph();

//        graph.printForward();
//
//        graph.printBackward();

//        graph.printAdj();
    }

    public int numberOfTeams() {
        return n;
    }

    public Iterable<String> teams() {
        return teamNames;
    }

    private Integer getTeamIndex(String teamName) {
        return teamIndices.get(teamName);
    }

    private String getTeamName(int teamIndex) {
        return teamNames.get(teamIndex);
    }

    public int wins(String teamName) {
        Integer teamIndex = getTeamIndex(teamName);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        return w[getTeamIndex(teamName)];
    }

    public int losses(String teamName) {
        Integer teamIndex = getTeamIndex(teamName);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        return l[getTeamIndex(teamName)];
    }

    public int remaining(String teamName) {
        Integer teamIndex = getTeamIndex(teamName);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        return r[getTeamIndex(teamName)];
    }

    public int against(String teamName1, String teamName2) {
        Integer teamIndex1 = getTeamIndex(teamName1);
        Integer teamIndex2 = getTeamIndex(teamName2);
        if (teamIndex1 == null || teamIndex2 == null) {
            throw new IllegalArgumentException();
        }

        return g[getTeamIndex(teamName1)][getTeamIndex(teamName2)];
    }

    private int getGameIndex(int team1, int team2) {
        return n + team1 * n + team2;
    }

    private int getGameTeam1(int index) {
        return index / n - 1;
    }

    private int getGameTeam2(int index) {
        return index % n;
    }

    private class Edge {
        private final int u;
        private final int v;
        private int capacity;
        private int flow;

        public Edge(int u, int v, int capacity) {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
        }

        public int from() {
            return u;
        }

        public int to() {
            return v;
        }

        public int other(int either) {
            if (either == u) return v;
            else return u;
        }

        public int capacityTo(int vertex) {
            if (vertex == v) return capacity - flow;
            else return flow;
        }

        public void addFlowTo(int vertex, int delta) {
            if (vertex == v) flow += delta;
            else flow -= delta;
        }

        public int flow() {
            return flow;
        }

        public void resetWithCapacity(int capacity) {
            this.capacity = capacity;
            this.flow = 0;
        }

        public void resetFlow() {
            this.flow = 0;
        }
    }

    private class Graph {
        List<List<Edge>> matrix;

        public Graph() {
            matrix = new ArrayList<>();
            for (int i = 0; i < (n + 1) * n + 2; i++) {
                matrix.add(new ArrayList<>());
            }

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int gameIndex = getGameIndex(i, j);

                    int numGames = g[i][j];

                    Edge edge = new Edge(source, gameIndex, numGames);

                    matrix.get(source).add(edge);
                    matrix.get(gameIndex).add(edge);

                    edge = new Edge(gameIndex, i, Integer.MAX_VALUE);

                    matrix.get(gameIndex).add(edge);
                    matrix.get(i).add(edge);

                    edge = new Edge(gameIndex, j, Integer.MAX_VALUE);

                    matrix.get(gameIndex).add(edge);
                    matrix.get(j).add(edge);
                }
            }

            for (int i = 0; i < n; i++) {
                Edge edge = new Edge(i, target, 0);

                matrix.get(i).add(edge);
                matrix.get(target).add(edge);
            }
        }

        public int V() {
            return matrix.size();
        }

        public List<Edge> adj(int vertex) {
            return matrix.get(vertex);
        }

        private void printNode(int index) {
            if (index == source) {
                System.out.print("Source");
            }
            else if (index == target) {
                System.out.print("Target");
            }
            else if (index < n) {
                System.out.print("Team " + index);
            }
            else {
                int team1 = getGameTeam1(index);
                int team2 = getGameTeam2(index);

                System.out.print("Game " + team1 + "-" + team2);
            }
        }

        private void printEdge(Edge edge, int from, boolean forward) {
            if (forward && edge.from() != from) return;
            if (!forward && edge.to() != from) return;

            printNode(forward ? edge.from() : edge.to());
            System.out.print(" points to ");
            printNode(forward ? edge.to() : edge.from());
            System.out.println(" (" + edge.capacityTo(forward ? edge.to() : edge.from()) + ")");
        }

        public void printForward() {
            System.out.println("FORWARD");

            System.out.println("From source");
            for (Edge edge : graph.adj(source)) {
                printEdge(edge, source, true);
            }

            System.out.println();

            System.out.println("From games");
            for (int i = n; i < n * n; i++) {
                for (Edge edge : graph.adj(i)) {
                    printEdge(edge, i, true);
                }
            }

            System.out.println();

            System.out.println("From teams");
            for (int i = 0; i < n; i++) {
                for (Edge edge : graph.adj(i)) {
                    printEdge(edge, i, true);
                }
            }

            System.out.println();

            System.out.println("From target");

            for (Edge edge : graph.adj(target)) {
                printEdge(edge, target, true);
            }
        }

        public void printBackward() {
            System.out.println("BACKWARD");

            System.out.println("From target");
            for (Edge edge : graph.adj(target)) {
                printEdge(edge, target, false);
            }

            System.out.println();

            System.out.println("From teams");
            for (int i = 0; i < n; i++) {
                for (Edge edge : graph.adj(i)) {
                    printEdge(edge, i, false);
                }
            }

            System.out.println();

            System.out.println("From games");
            for (int i = n; i < n * n; i++) {
                for (Edge edge : graph.adj(i)) {
                    printEdge(edge, i, false);
                }
            }

            System.out.println();

            System.out.println("From source");

            for (Edge edge : graph.adj(source)) {
                printEdge(edge, source, false);
            }
        }

        public void resetFlow() {
            for (Edge edge : graph.adj(source)) {
                edge.resetFlow();
            }

            for (int i = 0; i < n; i++) {
                for (Edge edge : graph.adj(i)) {
                    edge.resetFlow();
                }
            }
        }

        public void clearTeam(int index) {
            for (Edge teamEdge : graph.adj(index)) {
                if (teamEdge.to() == target) {
                    teamEdge.resetWithCapacity(0);
                }
                else {
                    for (Edge gameEdge : graph.adj(teamEdge.from())) {
                        gameEdge.resetWithCapacity(0);
                    }
                }
            }
        }

        public void restoreTeam(int index) {
            for (Edge teamEdge : graph.adj(index)) {
                if (teamEdge.to() == target) {
                    teamEdge.resetWithCapacity(0);
                }
                else {
                    for (Edge gameEdge : graph.adj(teamEdge.from())) {
                        if (gameEdge.from() != source) {
                            gameEdge.resetWithCapacity(Integer.MAX_VALUE);
                        }
                        else {
                            gameEdge.resetWithCapacity(g[getGameTeam1(gameEdge.to())][(getGameTeam2(gameEdge.to()))]);
                        }
                    }
                }
            }
        }

        public void printAdj() {
            for (int i = 0; i < matrix.size(); i++) {
                printNode(i);
                System.out.println(" edges:");
                for (Edge edge : matrix.get(i)) {
                    if (edge.from() == i) {
                        System.out.print("To ");
                        printNode(edge.to());
                    }
                    else if (edge.to() == i) {
                        System.out.print("From ");
                        printNode(edge.from());
                    }
                    else {
                        System.out.println("ERROR");
                    }
                    System.out.println(" (" + edge.capacity + ", " + edge.flow + ")");
                }
                System.out.println();
            }
        }
    }

    private class FF {
        private boolean[] marked;     // marked[v] = true iff s->v path in residual graph
        private Edge[] edgeTo;    // edgeTo[v] = last edge on shortest residual s->v path
        private int value;         // current value of max flow
        private FlowNetwork flowNetwork = new FlowNetwork(2, 1);
        private FordFulkerson ff = new FordFulkerson(flowNetwork, 0, 1);

        public FF() {
            while (hasAugmentingPath()) {
                // compute bottleneck capacity
                int bottle = Integer.MAX_VALUE;
                for (int v = target; v != source; v = edgeTo[v].other(v)) {
                    bottle = Math.min(bottle, edgeTo[v].capacityTo(v));
                }

                // augment flow
                for (int v = target; v != source; v = edgeTo[v].other(v)) {
                    edgeTo[v].addFlowTo(v, bottle);
                }

                value += bottle;
            }
        }

        // is there an augmenting path?
        // if so, upon termination edgeTo[] will contain a parent-link representation of such a path
        // this implementation finds a shortest augmenting path (fewest number of edges),
        // which performs well both in theory and in practice
        private boolean hasAugmentingPath() {
            edgeTo = new Edge[graph.V()];
            marked = new boolean[graph.V()];

            // breadth-first search
            Queue<Integer> q = new LinkedList<>();
            q.offer(source);
            marked[source] = true;
            while (!q.isEmpty() && !marked[target]) {
                int v = q.poll();

                for (Edge e : graph.adj(v)) {
                    int w = e.other(v);

                    // if residual capacity from v to w
                    if (e.capacityTo(w) > 0) {
                        if (!marked[w]) {
                            edgeTo[w] = e;
                            marked[w] = true;
                            q.offer(w);
                        }
                    }
                }
            }

            // is there an augmenting path?
            return marked[target];
        }

        public int maxFlow() {
            return value;
        }

        public boolean inCut(int vertex) {
            return marked[vertex];
        }
    }

    private boolean isTrivial(int teamIndex) {
        for (int i = 0; i < n; i++) {
            if (w[i] > w[teamIndex] + r[teamIndex]) {
                return true;
            }
        }
        return false;
    }

    public boolean isEliminated(String team) {
        Integer teamIndex = getTeamIndex(team);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        if (isTrivial(teamIndex)) {
            return true;
        }

        graph.resetFlow();

        for (Edge edge : graph.adj(target)) {
            edge.resetWithCapacity(Math.max(w[teamIndex] + r[teamIndex] - w[edge.from()], 0));
        }

        graph.clearTeam(teamIndex);

//        System.out.println("After clearing team " + teamIndex);
//
//        graph.printAdj();

//        graph.printForward();

        int maxCapacity = 0;
        for (Edge edge : graph.adj(source)) {
            maxCapacity += edge.capacity;
        }

        FF ff = new FF();

        graph.restoreTeam(teamIndex);

//        System.out.println("Max capacity: " + maxCapacity);
//        System.out.println("Max flow: " + ff.maxFlow());

        return maxCapacity != ff.maxFlow();
    }

    public Iterable<String> certificateOfElimination(String team) {
        Integer teamIndex = getTeamIndex(team);
        if (teamIndex == null) {
            throw new IllegalArgumentException();
        }

        List<String> ans = new ArrayList<>();

        if (isTrivial(teamIndex)) {
            for (int i = 0; i < n; i++) {
                if (w[i] > w[teamIndex] + r[teamIndex]) {
                    ans.add(getTeamName(i));
                }
            }

            return ans;
        }

//        graph.printForward();
        graph.resetFlow();

        for (Edge edge : graph.adj(target)) {
            edge.resetWithCapacity(w[teamIndex] + r[teamIndex] - w[edge.from()]);
        }

        graph.clearTeam(teamIndex);

//        graph.printForward();

        FF ff = new FF();

        for (int i = 0; i < n; i++) {
            if (ff.inCut(i)) {
                ans.add(getTeamName(i));
            }
        }

        graph.restoreTeam(teamIndex);

        if (ans.size() == 0) {
            return null;
        }

        return ans;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                System.out.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    System.out.print(t + " ");
                }
                System.out.println("}");
            }
            else {
                System.out.println(team + " is not eliminated: " +
                        (division.certificateOfElimination(team) == null ? "null" : "not null"));
            }
        }
    }
}
