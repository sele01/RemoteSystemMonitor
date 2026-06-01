package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseManager {

// Database connection details
private static final String URL =
        "jdbc:mysql://localhost:3306/resource_monitor";

private static final String USER = "root";

private static final String PASSWORD = "";

private DatabaseManager() {
    // Prevent object creation
}

public static void saveData(
        String machineName,
        double cpu,
        double memory,
        double disk
) throws Exception {

    String sql =
            "INSERT INTO system_stats " +
            "(machine_name, cpu_usage, memory_usage, disk_usage) " +
            "VALUES (?, ?, ?, ?)";

    // Open database connection and save system statistics
    try (
            Connection con =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            PreparedStatement pst =
                    con.prepareStatement(sql)
    ) {

        pst.setString(1, machineName);
        pst.setDouble(2, cpu);
        pst.setDouble(3, memory);
        pst.setDouble(4, disk);

        pst.executeUpdate();
    }
}


}
