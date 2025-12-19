package util;

import algorithm.GeneticAlgorithm;
import model.Individual;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FitnessChart extends ApplicationFrame {

    // === METHOD ĐỂ LẤY CHART PANEL (GIỮ NGUYÊN, RẤT HỮU ÍCH KHI LƯU ẢNH) ===

    private ChartPanel chartPanel; // Khai báo biến toàn cục để lưu trữ ChartPanel
    public ChartPanel getChartPanel() {
        return this.chartPanel; // Trả về biến đã lưu thay vì ép kiểu ContentPane
    }

//    // Constructor mới – thêm testName để lưu tên file đúng ý bạn

    public FitnessChart(String title, GeneticAlgorithm ga, String testName) {
        super(title);

        // 1. TẠO DỮ LIỆU BIỂU ĐỒ (Giữ nguyên logic của bạn)
        XYSeries series = new XYSeries("Fitness tốt nhất qua các thế hệ");
        List<Integer> generations = ga.getGenerationLog();
        List<Double> fitnessLog = ga.getBestFitnessLog();

        double bestEver = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < generations.size(); i++) {
            double fitness = fitnessLog.get(i);
            if (fitness > bestEver) {
                bestEver = fitness;
            }
            if (bestEver >= 0) {
                series.add((double) generations.get(i), bestEver);
            }
        }

        if (series.getItemCount() == 0) {
            for (int i = 0; i < generations.size(); i++) {
                series.add(generations.get(i), fitnessLog.get(i));
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        // 2. TẠO BIỂU ĐỒ (Giữ nguyên logic làm đẹp của bạn)
        JFreeChart chart = ChartFactory.createXYLineChart(
                "QUÁ TRÌNH HỘI TỤ CỦA THUẬT TOÁN DI TRUYỀN (KNAPSACK 0/1) - BỘ: " + testName,
                "Thế hệ", "Fitness (Giá trị tối ưu)",
                dataset, PlotOrientation.VERTICAL,
                true, true, false
        );

        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
        chart.getXYPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.getXYPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);

        chartPanel = new ChartPanel(chart); // Gán trực tiếp vào biến toàn cục của class

        chartPanel.setPreferredSize(new Dimension(1600, 600));
        chartPanel.setMouseWheelEnabled(true);

        // 3. XÂY DỰNG NỘI DUNG THÔNG TIN (Giữ nguyên nội dung bạn đã viết)
        // === LẤY BEST EVER CHÍNH XÁC NHẤT TỪ HISTORY (DÙNG bestEver ĐÃ TÍNH Ở PHẦN 1) ===
        int bestIndex = 0;
        for (int i = 0; i < fitnessLog.size(); i++) {
            if (Math.abs(fitnessLog.get(i) - bestEver) < 1e-9) { // So sánh chính xác với double
                bestIndex = i;
                break; // Lấy lần đầu tiên đạt bestEver
            }
        }

        int firstAppearanceGen = generations.get(bestIndex);
        Individual bestInd = ga.getBestIndividualLog().get(bestIndex); // Cá thể tốt nhất thực sự

        StringBuilder subtitleSB = new StringBuilder();
        subtitleSB.append("THÔNG TIN BÀI TOÁN ĐẦU VÀO:\n");
        subtitleSB.append("   • Số lượng cá thể trong quần thể : ").append(ga.getPopulation().size()).append("\n");
        subtitleSB.append("   • Số lượng vật phẩm (n)          : ").append(ga.getPopulation().getProblem().getNumItems()).append("\n");
        subtitleSB.append("   • Sức chứa tối đa của túi        : ").append(ga.getPopulation().getProblem().getMaxWeight()).append("\n");
        subtitleSB.append("   • Tổng số thế hệ đã chạy         : ").append(generations.get(generations.size() - 1)).append("\n\n");

        subtitleSB.append("   DANH SÁCH VẬT PHẨM:\n");
        subtitleSB.append("   ┌─────┬────────────┬──────────────┐\n");
        subtitleSB.append("   │ STT │   Giá trị  │ Trọng lượng  │\n");
        subtitleSB.append("   ├─────┼────────────┼──────────────┤\n");

        int[] values = ga.getPopulation().getProblem().getValues();
        int[] weights = ga.getPopulation().getProblem().getWeights();
        int maxDisplay = Math.min(values.length, 20);
        for (int i = 0; i < maxDisplay; i++) {
            subtitleSB.append(String.format("   │ V%-2d │ %-10d │ %-12d │\n", i+1, values[i], weights[i]));
        }
        if (values.length > 20) {
            subtitleSB.append("   │ ... │    ...     │     ...      │\n");
        }
        subtitleSB.append("   └─────┴────────────┴──────────────┘\n\n");

        subtitleSB.append("KẾT QUẢ TỐI ƯU TÌM ĐƯỢC (TỐT NHẤT TRONG TOÀN BỘ QUÁ TRÌNH):\n");
        subtitleSB.append("   • Xuất hiện lần đầu tại thế hệ   : ").append(firstAppearanceGen).append("\n");
        subtitleSB.append("   • Các vật phẩm được chọn         : ").append(bestInd.getSelectedItemsString()).append("\n");
        subtitleSB.append("   • Tổng giá trị tối đa đạt được   : ").append((int)bestEver).append("\n");
        subtitleSB.append("   • Tổng khối lượng đã sử dụng     : ").append(bestInd.getTotalWeight(ga.getPopulation().getProblem()) + " / " + ga.getPopulation().getProblem().getMaxWeight()).append("\n");
        subtitleSB.append("   • Độ thích nghi (Fitness)        : ").append(String.format("%.1f", bestEver));

        // 4. ĐƯA THÔNG TIN VÀO JTEXTAREA CÓ THANH CUỘN (SCROLL)
        JTextArea infoArea = new JTextArea(subtitleSB.toString());
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(245, 245, 245));
        infoArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setPreferredSize(new Dimension(1600, 350)); // Chiều cao phần thông tin bên dưới

        // 5. TỔ CHỨC LAYOUT: BIỂU ĐỒ TRÊN - THÔNG TIN DƯỚI
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(chartPanel, BorderLayout.CENTER); // Sử dụng biến đã gán ở trên
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Hàm gọi – thêm testName
    public static void showChart(GeneticAlgorithm ga, String testName) {
        SwingUtilities.invokeLater(() -> {
            FitnessChart frame = new FitnessChart("Biểu đồ hội tụ - Bộ: " + testName, ga, testName);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Lưu vào thư mục riêng + tên đầy đủ
            try {
                String thuMucLuu = "BieuDoHoiTu";
                java.io.File folder = new java.io.File(thuMucLuu);
                if (!folder.exists()) folder.mkdir();

                String fileName = thuMucLuu + "/BieuDo_HoiTu_" + testName + "_" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png";

                // 1. Lấy các thành phần giao diện
                // Giả sử mainPanel là ContentPane (BorderLayout)
                JPanel mainPanel = (JPanel) frame.getContentPane();
                ChartPanel cPanel = frame.getChartPanel();

                // Tìm JScrollPane và JTextArea bên trong
                JScrollPane scrollPane = null;
                JTextArea textArea = null;
                for (Component comp : mainPanel.getComponents()) {
                    if (comp instanceof JScrollPane) {
                        scrollPane = (JScrollPane) comp;
                        textArea = (JTextArea) ((JViewport) scrollPane.getComponent(0)).getView();
                    }
                }

                if (cPanel == null || textArea == null) return;

                // 2. Tính toán kích thước tổng thực tế (Toàn bộ biểu đồ + Toàn bộ văn bản)
                int width = Math.max(cPanel.getWidth(), textArea.getWidth());
                int totalHeight = cPanel.getHeight() + textArea.getPreferredSize().height + 20;

                // 3. Tạo ảnh BufferedImage với chiều cao đầy đủ
                BufferedImage combinedImage = new BufferedImage(width, totalHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = combinedImage.createGraphics();

                // Đặt nền trắng cho toàn bộ ảnh
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, width, totalHeight);

                // 4. Vẽ biểu đồ vào phần trên của ảnh
                cPanel.paint(g2d);

                // 5. Dịch chuyển tọa độ Graphics để vẽ văn bản xuống phía dưới biểu đồ
                g2d.translate(0, cPanel.getHeight());

                // Vẽ toàn bộ nội dung JTextArea (không bị giới hạn bởi khung nhìn ScrollPane)
                textArea.setSize(textArea.getPreferredSize()); // Ép kích thước thực tế để vẽ hết chữ
                textArea.paint(g2d);

                g2d.dispose();

                // 6. Xuất file PNG
                ImageIO.write(combinedImage, "png", new File(fileName));
                System.out.println("ĐÃ LƯU ẢNH TRỌN VẸN (GỒM HẾT DANH SÁCH CUỘN) TẠI: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Lỗi lưu ảnh: " + e.getMessage());
            }
        });
    }

    // === BIỂU ĐỒ SO SÁNH THỜI GIAN – LƯU VÀO THƯ MỤC RIÊNG (GIỮ NGUYÊN ĐÃ ĐẸP) ===
    public static void showTimeComparisonChart() {
        List<List<Object>> data = ExcelWriter.readExcelData();

        if (data.isEmpty()) {
            System.out.println("Chưa có dữ liệu trong Excel để vẽ biểu đồ thời gian so sánh!");
            return;
        }

        List<String> testNames = new ArrayList<>();
        List<Long> times = new ArrayList<>();

        for (List<Object> row : data) {
            testNames.add((String) row.get(0));
            times.add((Long) row.get(2));
        }

        SwingUtilities.invokeLater(() -> {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < testNames.size(); i++) {
                dataset.addValue(times.get(i), "Thời gian chạy", testNames.get(i));
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "BIỂU ĐỒ THỜI GIAN THỰC HIỆN CỦA CÁC BỘ DỮ LIỆU",
                    "Bộ dữ liệu",
                    "Thời gian (milisecond)",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            // Làm đẹp biểu đồ
            chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
            chart.getCategoryPlot().setBackgroundPaint(Color.WHITE);
            chart.getCategoryPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);
            chart.getCategoryPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);

            ChartPanel panel = new ChartPanel(chart);
            panel.setPreferredSize(new Dimension(1200, 600));
            panel.setMouseWheelEnabled(true);

            JFrame frame = new JFrame("Biểu đồ thời gian thực hiện - So sánh các bộ dữ liệu");
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Lưu vào thư mục riêng
            try {
                String thuMucLuu = "SoSanh_BieuDo";
                java.io.File folder = new java.io.File(thuMucLuu);
                if (!folder.exists()) {
                    folder.mkdir();
                    System.out.println("Đã tạo thư mục lưu biểu đồ so sánh: " + thuMucLuu);
                }

                String fileName = thuMucLuu + "/BieuDo_ThoiGian_SoSanh_" +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png";

                org.jfree.chart.ChartUtils.saveChartAsPNG(new java.io.File(fileName), chart, 1400, 800);

                System.out.println("ĐÃ LƯU BIỂU ĐỒ THỜI GIAN SO SÁNH VÀO: " + fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}