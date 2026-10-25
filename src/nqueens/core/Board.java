
package nqueens.core;

import java.util.Arrays;

public final class Board {
    private final int[] queenColumns;

    public Board(int[] queenColumns) {
        this.queenColumns = queenColumns.clone();
    }

    public int size() {
        return queenColumns.length;
    }

    public int columnAtRow(int row) {
        return queenColumns[row];
    }

    public int[] toArray() {
        return queenColumns.clone();
    }

    public String render() {
        StringBuilder out = new StringBuilder();

        for (int r = 0; r < size(); r++) {
            for (int c = 0; c < size(); c++) {
                out.append(queenColumns[r] == c ? "Q " : ". ");
            }
            out.append(System.lineSeparator());
        }

        return out.toString();
    }

    @Override
    public String toString() {
        return Arrays.toString(queenColumns);
    }
}
