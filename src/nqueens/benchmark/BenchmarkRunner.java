
package nqueens.benchmark;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import nqueens.core.NQueensSolver;
import nqueens.core.SearchResult;
import nqueens.core.SolutionValidator;
import nqueens.strategies.CenterOutOrdering;
import nqueens.strategies.KnightDFSOrdering;
import nqueens.strategies.SearchStrategy;
import nqueens.strategies.StandardOrdering;

public final class BenchmarkRunner {
    private static volatile long sink;

    private final int warmupBatches;
    private final int trials;
    private final int repetitions;

    public BenchmarkRunner(int warmupBatches, int trials, int repetitions) {
        this.warmupBatches = warmupBatches;
        this.trials = trials;
        this.repetitions = repetitions;
    }

    public List<BenchmarkResult> runForSize(int n) {
        List<SearchStrategy> strategies = Arrays.asList(
            new StandardOrdering(),
            new KnightDFSOrdering(n),
            new CenterOutOrdering()
        );

        List<BenchmarkResult> results = new ArrayList<>();

        for (SearchStrategy strategy : strategies) {
            SearchResult check =
                new NQueensSolver(n, strategy).solveFirst();

            if (!check.found()
                    || !SolutionValidator.isValid(check.board())) {
                throw new IllegalStateException(
                    "Invalid solution for N=" + n
                    + ", strategy=" + strategy.name()
                );
            }

            for (int i = 0; i < warmupBatches; i++) {
                runSearchBatch(n, strategy, repetitions);
            }

            long[] searchSamples = new long[trials];
            long[] totalSamples = new long[trials];
            long[] prepSamples = new long[trials];

            for (int t = 0; t < trials; t++) {
                long start = System.nanoTime();

                for (int i = 0; i < repetitions; i++) {
                    SearchResult result =
                        new NQueensSolver(n, strategy).solveFirst();

                    sink += result.nodes() + result.candidateChecks();
                }

                searchSamples[t] = System.nanoTime() - start;

                start = System.nanoTime();

                for (int i = 0; i < repetitions; i++) {
                    SearchStrategy fresh =
                        makeStrategy(strategy.name(), n);

                    SearchResult result =
                        new NQueensSolver(n, fresh).solveFirst();

                    sink += result.nodes() + result.candidateChecks();
                }

                totalSamples[t] = System.nanoTime() - start;

                start = System.nanoTime();

                for (int i = 0; i < repetitions; i++) {
                    SearchStrategy fresh =
                        makeStrategy(strategy.name(), n);

                    sink += fresh.preprocessingSquares();
                }

                prepSamples[t] = System.nanoTime() - start;
            }

            double searchMs = medianMs(searchSamples, repetitions);
            double totalMs = medianMs(totalSamples, repetitions);

            double prepMs = strategy.name().equals("KnightDFS")
                ? medianMs(prepSamples, repetitions)
                : 0.0;

            results.add(new BenchmarkResult(
                n,
                strategy.name(),
                check.nodes(),
                check.candidateChecks(),
                check.placements(),
                check.backtracks(),
                strategy.preprocessingSquares(),
                searchMs,
                prepMs,
                totalMs
            ));
        }

        return results;
    }

    private static SearchStrategy makeStrategy(String name, int n) {
        switch (name) {
            case "Standard":
                return new StandardOrdering();
            case "KnightDFS":
                return new KnightDFSOrdering(n);
            case "CenterOut":
                return new CenterOutOrdering();
            default:
                throw new IllegalArgumentException(
                    "Unknown strategy: " + name
                );
        }
    }

    private static void runSearchBatch(
            int n, SearchStrategy strategy, int reps) {
        for (int i = 0; i < reps; i++) {
            SearchResult result =
                new NQueensSolver(n, strategy).solveFirst();

            sink += result.nodes() + result.candidateChecks();
        }
    }

    private static double medianMs(long[] samples, int repetitions) {
        Arrays.sort(samples);
        return samples[samples.length / 2]
            / 1_000_000.0 / repetitions;
    }

    public static void main(String[] args) throws Exception {
        int minN = args.length > 0
            ? Integer.parseInt(args[0]) : 4;

        int maxN = args.length > 1
            ? Integer.parseInt(args[1]) : 20;

        int reps = args.length > 2
            ? Integer.parseInt(args[2]) : 100;

        if (minN < 1 || maxN < minN || reps < 1) {
            System.err.println(
                "Usage: java nqueens.benchmark.BenchmarkRunner "
                + "[minN] [maxN] [repetitions]"
            );
            System.exit(2);
        }

        List<BenchmarkResult> all = new ArrayList<>();

        System.out.println(
            "N,Algorithm,Nodes,CandidateChecks,Placements,"
            + "Backtracks,DFS squares,Search ms,Prep ms,Total ms"
        );

        for (int n = minN; n <= maxN; n++) {
            int scaledReps = n >= 19 ? Math.max(1, reps / 20)
                : n >= 16 ? Math.max(1, reps / 5)
                : reps;

            BenchmarkRunner runner =
                new BenchmarkRunner(2, 5, scaledReps);

            for (BenchmarkResult r : runner.runForSize(n)) {
                all.add(r);

                System.out.printf(
                    Locale.ROOT,
                    "%d,%s,%d,%d,%d,%d,%d,%.6f,%.6f,%.6f%n",
                    r.n,
                    r.algorithm,
                    r.nodes,
                    r.candidateChecks,
                    r.placements,
                    r.backtracks,
                    r.preprocessingSquares,
                    r.searchMedianMs,
                    r.preprocessingMedianMs,
                    r.totalMedianMs
                );
            }
        }

        CsvExporter.write(
            Paths.get("results", "benchmark.csv"), all
        );

        System.out.println(
            "\nCSV written to results/benchmark.csv"
        );
        System.out.println("Benchmark sink: " + sink);
    }
}
