package ihm.View;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.util.List;

import ihm.Controller.MakeOrderButtonController;
import ihm.Model.ClientModel;
import ihm.Model.DelivererModel;
import ihm.Model.PizzaModel;
import ihm.View.Component.Navbar;
import ihm.View.Component.Title;

public class MakeOrder extends JFrame {
    
    private JComboBox<String> clientComboBox;
    private JComboBox<String> pizzaComboBox;
    private JRadioButton smallSizeRadioButton;
    private JRadioButton largeSizeRadioButton;
    private JTextField quantityTextField;
    private JComboBox<String> deliveryPersonComboBox;

    private MakeOrderButtonController mButtonController;

    public MakeOrder() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RaPizz - Faire une commande");

        // this.mButtonController = new MakeOrderButtonController(this);


        // this.mButtonController = new MakeOrderButtonController(this, clientComboBox);
        // this.mButtonController = new MakeOrderButtonController(this, clientComboBox, pizzaComboBox, smallSizeRadioButton, largeSizeRadioButton, quantityTextField, deliveryPersonComboBox);

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

        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel clientLabel = new JLabel("Client:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(clientLabel, gbc);

        ClientModel cModel = new ClientModel();
        List<String> clientsList = cModel.getEveryClientsOnDB();
        clientsList.add(0, "Sélectionnez votre nom");

        String[] clientsArr = clientsList.toArray(new String[0]);
        this.clientComboBox = new JComboBox<>(clientsArr);
        gbc.gridx = 1;
        contentPanel.add(this.clientComboBox, gbc);


        JButton addClientButton = new JButton("Ajouter Client");
        gbc.gridx = 2;
        contentPanel.add(addClientButton, gbc);
        // addClientButton.addActionListener(mButtonController);

        JLabel pizzaLabel = new JLabel("Pizza:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(pizzaLabel, gbc);

        PizzaModel pModel = new PizzaModel();
        List<String> pizzaList = pModel.getEveryPizzaOnDB();
        pizzaList.add(0, "Sélectionnez votre pizza");

        String[] pizzasArr = pizzaList.toArray(new String[0]);
        this.pizzaComboBox = new JComboBox<>(pizzasArr);
        gbc.gridx = 1;
        contentPanel.add(this.pizzaComboBox, gbc);


        JLabel sizeLabel = new JLabel("Taille:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(sizeLabel, gbc);

        JPanel sizePanel = new JPanel();
        this.smallSizeRadioButton = new JRadioButton("Humaine");
        this.largeSizeRadioButton = new JRadioButton("Ogresse");
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(this.smallSizeRadioButton);
        sizeGroup.add(this.largeSizeRadioButton);
        sizePanel.add(this.smallSizeRadioButton);
        sizePanel.add(this.largeSizeRadioButton);
        gbc.gridx = 1;
        contentPanel.add(sizePanel, gbc);


        JLabel quantityLabel = new JLabel("Quantité:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(quantityLabel, gbc);

        this.quantityTextField = new JTextField(10);
        gbc.gridx = 1;
        contentPanel.add(this.quantityTextField, gbc);

        JLabel deliveryPersonLabel = new JLabel("Livreur:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPanel.add(deliveryPersonLabel, gbc);

        DelivererModel dModel = new DelivererModel();
        List<String> delivererList = dModel.getEveryDelivererOnDB();
        delivererList.add(0, "Séléctionnez votre livreur");

        String[] delivererArr = delivererList.toArray(new String[0]);
        this.deliveryPersonComboBox = new JComboBox<>(delivererArr);
        gbc.gridx = 1;
        contentPanel.add(this.deliveryPersonComboBox, gbc);

        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton sendOrderButton = new JButton("Envoyer la commande");
        buttonPanel.add(sendOrderButton);
        
        // this.mButtonController = new MakeOrderButtonController(this, clientComboBox);
        this.mButtonController = new MakeOrderButtonController(this, clientComboBox, pizzaComboBox, smallSizeRadioButton, largeSizeRadioButton, quantityTextField, deliveryPersonComboBox);
        addClientButton.addActionListener(mButtonController);
        sendOrderButton.addActionListener(mButtonController);

        this.setLayout(new BorderLayout());
        this.add(titleMenuPanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }
}
