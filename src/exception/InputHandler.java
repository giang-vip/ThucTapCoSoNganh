package exception;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    // Nhập số nguyên dương > 0
    public static int nhapSoNguyenDuong(String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >0) {
                    return value;
                } else {
                    System.out.println("Lỗi: Giá trị phải lớn hơn 0! Vui lòng nhập lại.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Chỉ được nhập số nguyên! Vui lòng nhập lại.\n");
            }
        }
    }
    // Nhập số nguyên dương >= 0
    public static int nhapSoDuong(String msg) {
        while (true) {
            System.out.print(msg);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >=0) {
                    return value;
                } else {
                    System.out.println("Lỗi: Giá trị phải lớn hơn hoặc bằng 0! Vui lòng nhập lại.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Chỉ được nhập số nguyên! Vui lòng nhập lại.\n");
            }
        }
    }

    // Nhập giá trị vật phẩm (cho phép = 0, không âm)
    public static int nhapGiaTriVatPham(int index) {
        while (true) {
            System.out.print("   → Giá trị vật phẩm " + (index + 1) + " (≥ 0): ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("   Lỗi: Giá trị không được âm!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("   Lỗi: Chỉ được nhập số nguyên!\n");
            }
        }
    }

    // Nhập trọng lượng vật phẩm (phải > 0)
    public static int nhapTrongLuongVatPham(int index) {
        while (true) {
            System.out.print("   → Trọng lượng vật phẩm " + (index + 1) + " (> 0): ");
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("   Lỗi: Trọng lượng phải lớn hơn 0!\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("   Lỗi: Chỉ được nhập số nguyên dương nha!\n");
            }
        }
    }

    // --------------------------------------------------------------------
    // HÀM NHẬP CHUỖI TỪ BÀN PHÍM – SIÊU AN TOÀN, SIÊU ĐẸP
    // --------------------------------------------------------------------
    public static String nhapChuoi(String thongBao) {
        System.out.print(thongBao);
        java.util.Scanner sc = new java.util.Scanner(System.in);

        while (true) {
            try {
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Không được để trống! Vui lòng nhập lại: ");
                    continue;
                }
                return input;
            } catch (Exception e) {
                System.out.print("Lỗi nhập liệu! Vui lòng nhập lại: ");
            }
        }
    }
}