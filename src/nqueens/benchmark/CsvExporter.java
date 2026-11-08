
package nqueens.benchmark;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public final class CsvExporter {
    private CsvExporter() {}

    public static void write(
            Path path, List<BenchmarkResult> results) throws IOException {

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        StringBuilder csv = new StringBuilder();

        csv.append(
            "n,algorithm,nodes,candidate_checks,placements,backtracks,"
            + "preprocessing_squares,search_median_ms,"
            + "preprocessing_median_ms,total_median_ms\n"
        );

        for (BenchmarkResult r : results) {
            csv.append(r.n).append(',')
               .append(r.algorithm).append(',')
               .append(r.nodes).append(',')
               .append(r.candidateChecks).append(',')
               .append(r.placements).append(',')
               .append(r.backtracks).append(',')
               .append(r.preprocessingSquares).append(',')
               .append(String.format(Locale.ROOT, "%.6f", r.searchMedianMs))
               .append(',')
               .append(String.format(
                   Locale.ROOT, "%.6f", r.preprocessingMedianMs))
               .append(',')
               .append(String.format(Locale.ROOT, "%.6f", r.totalMedianMs))
               .append('\n');
        }

        Files.write(
            path, csv.toString().getBytes(StandardCharsets.UTF_8)
        );
    }
}
