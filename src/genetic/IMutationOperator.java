package genetic;

import model.Population;

// Interface định nghĩa phương thức cho việc đột biến cá thể
public interface IMutationOperator {
    // Phương thức thực hiện đột biến trên quần thể
    void performMutation(Population population);
}
