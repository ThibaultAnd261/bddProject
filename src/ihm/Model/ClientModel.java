package ihm.Model;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import ihm.DB.ConnectionDB;

public class ClientModel {
    private int idClient;
    private String nameClient;
    private List<String> clients;

    ConnectionDB connectionDB = new ConnectionDB();
    Connection cnx;

    public ClientModel() {
        this.clients = new ArrayList<>();
    }

    public ClientModel(int idClient) {
        this.idClient = idClient;
        this.clients = new ArrayList<>();
    }

    public ClientModel(String nameClient){
        this.nameClient = nameClient;
    }

    public int getClientIdFromName(String clientName) {
        int clientId = -1;
        this.cnx = connectionDB.connect();
    
        String query = "SELECT id FROM Clients WHERE nom = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, clientName);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    clientId = rset.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
            return -1;
        } finally {
            connectionDB.closeConnection(cnx);
        }
    
        return clientId;
    }

    public BigDecimal getClientBalance(String clientName) {
        BigDecimal balance = BigDecimal.ZERO;
        this.cnx = connectionDB.connect();

        String query = "SELECT solde_compte FROM Clients WHERE nom = ?";

        try {
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, clientName);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    balance = rset.getBigDecimal("solde_compte");
                }
            }
        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT GETCLIENTBALANCE() : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return balance;
    }
    

    public List<String> getEveryClientsOnDB() {
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT nom FROM clients");
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                clients.add(rset.getString(1));
            }

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return clients;
    }

    public void addClientToDB(String clientName, String soldeCompte, JFrame frame) {
        this.cnx = connectionDB.connect();
        String insertSQL = "INSERT INTO Clients (nom, solde_compte) VALUES (?, ?)";

        try {
            BigDecimal soldeCompteDecimal = new BigDecimal(soldeCompte);
            
            PreparedStatement pst = cnx.prepareStatement(insertSQL);
            pst.setString(1, clientName);
            pst.setBigDecimal(2, soldeCompteDecimal);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Client ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout du client: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        } finally {
            connectionDB.closeConnection(cnx);
        }
        connectionDB.closeConnection(cnx);
    }

    public void updateClientBalance(String clientName, BigDecimal newBalance) {
        this.cnx = connectionDB.connect();
        String updateSQL = "UPDATE Clients SET solde_compte = ? WHERE nom = ?";
    
        try {
            PreparedStatement pst = cnx.prepareStatement(updateSQL);
            pst.setBigDecimal(1, newBalance);
            pst.setString(2, clientName);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du solde du client : " + e.getMessage());
        }
        
        connectionDB.closeConnection(cnx);
    }
}
