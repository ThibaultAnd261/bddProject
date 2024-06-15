package ihm.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import ihm.DB.ConnectionDB;

public class PizzaModel {
    private int idPizza;
    private String namePizza;
    private double pricePizza;
    private List<String> ingredientsPizza;
    private List<String> listPizza;

    ConnectionDB connectionDB = new ConnectionDB();
    Connection cnx;

    public PizzaModel(){
        this.ingredientsPizza = new ArrayList<>();
        this.listPizza = new ArrayList<>();
    }

    public PizzaModel(int idPizza){
        this.idPizza = idPizza;
        this.ingredientsPizza = new ArrayList<>();
        this.listPizza = new ArrayList<>();
    }

    public int getPizzaId(){
        return this.idPizza;
    }

    public String getPizzaString(){
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT nom FROM pizzas WHERE id=?");
            pst.setInt(1, this.idPizza);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                this.namePizza = rset.getString(1);
            }

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return this.namePizza;
    }

    public double getPizzaPrice(){
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT prix_base FROM pizzas WHERE id=?");
            pst.setInt(1, this.idPizza);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                this.pricePizza = rset.getDouble(1);
            }

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return this.pricePizza;
    }

    public double getPizzaPrice(String namePizza){
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT prix_base FROM pizzas WHERE nom=?");
            pst.setString(1, namePizza);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                this.pricePizza = rset.getDouble(1);
            }

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return this.pricePizza;
    }

    public List<String> getPizzaIngredients() {
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT i.nom FROM ingrédients i JOIN pizzas_ingrédients pi ON i.id = pi.ingredient_id WHERE pi.pizza_id = ?");
            pst.setInt(1, this.idPizza);
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                ingredientsPizza.add(rset.getString(1));
            }

        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return ingredientsPizza;
    }

    public List<String> getEveryPizzaOnDB(){
        this.cnx = connectionDB.connect();

        try {
            PreparedStatement pst = cnx.prepareStatement("SELECT nom FROM pizzas");
            ResultSet rset = pst.executeQuery();

            while (rset.next()) {
                listPizza.add(rset.getString(1));
            }
        } catch (Exception e) {
            System.err.println("ERREUR STATEMENT : " + e.getMessage());
        }

        connectionDB.closeConnection(cnx);

        return listPizza;
    }

    
}
