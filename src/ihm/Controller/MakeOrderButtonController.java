package ihm.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import ihm.Model.ClientModel;
import ihm.Model.CommandModel;
import ihm.Model.PizzaModel;
import ihm.View.AddToDBPage;

public class MakeOrderButtonController implements ActionListener {
    private JFrame frame;
    private JComboBox<String> clientComboBox;
    private JComboBox<String> pizzaComboBox;
    private JRadioButton smallSizeRadioButton;
    private JRadioButton largeSizeRadioButton;
    private JTextField quantityTextField;
    private JComboBox<String> deliveryPersonComboBox;

    private String str;
    private String clientName;
    private String pizzaName;
    private String size;
    private int quantity;
    private String delivererName;

    // public MakeOrderButtonController(JFrame frame) {

    // public MakeOrderButtonController(JFrame frame, JComboBox<String>
    // clientComboBox) {
    public MakeOrderButtonController(JFrame frame, JComboBox<String> clientComboBox, JComboBox<String> pizzaComboBox,
            JRadioButton smallSizeRadioButton, JRadioButton largeSizeRadioButton, JTextField quantityTextField,
            JComboBox<String> deliveryPersonComboBox) {
        this.frame = frame;
        this.clientComboBox = clientComboBox;
        this.pizzaComboBox = pizzaComboBox;
        this.smallSizeRadioButton = smallSizeRadioButton;
        this.largeSizeRadioButton = largeSizeRadioButton;
        this.quantityTextField = quantityTextField;
        this.deliveryPersonComboBox = deliveryPersonComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.str = e.getActionCommand();
        System.out.println("str MakeOrderButtonController : " + str);

        if (this.str.equals("Ajouter Client")) {
            new AddToDBPage("Client");
            this.frame.dispose();
        } else if (this.str.equals("Envoyer la commande")) {
            if (!checkFormValues()) {
                return;
            }

            this.size = smallSizeRadioButton.isSelected() ? "humaine" : "ogresse";

            try {
                CommandModel cmdModel = new CommandModel();

                ClientModel cliModel = new ClientModel();
                BigDecimal cliBalance = cliModel.getClientBalance(this.clientName);
                System.out.println("cliBalance : " + cliBalance);
                
                PizzaModel pModel = new PizzaModel();
                double pizzaPriceDouble = pModel.getPizzaPrice(this.pizzaName);
                BigDecimal pizzaPrice = BigDecimal.valueOf(pizzaPriceDouble);
                
                System.out.println("Pizza prix : " + pModel.getPizzaPrice(this.pizzaName));
                
                BigDecimal quantityBigDecimal = BigDecimal.valueOf(this.quantity);
                
                // Calcul du montant total
                BigDecimal totalAmount = quantityBigDecimal.multiply(pizzaPrice);
                System.out.println("Total amount : " + totalAmount);

                // System.out.println(cliModel.getClientBalance(this.clientName));
                if (cliBalance.compareTo(totalAmount) > 0){
                    cmdModel.addCommandToDB(clientName, delivererName);
                    cmdModel.addPizzaCommandToDB(pizzaName, size, quantity);

                    BigDecimal newBalance = cliBalance.subtract(totalAmount);
                    cliModel.updateClientBalance(clientName, newBalance);
                    JOptionPane.showMessageDialog(frame, "Commande envoyée avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Solde insuffisant", "Erreur", JOptionPane.ERROR_MESSAGE);
                }


            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de l'envoi de la commande: " + exc.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean checkFormValues() {
        System.out.println("Clientname : " + this.clientName);
        this.clientName = (String) clientComboBox.getSelectedItem();
        if (this.clientName == null || this.clientName.isEmpty() || this.clientName.equals("Sélectionnez votre nom")) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un client valide", "Erreur",
            JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        this.pizzaName = (String) pizzaComboBox.getSelectedItem();
        System.out.println("Pizzaname : " + this.pizzaName);
        if (this.pizzaName == null || this.pizzaName.isEmpty() || this.pizzaName.equals("Sélectionnez votre pizza")) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner une pizza valide", "Erreur",
            JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        this.delivererName = (String) deliveryPersonComboBox.getSelectedItem();
        System.out.println("delivererName : " + this.delivererName);
        if (this.delivererName == null || this.delivererName.isEmpty()
        || this.delivererName.equals("Séléctionnez votre livreur")) {
            JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un livreur valide", "Erreur",
            JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        this.quantity = Integer.parseInt(quantityTextField.getText());
        System.out.println("quantity : " + this.quantity);
        if (this.quantity <= 0) {
            JOptionPane.showMessageDialog(frame, "La quantité doit être supérieure à zéro", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

}
