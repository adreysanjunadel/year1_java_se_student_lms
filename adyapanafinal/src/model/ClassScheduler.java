/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClassScheduler {
    
    private static ClassScheduler instance;

    private ScheduledExecutorService scheduler;
    private Map<String, Integer> teacherMap;
    private Map<String, Integer> subjectMap;
    private Connection connection;

    public ClassScheduler() {
        teacherMap = new HashMap<>();
        subjectMap = new HashMap<>();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana", "root", "[1nTh3Night]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ClassScheduler getInstance() {
        if (instance == null) {
            instance = new ClassScheduler();
        }
        return instance;
    }

    public void startScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::scheduleFutureClasses, 0, 24, TimeUnit.HOURS);
    }

    public void stopScheduler() {
        scheduler.shutdown();
    }

    private void scheduleFutureClasses() {
        try {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Query for future classes
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `student_has_class` INNER JOIN `class`.`classno` = `student_has_class`.`classno`"
                    + " WHERE `class`.`date` >= ?");
            statement.setDate(1, java.sql.Date.valueOf(currentDate));
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and assign classes to students
            while (resultSet.next()) {
                int classNo = resultSet.getInt("classno");
                int teacherNo = resultSet.getInt("tno");
                int subjectNo = resultSet.getInt("subno");

                // Check if the teacher and subject exist in the maps
                if (teacherMap.containsKey(Integer.toString(teacherNo)) && subjectMap.containsKey(Integer.toString(subjectNo))) {
                    // Get the relevant students for the teacher and subject
                    PreparedStatement studentStatement = connection.prepareStatement("SELECT `sno` FROM `student`"
                            + "INNER JOIN `student_has_class` ON `student_has_class`.`student_sno` = `student`.`sno`"
                            + "INNER JOIN `class` ON `class`.`classno`  WHERE `class`.`tno` = ? AND `class`.`subno` = ?");
                    studentStatement.setInt(1, teacherNo);
                    studentStatement.setInt(2, subjectNo);
                    ResultSet studentResultSet = studentStatement.executeQuery();

                    // Insert the records into the student_has_class table
                    while (studentResultSet.next()) {
                        int studentNo = studentResultSet.getInt("sno");

                        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO `student_has_class` (`student_sno`, `class_classno`, `subno`) VALUES (?, ?, ?)");
                        insertStatement.setInt(1, studentNo);
                        insertStatement.setInt(2, classNo);
                        insertStatement.setInt(3, subjectNo);
                        insertStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTeacherMap() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT tno, first_name, last_name FROM `teacher`");

            while (resultSet.next()) {
                int teacherNo = resultSet.getInt("tno");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String fullName = firstName + " " + lastName;
                teacherMap.put(fullName, teacherNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadSubjectMap() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT subno, description FROM `subject`");

            while (resultSet.next()) {
                int subjectNo = resultSet.getInt("subno");
                String subjectDesc = resultSet.getString("description");
                subjectMap.put(subjectDesc, subjectNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentClassDetails(java.util.Date currentDate) {
        try {
            java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String dateString = format1.format(currentDate);

            java.text.SimpleDateFormat format2 = new java.text.SimpleDateFormat("HH:mm:ss");
            String timeString = format2.format(currentDate);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM `student_has_class` "
                    + "INNER JOIN `class` ON `class`.`classno` = `student_has_class`.`class_classno` WHERE `class`.`date` = ? AND `class`.`start_time` <= ? AND `class`.`end_time` >= ?");
            statement.setString(1, dateString);
            statement.setString(2, timeString);
            statement.setString(3, timeString);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int classNo = resultSet.getInt("classno");
                int teacherNo = resultSet.getInt("tno");
                int subjectNo = resultSet.getInt("subno");

                PreparedStatement teacherStatement = connection.prepareStatement("SELECT `first_name`, `last_name` FROM `teacher` WHERE `tno` = ?");
                teacherStatement.setInt(1, teacherNo);
                ResultSet teacherResultSet = teacherStatement.executeQuery();

                PreparedStatement subjectStatement = connection.prepareStatement("SELECT description FROM `subject` WHERE `subno` = ?");
                subjectStatement.setInt(1, subjectNo);
                ResultSet subjectResultSet = subjectStatement.executeQuery();

                if (teacherResultSet.next() && subjectResultSet.next()) {
                    String firstName = teacherResultSet.getString("first_name");
                    String lastName = teacherResultSet.getString("last_name");
                    String subjectDesc = subjectResultSet.getString("description");
                    String classCode = resultSet.getString("classno");

                    return subjectDesc + "," + resultSet.getString("start_time") + ","
                            + resultSet.getString("end_time") + "," + firstName + " " + lastName + "," + classCode;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        ClassScheduler scheduler = new ClassScheduler();
        scheduler.loadTeacherMap();
        scheduler.loadSubjectMap();
        scheduler.startScheduler();

        // Keep the application running
        try {
            Thread.sleep(60000); // Sleep for 1 minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.stopScheduler();
    }
}
