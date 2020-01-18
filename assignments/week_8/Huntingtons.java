public class Huntingtons {
    public static int maxRepeats(String dna) {
        int maxRepeats = 0;
        int count;

        for (int i = 0; i + 3 <= dna.length(); i++) {
            count = 0;
            while (i + 3 <= dna.length() && dna.substring(i, i + 3).equals("CAG")) {
                count += 1;
                i += 3;
            }
            if (count > 0) {
                i -= 1;
            }
            if (count > maxRepeats) {
                maxRepeats = count;
            }
        }

        return maxRepeats;
    }

    // Returns a copy of s, with all whitespace (spaces, tabs, and newlines) removed.
    public static String removeWhitespace(String s) {
        int lettersCount;
        char[] letters;
        int index;

        s = s.replace('\t', ' ');
        s = s.replace('\n', ' ');

        lettersCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                lettersCount += 1;
            }
        }

        letters = new char[lettersCount];
        index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                letters[index] = s.charAt(i);
                index += 1;
            }
        }

        return new String(letters);
    }

    // Returns one of these diagnoses corresponding to the maximum number of repeats:
    // "not human", "normal", "high risk", or "Huntington's".
    public static String diagnose(int maxRepeats) {
        if (maxRepeats < 10 || maxRepeats > 180) {
            return "not human";
        }
        else if (maxRepeats < 36) {
            return "normal";
        }
        else if (maxRepeats < 40) {
            return "high risk";
        }
        else {
            return "Huntington's";
        }
    }

    public static void main(String[] args) {
        String filename = args[0];
        In file = new In(filename);
        String dna = file.readAll();
        int maxRepeats = maxRepeats(removeWhitespace(dna));
        StdOut.println("max repeats = " + maxRepeats);
        StdOut.println(diagnose(maxRepeats));
    }
}
