// Lớp chính để chạy chương trình, nhập dữ liệu và khởi chạy GA
import algorithm.GeneticAlgorithm;
import algorithm.StoppingCriteria;
import genetic.ICrossoverOperator;
import genetic.IMutationOperator;
import genetic.ISelectionOperator;
import genetic.implement.BitFlipMutation;
import genetic.implement.RankThresholdSelection;
import genetic.implement.UniformCrossover;
import model.KnapsackProblem;
import model.Population;
import util.DataGenerator;
import exception.InputHandler;
import util.ResultDisplay;

public class Main {
    public static void main(String[] args) {
        System.out.println("Chọn cách nhập dữ liệu:");
        System.out.println("1. Nhập tay hoàn toàn (giá trị + trọng lượng từng món)");
        System.out.println("2. Chỉ nhập thông số cơ bản → tự động sinh ngẫu nhiên 2 mảng");
        System.out.print("→ Bạn chọn (1 hoặc 2): ");

        String chon = new java.util.Scanner(System.in).nextLine().trim();

        int soLuongCaThe, soLuongVat, maxWeight;
        int[] giaTri, khoiLuong;

        // === HỎI CHUNG 3 THAM SỐ CHO CẢ 2 TRƯỜNG HỢP ===
        soLuongCaThe = InputHandler.nhapSoNguyenDuong("Nhập số lượng cá thể (ví dụ: 100): ");
        soLuongVat    = InputHandler.nhapSoNguyenDuong("Nhập số lượng vật phẩm (n > 0): ");
        maxWeight     = InputHandler.nhapSoNguyenDuong("Nhập sức chứa tối đa của túi: ");

        if ("1".equals(chon)) {
            // NHẬP TAY TỪNG MÓN
            System.out.println("\n► NHẬP TAY GIÁ TRỊ VÀ TRỌNG LƯỢNG\n");
            giaTri = new int[soLuongVat];
            khoiLuong = new int[soLuongVat];

            System.out.println("Nhập giá trị từng vật phẩm:");
            for (int i = 0; i < soLuongVat; i++) {
                giaTri[i] = InputHandler.nhapGiaTriVatPham(i);
            }

            System.out.println("\nNhập trọng lượng từng vật phẩm:");
            for (int i = 0; i < soLuongVat; i++) {
                khoiLuong[i] = InputHandler.nhapTrongLuongVatPham(i);
            }

        } else {
            // CHỈ SINH NGẪU NHIÊN 2 MẢNG THEO SỐ LƯỢNG NGƯỜI DÙNG ĐÃ NHẬP
            System.out.println("\n► TỰ ĐỘNG SINH NGẪU NHIÊN 2 MẢNG THEO THÔNG SỐ BẠN VỪA NHẬP\n");
            giaTri    = DataGenerator.generateRandomArray(soLuongVat, 5, 200);
            khoiLuong = DataGenerator.generateRandomArray(soLuongVat, 5, 100);
            System.out.println("Đã sinh ngẫu nhiên " + soLuongVat + " vật phẩm!");
        }

        // HIỂN THỊ THÔNG TIN BÀI TOÁN
        DataGenerator.displayProblem(giaTri, khoiLuong, maxWeight);

        // Tạo đối tượng bài toán
        KnapsackProblem problem = new KnapsackProblem(giaTri, khoiLuong, maxWeight);

        // Tạo quần thể
        Population population = new Population(soLuongCaThe, soLuongVat, problem);

        // Tạo điều kiện dừng
        StoppingCriteria stoppingCriteria = new StoppingCriteria(1000, 30, 100);


        // Tạo các operator
        ISelectionOperator selectionOp = new RankThresholdSelection(0.2); // Ngưỡng loại bỏ (20%)
        ICrossoverOperator crossoverOp = new UniformCrossover(20); // Lớp thực hiện lai ghép đồng nhất (uniform crossover) với 20 lần lai
        IMutationOperator mutationOp = new BitFlipMutation(0.1);// TỈ LỆ ĐT BIẾN CHỌN 0.1

        // Tạo và chạy GA
        GeneticAlgorithm ga = new GeneticAlgorithm(population, stoppingCriteria, selectionOp, crossoverOp, mutationOp);
        ga.run();

        // Hiển thị kết quả cuối cùng
        System.out.println("\n=== THUAT TOAN KET THUC ===");
        System.out.println("So the he: " + ga.getStoppingCriteria().getCurrentGeneration());
        System.out.println("Fitness tot nhat dat duoc: " + ga.getStoppingCriteria().getBestFitness());

        ResultDisplay.displayPopulation(ga.getPopulation(), problem);
        ResultDisplay.displayBest(ga.getPopulation(), problem);
    }
}