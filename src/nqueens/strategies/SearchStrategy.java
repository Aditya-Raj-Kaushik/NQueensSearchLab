
package nqueens.strategies;

public interface SearchStrategy {
    int[] orderForRow(int n, int row);

    String name();

    default long preprocessingSquares() {
        return 0L;
    }
}
