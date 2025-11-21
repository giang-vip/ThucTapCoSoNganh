package model;

// Lớp đại diện cho bài toán Knapsack (cái túi 0/1), lưu trữ dữ liệu đầu vào
public class KnapsackProblem {
    // Mảng giá trị của các vật phẩm
    private int[] values;
    // Mảng khối lượng của các vật phẩm
    private int[] weights;
    // Khối lượng tối đa của cái túi
    private int maxWeight;

    // Constructor để khởi tạo dữ liệu bài toán
    public KnapsackProblem(int[] values, int[] weights, int maxWeight) {
        this.values = values;
        this.weights = weights;
        this.maxWeight = maxWeight;
    }

    // trả về số lượng phần vật phẩm
    public int getNumItems() {
        return values.length;  // hoặc weights.length, cả 2 đều bằng nhau
    }

    // Getter để lấy mảng giá trị
    public int[] getValues() {
        return values;
    }

    // Getter để lấy mảng khối lượng
    public int[] getWeights() {
        return weights;
    }

    // Getter để lấy khối lượng tối đa
    public int getMaxWeight() {
        return maxWeight;
    }
}