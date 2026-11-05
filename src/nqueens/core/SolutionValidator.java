
package nqueens.core;

public final class SolutionValidator {
    private SolutionValidator() {}

    public static boolean isValid(Board board) {
        if (board == null || board.size() < 1) {
            return false;
        }

        int n = board.size();
        boolean[] columns = new boolean[n];
        boolean[] descending = new boolean[2 * n - 1];
        boolean[] ascending = new boolean[2 * n - 1];

        for (int r = 0; r < n; r++) {
            int c = board.columnAtRow(r);

            if (c < 0 || c >= n) {
                return false;
            }

            int d1 = r - c + n - 1;
            int d2 = r + c;

            if (columns[c] || descending[d1] || ascending[d2]) {
                return false;
            }

            columns[c] = true;
            descending[d1] = true;
            ascending[d2] = true;
        }

        return true;
    }
}
