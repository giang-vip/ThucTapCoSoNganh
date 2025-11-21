package util;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class CHỈ chịu trách nhiệm GHI FILE LOG
 * Ghi đầy đủ: thời gian + đầu vào + kết quả → mở ra là biết hết!
 */
public class Logger {

    private static final String FILE_NAME = "KetQua_KnapsackGA_Full.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

    /**
     * Ghi toàn bộ thông tin: đầu vào + kết quả + thời gian
     */
    public static void logFullResult(KnapsackProblem problem, Population population, int generations) {
        Individual best = population.getBestIndividual();
        String time = dtf.format(LocalDateTime.now());

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("═".repeat(80)).append("\n");
        sb.append("        KẾT QUẢ THUẬT TOÁN DI TRUYỀN - BÀI TOÁN CÁI TÚI 0-1\n");
        sb.append("                  Thời gian chạy: ").append(time).append("\n");
        sb.append("═".repeat(80)).append("\n\n");

        // === THÔNG TIN ĐẦU VÀO ===
        sb.append("THÔNG TIN BÀI TOÁN ĐẦU VÀO:\n");
        sb.append("   • Số lượng cá thể trong quần thể : ").append(population.size()).append("\n");
        sb.append("   • Số lượng vật phẩm (n)          : ").append(problem.getNumItems()).append("\n");
        sb.append("   • Sức chứa tối đa của túi        : ").append(problem.getMaxWeight()).append("\n");
        sb.append("   • Tổng số thế hệ đã chạy         : ").append(generations).append("\n\n");

        sb.append("   DANH SÁCH VẬT PHẨM:\n");
        sb.append("   ┌─────┬──────────┬──────────────┐\n");
        sb.append("   │ STT │ Giá trị  │ Trọng lượng  │\n");
        sb.append("   ├─────┼──────────┼──────────────┤\n");
        for (int i = 0; i < problem.getNumItems(); i++) {
            sb.append(String.format("   │ V%-3d│ %-8d │ %-12d │\n", i+1, problem.getValues()[i], problem.getWeights()[i]));
        }
        sb.append("   └─────┴──────────┴──────────────┘\n\n");

        // === KẾT QUẢ TỐI ƯU ===
        sb.append("KẾT QUẢ TỐI ƯU TÌM ĐƯỢC:\n");
        sb.append("   • Các vật phẩm được chọn         : ");
        int usedWeight = 0;
        for (int i = 0; i < best.getGenes().length; i++) {
            if (best.getGenes()[i] == 1) {
                sb.append("Vật").append(i+1).append(" ");
                usedWeight += problem.getWeights()[i];
            }
        }
        sb.append("\n");
        sb.append("   • Tổng giá trị tối đa đạt được   : ").append(best.getFitness()).append("\n");
        sb.append("   • Tổng khối lượng đã sử dụng     : ").append(usedWeight)
                .append(" / ").append(problem.getMaxWeight()).append("\n");
        sb.append("   • Độ thích nghi (Fitness)        : ").append(best.getFitness()).append("\n");

        sb.append("\n").append("═".repeat(80)).append("\n");
        sb.append("                CẢM ƠN BẠN ĐÃ SỬ DỤNG CHƯƠNG TRÌNH!\n");
        sb.append("═".repeat(80)).append("\n");

        writeToFile(sb.toString());
    }

    private static void writeToFile(String content) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(content);
            System.out.println("\nĐã ghi đầy đủ kết quả + bài toán vào file: " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}