package algorithm;

// Lớp kiểm tra điều kiện dừng cho thuật toán GA
public class StoppingCriteria {
    // Thế hệ hiện tại
    private int currentGeneration;
    // Số thế hệ tối đa
    private int maxGenerations;
    // Ngưỡng fitness cần đạt
    private double fitnessThreshold;
    // Số lần không cải thiện tối đa
    private int maxNoImprovement;
    // Fitness tốt nhất đạt được
    private double bestFitness;
    // Số lần không cải thiện liên tiếp
    private int noImprovementCount;

    // Constructor khởi tạo các tham số điều kiện dừng
    public StoppingCriteria(int maxGenerations, double fitnessThreshold, int maxNoImprovement) {
        this.maxGenerations = maxGenerations;
        this.fitnessThreshold = fitnessThreshold;
        this.maxNoImprovement = maxNoImprovement;
        this.currentGeneration = 0;
        this.bestFitness = Double.MIN_VALUE;
        this.noImprovementCount = 0;
    }

    // Phương thức kiểm tra điều kiện dừng dựa trên fitness hiện tại
    public boolean checkStop(double currentBest) {
        currentGeneration++; // Tăng thế hệ

        // Cập nhật nếu có cải thiện
        if (currentBest > bestFitness) {
            bestFitness = currentBest;
            noImprovementCount = 0;
        } else {
            noImprovementCount++;
        }

        // Kiểm tra đạt thế hệ tối đa
        if (currentGeneration >= maxGenerations) {
            System.out.println("Dung: Da dat so the he toi da (" + currentGeneration + ")");
            return true;
        }

        // Kiểm tra đạt ngưỡng fitness
        if (bestFitness >= fitnessThreshold) {
            System.out.println("Dung: Fitness da dat nguong yeu cau (" + bestFitness + ")");
            return true;
        }

        // Kiểm tra số lần không cải thiện
        if (noImprovementCount >= maxNoImprovement) {
            System.out.println("Dung: Qua nhieu lan khong cai thien (" + noImprovementCount + ")");
            return true;
        }

        return false; // Chưa dừng
    }

    // Getter để lấy fitness tốt nhất
    public double getBestFitness() {
        return bestFitness;
    }

    // Getter để lấy thế hệ hiện tại
    public int getCurrentGeneration() {
        return currentGeneration;
    }
}