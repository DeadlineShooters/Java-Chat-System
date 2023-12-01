package Client.Admin.Views.Components;

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

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Map.Entry;

public class LineChart extends JPanel {
    private int year;
    private ArrayList<Map.Entry<Integer, Integer>>dataPoints;
    private ChartPanel chartPanel;
    private JPanel yearPanel;

    public LineChart() {
        initUI();
    }

    private void initUI() {
        yearPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<Integer> yearComboBox = new JComboBox<>(generateYearList(2020, Calendar.getInstance().get(Calendar.YEAR)));
        yearComboBox.setMaximumSize(yearComboBox.getPreferredSize());
        yearComboBox.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR)); // Set current year as default
        year = (int) (Integer) yearComboBox.getSelectedItem();

        yearComboBox.addActionListener(e -> {
            year = (int) (Integer) yearComboBox.getSelectedItem();
            updateChart();
        });


        yearPanel.add(yearComboBox);
        add(yearPanel, BorderLayout.NORTH);

        dataPoints = new ArrayList<>();
        // add data
        for (int i = 1; i <= 12; ++i){
            Map.Entry<Integer,Integer> pair=new AbstractMap.SimpleEntry<>(i,i);
            dataPoints.add(pair);
        }

        // add initial data
        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel, BorderLayout.CENTER);


    }

    private void updateChart() {
        // Update the chart with the selected year
        dataPoints.clear();
        for (int i = 1; i <= 12; ++i) {
            Map.Entry<Integer, Integer> pair = new AbstractMap.SimpleEntry<>(i, i);
            dataPoints.add(pair);
        }
        // Refresh the chart with the new data for the selected year

        chartPanel.setChart(createChart(createDataset()));

        removeAll();
        add(yearPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private Integer[] generateYearList(int startYear, int endYear) {
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
//        series.add(18, 567);
//        series.add(20, 612);
//        series.add(25, 800);
//        series.add(30, 980);
//        series.add(40, 1410);
//        series.add(50, 2350);
//
        var dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Newly registered users per year",
                "Month",
                "The number of new registrations",
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

        chart.setTitle(new TextTitle("Newly Registered Users Per Year",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }


}