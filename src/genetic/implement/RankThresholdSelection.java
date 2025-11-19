package genetic.implement;// Lớp thực hiện chọn lọc dựa trên rank và ngưỡng 20% (thay thế 20% yếu nhất)
import genetic.ISelectionOperator;
import model.Individual;
import model.Population;

import java.util.Arrays;
import java.util.Random;


public class RankThresholdSelection implements ISelectionOperator {
    // Ngưỡng loại bỏ (20%)
    private double threshold ;

    public RankThresholdSelection(double threshold) {
        this.threshold = threshold;
    }


    // Phương thức thực hiện chọn lọc
    public void performSelection(Population population) {
        int n = population.size(); // Kích thước quần thể
        int m = population.getProblem().getValues().length; // Số vật phẩm
        Individual[] newIndividuals = new Individual[n]; // Mảng cá thể mới

        Random random = new Random();

        // Bước 1: Xếp hạng cá thể theo fitness giảm dần
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        Arrays.sort(indices, (a, b) -> Integer.compare(population.getIndividual(b).getFitness(), population.getIndividual(a).getFitness()));

        // Tạo mảng rank: rank[i] = thứ hạng của cá thể i (1 là tốt nhất)
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[indices[i]] = i + 1;
        }

        // Bước 2: Tính ngưỡng loại bỏ (20% yếu nhất)
        int nguong = (int) (n * threshold);

        // Bước 3: Thực hiện chọn lọc
        for (int i = 0; i < n; i++) {
            if (rank[i] > n - nguong) {
                // Nếu yếu, thay bằng cá thể tốt ngẫu nhiên
                int goodIndex = indices[random.nextInt(n - nguong)];
                newIndividuals[i] = population.getIndividual(goodIndex).clone();
            } else {
                // Nếu tốt, giữ nguyên
                newIndividuals[i] = population.getIndividual(i).clone();
            }
        }

        // Cập nhật quần thể mới
        for (int i = 0; i < n; i++) {
            population.setIndividual(i, newIndividuals[i]);
        }

        // In kết quả sau chọn lọc
        System.out.println("\n=== SAU KHI CHON LOC (RANK + THRESHOLD 20%) ===");
        for (int i = 0; i < n; i++) {
            System.out.print("v" + (i + 1) + ":\t");
            for (int j = 0; j < m; j++) {
                System.out.print(population.getIndividual(i).getGenes()[j] + "\t");
            }
            System.out.println();
        }
    }
}