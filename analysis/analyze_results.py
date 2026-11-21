import argparse
import csv
from pathlib import Path

import matplotlib.pyplot as plt


def load_results(csv_path):
    rows = []

    with csv_path.open(
        "r",
        newline="",
        encoding="utf-8"
    ) as file:

        reader = csv.DictReader(file)

        required = {
            "n",
            "strategy",
            "candidate_checks",
            "nodes",
            "search_median_ms",
            "total_median_ms"
        }

        if not required.issubset(reader.fieldnames or []):
            raise ValueError(
                "Benchmark CSV is missing required columns."
            )

        for row in reader:
            rows.append({
                "n": int(row["n"]),
                "strategy": row["strategy"],
                "candidate_checks": int(row["candidate_checks"]),
                "nodes": int(row["nodes"]),
                "search_ms": float(row["search_median_ms"]),
                "total_ms": float(row["total_median_ms"])
            })

    if not rows:
        raise ValueError("The benchmark CSV contains no data.")

    return rows


def plot_metric(rows, metric, title, ylabel, output_dir):
    strategies = sorted({
        row["strategy"] for row in rows
    })

    plt.figure(figsize=(10, 6))

    for strategy in strategies:

        selected = sorted(
            [
                row for row in rows
                if row["strategy"] == strategy
            ],
            key=lambda row: row["n"]
        )

        x = [row["n"] for row in selected]
        y = [row[metric] for row in selected]

        plt.plot(
            x,
            y,
            marker="o",
            label=strategy
        )

    plt.title(title)
    plt.xlabel("Board size (N)")
    plt.ylabel(ylabel)
    plt.legend()
    plt.grid(True, alpha=0.3)
    plt.tight_layout()

    output_path = output_dir / f"{metric}.png"

    plt.savefig(output_path, dpi=300)
    plt.close()

    print(f"Generated: {output_path}")


def main():

    parser = argparse.ArgumentParser(
        description="Analyze N-Queens benchmark results."
    )

    parser.add_argument(
        "--input",
        default="results/benchmark.csv"
    )

    parser.add_argument(
        "--output",
        default="results/figures"
    )

    args = parser.parse_args()

    csv_path = Path(args.input)
    output_dir = Path(args.output)

    if not csv_path.exists():
        raise FileNotFoundError(
            f"Benchmark file not found: {csv_path}"
        )

    output_dir.mkdir(
        parents=True,
        exist_ok=True
    )

    rows = load_results(csv_path)

    plots = [
        (
            "candidate_checks",
            "Candidate Checks vs Board Size",
            "Candidate checks"
        ),
        (
            "nodes",
            "Recursive Nodes vs Board Size",
            "Recursive nodes"
        ),
        (
            "search_ms",
            "Search Time vs Board Size",
            "Median search time (ms)"
        ),
        (
            "total_ms",
            "Total Execution Time vs Board Size",
            "Median total time (ms)"
        )
    ]

    for metric, title, ylabel in plots:

        plot_metric(
            rows,
            metric,
            title,
            ylabel,
            output_dir
        )

    print("\nAnalysis completed.")


if __name__ == "__main__":
    main()