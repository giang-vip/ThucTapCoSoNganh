package genetic.implement;
import genetic.ICrossoverOperator;
import model.Individual;
import model.Population;

import java.util.Random;
// Lớp thực hiện lai ghép đồng nhất (uniform crossover) với 20 lần lai
public class UniformCrossover implements ICrossoverOperator {
    // Số lần lai ghép (20 theo mục cơ sở lí thuyết gốc)
    private int numCrossovers ;

    public UniformCrossover(int numCrossovers) {
        this.numCrossovers = numCrossovers;
    }


    // Phương thức thực hiện lai ghép
    public void performCrossover(Population population) {
        Random random = new Random();
        int n = population.size(); // Kích thước quần thể

        // Thực hiện 20 lần lai
        for (int i = 0; i < numCrossovers; i++) {
            // Chọn ngẫu nhiên 2 cá thể cha mẹ
            int parent1 = random.nextInt(n);
            int parent2 = random.nextInt(n);

            Individual ind1 = population.getIndividual(parent1);

            // Duyệt từng gene, với xác suất 50% lấy từ mẹ
            for (int j = 0; j < ind1.getGenes().length; j++) {
                if (random.nextInt(2) == 1) {
                    ind1.setGene(j, population.getIndividual(parent2).getGenes()[j]);
                }
            }
        }

        // In kết quả sau lai ghép
        System.out.println("\n=== SAU KHI LAI GHEP ===");
        for (int i = 0; i < n; i++) {
            System.out.print("v" + (i + 1) + ":\t");
            for (int j = 0; j < population.getIndividual(i).getGenes().length; j++) {
                System.out.print(population.getIndividual(i).getGenes()[j] + "\t");
            }
            System.out.println();
        }
    }
}