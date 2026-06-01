package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Remote interface used by clients to send
 * system performance data to the server.
 */
public interface MonitorService extends Remote {

    void sendSystemData(
            String machineName,
            double cpuUsage,
            double memoryUsage,
            double diskUsage
    ) throws RemoteException;

}