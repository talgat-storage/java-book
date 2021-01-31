import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class BoggleSolver
{
    private final int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    private static class TrieNode {
        public TrieNode[] children = new TrieNode[26];
        public boolean end = false;
    }

    private final TrieNode root;

    public BoggleSolver(String[] dictionary) {
        this.root = new TrieNode();

        for (String word : dictionary) {
            TrieNode curr = root;
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);

                if (curr.children[ch - 'A'] == null) {
                    curr.children[ch - 'A'] = new TrieNode();
                }

                curr = curr.children[ch - 'A'];
            }
            curr.end = true;
        }
    }

    private void helper(BoggleBoard board, int row, int col, boolean[][] visited,
                        StringBuilder sb, TrieNode curr, Set<String> ans) {
        if (row < 0 || row >= board.rows() ||
                col < 0 || col >= board.cols() ||
                visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        char ch = board.getLetter(row, col);

        if (ch == 'Q') {
            if (curr.children[ch - 'A'] != null &&
                    curr.children[ch - 'A'].children['U' - 'A'] != null) {
                sb.append('Q').append('U');
                curr = curr.children[ch - 'A'].children['U' - 'A'];

                if (curr.end && sb.length() >= 3) {
                    ans.add(sb.toString());
                }

                for (int i = 0; i < dr.length; i++) {
                    helper(board, row + dr[i], col + dc[i], visited, sb, curr, ans);
                }

                sb.setLength(sb.length() - 2);
            }
        }
        else {
            if (curr.children[ch - 'A'] != null) {
                sb.append(ch);
                curr = curr.children[ch - 'A'];

                if (curr.end && sb.length() >= 3) {
                    ans.add(sb.toString());
                }

                for (int i = 0; i < dr.length; i++) {
                    helper(board, row + dr[i], col + dc[i], visited, sb, curr, ans);
                }

                sb.setLength(sb.length() - 1);
            }
        }

        visited[row][col] = false;
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        StringBuilder sb = new StringBuilder();
        Set<String> ans = new HashSet<>();

        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                helper(board, row, col, visited, sb, root, ans);
            }
        }

        return ans;
    }

    public int scoreOf(String word) {
        if (word.length() < 3) return 0;

        TrieNode curr = root;

        int i = 0;
        while (curr != null && i < word.length()) {
            char ch = word.charAt(i);

            curr = curr.children[ch - 'A'];

            i += 1;
        }

        if (curr != null && curr.end) {
            if (word.length() <= 4) return 1;
            else if (word.length() == 5) return 2;
            else if (word.length() == 6) return 3;
            else if (word.length() == 7) return 5;
            return 11;
        }

        return 0;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        Iterable<String> validWords = solver.getAllValidWords(board);
        int count = 0;
        for (String word : validWords) {
            count += 1;
        }
        StdOut.println(count + " words");
        int score = 0;
        for (String word : validWords) {
            int wordScore = solver.scoreOf(word);
            if (wordScore > 0) {
                StdOut.println(word + " " + wordScore);
            }
            score += wordScore;
        }
        StdOut.println("Score = " + score);
    }
}
