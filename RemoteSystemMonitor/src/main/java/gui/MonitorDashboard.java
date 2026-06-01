package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;

public class MonitorDashboard extends JFrame {

private static DefaultTableModel model;

private static JLabel clientCountLabel;

// Stores machine names and their corresponding table row
private static HashMap<String, Integer> machineRows =
        new HashMap<>();

public MonitorDashboard() {

    setTitle("Remote System Performance Monitor");
    setSize(900, 550);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel mainPanel =
            new JPanel(new BorderLayout());

    mainPanel.setBackground(
            new Color(245,247,250)
    );

    // Header section
    JPanel headerPanel =
            new JPanel();

    headerPanel.setBackground(
            new Color(41,128,185)
    );

    headerPanel.setPreferredSize(
            new Dimension(900,80)
    );

    JLabel titleLabel =
            new JLabel(
                    "Remote System Performance & Resource Monitor"
            );

    titleLabel.setForeground(
            Color.WHITE
    );

    titleLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    24
            )
    );

    headerPanel.add(titleLabel);

    // Table column names
    String[] columns = {
            "Machine Name",
            "CPU %",
            "Memory %",
            "Disk %"
    };

    model =
            new DefaultTableModel(
                    columns,
                    0
            );

    JTable table =
            new JTable(model);

    table.setRowHeight(30);

    JScrollPane scrollPane =
            new JScrollPane(table);

    // Footer section displaying connected clients
    JPanel footerPanel =
            new JPanel(
                    new FlowLayout(
                            FlowLayout.LEFT
                    )
            );

    clientCountLabel =
            new JLabel(
                    "Connected Machines: 0"
            );

    clientCountLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    14
            )
    );

    footerPanel.add(clientCountLabel);

    mainPanel.add(
            headerPanel,
            BorderLayout.NORTH
    );

    mainPanel.add(
            scrollPane,
            BorderLayout.CENTER
    );

    mainPanel.add(
            footerPanel,
            BorderLayout.SOUTH
    );

    add(mainPanel);

    setVisible(true);
}

// Adds new machine data or updates existing machine statistics
public static void addData(
        String machine,
        double cpu,
        double memory,
        double disk
) {

    if(machineRows.containsKey(machine)) {

        int row =
                machineRows.get(machine);

        // Update existing machine values
        model.setValueAt(
                cpu + "%",
                row,
                1
        );

        model.setValueAt(
                memory + "%",
                row,
                2
        );

        model.setValueAt(
                disk + "%",
                row,
                3
        );

    }
    else {

        // Add a new machine to the table
        model.addRow(
                new Object[]{
                        machine,
                        cpu + "%",
                        memory + "%",
                        disk + "%"
                }
        );

        int rowIndex =
                model.getRowCount() - 1;

        machineRows.put(
                machine,
                rowIndex
        );

        // Update connected machine count
        clientCountLabel.setText(
                "Connected Machines: "
                        + machineRows.size()
        );
    }
}

}
