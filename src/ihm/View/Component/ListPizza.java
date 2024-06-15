package ihm.View.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import ihm.Model.PizzaModel;

import java.awt.*;
import java.util.List;

public class ListPizza extends JPanel {
    
    private PizzaModel pModel;

    public ListPizza(){
        this.setLayout(new GridLayout(1, 3, 10, 10));
        this.add(createPizzaCard(1));
        this.add(createPizzaCard(2));
        this.add(createPizzaCard(3));    
    }

    private JPanel createPizzaCard(int pizzaId) {
        JPanel card = new JPanel();
        this.pModel = new PizzaModel(pizzaId);

        card.setBorder(BorderFactory.createTitledBorder(this.pModel.getPizzaString()));

        JPanel ingredientsPanel = new JPanel(new BorderLayout());
        JLabel ingredientsLabel = new JLabel("Ingrédients :", JLabel.CENTER);

        ingredientsPanel.add(ingredientsLabel, BorderLayout.NORTH);

        List<String> ingredients = this.pModel.getPizzaIngredients();

        JTextArea ingredientsTextArea = new JTextArea(5, 20);
        ingredientsTextArea.setEditable(false);
        ingredientsTextArea.setWrapStyleWord(true);
        ingredientsTextArea.setLineWrap(true);

        for (String ingredient : ingredients) {
            ingredientsTextArea.append(ingredient + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(ingredientsTextArea);
        ingredientsPanel.add(scrollPane, BorderLayout.CENTER);

        card.add(ingredientsPanel, BorderLayout.CENTER);

        JLabel priceLabel = new JLabel("Prix : " + this.pModel.getPizzaPrice() + " €", JLabel.CENTER);
        card.add(priceLabel, BorderLayout.SOUTH);

        return card;
    }
}
