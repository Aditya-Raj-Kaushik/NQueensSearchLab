
# Algorithms and Design

## 1. Problem Definition

The N-Queens problem asks us to place N queens on an N x N
chessboard so that no two queens share a row, column, or diagonal.

This project searches for the first valid solution rather than
enumerating every possible solution.

## 2. Backtracking

The solver places one queen per row. For every candidate column,
it checks whether another queen already occupies the same column
or either diagonal.

For row r and column c, diagonal indices are:

- Descending diagonal: r - c + N - 1
- Ascending diagonal: r + c

Boolean arrays allow these conflict checks to be performed in O(1)
time per candidate.

When a candidate is safe, the solver places the queen and
recursively searches the next row. If that branch fails, it removes
the queen and tries another candidate.

## 3. Strategy A: Standard Ordering

Columns are checked from left to right:

0, 1, 2, ..., N - 1

This is the baseline implementation.

## 4. Strategy B: Knight-DFS Ordering

Every chessboard square is treated as a graph vertex. An edge
connects two squares when a knight can legally move between them.

A deterministic depth-first traversal begins at the bottom-right
square. Any squares that remain unvisited are processed in
row-major order.

For each row, the columns are then ordered by when their squares
were first visited during the traversal.

The traversal changes only the order in which candidate columns
are tried. It does not change queen-safety rules.

Preprocessing visits N^2 squares. Its cost must be considered when
comparing end-to-end performance.

## 5. Strategy C: Center-Out Ordering

The solver tries the center column first, then alternates outward.

For even N, the implementation starts at N / 2, then tries the
left neighbor before the right neighbor at each distance.

This is a simple ordering heuristic that requires no knight-board
traversal.

## 6. Metrics

- Recursive nodes: calls to the search method, including the
  successful terminal call.
- Candidate checks: columns considered across all search states.
- Placements: safe queen placements attempted.
- Backtracks: placements undone after a failed branch.
- Preprocessing squares: squares visited by Knight-DFS.
- Search time: time measured for the search-only benchmark.
- Total time: end-to-end time including fresh strategy setup.

Candidate checks are not unique squares: the same column can be
checked in different recursive states.

## 7. Limitations

The current solver finds only the first solution. Its results
cannot automatically be generalized to counting or enumerating
all solutions.

Timing depends on the JVM, hardware, warm-up, and background load.
Neither Knight-DFS nor center-out is claimed to be a novel
algorithm. The project is an experimental comparison of ordering
heuristics.
