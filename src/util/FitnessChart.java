package util;

import algorithm.GeneticAlgorithm;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FitnessChart extends ApplicationFrame {

    public FitnessChart(String title, GeneticAlgorithm ga) {
        super(title);

        XYSeries series = new XYSeries("Fitness tốt nhất qua các thế hệ");

        List<Integer> generations = ga.getGenerationLog();
        List<Double> fitnessLog = ga.getBestFitnessLog();

        // Chỉ lấy các điểm khả thi (fitness >= 0) để đồ thị đẹp hơn
        // Nếu bạn muốn thấy cả vùng phạt thì bỏ điều kiện if đi
        for (int i = 0; i < generations.size(); i++) {
            double fitness = fitnessLog.get(i);
            if (fitness >= 0) {
                series.add((double) generations.get(i), fitness); // ép kiểu Integer → double
            }
        }

        // Nếu không có điểm khả thi nào → vẫn vẽ toàn bộ
        if (series.getItemCount() == 0) {
            for (int i = 0; i < generations.size(); i++) {
                series.add(generations.get(i), fitnessLog.get(i));
            }
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "QUÁ TRÌNH HỘI TỤ CỦA THUẬT TOÁN DI TRUYỀN (KNAPSACK 0/1)",
                "Thế hệ",
                "Fitness (Giá trị tối ưu)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Tùy chỉnh đẹp hơn
        chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 16));
        chart.getXYPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().setRangeGridlinePaint(Color.LIGHT_GRAY);
        chart.getXYPlot().setDomainGridlinePaint(Color.LIGHT_GRAY);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 600));
        chartPanel.setMouseWheelEnabled(true);

        setContentPane(chartPanel);
    }

    // Gọi hàm này sau khi GA chạy xong
    public static void showChart(GeneticAlgorithm ga) {
        SwingUtilities.invokeLater(() -> {
            FitnessChart chart = new FitnessChart("Biểu đồ hội tụ - Genetic Algorithm Knapsack", ga);
            chart.pack();
            chart.setLocationRelativeTo(null); // giữa màn hình
            chart.setVisible(true);
            chart.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        });
    }
}