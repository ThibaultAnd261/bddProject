package ihm.View;

import javax.swing.*;

import ihm.View.Component.ListPizza;
import ihm.View.Component.Navbar;
import ihm.View.Component.Title;

import java.awt.*;

public class Accueil extends JFrame{
        
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public Accueil() {
        // Configuration de la fenêtre principale
        this.setSize(800, 600); // Augmenter la hauteur de la fenêtre
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RaPizz - Accueil");

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

        // --------- 
        // Content

        // Panel principal avec CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ajout des cartes
        JPanel choixPizzasPanel = new ListPizza();
        cardPanel.add(choixPizzasPanel, "Choix des pizzas");


        this.setLayout(new BorderLayout());
        this.add(titleMenuPanel, BorderLayout.NORTH);
        this.add(cardPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
