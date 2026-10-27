
package nqueens.core;

import java.util.Arrays;
import nqueens.strategies.SearchStrategy;

public final class NQueensSolver {
    private final int n;
    private final SearchStrategy strategy;

    private final int[] queenColumns;
    private final boolean[] columns;
    private final boolean[] descending;
    private final boolean[] ascending;

    private long nodes;
    private long candidateChecks;
    private long placements;
    private long backtracks;

    public NQueensSolver(int n, SearchStrategy strategy) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be at least 1");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }

        this.n = n;
        this.strategy = strategy;

        queenColumns = new int[n];
        Arrays.fill(queenColumns, -1);

        columns = new boolean[n];
        descending = new boolean[2 * n - 1];
        ascending = new boolean[2 * n - 1];
    }

    public SearchResult solveFirst() {
        nodes = 0;
        candidateChecks = 0;
        placements = 0;
        backtracks = 0;

        boolean found = search(0);

        Board board = found ? new Board(queenColumns) : null;

        return new SearchResult(
            board, nodes, candidateChecks, placements, backtracks
        );
    }

    private boolean search(int row) {
        nodes++;

        if (row == n) {
            return true;
        }

        int[] order = strategy.orderForRow(n, row);

        if (order.length != n) {
            throw new IllegalStateException("Invalid column-order length");
        }

        for (int c : order) {
            if (c < 0 || c >= n) {
                throw new IllegalStateException("Column out of range");
            }

            candidateChecks++;

            int d1 = row - c + n - 1;
            int d2 = row + c;

            if (columns[c] || descending[d1] || ascending[d2]) {
                continue;
            }

            queenColumns[row] = c;
            columns[c] = true;
            descending[d1] = true;
            ascending[d2] = true;
            placements++;

            if (search(row + 1)) {
                return true;
            }

            queenColumns[row] = -1;
            columns[c] = false;
            descending[d1] = false;
            ascending[d2] = false;
            backtracks++;
        }

        return false;
    }
}
