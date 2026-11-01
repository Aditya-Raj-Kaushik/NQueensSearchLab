
# Experimental Findings

## Environment

- Operating system:
- Java version:
- CPU:
- Date:
- Benchmark command:

## Results

| N | Strategy | Nodes | Candidate checks | Search ms | Prep ms | Total ms |
|---:|---|---:|---:|---:|---:|---:|
| | Standard | | | | | |
| | KnightDFS | | | | | |
| | CenterOut | | | | | |

## Questions

1. Which strategy used the fewest candidate checks?
2. Did fewer checks translate into lower total runtime?
3. At which board sizes did a strategy perform worse?
4. How significant was Knight-DFS preprocessing?
5. Were the results consistent across repeated runs?

## Limitations

The current project finds the first solution only. The benchmark
does not establish performance for all-solutions search, and no
claim of algorithmic novelty is made.
