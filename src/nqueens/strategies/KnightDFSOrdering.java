
package nqueens.strategies;

import java.util.ArrayList;
import java.util.List;

public final class KnightDFSOrdering implements SearchStrategy {
    private static final int[] DR = {-2, -2, -1, -1, 1, 1, 2, 2};
    private static final int[] DC = {-1, 1, -2, 2, -2, 2, -1, 1};

    private final int n;
    private final int[][] orders;
    private final long preprocessingSquares;

    public KnightDFSOrdering(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("N must be at least 1");
        }

        this.n = n;

        boolean[][] visited = new boolean[n][n];

        @SuppressWarnings("unchecked")
        List<Integer>[] rowColumns = new ArrayList[n];

        for (int r = 0; r < n; r++) {
            rowColumns[r] = new ArrayList<>();
        }

        long[] visits = {0L};

        // Begin at the bottom-right square.
        dfs(n - 1, n - 1, visited, rowColumns, visits);

        // Restart at unvisited squares in row-major order.
        for (int square = 0; square < n * n; square++) {
            int r = square / n;
            int c = square % n;

            if (!visited[r][c]) {
                dfs(r, c, visited, rowColumns, visits);
            }
        }

        orders = new int[n][n];

        for (int r = 0; r < n; r++) {
            if (rowColumns[r].size() != n) {
                throw new IllegalStateException(
                    "DFS did not visit every square in row " + r
                );
            }

            for (int i = 0; i < n; i++) {
                orders[r][i] = rowColumns[r].get(i);
            }
        }

        preprocessingSquares = visits[0];
    }

    private void dfs(
            int r,
            int c,
            boolean[][] visited,
            List<Integer>[] rowColumns,
            long[] visits) {

        visited[r][c] = true;
        rowColumns[r].add(c);
        visits[0]++;

        for (int i = 0; i < 8; i++) {
            int nr = r + DR[i];
            int nc = c + DC[i];

            if (nr >= 0 && nr < n && nc >= 0 && nc < n
                    && !visited[nr][nc]) {
                dfs(nr, nc, visited, rowColumns, visits);
            }
        }
    }

    @Override
    public int[] orderForRow(int requestedN, int row) {
        if (requestedN != n) {
            throw new IllegalArgumentException(
                "Strategy was created for N=" + n
            );
        }

        return orders[row].clone();
    }

    @Override
    public String name() {
        return "KnightDFS";
    }

    @Override
    public long preprocessingSquares() {
        return preprocessingSquares;
    }
}
