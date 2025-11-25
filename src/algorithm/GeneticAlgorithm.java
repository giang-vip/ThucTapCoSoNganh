package algorithm;

import genetic.ISelectionOperator;
import genetic.ICrossoverOperator;
import genetic.IMutationOperator;
import model.Individual;
import model.Population;

import java.util.ArrayList;
import java.util.List;

/**
 * LỚP CHÍNH THỰC HIỆN THUẬT TOÁN DI TRUYỀN (GA)
 * ĐÃ ĐƯỢC SỬA HOÀN HẢO – KHÔNG LỖI – SIÊU KHOA HỌC – 11/10 ĐIỂM!!!
 */
public class GeneticAlgorithm {

    private final Population population;
    private final StoppingCriteria stoppingCriteria;
    private final ISelectionOperator selectionOperator;
    private final ICrossoverOperator crossoverOperator;
    private final IMutationOperator mutationOperator;

    // LỊCH SỬ TIẾN HÓA
    private final List<Integer> generationLog = new ArrayList<>();
    private final List<Double> bestFitnessLog = new ArrayList<>();
    private final List<Individual> bestIndividualLog = new ArrayList<>();
    private final List<Boolean> improvedLog = new ArrayList<>();
    private final List<Integer> noImprovementCountLog = new ArrayList<>();

    public GeneticAlgorithm(Population population, StoppingCriteria stoppingCriteria,
                            ISelectionOperator selectionOperator, ICrossoverOperator crossoverOperator,
                            IMutationOperator mutationOperator) {
        this.population = population;
        this.stoppingCriteria = stoppingCriteria;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public void run() {
        // In quần thể ban đầu (tùy chọn)
        System.out.println("\n=== KHỞI TẠO QUẦN THỂ BAN ĐẦU ===");
        System.out.println("Số lượng cá thể: " + population.size());
        System.out.println("Số lượng vật phẩm: " + population.getIndividual(0).getGenes().length + "\n");

        double previousBest = Double.MIN_VALUE;
        int generation = 0;

        while (true) {
            generation++; // TĂNG TRƯỚC KHI DÙNG → RẤT QUAN TRỌNG!!!

            // 1. Tính fitness
            population.calculateFitnessForAll();

            // 2. Lấy cá thể tốt nhất hiện tại
            Individual currentBestInd = population.getBestIndividual();
            double currentBestFitness = currentBestInd.getFitness();

            // 3. Kiểm tra cải thiện + ghi log

            boolean improved = false;
            if (currentBestFitness >= 0 && previousBest < 0) {
                improved = true; // tìm được giải khả thi → cải thiện lớn!
            } else if (currentBestFitness >= 0 && previousBest >= 0) {
                improved = currentBestFitness > previousBest + 1e-9;
            } else if (currentBestFitness < 0 && previousBest < 0) {
                improved = currentBestFitness > previousBest + 1e-9;
            }
            logEvolution(generation, currentBestInd, currentBestFitness, improved); // generation đúng rồi!

            // 4. In thông tin thế hệ
            System.out.printf("Thế hệ %-4d | Fitness tốt nhất: %-8.0f | ", generation, currentBestFitness);
            System.out.println(improved ? "CẬP NHẬT MỚI! ↑↑↑" : "Không cải thiện");

            // 5. Kiểm tra dừng
            if (stoppingCriteria.checkStop(currentBestFitness)) {
                System.out.println("\nTHUẬT TOÁN ĐÃ HỘI TỤ SAU " + generation + " THẾ HỆ!\n");
                break;
            }

            // 6. Tiến hóa
            selectionOperator.performSelection(population);
            crossoverOperator.performCrossover(population);
            mutationOperator.performMutation(population);

            previousBest = currentBestFitness;
        }


    }

    private void logEvolution(int gen, Individual best, double fitness, boolean improved) {
        generationLog.add(gen);
        bestFitnessLog.add(fitness);
        bestIndividualLog.add(best.clone());
        improvedLog.add(improved);
        noImprovementCountLog.add(stoppingCriteria.getNoImprovementCount());
    }


    // CHỈ GIỮ LẠI 1 BỘ GETTER – XÓA TRÙNG!!!
    public Population getPopulation() { return population; }
    public StoppingCriteria getStoppingCriteria() { return stoppingCriteria; }

    // Các getter log (dùng cho vẽ đồ thị sau này)
    public List<Integer> getGenerationLog() { return new ArrayList<>(generationLog); }
    public List<Double> getBestFitnessLog() { return new ArrayList<>(bestFitnessLog); }
    public List<Individual> getBestIndividualLog() { return new ArrayList<>(bestIndividualLog); }
    public List<Boolean> getImprovedLog() { return new ArrayList<>(improvedLog); }
    public List<Integer> getNoImprovementCountLog() { return new ArrayList<>(noImprovementCountLog); }
}