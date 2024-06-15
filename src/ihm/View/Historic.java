package ihm.View;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

import ihm.Model.StatsModel;
import ihm.View.Component.Navbar;
import ihm.View.Component.Title;

public class Historic extends JFrame {

    public Historic() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RaPizz - Historique des commandes");

        // Header
        JPanel titlePanel = new Title();
        JPanel navbarPanel = new Navbar(this);
        JPanel titleMenuPanel = new JPanel(new BorderLayout());
        titleMenuPanel.add(titlePanel, BorderLayout.NORTH);
        titleMenuPanel.add(navbarPanel, BorderLayout.CENTER);
        this.add(titleMenuPanel, BorderLayout.NORTH);

        // Body
        JPanel historyPanel = new JPanel(new BorderLayout());

        StatsModel statsModel = new StatsModel();
        ResultSet lastOrders = statsModel.getLastOrders();

        // Last orders table
        JTable ordersTable = createTableFromResultSet(lastOrders, new String[] { "ID", "Client", "Livreur", "Date", "Retard" });
        JScrollPane scrollPaneOrders = new JScrollPane(ordersTable);
        historyPanel.add(scrollPaneOrders, BorderLayout.CENTER);

        this.add(historyPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JTable createTableFromResultSet(ResultSet rs, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        try {
            while (rs.next()) {
                Object[] rowData = new Object[columnNames.length];
                for (int i = 0; i < columnNames.length; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            System.err.println("ERREUR LORS DE LA LECTURE DU RESULTSET: " + e.getMessage());
        }

        return new JTable(tableModel);
    }
}
