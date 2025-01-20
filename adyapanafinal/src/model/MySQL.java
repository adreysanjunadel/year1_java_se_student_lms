package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQL {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adyapana", "root", "[1nTh3Night]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String query) throws Exception {
        PreparedStatement statement = connection.prepareStatement(query);
        if (query.startsWith("SELECT")) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } else {
            int result = statement.executeUpdate();
            return null;
        }
    }

    public static ResultSet executePreparedStatement(String query, Object... parameters) throws Exception {
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < parameters.length; i++) {
            statement.setObject(i + 1, parameters[i]);
        }
        if (query.startsWith("SELECT")) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } else {
            int result = statement.executeUpdate();
            return null;
        }
    }
}
