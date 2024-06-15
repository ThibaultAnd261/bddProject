package ihm.DB;

import java.sql.*;

public class ConnectionDB {
    public Connection connect(){
        try {
            // System.out.println("Initialisation de la connexion");
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/bddproject_esiee", "root", "mdp");
                return cnx;
            } catch (Exception e) {
                System.err.println("Pb connexion à la BDD : " +e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Pb initialisation connexion : " +e.getMessage());
        }

        throw new NullPointerException();
    }

    public void closeConnection(Connection cnx){
        try {
            cnx.close();
        } catch (Exception e) {
            System.err.println("Pb fermeture connexion : " +e.getMessage());
        }
    }
}
