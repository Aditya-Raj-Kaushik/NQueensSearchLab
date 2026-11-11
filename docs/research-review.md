
## 4. Add the research documentation

### `docs/research-review.md`

This is particularly important because Navendu suggested reviewing published research. Use this file to document what you read and how it relates to your implementation.

```markdown
# Literature Review: N-Queens Search Strategies

## 1. Objective

Study existing approaches to the N-Queens problem and understand
how search ordering and constraint-solving techniques affect
search efficiency.

## 2. Research areas

Investigate the following areas:

1. Traditional backtracking
2. Constraint Satisfaction Problems (CSP)
3. Variable ordering heuristics
4. Value ordering heuristics
5. Minimum Remaining Values (MRV)
6. Least Constraining Value (LCV)
7. Symmetry breaking
8. Heuristic-based search

## 3. Paper Review Template

### Paper 1

- Title:
- Authors:
- Publication year:
- Journal / Conference:
- Link / DOI:

#### Problem addressed

Describe the problem investigated by the authors.

#### Proposed approach

Explain the algorithm or heuristic.

#### Experimental methodology

Describe the datasets, board sizes, metrics, and baselines.

#### Findings

Summarize the important results.

#### Limitations

Identify limitations discussed by the authors.

#### Relevance to NQueensSearchLab

Explain whether the proposed approach can be compared
with or incorporated into this project.

---

## 4. Comparison of Existing Approaches

| Approach | Search characteristic | Relevance |
|---|---|---|
| Standard Backtracking | Fixed variable and value ordering | Baseline |
| Center-Out Ordering | Changes candidate value ordering | Existing comparison |
| Knight-DFS Ordering | Uses knight traversal to derive candidate order | Experimental comparison |
| MRV | Selects the most constrained variable | Potential extension |
| LCV | Prioritizes less restrictive values | Potential extension |
| Symmetry Breaking | Eliminates equivalent search states | Potential extension |

## 5. Research Gap and Further Work

Complete this section only after reviewing the papers.

Discuss:

- What existing approaches already accomplish.
- What has not been adequately evaluated.
- Whether Knight-DFS ordering has a meaningful comparison
  or research question.
- Whether combining ordering heuristics is worth investigating.

## 6. References

Add complete citations for every paper reviewed.