package ihm.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ihm.DB.ConnectionDB;

public class DelivererModel {
    private List<String> listDeliverer;

    ConnectionDB connectionDB = new ConnectionDB();
    Connection cnx;

    public DelivererModel() {
        this.listDeliverer = new ArrayList<>();
    }

    public int getDelivererIdFromName(String delivererName) {
        int delivererId = -1;
        this.cnx = connectionDB.connect();

        String query = "SELECT id FROM Livreurs WHERE nom = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, delivererName);
            try (ResultSet rset = pst.executeQuery()) {
                if (rset.next()) {
                    delivererId = rset.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
            return -1;
        } finally {
            connectionDB.closeConnection(cnx);
        }

        return delivererId;
    }

    public List<String> getEveryDelivererOnDB() {
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT nom FROM livreurs");
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                listDeliverer.add(rset.getString(1));
            }
        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return listDeliverer;
    }
}
