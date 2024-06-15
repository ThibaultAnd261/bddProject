package ihm.Model;

import java.io.EOFException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ihm.DB.ConnectionDB;

public class CommandModel {

    ConnectionDB connectionDB = new ConnectionDB();
    Connection cnx;

    public CommandModel() {

    }

    public void addCommandToDB(String clientName, String delivererName) {
        this.cnx = connectionDB.connect();
        String query = "INSERT INTO Commandes (client_id, livreur_id, vehicule_id, date, retard) VALUES (?, ?, ?, ?, ?)";

        ClientModel clientModel = new ClientModel();
        int clientId = clientModel.getClientIdFromName(clientName);
        // System.out.println(clientId); // WORK

        DelivererModel delivererModel = new DelivererModel();
        int delivererId = delivererModel.getDelivererIdFromName(delivererName);
        // System.out.println("delivererId : " + delivererId); // WORK

        try {
            PreparedStatement pst = cnx.prepareStatement(query);

            pst.setInt(1, clientId);
            pst.setInt(2, delivererId);
            pst.setInt(3, 1);
            pst.setDate(4, new Date(System.currentTimeMillis()));
            pst.setInt(5, 0);

            pst.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }

        connectionDB.closeConnection(cnx);
    }

    public void addPizzaCommandToDB(String pizzaName, String size, int quantity) {
        this.cnx = connectionDB.connect();

        String selectPizzaSQL = "SELECT id FROM Pizzas WHERE nom = ?";
        String insertPizzasCommandesSQL = "INSERT INTO Pizzas_Commandes (commande_id, pizza_id, taille, quantité) VALUES (?, ?, ?, ?)";

        try {
            // Récupérer l'ID de la pizza
            PreparedStatement pstPizza = cnx.prepareStatement(selectPizzaSQL);
            pstPizza.setString(1, pizzaName);
            ResultSet rsPizza = pstPizza.executeQuery();
            int pizzaId = 0;
            if (rsPizza.next()) {
                pizzaId = rsPizza.getInt("id");
            }

            // Récupérer l'ID de la dernière commande insérée
            int commandeId = getLastInsertedCommandeId();

            if (commandeId == -1) {
                throw new SQLException("ERREUR RETRIEVE LAST COMMANDID");
            }

            // Insérer la pizza dans la commande
            PreparedStatement pstPizzasCommandes = cnx.prepareStatement(insertPizzasCommandesSQL);
            pstPizzasCommandes.setInt(1, commandeId);
            pstPizzasCommandes.setInt(2, pizzaId);
            pstPizzasCommandes.setString(3, size);
            pstPizzasCommandes.setInt(4, quantity);
            pstPizzasCommandes.executeUpdate();

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT ADDPIZZACOMMANDTODB(): " + e.getMessage());
        }
        connectionDB.closeConnection(cnx);
    }

    private int getLastInsertedCommandeId() {
        int commandeId = -1;

        String query = "SELECT id FROM Commandes ORDER BY id DESC LIMIT 1";
        try (PreparedStatement pst = cnx.prepareStatement(query); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                commandeId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT GETLASTINSERTEDCOMMANDEID(): " + e.getMessage());
        }

        return commandeId;
    }
}
