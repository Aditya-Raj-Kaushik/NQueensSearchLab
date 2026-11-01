
package nqueens.strategies;

public final class StandardOrdering implements SearchStrategy {
    @Override
    public int[] orderForRow(int n, int row) {
        int[] order = new int[n];

        for (int c = 0; c < n; c++) {
            order[c] = c;
        }

        return order;
    }

    @Override
    public String name() {
        return "Standard";
    }
}
