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
import util.*;

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
        runGA(soLuongCaThe, soLuongVat, maxWeight, giaTri, khoiLuong,"NhapTay_n" + soLuongVat + "_W" + maxWeight);
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
        runGA(soLuongCaThe, soLuongVat, maxWeight, giaTri, khoiLuong,"NgauNhien_n" + soLuongVat + "_W" + maxWeight);
    }

    // --------------------------------------------------------------------
// CHẾ ĐỘ 3: NHẬP ĐƯỜNG DẪN FILE (TÙY CHỌN) → ĐỌC + HIỂN THỊ + CHẠY GA
// --------------------------------------------------------------------
    public static void chayCheDoDocTuFile() {
        System.out.println("\nBẠN ĐÃ CHỌN: ĐỌC DỮ LIỆU TỪ FILE (TÙY CHỌN ĐƯỜNG DẪN)\n");

        String duongDanFile = InputHandler.nhapChuoi("Nhập đường dẫn file dữ liệu (ví dụ: 5item.txt hoặc data/5item.txt: ");

        try (java.util.Scanner scanner = new java.util.Scanner(new java.io.File(duongDanFile))) {

            // Dòng 1: số lượng cá thể + sức chứa túi
            int soLuongCaThe = scanner.nextInt();
            int maxWeight    = scanner.nextInt();
            scanner.nextLine(); // bỏ qua phần còn lại của dòng 1

            // Dòng 2: mảng giá trị
            String lineGiaTri = scanner.nextLine().trim();
            String[] valueStr = lineGiaTri.split("\\s+");
            int soLuongVat = valueStr.length;
            int[] giaTri = new int[soLuongVat];
            for (int i = 0; i < soLuongVat; i++) {
                giaTri[i] = Integer.parseInt(valueStr[i]);
            }

            // Dòng 3: mảng trọng lượng
            String lineKhoiLuong = scanner.nextLine().trim();
            String[] weightStr = lineKhoiLuong.split("\\s+");
            int[] khoiLuong = new int[soLuongVat];
            for (int i = 0; i < soLuongVat; i++) {
                khoiLuong[i] = Integer.parseInt(weightStr[i]);
            }

            // === KIỂM TRA DỮ LIỆU ĐẦU VÀO ĐỂ TRÁNH SAI LOGIC (SIÊU QUAN TRỌNG!!!) ===
            // Ví dụ cuộc sống đơn giản: Giống như kiểm tra nguyên liệu nấu phở không được có "thịt -5kg" hay "nước dùng -10 lít" – phải báo lỗi ngay!
            boolean coLoi = false;

            // Kiểm tra số lượng cá thể và sức chứa túi phải > 0
            if (soLuongCaThe <= 0) {
                System.out.println("LỖI: Số lượng cá thể phải lớn hơn 0! Hiện tại: " + soLuongCaThe);
                coLoi = true;
            }
            if (maxWeight < 0) {
                System.out.println("LỖI: Sức chứa túi phải ≥ 0! Hiện tại: " + maxWeight);
                coLoi = true;
            }

            // Kiểm tra giá trị phải ≥ 0 (không âm)
            for (int i = 0; i < giaTri.length; i++) {
                if (giaTri[i] < 0) {
                    System.out.println("LỖI: Giá trị vật phẩm " + (i+1) + " không được âm! Hiện tại: " + giaTri[i]);
                    coLoi = true;
                }
            }

            // Kiểm tra trọng lượng phải > 0 (không âm và không bằng 0 – vì vật phẩm trọng lượng 0 vô nghĩa)
            for (int i = 0; i < khoiLuong.length; i++) {
                if (khoiLuong[i] <= 0) {
                    System.out.println("LỖI: Trọng lượng vật phẩm " + (i+1) + " phải lớn hơn 0! Hiện tại: " + khoiLuong[i]);
                    coLoi = true;
                }
            }

            // Nếu có lỗi → dừng không chạy GA
            if (coLoi) {
                System.out.println("\nDỮ LIỆU ĐẦU VÀO KHÔNG HỢP LỆ – KHÔNG THỂ CHẠY THUẬT TOÁN!");
                System.out.println("Vui lòng sửa file dữ liệu và thử lại.\n");
                return; // Dừng hàm, không chạy GA
            }

            // Nếu OK → hiển thị lại dữ liệu và chạy GA
            System.out.println("\nĐỌC FILE THÀNH CÔNG TỪ: " + duongDanFile);
            String fileName = new java.io.File(duongDanFile).getName(); // Lấy tên file
            String testName = fileName.substring(0, fileName.lastIndexOf('.')); // Bỏ đuôi .txt → "5item"
            // Chạy GA như bình thường
            runGA(soLuongCaThe, soLuongVat, maxWeight, giaTri, khoiLuong,testName);

        } catch (java.io.FileNotFoundException e) {
            System.out.println("KHÔNG TÌM THẤY FILE: " + duongDanFile);
            System.out.println("Lưu ý:");
            System.out.println("   • File phải tồn tại");
            System.out.println("   • Đặt trong thư mục dự án hoặc dùng đường dẫn tuyệt đối");
            System.out.println("   • Định dạng file mẫu:");
            System.out.println("       10 50");
            System.out.println("       15 12 8 20 18 25 10 14 22 16");
            System.out.println("       8 5 3 9 7 12 4 6 10 8");
        } catch (java.util.NoSuchElementException e) {
            System.out.println("NỘI DUNG FILE KHÔNG ĐÚNG ĐỊNH DẠNG!");
            System.out.println("Vui lòng kiểm tra lại file có đúng 3 dòng không:");
            System.out.println("   Dòng 1: số cá thể + sức chứa");
            System.out.println("   Dòng 2: các giá trị (cách nhau bằng khoảng trắng)");
            System.out.println("   Dòng 3: các trọng lượng");
        } catch (NumberFormatException e) {
            System.out.println("DỮ LIỆU TRONG FILE KHÔNG PHẢI LÀ SỐ!");
            System.out.println("Vui lòng chỉ nhập số nguyên trong file.");
        } catch (Exception e) {
            System.out.println("LỖI KHÔNG XÁC ĐỊNH KHI ĐỌC FILE: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // --------------------------------------------------------------------
    // HÀM CHUNG: TẠO + CHẠY THUẬT TOÁN DI TRUYỀN (giữ nguyên toàn bộ khởi tạo)
    // --------------------------------------------------------------------
    private static void runGA(int soLuongCaThe, int soLuongVat,int maxWeight, int[] giaTri, int[] khoiLuong,String testName) {
        long startTime = System.currentTimeMillis(); // Bắt đầu đo thời gian (như bấm đồng hồ bấm giờ nấu ăn)

        // Hiển thị thông tin bài toán (dùng class cũ của bạn)
        DataGenerator.displayProblem(giaTri, khoiLuong, maxWeight);

        // Tạo đối tượng bài toán
        KnapsackProblem problem = new KnapsackProblem(giaTri, khoiLuong, maxWeight);

        // Tạo quần thể
        Population population = new Population(soLuongCaThe, soLuongVat, problem);

        // Tạo điều kiện dừng
        StoppingCriteria stoppingCriteria = new StoppingCriteria(10000, 1000);

        // Tạo các operator (giữ nguyên như code cũ của bạn)
        ISelectionOperator selectionOp = new RankThresholdSelection(0.2);
        ICrossoverOperator crossoverOp = new UniformCrossover(20);
        IMutationOperator mutationOp  = new BitFlipMutation(0.005);

        // Tạo và chạy GA
        GeneticAlgorithm ga = new GeneticAlgorithm(population, stoppingCriteria, selectionOp, crossoverOp, mutationOp);
        ga.run();

        // Sau khi ga.run();
        ResultDisplay.displayEvolutionTable(ga, problem);

        // Có thể kết hợp với Logger để ghi bảng này vào file luôn!
        //Logger.logEvolutionTable(ga, problem);

        // Hiển thị kết quả (dùng 2 hàm cũ của bạn trong ResultDisplay)
        System.out.println("\n=== THUẬT TOÁN ĐÃ KẾT THÚC ===");
        DataGenerator.displayProblem(giaTri, khoiLuong, maxWeight);
        System.out.println("Số thế hệ đã chạy      : " + ga.getStoppingCriteria().getCurrentGeneration());
        System.out.println("Fitness tốt nhất đạt được: " + ga.getStoppingCriteria().getBestFitness());


        ResultDisplay.displayBest(ga, problem);

        Logger.logFullResult(problem, ga, ga.getStoppingCriteria().getCurrentGeneration());
        // DÒNG THẦN THÁNH – VẼ ĐỒ THỊ ĐẸP LUNG LINH
        FitnessChart.showChart(ga,testName);

        long endTime = System.currentTimeMillis();
        long timeMs = endTime - startTime; // Thời gian chạy (ms)

        // MỚI – lấy fitness tốt nhất thực sự (best ever) – luôn dương nếu có giải khả thi
        double bestFitness = ga.getBestFitnessLog().stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0);
        int convergenceGen = ga.getGenerationLog().get(ga.getGenerationLog().size() - 1); // Thế hệ cuối

        // === THÊM MỚI: GHI TỰ ĐỘNG VÀO EXCEL (NHƯ GHI NHẬT KÝ NẤU ĂN) ===
        ExcelWriter.appendResultToExcel(testName, bestFitness, timeMs, convergenceGen);

        System.out.println("=== KẾT QUẢ ĐÃ GHI TỰ ĐỘNG VÀO EXCEL ===");
        System.out.println("→ Bộ dữ liệu: " + testName);
        System.out.println("→ Fitness: " + bestFitness);
        System.out.println("→ Thời gian: " + timeMs + " ms");
        System.out.println("→ Thế hệ: " + convergenceGen);
    }
}
