package ihm.View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import ihm.Model.StatsModel;
import ihm.View.Component.Navbar;
import ihm.View.Component.Title;

public class Stats extends JFrame {
    public Stats() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RaPizz - Statistiques");

        // ---------
        // Header

        // Titre
        JPanel titlePanel = new Title();

        // Menu de navigation
        JPanel navbarPanel = new Navbar(this);

        // Titre + navbar
        JPanel titleMenuPanel = new JPanel(new BorderLayout());
        titleMenuPanel.add(titlePanel, BorderLayout.NORTH);
        titleMenuPanel.add(navbarPanel, BorderLayout.CENTER);

        // Body
        JPanel statsPanel = new JPanel(new GridLayout(3, 1));

        StatsModel statsModel = new StatsModel();
        int totalOrders = statsModel.getTotalOrders();
        double totalRevenue = statsModel.getTotalRevenue();
        ResultSet deliveriesByPerson = statsModel.getDeliveriesByPerson();
        ResultSet ordersByClient = statsModel.getOrdersByClient();

        // Total orders
        JLabel totalOrdersLabel = new JLabel("Nombre total de commandes: " + totalOrders);
        statsPanel.add(totalOrdersLabel);

        JLabel totalRevenueLabel = new JLabel("Somme ramassée par l'entreprise: " + totalRevenue + " €");
        statsPanel.add(totalRevenueLabel);

        String[] columnNamesLivr = { "Livreur", "Nombre de livraisons" };
        JTable deliveriesTableLivr = createTableFromResultSet(deliveriesByPerson, columnNamesLivr);
        JScrollPane scrollPaneLivr = new JScrollPane(deliveriesTableLivr);
        statsPanel.add(scrollPaneLivr);

        String[] columnNamesClient = { "Client", "Nombre de commandes" };
        JTable deliveriesTableClient = createTableFromResultSet(ordersByClient, columnNamesClient);
        JScrollPane scrollPaneClient = new JScrollPane(deliveriesTableClient);
        statsPanel.add(scrollPaneClient);


        this.add(titleMenuPanel, BorderLayout.NORTH);
        this.add(statsPanel, BorderLayout.CENTER);
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
