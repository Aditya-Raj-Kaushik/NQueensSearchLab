
package nqueens;

import java.util.Scanner;
import nqueens.core.NQueensSolver;
import nqueens.core.SearchResult;
import nqueens.core.SolutionValidator;
import nqueens.strategies.CenterOutOrdering;
import nqueens.strategies.KnightDFSOrdering;
import nqueens.strategies.SearchStrategy;
import nqueens.strategies.StandardOrdering;

public final class Main {
    public static void main(String[] args) {
        int n;

        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter board size N: ");
            n = scanner.nextInt();
        }

        if (n < 1) {
            System.out.println("N must be at least 1.");
            return;
        }

        SearchStrategy[] strategies = {
            new StandardOrdering(),
            new KnightDFSOrdering(n),
            new CenterOutOrdering()
        };

        System.out.println("\nN-Queens Search Lab");
        System.out.println("Board size: N = " + n);

        for (SearchStrategy strategy : strategies) {
            SearchResult result =
                new NQueensSolver(n, strategy).solveFirst();

            System.out.println("\n--- " + strategy.name() + " ---");

            if (!result.found()) {
                System.out.println("No solution exists.");
                continue;
            }

            if (!SolutionValidator.isValid(result.board())) {
                throw new IllegalStateException("Solution validation failed");
            }

            System.out.print(result.board().render());
            System.out.println("Valid solution: yes");
            System.out.println("Recursive nodes: " + result.nodes());
            System.out.println("Candidate checks: " + result.candidateChecks());
            System.out.println("Placements: " + result.placements());
            System.out.println("Backtracks: " + result.backtracks());
            System.out.println(
                "Preprocessing squares: " + strategy.preprocessingSquares()
            );
        }
    }
}
