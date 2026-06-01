package client;

import rmi.MonitorService;
import util.SystemInfo;

import javax.swing.JOptionPane;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain {

public static void main(String[] args) {

    try {

        // Ask the user to enter a machine name
        String machineName =
                JOptionPane.showInputDialog(
                        null,
                        "Enter Machine Name"
                );

        // Close the application if no machine name is provided
        if(machineName == null ||
           machineName.trim().isEmpty()) {

            System.exit(0);
        }

        // Connect to the RMI registry
        Registry registry =
                LocateRegistry.getRegistry(
                        "localhost",
                        1099
                );

        // Look up the remote monitoring service
        MonitorService service =
                (MonitorService) registry.lookup(
                        "MonitorService"
                );

        // Thread responsible for collecting and sending system data
        Thread monitoringThread =
                new Thread(() -> {

                    while (true) {

                        try {

                            // Retrieve current system statistics
                            double cpu =
                                    SystemInfo.getCPUUsage();

                            double memory =
                                    SystemInfo.getMemoryUsage();

                            double disk =
                                    SystemInfo.getDiskUsage();

                            // Send statistics to the server
                            service.sendSystemData(
                                    machineName,
                                    cpu,
                                    memory,
                                    disk
                            );

                            System.out.println(
                                    "Data Sent: "
                                            + machineName
                            );

                            // Send updates every 5 seconds
                            Thread.sleep(5000);

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    null,
                                    "Server Connection Lost.\nApplication will close."
                            );

                            System.exit(0);
                        }
                    }
                });

        monitoringThread.start();

        JOptionPane.showMessageDialog(
                null,
                "Monitoring Started For "
                        + machineName
        );

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                null,
                "Cannot Connect To Server.\nApplication will close."
        );

        System.exit(0);
    }
}

}
