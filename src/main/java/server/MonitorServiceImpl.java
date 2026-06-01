package server;

import database.DatabaseManager;
import gui.MonitorDashboard;
import rmi.MonitorService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * Implementation of the remote monitoring service.
 * Receives system statistics from clients,
 * stores them in the database, and updates the dashboard.
 */
public class MonitorServiceImpl extends UnicastRemoteObject
        implements MonitorService {

    // Stores the last time data was received from any client
    public static volatile long lastClientTime =
            System.currentTimeMillis();

    public MonitorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void sendSystemData(
            String machineName,
            double cpuUsage,
            double memoryUsage,
            double diskUsage
    ) throws RemoteException {

        // Update last communication time
        lastClientTime =
                System.currentTimeMillis();

        System.out.println("\n===== DATA RECEIVED =====");
        System.out.println("Machine : " + machineName);
        System.out.println("CPU     : " + cpuUsage + "%");
        System.out.println("Memory  : " + memoryUsage + "%");
        System.out.println("Disk    : " + diskUsage + "%");
        System.out.println("=========================");

        try {

            // Save received data into the database
            DatabaseManager.saveData(
                    machineName,
                    cpuUsage,
                    memoryUsage,
                    diskUsage
            );

            // Update the monitoring dashboard
            MonitorDashboard.addData(
                    machineName,
                    cpuUsage,
                    memoryUsage,
                    diskUsage
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}