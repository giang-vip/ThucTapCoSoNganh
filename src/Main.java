// Lớp chính để chạy chương trình, nhập dữ liệu và khởi chạy GA
import input.KnapsackRunner;
import exception.InputHandler;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("""
            ╔══════════════════════════════════════════════════════════════════╗
            ║       GIẢI BÀI TOÁN CÁI TÚI 0-1 BẰNG THUẬT TOÁN DI TRUYỀN        ║
            ║                     PHIÊN BẢN HOÀN HẢO 2025                      ║
            ╚══════════════════════════════════════════════════════════════════╝
            """);

        int choice;
        do {
            System.out.println("═".repeat(68));
            System.out.println("                          MENU CHÍNH");
            System.out.println("═".repeat(68));
            System.out.println("1 → Nhập tay hoàn toàn (giá trị + trọng lượng từng món)");
            System.out.println("2 → Nhập thông số cơ bản → tự động sinh ngẫu nhiên 2 mảng");
            System.out.println("0 → Thoát chương trình");
            System.out.println("═".repeat(68));

            choice = InputHandler.nhapSoNguyenDuong("→ Nhập lựa chọn của bạn (0-2): ");

            switch (choice) {
                case 1 -> KnapsackRunner.chayCheDoNhapTay();
                case 2 -> KnapsackRunner.chayCheDoSinhNgauNhien();

                default -> System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }

            // Dừng một chút để người dùng đọc kết quả trước khi về menu
            if (choice == 1 || choice == 2) {
                System.out.println("\nNhấn Enter để trở về menu chính...");
                sc.nextLine();
            }

        } while (choice != 0);

        sc.close();
    }


}