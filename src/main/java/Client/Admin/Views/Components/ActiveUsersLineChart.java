package Client.Admin.Views.Components;

import Client.Admin.Repository.SessionRepository;

import java.util.*;

public class ActiveUsersLineChart extends BaseLineChart {

    public ActiveUsersLineChart(String title, String xLabel, String yLabel) {
        super(title, xLabel, yLabel);
    }
    @Override
    protected void updateDataPoints() {
        dataPoints = new ArrayList<>();

        // Get the sessions for the selected year
        List<Map<String, Object>> sessions = sessionRepository.getSessionsForYear(year);

        // Create a map to store the count of active users for each month
        Map<Integer, Set<String>> activeUsersPerMonth = new HashMap<>();

        // Iterate over each session
        for (Map<String, Object> session : sessions) {
            Date loginTime = (Date) session.get("logintime");
            Date logoutTime = (Date) session.get("logouttime");

            Calendar cal = Calendar.getInstance();
            cal.setTime(loginTime);

            // Iterate for each month between logintime and logouttime (inclusive)
            while (cal.getTime().before(logoutTime) || cal.getTime().equals(logoutTime)) {
                int month = cal.get(Calendar.MONTH) + 1; // Calendar months are 0-based

                // Get or create the set for the month
                Set<String> usersForMonth = activeUsersPerMonth.computeIfAbsent(month, k -> new HashSet<>());

                // Add the user to the set (only if not already counted for this month)
                usersForMonth.add((String) session.get("username"));

                cal.add(Calendar.MONTH, 1); // Move to the next month
            }

        }

        // Convert the map entries to a list of entries for plotting
        for (Map.Entry<Integer, Set<String>> entry : activeUsersPerMonth.entrySet()) {
            dataPoints.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().size()));
        }
    }



}
