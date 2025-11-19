package genetic.implement;// Lớp thực hiện đột biến lật bit với tỉ lệ đột biến

import genetic.IMutationOperator;
import model.Individual;
import model.Population;

import java.util.Random;

public class BitFlipMutation implements IMutationOperator {
    // Tỉ lệ đột biến (ví dụ 0.1 = 10%)
    private double mutationRate;

    // Constructor khởi tạo tỉ lệ đột biến
    public BitFlipMutation(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    // Phương thức thực hiện đột biến
    public void performMutation(Population population) {
        Random random = new Random();

        // Duyệt từng cá thể
        for (Individual ind : population.getIndividuals()) {
            // Duyệt từng gene
            for (int j = 0; j < ind.getGenes().length; j++) {
                // Với xác suất mutationRate, lật bit (0->1 hoặc 1->0)
                if (random.nextDouble() < mutationRate) {
                    ind.setGene(j, 1 - ind.getGenes()[j]);
                }
            }
        }

        // In kết quả sau đột biến
        System.out.println("\n=== SAU KHI ĐỘT BIẾN ===");
        for (int i = 0; i < population.size(); i++) {
            System.out.print("v" + (i + 1) + ":\t");
            for (int j = 0; j < population.getIndividual(i).getGenes().length; j++) {
                System.out.print(population.getIndividual(i).getGenes()[j] + "\t");
            }
            System.out.println();
        }
    }
}