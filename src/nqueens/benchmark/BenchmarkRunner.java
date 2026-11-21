package nqueens.benchmark;

import java.util.*;

import nqueens.core.*;
import nqueens.strategies.*;

public final class BenchmarkRunner {

    private final int warmups;
    private final int repetitions;
    private final int maxN;

    public BenchmarkRunner(int warmups, int repetitions, int maxN) {
        this.warmups = warmups;
        this.repetitions = repetitions;
        this.maxN = maxN;
    }

    public List<BenchmarkResult> run(int minN, int maxN) {

        List<BenchmarkResult> results = new ArrayList<>();

        for (int n = minN; n <= maxN; n++) {
            results.add(runOne(n, "Standard", new StandardOrdering()));
            results.add(runOne(n, "KnightDFS", new KnightDFSOrdering(n)));
            results.add(runOne(n, "CenterOut", new CenterOutOrdering()));
        }

        return results;
    }

    private BenchmarkResult runOne(
            int n,
            String name,
            SearchStrategy strategy) {

        for (int i = 0; i < warmups; i++) {
            new NQueensSolver(n, strategy).solveFirst();
        }

        List<Double> times = new ArrayList<>();
        SearchResult result = null;

        for (int i = 0; i < repetitions; i++) {

            long start = System.nanoTime();

            result = new NQueensSolver(n, strategy).solveFirst();

            long end = System.nanoTime();

            times.add((end - start) / 1_000_000.0);
        }

        Collections.sort(times);

        double median = times.get(times.size() / 2);

        if (result == null) {
            throw new IllegalStateException("No benchmark result");
        }

        if (result.found() &&
                !SolutionValidator.isValid(result.board())) {
            throw new IllegalStateException("Invalid solution");
        }

        return new BenchmarkResult(
                n,
                name,
                result.found(),
                result.nodes(),
                result.candidateChecks(),
                result.placements(),
                result.backtracks(),
                strategy.preprocessingSquares(),
                0.0,
                median,
                median
        );
    }
}