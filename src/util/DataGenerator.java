package util;// Lớp sinh dữ liệu ngẫu nhiên cho bài toán
import java.util.Random;

public class DataGenerator {
    // Phương thức sinh mảng ngẫu nhiên trong khoảng min-max
    public static int[] generateRandomArray(int size, int min, int max) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(max - min + 1) + min;
        }
        return array;
    }

    // Phương thức hiển thị mảng
    public static void displayArray(String name, int[] array) {
        System.out.print(name + ": ");
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    // In thông tin bài toán đẹp đẹp
    public static void displayProblem(int[] values, int[] weights, int maxWeight) {
        System.out.println("\n=== THÔNG TIN BÀI TOÁN CÁI TÚI ===");
        displayArray("Giá trị (value)   ", values);
        displayArray("Trọng lượng (weight)", weights);
        System.out.println("Trọng lượng tối đa của túi: " + maxWeight);
        System.out.println("Số vật phẩm: " + values.length);
        System.out.println("=====================================\n");
    }
}