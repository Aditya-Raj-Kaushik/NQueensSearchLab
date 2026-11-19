
package nqueens.benchmark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nqueens.core.NQueensSolver;
import nqueens.core.SearchResult;
import nqueens.core.SolutionValidator;
import nqueens.strategies.CenterOutOrdering;
import nqueens.strategies.KnightDFSOrdering;
import nqueens.strategies.SearchStrategy;
import nqueens.strategies.StandardOrdering;

public final class BenchmarkRunner {

    private final int warmups;
    private final int repetitions;

    public BenchmarkRunner(int warmups, int repetitions, int maxN) {
        if (warmups < 0 || repetitions < 1 || maxN < 1) {
            throw new IllegalArgumentException("Invalid benchmark settings");
        }

        this.warmups = warmups;
        this.repetitions = repetitions;
    }

    public List<BenchmarkResult> run(int minN, int maxN) {

        if (minN < 1 || maxN < minN) {
            throw new IllegalArgumentException("Invalid N range");
        }

        List<BenchmarkResult> results = new ArrayList<>();

        for (int n = minN; n <= maxN; n++) {
            results.add(runOne(n, "Standard"));
            results.add(runOne(n, "KnightDFS"));
            results.add(runOne(n, "CenterOut"));
        }

        return results;
    }

    private SearchStrategy createStrategy(String name, int n) {
        switch (name) {
            case "Standard":
                return new StandardOrdering();

            case "KnightDFS":
                return new KnightDFSOrdering(n);

            case "CenterOut":
                return new CenterOutOrdering();

            default:
                throw new IllegalArgumentException("Unknown strategy: " + name);
        }
    }

    private BenchmarkResult runOne(int n, String name) {

        // Warm-up runs are excluded from measurements.
        for (int i = 0; i < warmups; i++) {
            SearchStrategy strategy = createStrategy(name, n);
            new NQueensSolver(n, strategy).solveFirst();
        }

        List<Double> preprocessingTimes = new ArrayList<>();
        List<Double> searchTimes = new ArrayList<>();

        SearchResult referenceResult = null;
        long preprocessingSquares = 0;

        for (int i = 0; i < repetitions; i++) {

            // Measure strategy construction and preprocessing.
            long preprocessingStart = System.nanoTime();

            SearchStrategy strategy = createStrategy(name, n);

            long preprocessingEnd = System.nanoTime();

            double preprocessingMs =
                (preprocessingEnd - preprocessingStart) / 1_000_000.0;

            preprocessingTimes.add(preprocessingMs);

            preprocessingSquares = strategy.preprocessingSquares();

            // Measure solver execution separately.
            long searchStart = System.nanoTime();

            SearchResult result =
                new NQueensSolver(n, strategy).solveFirst();

            long searchEnd = System.nanoTime();

            double searchMs =
                (searchEnd - searchStart) / 1_000_000.0;

            searchTimes.add(searchMs);

            if (result.found()
                    && !SolutionValidator.isValid(result.board())) {
                throw new IllegalStateException(
                    "Invalid solution for N=" + n + ", strategy=" + name
                );
            }

            if (referenceResult == null) {
                referenceResult = result;
            } else if (referenceResult.found() != result.found()
                    || referenceResult.nodes() != result.nodes()
                    || referenceResult.candidateChecks()
                        != result.candidateChecks()
                    || referenceResult.placements() != result.placements()
                    || referenceResult.backtracks() != result.backtracks()) {
                throw new IllegalStateException(
                    "Inconsistent search counters for N=" + n
                        + ", strategy=" + name
                );
            }
        }

        Collections.sort(preprocessingTimes);
        Collections.sort(searchTimes);

        double preprocessingMedian = median(preprocessingTimes);
        double searchMedian = median(searchTimes);
        double totalMedian = preprocessingMedian + searchMedian;

        return new BenchmarkResult(
            n,
            name,
            referenceResult.found(),
            referenceResult.nodes(),
            referenceResult.candidateChecks(),
            referenceResult.placements(),
            referenceResult.backtracks(),
            preprocessingSquares,
            preprocessingMedian,
            searchMedian,
            totalMedian
        );
    }

    private double median(List<Double> values) {
        int size = values.size();

        if (size % 2 == 1) {
            return values.get(size / 2);
        }

        return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
    }
}
