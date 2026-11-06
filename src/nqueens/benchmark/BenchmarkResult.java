
package nqueens.benchmark;

public final class BenchmarkResult {
    public final int n;
    public final String algorithm;
    public final long nodes;
    public final long candidateChecks;
    public final long placements;
    public final long backtracks;
    public final long preprocessingSquares;
    public final double searchMedianMs;
    public final double preprocessingMedianMs;
    public final double totalMedianMs;

    public BenchmarkResult(
            int n,
            String algorithm,
            long nodes,
            long candidateChecks,
            long placements,
            long backtracks,
            long preprocessingSquares,
            double searchMedianMs,
            double preprocessingMedianMs,
            double totalMedianMs) {
        this.n = n;
        this.algorithm = algorithm;
        this.nodes = nodes;
        this.candidateChecks = candidateChecks;
        this.placements = placements;
        this.backtracks = backtracks;
        this.preprocessingSquares = preprocessingSquares;
        this.searchMedianMs = searchMedianMs;
        this.preprocessingMedianMs = preprocessingMedianMs;
        this.totalMedianMs = totalMedianMs;
    }
}
