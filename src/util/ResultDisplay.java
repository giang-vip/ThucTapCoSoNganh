package util;// Lớp hiển thị kết quả và ghi vào file
import model.Individual;
import model.KnapsackProblem;
import model.Population;

import java.io.FileWriter;
import java.io.IOException;

public class ResultDisplay {
    // Phương thức hiển thị toàn bộ quần thể sau tiến hóa
    public static void displayPopulation(Population population, KnapsackProblem problem,int generations) {

        System.out.println("\n=== CAC CA THE SAU KHI TIEN HOA ===");


        for (int i = 0; i < population.size(); i++) {
            Individual ind = population.getIndividual(i);
            System.out.print("v" + (i + 1) + " chon: ");
            int tongGiaTri = 0;
            int tongKhoiLuong = 0;

            // Duyệt genes để tính tổng và in vật phẩm được chọn
            for (int j = 0; j < ind.getGenes().length; j++) {
                if (ind.getGenes()[j] == 1) {
                    System.out.print("Vat" + (j + 1) + " ");
                    tongGiaTri += problem.getValues()[j];
                    tongKhoiLuong += problem.getWeights()[j];
                }
            }

            // Kiểm tra vượt trọng lượng
            String trangThai = (tongKhoiLuong > problem.getMaxWeight()) ? " (QUA TAI)" : "";
            System.out.println(" | Tong gia tri = " + tongGiaTri +
                    ", Tong khoi luong = " + tongKhoiLuong + trangThai);
        }
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
        System.out.print("v" + (bestIndex + 1) + " chon: ");

        int tongGiaTri = 0;
        int tongKhoiLuong = 0;

        // Duyệt genes để tính tổng và in vật phẩm
        for (int j = 0; j < bestInd.getGenes().length; j++) {
            if (bestInd.getGenes()[j] == 1) {
                System.out.print("Vat" + (j + 1) + " ");
                tongGiaTri += problem.getValues()[j];
                tongKhoiLuong += problem.getWeights()[j];
            }
        }

        System.out.println("\nTong gia tri = " + tongGiaTri);
        System.out.println("Tong khoi luong = " + tongKhoiLuong);
        System.out.println("Do thich nghi (Fitness) = " + bestFitness);

    }
}