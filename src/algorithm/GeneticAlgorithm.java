package algorithm;

import genetic.ISelectionOperator;
import genetic.IMutationOperator;
import genetic.ICrossoverOperator;
import model.Population;

// Lớp chính thực hiện thuật toán GA, quản lý vòng lặp tiến hóa
public class GeneticAlgorithm {
    // Quần thể
    private Population population;
    // Điều kiện dừng
    private StoppingCriteria stoppingCriteria;
    // Operator chọn lọc
    private ISelectionOperator selectionOperator;
    // Operator lai ghép
    private ICrossoverOperator crossoverOperator;
    // Operator đột biến
    private IMutationOperator mutationOperator;

    // Constructor khởi tạo các thành phần
    public GeneticAlgorithm(Population population, StoppingCriteria stoppingCriteria,
                            ISelectionOperator selectionOperator, ICrossoverOperator crossoverOperator,
                            IMutationOperator mutationOperator) {
        this.population = population;
        this.stoppingCriteria = stoppingCriteria;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    // Phương thức chạy thuật toán GA
    public void run() {
        // In quần thể ban đầu
        System.out.println("=== QUAN THE BAN DAU ===");
        for (int i = 0; i < population.size(); i++) {
            System.out.print("v" + (i + 1) + ":\t");
            for (int j = 0; j < population.getIndividual(i).getGenes().length; j++) {
                System.out.print(population.getIndividual(i).getGenes()[j] + "\t");
            }
            System.out.println();
        }

        // Vòng lặp tiến hóa
        while (true) {
            // Bước 1: Tính fitness cho quần thể
            population.calculateFitnessForAll();

            // In độ thích nghi
            System.out.println("\n=== DO THICH NGHI CUA CAC CA THE ===");
            for (int i = 0; i < population.size(); i++) {
                System.out.println("v" + (i + 1) + " = " + population.getIndividual(i).getFitness());
            }

            // Bước 2: Kiểm tra điều kiện dừng
            if (stoppingCriteria.checkStop(population.getBestFitness())) {
                break;
            }

            // Bước 3: Chọn lọc
            selectionOperator.performSelection(population);

            // Bước 4: Lai ghép
            crossoverOperator.performCrossover(population);

            // Bước 5: Đột biến
            mutationOperator.performMutation(population);
        }
    }

    // Getter để lấy quần thể
    public Population getPopulation() {
        return population;
    }

    // Getter để lấy điều kiện dừng
    public StoppingCriteria getStoppingCriteria() {
        return stoppingCriteria;
    }
}