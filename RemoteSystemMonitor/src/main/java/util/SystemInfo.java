package util;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.lang.management.ManagementFactory;

public class SystemInfo {


// Used to access operating system statistics
private static final OperatingSystemMXBean OS_BEAN =
        (OperatingSystemMXBean)
                ManagementFactory.getOperatingSystemMXBean();

private SystemInfo() {
    // Utility class
}

// Returns current CPU usage percentage
public static double getCPUUsage() {

    double cpu = OS_BEAN.getCpuLoad();

    return Math.round(cpu * 1000.0) / 10.0;
}

// Calculates memory usage percentage
public static double getMemoryUsage() {

    long totalMemory =
            OS_BEAN.getTotalMemorySize();

    long freeMemory =
            OS_BEAN.getFreeMemorySize();

    double usedMemoryPercentage =
            ((double) (totalMemory - freeMemory)
                    / totalMemory) * 100;

    return Math.round(
            usedMemoryPercentage * 10.0
    ) / 10.0;
}

// Calculates disk usage percentage
public static double getDiskUsage() {

    File disk =
            File.listRoots()[0];

    long totalSpace =
            disk.getTotalSpace();

    long freeSpace =
            disk.getFreeSpace();

    double usedDiskPercentage =
            ((double) (totalSpace - freeSpace)
                    / totalSpace) * 100;

    return Math.round(
            usedDiskPercentage * 10.0
    ) / 10.0;
}


}
