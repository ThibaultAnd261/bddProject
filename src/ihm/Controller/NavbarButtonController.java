package ihm.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import ihm.View.Accueil;
import ihm.View.Historic;
import ihm.View.MakeOrder;
import ihm.View.Stats;

public class NavbarButtonController implements ActionListener {
    private String str;
    private JFrame frame;

    public NavbarButtonController(JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.str = e.getActionCommand();

        // System.out.println("str : " + str);

        if (this.str.equals("Carte pizza")) {
            new Accueil();
            this.frame.dispose();
        } else if (this.str.equals("Faire une commande")){
            new MakeOrder();
            this.frame.dispose();
        } else if (this.str.equals("Statistiques")){
            new Stats();
            this.frame.dispose();
        } else if (this.str.equals("Historique")){
            new Historic();
            this.frame.dispose();
        }

        // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}
