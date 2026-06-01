package server;

import gui.MonitorDashboard;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
 * Starts the RMI server, creates the dashboard,
 * and monitors client activity.
 */
public class ServerMain {

    public static void main(String[] args) {

        try {

            // Launch monitoring dashboard
            MonitorDashboard dashboard =
                    new MonitorDashboard();

            Registry registry;

            try {

                // Create a new registry if one does not already exist
                registry =
                        LocateRegistry.createRegistry(1099);

            } catch (Exception e) {

                // Use existing registry if available
                registry =
                        LocateRegistry.getRegistry(1099);
            }

            // Create remote service object
            MonitorServiceImpl service =
                    new MonitorServiceImpl();

            // Register service with the RMI registry
            registry.rebind(
                    "MonitorService",
                    service
            );

            System.out.println(
                    "RMI Server Started."
            );

            // Monitor client activity and shut down
            // if no data is received for 60 seconds
            Thread timeoutThread =
                    new Thread(() -> {

                        while(true) {

                            try {

                                Thread.sleep(10000);

                                long current =
                                        System.currentTimeMillis();

                                long difference =
                                        current
                                        - MonitorServiceImpl.lastClientTime;

                                if(difference > 60000) {

                                    System.out.println(
                                            "No clients connected for 60 seconds."
                                    );

                                    System.exit(0);
                                }

                            } catch(Exception ex) {

                                ex.printStackTrace();

                            }
                        }
                    });

            timeoutThread.start();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}