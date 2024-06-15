package ihm.View.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

import ihm.Controller.NavbarButtonController;

public class Navbar extends JPanel {
    private NavbarButtonController cButtonController;

    public Navbar(JFrame frame) {
        cButtonController = new NavbarButtonController(frame);
        this.setLayout(new FlowLayout());
        
        JButton homeButton = new JButton("Carte pizza");
        homeButton.addActionListener(this.cButtonController);
        
        JButton makeOrderButton = new JButton("Faire une commande");
        makeOrderButton.addActionListener(this.cButtonController);
        
        JButton statsButton = new JButton("Statistiques");
        statsButton.addActionListener(this.cButtonController);
        
        JButton historicButton = new JButton("Historique");
        historicButton.addActionListener(this.cButtonController);
        
        this.add(homeButton);
        this.add(makeOrderButton);
        this.add(statsButton);
        this.add(historicButton);
    }

}
