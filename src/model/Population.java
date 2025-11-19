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
            for (int j = 0; j < numItems; j++) {
                // Khởi tạo ngẫu nhiên genes (0 hoặc 1)
                ind.setGene(j, random.nextInt(2));
            }
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