package ihm.View;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

import ihm.Controller.AddToDBPageController;
import ihm.View.Component.Navbar;
import ihm.View.Component.Title;

public class AddToDBPage extends JFrame{
    private String typeAdd;
    private JLabel mainLabel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JTextField soldeTextField;
    private JButton submitButton;

    private AddToDBPageController aDbPageController;

    public AddToDBPage(String typeAdd) {
        this.typeAdd = typeAdd;

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RaPizz - Ajout " + getStringTypeAddForLabel());

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

        this.add(titleMenuPanel, BorderLayout.NORTH);

        // ---------
        // Content

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        // Main Label
        mainLabel = new JLabel("Ajout " + getStringTypeAddForLabel(), JLabel.CENTER);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(mainLabel, gbc);

        // Name Label
        nameLabel = new JLabel(getNameLabelForType(), JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(nameLabel, gbc);

        // Name Text Field
        nameTextField = new JTextField(20);
        gbc.gridx = 1;
        contentPanel.add(nameTextField, gbc);

        if (typeAdd.equals("Client")) {
            JLabel soldeLabel = new JLabel("Solde du compte:", JLabel.CENTER);
            soldeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPanel.add(soldeLabel, gbc);
        
            this.soldeTextField = new JTextField(20);
            gbc.gridx = 1;
            contentPanel.add(this.soldeTextField, gbc);
        }

        // Add content panel to the center of the frame
        this.add(contentPanel, BorderLayout.CENTER);

        // ---------
        // Footer

        this.aDbPageController = new AddToDBPageController(this, this.nameTextField, this.soldeTextField, typeAdd);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton = new JButton("Envoyer la requête");
        submitButton.addActionListener(aDbPageController);
        buttonPanel.add(submitButton);

        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private String getStringTypeAddForLabel() {
        if (typeAdd.equals("Client")) {
            return "d'un client";
        }

        return "";
    }

    private String getNameLabelForType() {
        if (typeAdd.equals("Client")) {
            return "Le nom du nouveau client:";
        }

        return "";
    }
}
