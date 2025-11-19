package util;// Lớp hiển thị kết quả và ghi vào file
import model.Individual;
import model.KnapsackProblem;
import model.Population;

import java.io.FileWriter;
import java.io.IOException;

public class ResultDisplay {
    // Phương thức hiển thị toàn bộ quần thể sau tiến hóa
    public static void displayPopulation(Population population, KnapsackProblem problem) {
        System.out.println("\n=== CAC CA THE SAU KHI TIEN HOA ===");
        StringBuilder data = new StringBuilder("=== CAC CA THE SAU KHI TIEN HOA ===\n");

        for (int i = 0; i < population.size(); i++) {
            Individual ind = population.getIndividual(i);
            data.append("v").append(i + 1).append(" chon: ");
            System.out.print("v" + (i + 1) + " chon: ");
            int tongGiaTri = 0;
            int tongKhoiLuong = 0;

            // Duyệt genes để tính tổng và in vật phẩm được chọn
            for (int j = 0; j < ind.getGenes().length; j++) {
                if (ind.getGenes()[j] == 1) {
                    data.append("Vat").append(j + 1).append(" ");
                    System.out.print("Vat" + (j + 1) + " ");
                    tongGiaTri += problem.getValues()[j];
                    tongKhoiLuong += problem.getWeights()[j];
                }
            }

            // Kiểm tra vượt trọng lượng
            String trangThai = (tongKhoiLuong > problem.getMaxWeight()) ? " (QUA TAI)" : "";
            data.append(" | Tong gia tri = ").append(tongGiaTri)
                    .append(", Tong khoi luong = ").append(tongKhoiLuong)
                    .append(trangThai).append("\n");

            System.out.println(" | Tong gia tri = " + tongGiaTri +
                    ", Tong khoi luong = " + tongKhoiLuong + trangThai);
        }

        // Ghi vào file
        writeToFile("ketqua.txt", data.toString());
    }

    // Phương thức hiển thị cá thể tốt nhất
    public static void displayBest(Population population, KnapsackProblem problem) {
        int bestIndex = 0;
        int bestFitness = Integer.MIN_VALUE;
        // Tìm cá thể có fitness cao nhất
        for (int i = 0; i < population.size(); i++) {
            int f = population.getIndividual(i).getFitness();
            if (f > bestFitness) {
                bestFitness = f;
                bestIndex = i;
            }
        }

        Individual bestInd = population.getIndividual(bestIndex);

        System.out.println("\n=== CA THE TOT NHAT ===");
        StringBuilder data = new StringBuilder("\n=== CA THE TOT NHAT ===\n");

        data.append("v").append(bestIndex + 1).append(" chon: ");
        System.out.print("v" + (bestIndex + 1) + " chon: ");

        int tongGiaTri = 0;
        int tongKhoiLuong = 0;

        // Duyệt genes để tính tổng và in vật phẩm
        for (int j = 0; j < bestInd.getGenes().length; j++) {
            if (bestInd.getGenes()[j] == 1) {
                data.append("Vat").append(j + 1).append(" ");
                System.out.print("Vat" + (j + 1) + " ");
                tongGiaTri += problem.getValues()[j];
                tongKhoiLuong += problem.getWeights()[j];
            }
        }

        data.append("\nTong gia tri = ").append(tongGiaTri)
                .append("\nTong khoi luong = ").append(tongKhoiLuong)
                .append("\nDo thich nghi (Fitness) = ").append(bestFitness)
                .append("\n");

        System.out.println("\nTong gia tri = " + tongGiaTri);
        System.out.println("Tong khoi luong = " + tongKhoiLuong);
        System.out.println("Do thich nghi (Fitness) = " + bestFitness);

        // Ghi vào file
        writeToFile("ketqua.txt", data.toString());
    }

    // Phương thức ghi dữ liệu vào file (append mode)
    private static void writeToFile(String fileName, String content) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(content + "\n");
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
        }
    }
}