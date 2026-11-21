package nqueens.benchmark;

public final class BenchmarkResult {

    public final int n;
    public final String strategy;
    public final boolean solved;

    public final long nodes;
    public final long candidateChecks;
    public final long placements;
    public final long backtracks;
    public final long preprocessingSquares;

    public final double preprocessingMs;
    public final double searchMs;
    public final double totalMs;
    public Object algorithm;
    public Object searchMedianMs;
    public Object preprocessingMedianMs;
    public Object totalMedianMs;

    public BenchmarkResult(
            int n,
            String strategy,
            boolean solved,
            long nodes,
            long candidateChecks,
            long placements,
            long backtracks,
            long preprocessingSquares,
            double preprocessingMs,
            double searchMs,
            double totalMs) {

        this.n = n;
        this.strategy = strategy;
        this.solved = solved;
        this.nodes = nodes;
        this.candidateChecks = candidateChecks;
        this.placements = placements;
        this.backtracks = backtracks;
        this.preprocessingSquares = preprocessingSquares;
        this.preprocessingMs = preprocessingMs;
        this.searchMs = searchMs;
        this.totalMs = totalMs;
    }
}