
package nqueens.core;

public final class SearchResult {
    private final Board board;
    private final long nodes;
    private final long candidateChecks;
    private final long placements;
    private final long backtracks;

    public SearchResult(
            Board board,
            long nodes,
            long candidateChecks,
            long placements,
            long backtracks) {
        this.board = board;
        this.nodes = nodes;
        this.candidateChecks = candidateChecks;
        this.placements = placements;
        this.backtracks = backtracks;
    }

    public boolean found() {
        return board != null;
    }

    public Board board() {
        return board;
    }

    public long nodes() {
        return nodes;
    }

    public long candidateChecks() {
        return candidateChecks;
    }

    public long placements() {
        return placements;
    }

    public long backtracks() {
        return backtracks;
    }
}
