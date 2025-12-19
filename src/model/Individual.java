package model;

// Lớp đại diện cho một cá thể (giải pháp) trong quần thể, bao gồm genes (mảng 0/1) và fitness
public class Individual {
    // Mảng genes: mỗi phần tử là 0 hoặc 1, đại diện cho việc chọn vật phẩm hay không
    private int[] genes;
    // Độ thích nghi (fitness) của cá thể
    private int fitness;

    // Constructor khởi tạo cá thể với số lượng vật phẩm
    public Individual(int numItems) {
        genes = new int[numItems];
    }

    // Phương thức tính toán fitness dựa trên dữ liệu bài toán
    // Nếu tổng khối lượng vượt quá maxWeight, phạt bằng cách trừ 100000
    public void calculateFitness(KnapsackProblem problem) {
        int totalValue = 0;  // Tổng giá trị
        int totalWeight = 0; // Tổng khối lượng
        for (int j = 0; j < genes.length; j++) {
            if (genes[j] == 1) { // Nếu vật phẩm được chọn
                totalValue += problem.getValues()[j];
                totalWeight += problem.getWeights()[j];
            }
        }
        // Áp dụng phạt nếu vượt trọng lượng
        fitness = (totalWeight > problem.getMaxWeight()) ? totalValue - 100000000 : totalValue;
    }

    // Phương thức clone để sao chép cá thể (sử dụng trong chọn lọc)
    public Individual clone() {
        Individual clone = new Individual(genes.length);
        System.arraycopy(genes, 0, clone.genes, 0, genes.length);
        clone.fitness = fitness;
        return clone;
    }

    // Getter để lấy mảng genes
    public int[] getGenes() {
        return genes;
    }

    // Getter để lấy fitness
    public int getFitness() {
        return fitness;
    }

    // Setter để thay đổi giá trị gene tại vị trí index
    public void setGene(int index, int value) {
        genes[index] = value;
    }

    // Chú thích đặc biệt: Hàm này giống như "cân tổng các món đồ đang chọn trong giỏ" – tính tổng trọng lượng các vật phẩm được chọn (genes[i] == 1)
    public int getTotalWeight(KnapsackProblem problem) {
        int totalWeight = 0;
        int[] weights = problem.getWeights();
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 1) {
                totalWeight += weights[i];
            }
        }
        return totalWeight;
    }

    // Chú thích đặc biệt: Hàm này giống như "liệt kê tên các món đồ đang cầm trong giỏ" – trả về chuỗi "Vật1 Vật3 Vật5" để dễ đọc
    public String getSelectedItemsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == 1) {
                sb.append("Vật").append(i + 1).append(" ");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "Không chọn vật nào";
    }

}