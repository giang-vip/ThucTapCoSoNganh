package genetic;

import model.Population;

// Interface định nghĩa phương thức cho việc chọn lọc cá thể trong thuật toán GA
public interface ISelectionOperator {
    // Phương thức thực hiện chọn lọc trên quần thể
    void performSelection(Population population);
}