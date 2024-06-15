package ihm.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import ihm.Model.ClientModel;

public class AddToDBPageController implements ActionListener {
    private JFrame frame;
    private String str;
    private String typeAdd;
    private JTextField textField;
    private JTextField soldeTextField;

    public AddToDBPageController(JFrame frame, JTextField textField, JTextField soldeTextField, String typeAdd){
        this.frame = frame;
        this.textField = textField;
        this.soldeTextField = soldeTextField;
        this.typeAdd = typeAdd;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.str = e.getActionCommand();

        if (this.str.equals("Envoyer la requête")){
            String textFieldValue = this.textField.getText();

            System.out.println("CLIQUZ BOUOTON");
            if (!textFieldValue.isEmpty()) {
                System.out.println("Textfield value non nul : " +  textFieldValue);
                if (typeAdd == "Client") {
                    String soldeCompteValue = this.soldeTextField.getText();
                    // System.out.println("Ajout d'un client en BDD");
                    ClientModel cModel = new ClientModel();
                    // System.out.println("soldeCompteValue : " + soldeCompteValue);
                    cModel.addClientToDB(textFieldValue, soldeCompteValue, frame);
                }
            }
        }
    }
    
}
