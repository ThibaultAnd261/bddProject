package ihm.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ihm.DB.ConnectionDB;

public class StatsModel {

    public StatsModel(){

    }

    private ConnectionDB connectionDB = new ConnectionDB();
    private Connection cnx;

    public int getTotalOrders() {
        int totalOrders = 0;
        this.cnx = connectionDB.connect();

        String query = "SELECT COUNT(*) FROM Commandes";

        try {
            PreparedStatement pst = cnx.prepareStatement(query); 
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                totalOrders = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETTOTALORDERS(): " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return totalOrders;
    }

    public double getTotalRevenue() {
        double totalRevenue = 0;
        Connection cnx = connectionDB.connect();

        String query = "SELECT SUM(p.prix_base * pc.quantité) FROM Pizzas_Commandes pc JOIN Pizzas p ON pc.pizza_id = p.id";
        try (PreparedStatement pst = cnx.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETTOTALREVENUE(): " + e.getMessage());
        } finally {
            connectionDB.closeConnection(cnx);
        }

        return totalRevenue;
    }

    public ResultSet getDeliveriesByPerson() {
        Connection cnx = connectionDB.connect();

        String query = "SELECT l.nom, COUNT(*) AS deliveries FROM Commandes c JOIN Livreurs l ON c.livreur_id = l.id GROUP BY l.nom";
        ResultSet rs = null;

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETDELIVERIESBYPERSON(): " + e.getMessage());
        }

        return rs;
    }

    public ResultSet getOrdersByClient() {
        Connection cnx = connectionDB.connect();

        String query = "SELECT c.nom, COUNT(*) AS orders FROM Commandes cm JOIN Clients c ON cm.client_id = c.id GROUP BY c.nom";
        ResultSet rs = null;

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETORDERSBYCLIENT(): " + e.getMessage());
        }

        return rs;
    }

    public ResultSet getLastOrders() {
        Connection cnx = connectionDB.connect();

        String query = "SELECT cm.id, cl.nom AS client, lv.nom AS livreur, cm.date, cm.retard " +
                       "FROM Commandes cm " +
                       "JOIN Clients cl ON cm.client_id = cl.id " +
                       "JOIN Livreurs lv ON cm.livreur_id = lv.id " +
                       "ORDER BY cm.date DESC";
        ResultSet rs = null;
        
        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETLASTORDERS(): " + e.getMessage());
        }

        return rs;
    }
}
