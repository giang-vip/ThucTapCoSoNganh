package genetic;

import model.Population;

// Interface định nghĩa phương thức cho việc lai ghép cá thể
public interface ICrossoverOperator {
    // Phương thức thực hiện lai ghép trên quần thể
    void performCrossover(Population population);
}
