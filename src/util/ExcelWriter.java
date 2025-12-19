package util;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelWriter {

    private static final String EXCEL_FILE = "KetQuaThucNghiem.xlsx";

    // Chú thích đặc biệt: Hàm này giống như "viết thêm nhật ký" – thêm 1 hàng mới vào Excel
    public static void appendResultToExcel(String testName, double fitness, long time, int gen) {
        Workbook workbook;
        Sheet sheet;

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE)) {
            workbook = new XSSFWorkbook(fis);  // Mở file cũ (nếu có)
            sheet = workbook.getSheet("Kết quả thực nghiệm");
            if (sheet == null) sheet = workbook.createSheet("Kết quả thực nghiệm");
        } catch (Exception e) {
            workbook = new XSSFWorkbook();  // Nếu chưa có file, tạo mới
            sheet = workbook.createSheet("Kết quả thực nghiệm");
            Row header = sheet.createRow(0);  // Thêm tiêu đề lần đầu
            header.createCell(0).setCellValue("Bộ dữ liệu");
            header.createCell(1).setCellValue("Fitness tối ưu");
            header.createCell(2).setCellValue("Thời gian chạy (ms)");
            header.createCell(3).setCellValue("Thế hệ hội tụ");
        }

        int lastRow = sheet.getLastRowNum();  // Tìm hàng cuối cùng
        Row newRow = sheet.createRow(lastRow + 1);  // Thêm hàng mới
        newRow.createCell(0).setCellValue(testName);
        newRow.createCell(1).setCellValue(fitness);
        newRow.createCell(2).setCellValue(time);
        newRow.createCell(3).setCellValue(gen);

        try (FileOutputStream fos = new FileOutputStream(EXCEL_FILE)) {
            workbook.write(fos);  // Lưu lại file
            System.out.println("Đã thêm kết quả mới vào file Excel: " + EXCEL_FILE);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("LỖI: File Excel đang mở trong chương trình khác! Vui lòng đóng Excel và chạy lại.");
            System.out.println("Hoặc xóa file cũ và chạy lại để tạo mới.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Chú thích đặc biệt: Hàm này giống như "đọc sổ nhật ký cũ" – lấy dữ liệu từ Excel để vẽ biểu đồ
    public static List<List<Object>> readExcelData() {
        List<List<Object>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_FILE)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Kết quả thực nghiệm");

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;  // Bỏ tiêu đề
                List<Object> rowData = new ArrayList<>();
                rowData.add(row.getCell(0).getStringCellValue());  // Bộ dữ liệu
                rowData.add(row.getCell(1).getNumericCellValue()); // Fitness
                rowData.add((long) row.getCell(2).getNumericCellValue()); // Thời gian
                rowData.add((int) row.getCell(3).getNumericCellValue()); // Thế hệ
                data.add(rowData);
            }
        } catch (Exception e) {
            System.out.println("Chưa có file Excel hoặc lỗi đọc: " + e.getMessage());
        }

        return data;
    }



}