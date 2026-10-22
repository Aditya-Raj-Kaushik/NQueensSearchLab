
# N-Queens Search Lab

A Java project comparing three candidate-ordering strategies for
finding a first solution to the N-Queens problem.

## Strategies

| Strategy | Description |
|---|---|
| Standard | Try columns from left to right |
| KnightDFS | Derive column ordering from knight-move DFS |
| CenterOut | Try the center column, then move outward |

All strategies share the same backtracking engine and queen-safety
checks. This makes it possible to study the effect of candidate
ordering without changing the underlying search rules.

## Requirements

- Java JDK 17 or later recommended
- VS Code (optional)
- No external libraries

Check your Java installation:

```powershell
java -version
javac -version
```

## Compile

Run from the project root:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java |
    ForEach-Object { $_.FullName })
```

## Run a solution demonstration

```powershell
java -cp out nqueens.Main 8
```

Change 8 to another board size to test different instances.

## Run the benchmark

```powershell
java -cp out nqueens.benchmark.BenchmarkRunner 4 20 100
```

Arguments are minimum N, maximum N, and repetitions.

The results are also written to:

`results/benchmark.csv`

## Run correctness tests

```powershell
javac -cp out -d out (Get-ChildItem -Recurse tests -Filter *.java |
    ForEach-Object { $_.FullName })

java -cp out nqueens.tests.ValidationTests
```

Expected output:

```text
All validation smoke tests passed for N=1..20.
```

## Metrics

- Recursive nodes
- Candidate checks
- Queen placements
- Backtracks
- Preprocessing squares
- Median search, preprocessing, and total time

## Limitations

The current solver searches for the first solution only.
Candidate checks are not unique squares. Lower candidate counts
do not guarantee lower execution time.

The project does not claim that Knight-DFS or center-out ordering
is novel or universally faster. Results should be reproduced on
the target machine before drawing conclusions.

See `docs/` for algorithm explanations, methodology, and a findings
template.
