
package nqueens.tests;

import nqueens.core.NQueensSolver;
import nqueens.core.SearchResult;
import nqueens.core.SolutionValidator;
import nqueens.strategies.CenterOutOrdering;
import nqueens.strategies.KnightDFSOrdering;
import nqueens.strategies.SearchStrategy;
import nqueens.strategies.StandardOrdering;

public final class ValidationTests {
    public static void main(String[] args) {
        for (int n = 1; n <= 20; n++) {
            SearchStrategy[] strategies = {
                new StandardOrdering(),
                new KnightDFSOrdering(n),
                new CenterOutOrdering()
            };

            for (SearchStrategy strategy : strategies) {
                SearchResult result =
                    new NQueensSolver(n, strategy).solveFirst();

                if (n == 2 || n == 3) {
                    if (result.found()) {
                        fail("Unexpected solution for N=" + n);
                    }
                } else {
                    if (!result.found()) {
                        fail("No solution found for N=" + n);
                    }

                    if (!SolutionValidator.isValid(result.board())) {
                        fail(
                            "Invalid solution for N=" + n
                            + ", strategy=" + strategy.name()
                        );
                    }
                }
            }
        }

        System.out.println(
            "All validation smoke tests passed for N=1..20."
        );
    }

    private static void fail(String message) {
        throw new AssertionError(message);
    }
}
