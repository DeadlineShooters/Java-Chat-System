package Client.Admin.Views.Components;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Map.Entry;

import Client.Admin.Repository.SessionRepository;
import Client.Admin.Repository.UserRepository;
import com.mysql.cj.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public abstract class BaseLineChart extends JPanel {
    protected UserRepository userRepository;
    protected SessionRepository sessionRepository;
    protected int year;
    protected ArrayList<Map.Entry<Integer, Integer>>dataPoints;
    private ChartPanel chartPanel;
    private JPanel yearPanel;
    private String title;
    private String xLabel;
    private String yLabel;

    protected abstract void updateDataPoints();

    public BaseLineChart(String title, String xLabel, String yLabel) {
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        userRepository = new UserRepository();
        sessionRepository = new SessionRepository();

        initUI();
    }
    private void initUI() {
        yearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<Integer> yearComboBox = new JComboBox<>(generateYearList(userRepository.getOldestYear(), Calendar.getInstance().get(Calendar.YEAR)));
        yearComboBox.setMaximumSize(yearComboBox.getPreferredSize());
        yearComboBox.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR)); // Set current year as default
        year = (int) (Integer) yearComboBox.getSelectedItem();

        yearComboBox.addActionListener(e -> {
            year = (int) (Integer) yearComboBox.getSelectedItem();
            updateChart();
        });


        yearPanel.add(yearComboBox);
        add(yearPanel, BorderLayout.NORTH);

        updateDataPoints();

        // add initial data
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel, BorderLayout.CENTER);
    }

    protected Integer[] generateYearList(int startYear, int endYear) {
        int numberOfYears = endYear - startYear + 1;
        Integer[] years = new Integer[numberOfYears];
        for (int i = 0; i < numberOfYears; i++) {
            years[i] = startYear + i;
        }
        return years;
    }

    private XYDataset createDataset() {

        var series = new XYSeries(year);

        for (Map.Entry<Integer, Integer> entry : dataPoints){
            series.add(entry.getKey(), entry.getValue());
        }
        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        // Get the x-axis of the plot
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();

// Set the range of the x-axis from 1 to 12 (integer values only)
        domainAxis.setRange(1, 12);

        domainAxis.setTickUnit(new NumberTickUnit(1));


        var renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle(title,
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }

    private void updateChart() {
        updateDataPoints();
        chartPanel.setChart(createChart(createDataset()));

        removeAll();
        add(yearPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
