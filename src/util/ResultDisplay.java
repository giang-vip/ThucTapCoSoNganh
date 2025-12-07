package util;

import algorithm.GeneticAlgorithm;
import model.Individual;
import model.KnapsackProblem;
import model.Population;

import java.util.List;

public class ResultDisplay {

    // BẢNG THEO DÕI TIẾN HÓA – SIÊU ĐẸP, SIÊU KHOA HỌC
    public static void displayEvolutionTable(GeneticAlgorithm ga, KnapsackProblem problem) {
        System.out.println("\n" + "█".repeat(140));
        System.out.println("           BẢNG THEO DÕI QUÁ TRÌNH TIẾN HÓA THUẬT TOÁN DI TRUYỀN (0/1 KNAPSACK)");
        System.out.println("█".repeat(140));

        // Tiêu đề bảng – mở rộng để chứa thêm 2 cột mới
        System.out.printf("%-6s %-10s %-50s %-12s %-10s %-15s %-12s %-15s%n",
                "Thế hệ", "Fitness", "Vật được chọn", "Tổng giá trị", "Tổng KL", "KL/Max", "Cải thiện?", "Stagnation");
        System.out.println("─".repeat(140));

        int maxWeight = problem.getMaxWeight();

        for (int i = 0; i < ga.getGenerationLog().size(); i++) {
            int gen = ga.getGenerationLog().get(i);
            double fitness = ga.getBestFitnessLog().get(i);
            Individual best = ga.getBestIndividualLog().get(i);
            boolean improved = ga.getImprovedLog().get(i);
            int noImpCount = ga.getNoImprovementCountLog().get(i);

            // Tính tổng giá trị và tổng khối lượng thực tế của cá thể tốt nhất ở thế hệ này
            int totalValue = 0;
            int totalWeight = 0;
            StringBuilder items = new StringBuilder();

            for (int j = 0; j < best.getGenes().length; j++) {
                if (best.getGenes()[j] == 1) {
                    items.append("V").append(j + 1).append(" ");
                    totalValue += problem.getValues()[j];
                    totalWeight += problem.getWeights()[j];
                }
            }
            if (items.length() == 0) items.append("Rỗng");

            // Cắt ngắn nếu quá dài (tránh làm lệch bảng)
            String itemsStr = items.toString();
            if (itemsStr.length() > 48) {
                itemsStr = itemsStr.substring(0, 45) + "...";
            }

            // Trạng thái trọng lượng
            String weightStatus = totalWeight <= maxWeight ?
                    totalWeight + "/" + maxWeight :
                    "⚠ " + totalWeight + "/" + maxWeight + " (vượt!)";

            // Cải thiện?
            String status = improved ? "CÓ ↑" : "Không";

            // In dòng – căn chỉnh chuẩn như Excel
            System.out.printf("%-6d %-10.0f %-50s %-12d %-10d %-15s %-12s %-15d%n",
                    gen,
                    fitness,
                    itemsStr,
                    totalValue,
                    totalWeight,
                    weightStatus,
                    status,
                    noImpCount);
        }

        // Dòng cuối cùng – tóm tắt kết quả cuối cùng
        Individual finalBest = ga.getBestIndividualLog().get(ga.getBestIndividualLog().size() - 1);
        int finalValue = 0, finalWeight = 0;
        for (int j = 0; j < finalBest.getGenes().length; j++) {
            if (finalBest.getGenes()[j] == 1) {
                finalValue += problem.getValues()[j];
                finalWeight += problem.getWeights()[j];
            }
        }

        System.out.println("█".repeat(140));
        System.out.printf("HOÀN TẤT SAU %d THẾ HỆ  %n",
                ga.getGenerationLog().size());
//                ga.getBestFitnessLog().get(ga.getBestFitnessLog().size() - 1),
//                finalValue, finalWeight, maxWeight);
        System.out.println("█".repeat(140));
    }

    // Bonus: hàm in nhanh cá thể tốt nhất (gọn nhẹ, vẫn giữ lại nếu bạn thích)
    public static void displayBestOnly(Population pop, KnapsackProblem problem) {
        Individual best = pop.getBestIndividual();
        int value = 0, weight = 0;
        System.out.print("\nKẾT QUẢ TỐI ƯU: ");
        for (int i = 0; i < best.getGenes().length; i++) {
            if (best.getGenes()[i] == 1) {
                System.out.print("V" + (i+1) + " ");
                value += problem.getValues()[i];
                weight += problem.getWeights()[i];
            }
        }
        System.out.printf("→ Giá trị = %d, Khối lượng = %d/%d, Fitness = %.0f%n",
                value, weight, problem.getMaxWeight(), best.getFitness());
    }
    // Hiện thị cá thể tốt nhất
    // HIỂN THỊ CÁ THỂ TỐT NHẤT THỰC SỰ TRONG TOÀN BỘ QUÁ TRÌNH (KHÔNG PHẢI THẾ HỆ CUỐI!)
    public static void displayBest(GeneticAlgorithm ga, KnapsackProblem problem) {
        // Tìm index của fitness cao nhất trong lịch sử
        List<Double> fitnessLog = ga.getBestFitnessLog();
        int bestIndex = 0;
        double bestFit = fitnessLog.get(0);

        for (int i = 1; i < fitnessLog.size(); i++) {
            double f = fitnessLog.get(i);
            if (f > bestFit || (f >= 0 && bestFit < 0)) {  // ưu tiên khả thi
                bestFit = f;
                bestIndex = i;
            }
        }

        Individual trueBest = ga.getBestIndividualLog().get(bestIndex);
        int generationFound = ga.getGenerationLog().get(bestIndex);

        System.out.println("\n=== CÁ THỂ TỐT NHẤT (TOÀN BỘ QUÁ TRÌNH TIẾN HÓA) ===");
        System.out.println("→ Xuất hiện lần đầu ở thế hệ: " + generationFound);
        System.out.print("Chọn: ");
        int totalValue = 0, totalWeight = 0;

        for (int j = 0; j < trueBest.getGenes().length; j++) {
            if (trueBest.getGenes()[j] == 1) {
                System.out.print("Vật" + (j + 1) + " ");
                totalValue += problem.getValues()[j];
                totalWeight += problem.getWeights()[j];
            }
        }

        System.out.println("\n→ Tổng giá trị: " + totalValue);
        System.out.println("→ Tổng khối lượng: " + totalWeight + " / " + problem.getMaxWeight());
        System.out.printf("→ Fitness tối ưu: %.0f%n", bestFit);
    }
}