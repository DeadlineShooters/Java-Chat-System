package Client.Admin.Views.Components;

import Client.Models.User;
import Client.Admin.Repository.UserRepository;
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
import java.sql.Date;


public class LineChart extends BaseLineChart {

    public LineChart(String title, String xLabel, String yLabel) {
        super(title, xLabel, yLabel);

    }
    @Override
    protected void updateDataPoints() {
        // Fetch user registrations data for the selected year from the database
        Date startDate = Date.valueOf(year + "-01-01");
        Date endDate = Date.valueOf(year + "-12-31");

        ArrayList<User> userList = userRepository.getUsersByDateRange(startDate, endDate);
        Map<Integer, Integer> registrationsPerMonth = new HashMap<>();

        // Count new user registrations per month
        for (User user : userList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(user.createdAt());
            int month = cal.get(Calendar.MONTH) + 1; // Month starts from 0, so add 1
            registrationsPerMonth.put(month, registrationsPerMonth.getOrDefault(month, 0) + 1);
        }

        // Update dataPoints based on fetched data
        dataPoints = new ArrayList<>(registrationsPerMonth.entrySet());
        dataPoints.sort(Entry.comparingByKey()); // Sort by month
    }


}
