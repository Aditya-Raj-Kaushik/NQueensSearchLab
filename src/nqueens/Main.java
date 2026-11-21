package nqueens;

import java.nio.file.Path;
import java.util.List;

import nqueens.core.*;
import nqueens.strategies.*;
import nqueens.benchmark.*;

public final class Main {

    public static void main(String[] args) throws Exception {

        if (args.length == 0 || args[0].equalsIgnoreCase("solve")) {

            int n = args.length > 1
                    ? Integer.parseInt(args[1]) : 8;

            solve(n);

        } else if (args[0].equalsIgnoreCase("benchmark")) {

            int minN = args.length > 1
                    ? Integer.parseInt(args[1]) : 4;

            int maxN = args.length > 2
                    ? Integer.parseInt(args[2]) : 20;

            int warmups = args.length > 3
                    ? Integer.parseInt(args[3]) : 3;

            int repetitions = args.length > 4
                    ? Integer.parseInt(args[4]) : 7;

            BenchmarkRunner runner =
                    new BenchmarkRunner(warmups, repetitions, maxN);

            List<BenchmarkResult> results =
                    runner.run(minN, maxN);

            for (BenchmarkResult r : results) {

                System.out.printf(
                    "%2d %-10s solved=%-5s nodes=%-9d "
                    + "checks=%-10d search=%8.4fms%n",
                    r.n,
                    r.strategy,
                    r.solved,
                    r.nodes,
                    r.candidateChecks,
                    r.searchMs
                );
            }

            CsvExporter.write(
                    Path.of("results/benchmark.csv"),
                    results
            );

            System.out.println(
                    "\nCSV exported to results/benchmark.csv"
            );

        } else {

            System.out.println("Usage:");
            System.out.println("solve [N]");
            System.out.println(
                "benchmark [minN] [maxN] [warmups] [repetitions]"
            );
        }
    }

    private static void solve(int n) {

        SearchStrategy[] strategies = {
            new StandardOrdering(),
            new KnightDFSOrdering(n),
            new CenterOutOrdering()
        };

        for (SearchStrategy strategy : strategies) {

            SearchResult result =
                    new NQueensSolver(n, strategy).solveFirst();

            System.out.println(
                    "\n========== " + strategy.name()
                    + " | N = " + n + " =========="
            );

            System.out.println("Solution found: " + result.found());

            if (result.found()) {

                System.out.println(result.board().render());

                System.out.println(
                    "Valid: "
                    + SolutionValidator.isValid(result.board())
                );
            }

            System.out.println("Nodes: " + result.nodes());
            System.out.println(
                    "Candidate checks: " + result.candidateChecks()
            );
            System.out.println("Placements: " + result.placements());
            System.out.println("Backtracks: " + result.backtracks());
        }
    }
}