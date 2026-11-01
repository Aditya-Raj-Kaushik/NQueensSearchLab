
# Benchmark Methodology

## Goal

Compare the work required by three candidate-ordering strategies
when finding the first valid N-Queens solution.

## Controlled Variables

All strategies use the same backtracking engine, conflict checks,
and first-solution stopping condition.

Only the order in which candidate columns are considered changes.

## Measurements

The benchmark records:

- Recursive search nodes
- Candidate-column checks
- Queen placements
- Backtracks
- Knight-DFS preprocessing squares
- Median search time
- Median preprocessing time
- Median end-to-end time

## Reproduction

Compile the source files:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java |
    ForEach-Object { $_.FullName })
```

Run the benchmark:

```powershell
java -cp out nqueens.benchmark.BenchmarkRunner 4 20 100
```

Run correctness tests:

```powershell
javac -cp out -d out (Get-ChildItem -Recurse tests -Filter *.java |
    ForEach-Object { $_.FullName })

java -cp out nqueens.tests.ValidationTests
```

## Interpretation

Candidate checks and recursive nodes are deterministic for a
fixed implementation and board size.

Timing measurements are machine-dependent. The benchmark uses
warm-up batches and median samples, but it is a lightweight
benchmark rather than a dedicated Java microbenchmark framework.

Results for first-solution search do not establish performance
for enumerating every solution.
