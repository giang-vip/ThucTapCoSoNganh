package model;//package Entity;
// Lớp đại diện cho quần thể, chứa danh sách các cá thể và liên kết với bài toán

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    // Danh sách các cá thể trong quần thể
    private List<Individual> individuals;
    // Dữ liệu bài toán Knapsack
    private KnapsackProblem problem;

    // Constructor khởi tạo quần thể với kích thước, số vật phẩm và bài toán
    public Population(int size, int numItems, KnapsackProblem problem) {
        this.problem = problem;
        individuals = new ArrayList<>(size);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            Individual ind = new Individual(numItems);
            // --- CÁCH SỬA MỚI: KHỞI TẠO THÔNG MINH (SMART INITIALIZATION) ---
            // Tạo một danh sách chỉ mục ngẫu nhiên để duyệt các vật phẩm không theo thứ tự
            List<Integer> indexes = new ArrayList<>();
            for (int k = 0; k < numItems; k++) indexes.add(k);
            java.util.Collections.shuffle(indexes); // Tráo đổi thứ tự lấy đồ

            int currentWeight = 0;

            for (int j : indexes) {
                // Chỉ lấy vật phẩm nếu túi còn đủ chỗ chứa
                if (random.nextDouble() < 0.2 && (currentWeight + problem.getWeights()[j] <= problem.getMaxWeight())) {
                    ind.setGene(j, 1);
                    currentWeight += problem.getWeights()[j];
                } else {
                    ind.setGene(j, 0);
                }
            }
            // ---------------------------------------------------------------
            individuals.add(ind);
        }
        // Tính fitness cho tất cả cá thể ban đầu
        calculateFitnessForAll();
    }

    // Phương thức tính fitness cho toàn bộ quần thể
    public void calculateFitnessForAll() {
        for (Individual ind : individuals) {
            ind.calculateFitness(problem);
        }
    }

    // Phương thức lấy fitness tốt nhất trong quần thể
    public int getBestFitness() {
        int best = Integer.MIN_VALUE;
        for (Individual ind : individuals) {
            if (ind.getFitness() > best) best = ind.getFitness();
        }
        return best;
    }

    // Getter để lấy danh sách cá thể
    public List<Individual> getIndividuals() {
        return individuals;
    }

    // Getter để lấy cá thể tại vị trí index
    public Individual getIndividual(int index) {
        return individuals.get(index);
    }
    // lấy cá thể có fitness cao nhất trong quần thể
    /**
     * LẤY CÁ THỂ TỐT NHẤT – ĐÃ SỬA HOÀN HẢO CHO BÀI KNAPSACK
     * Ưu tiên: Giải KHẢ THI luôn thắng giải không khả thi
     */
    public Individual getBestIndividual() {
        // Nếu population rỗng thì trả về null hoặc cá thể đầu (tùy bạn)
        if (individuals.isEmpty()) return null;

        return individuals.stream()
                .max((a, b) -> {
                    double fa = a.getFitness();
                    double fb = b.getFitness();

                    // Giải khả thi (fitness >= 0) luôn thắng giải bị phạt (fitness < 0)
                    if (fa >= 0 && fb < 0) return 1;
                    if (fa < 0 && fb >= 0) return -1;

                    // Cùng loại (cùng khả thi hoặc cùng không khả thi) → so fitness bình thường
                    return Double.compare(fa, fb);
                })
                .orElse(individuals.get(0)); // phòng trường hợp rỗng (không bao giờ xảy ra)
    }
    // Setter để thay thế cá thể tại vị trí index
    public void setIndividual(int index, Individual ind) {
        individuals.set(index, ind);
    }

    // Phương thức lấy kích thước quần thể
    public int size() {
        return individuals.size();
    }

    // Getter để lấy dữ liệu bài toán
    public KnapsackProblem getProblem() {
        return problem;
    }
}