package ihm.View.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class Title extends JPanel{

    public Title() {
        JLabel titleLabel = new JLabel("RaPizz", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(titleLabel);
    }
    
}
