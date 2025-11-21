package input;

import algorithm.GeneticAlgorithm;
import algorithm.StoppingCriteria;
import exception.InputHandler;
import genetic.ICrossoverOperator;
import genetic.IMutationOperator;
import genetic.ISelectionOperator;
import genetic.implement.BitFlipMutation;
import genetic.implement.RankThresholdSelection;
import genetic.implement.UniformCrossover;
import model.KnapsackProblem;
import model.Population;
import util.DataGenerator;
import util.Logger;
import util.ResultDisplay;

public class KnapsackRunner {
    // --------------------------------------------------------------------
    // CHẾ ĐỘ 1: NHẬP TAY HOÀN TOÀN
    // --------------------------------------------------------------------
    public static void chayCheDoNhapTay() {
        System.out.println("\nBẠN ĐÃ CHỌN: NHẬP TAY TOÀN BỘ DỮ LIỆU\n");

        int soLuongCaThe = InputHandler.nhapSoNguyenDuong("Nhập số lượng cá thể (ví dụ: 10): ");
        int soLuongVat   = InputHandler.nhapSoNguyenDuong("Nhập số lượng vật phẩm (5 < n <10): ");
        int maxWeight    = InputHandler.nhapSoNguyenDuong("Nhập sức chứa tối đa của túi (> 0): ");

        int[] giaTri   = new int[soLuongVat];
        int[] khoiLuong = new int[soLuongVat];

        System.out.println("\nNhập giá trị từng vật phẩm:");
        for (int i = 0; i < soLuongVat; i++) {
            giaTri[i] = InputHandler.nhapGiaTriVatPham(i);        // giữ nguyên tên hàm bạn có
        }

        System.out.println("\nNhập trọng lượng từng vật phẩm:");
        for (int i = 0; i < soLuongVat; i++) {
            khoiLuong[i] = InputHandler.nhapTrongLuongVatPham(i); // giữ nguyên tên hàm bạn có
        }

        // Chạy GA với dữ liệu vừa nhập
        runGA(soLuongCaThe, soLuongVat, maxWeight, giaTri, khoiLuong);
    }

    // --------------------------------------------------------------------
    // CHẾ ĐỘ 2: CHỈ NHẬP 3 THAM SỐ → TỰ ĐỘNG SINH MẢNG
    // --------------------------------------------------------------------
    public static void chayCheDoSinhNgauNhien() {
        System.out.println("\nBẠN ĐÃ CHỌN: TỰ ĐỘNG SINH NGẪU NHIÊN 2 MẢNG\n");

        int soLuongCaThe = InputHandler.nhapSoNguyenDuong("Nhập số lượng cá thể (ví dụ: 10): ");
        int soLuongVat   = InputHandler.nhapSoNguyenDuong("Nhập số lượng vật phẩm (n > 0): ");
        int maxWeight    = InputHandler.nhapSoNguyenDuong("Nhập sức chứa tối đa của túi: ");

        int[] giaTri    = DataGenerator.generateRandomArray(soLuongVat, 5, 20);
        int[] khoiLuong = DataGenerator.generateRandomArray(soLuongVat, 5, 10);

        System.out.println("Đã sinh ngẫu nhiên " + soLuongVat + " vật phẩm thành công!\n");

        // Chạy GA với dữ liệu ngẫu nhiên
        runGA(soLuongCaThe, soLuongVat, maxWeight, giaTri, khoiLuong);
    }

    // --------------------------------------------------------------------
    // HÀM CHUNG: TẠO + CHẠY THUẬT TOÁN DI TRUYỀN (giữ nguyên toàn bộ khởi tạo)
    // --------------------------------------------------------------------
    private static void runGA(int soLuongCaThe, int soLuongVat,int maxWeight, int[] giaTri, int[] khoiLuong) {

        // Hiển thị thông tin bài toán (dùng class cũ của bạn)
        DataGenerator.displayProblem(giaTri, khoiLuong, maxWeight);

        // Tạo đối tượng bài toán
        KnapsackProblem problem = new KnapsackProblem(giaTri, khoiLuong, maxWeight);

        // Tạo quần thể
        Population population = new Population(soLuongCaThe, soLuongVat, problem);

        // Tạo điều kiện dừng
        StoppingCriteria stoppingCriteria = new StoppingCriteria(1000, 30, 100);

        // Tạo các operator (giữ nguyên như code cũ của bạn)
        ISelectionOperator selectionOp = new RankThresholdSelection(0.2);
        ICrossoverOperator crossoverOp = new UniformCrossover(20);
        IMutationOperator mutationOp  = new BitFlipMutation(0.1);

        // Tạo và chạy GA
        GeneticAlgorithm ga = new GeneticAlgorithm(population, stoppingCriteria, selectionOp, crossoverOp, mutationOp);
        ga.run();

        // Hiển thị kết quả (dùng 2 hàm cũ của bạn trong ResultDisplay)
        System.out.println("\n=== THUẬT TOÁN ĐÃ KẾT THÚC ===");
        DataGenerator.displayProblem(giaTri, khoiLuong, maxWeight);
        System.out.println("Số thế hệ đã chạy      : " + ga.getStoppingCriteria().getCurrentGeneration());
        System.out.println("Fitness tốt nhất đạt được: " + ga.getStoppingCriteria().getBestFitness());

        ResultDisplay.displayPopulation(ga.getPopulation(), problem,ga.getStoppingCriteria().getCurrentGeneration());
        ResultDisplay.displayBest(ga.getPopulation(), problem);

        Logger.logFullResult(problem, population, ga.getStoppingCriteria().getCurrentGeneration());
    }
}
