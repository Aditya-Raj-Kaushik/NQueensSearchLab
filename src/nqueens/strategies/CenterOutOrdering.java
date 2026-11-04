
package nqueens.strategies;

public final class CenterOutOrdering implements SearchStrategy {
    @Override
    public int[] orderForRow(int n, int row) {
        int[] order = new int[n];
        int index = 0;
        int center = n / 2;

        order[index++] = center;

        for (int distance = 1; index < n; distance++) {
            int left = center - distance;
            int right = center + distance;

            if (left >= 0) {
                order[index++] = left;
            }

            if (right < n && index < n) {
                order[index++] = right;
            }
        }

        return order;
    }

    @Override
    public String name() {
        return "CenterOut";
    }
}
