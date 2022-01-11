package ui;

import model.GroceryList;
import model.Ingredient;
import model.IngredientList;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Grocery planner Application

public class GroceryPlanner extends JInternalFrame implements ActionListener {
    private static final String JSON_STORE_INGREDIENT = "data/IngredientList.json";
    private static final String JSON_STORE_GROCERY = "data/GroceryList.json";
    private GroceryList groceryList;
    private IngredientList ingredientList;
    private JsonGroceryListWriter jsonGroceryListWriter;
    private JsonGroceryListReader jsonGroceryListReader;
    private JsonIngredientListReader jsonIngredientListReader;
    private JsonIngredientListWriter ingredientWriter;
    private JTextArea textArea;
    private JButton viewGrocery;
    private JButton addIngredients;
    private JButton checkPantry;
    private JButton purchased;
    private JButton refresh;

    // MODIFIES: this
    // EFFECTS: run grocery planner GUI
    public GroceryPlanner() {
        super("Grocery Planner");
        setSize(350,500);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();

        initialize();
        setJMenuBar(createMenuBar());
        addIngredients = new JButton("Add Grocery");
        addIngredients.addActionListener(this);
        g.anchor = GridBagConstraints.LAST_LINE_START;
        buttonPanel.add(addIngredients,g);
        viewGroceryList(buttonPanel, g);
        checkPantry = new JButton("Check Pantry");
        checkPantry.addActionListener(this);
        g.anchor = GridBagConstraints.LAST_LINE_END;
        buttonPanel.add(checkPantry,g);
        add(buttonPanel, BorderLayout.PAGE_END);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: create menu bar for grocery planner GUI
    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu saveGrocery = new JMenu("Save Grocery List");
        createMenuItem(saveGrocery, new SaveGrocery());

        JMenu loadGrocery = new JMenu("Load Grocery List");
        createMenuItem(loadGrocery, new LoadGrocery());

        menu.add(saveGrocery);
        menu.add(loadGrocery);
        return menu;
    }

    // EFFECTS: create menu item for menu bar
    private void createMenuItem(JMenu menu, AbstractAction abstractAction) {
        JMenuItem menuItem = new JMenuItem(abstractAction);
        menu.add(menuItem);
    }

    // MODIFIES: this
    // EFFECTS: initialize groceryList
    private void initialize() {
        groceryList = new GroceryList();
    }

    // EFFECTS: load ingredient to grocery
    private void addIngredientsToGrocery() {
        loadGrocery(new JPanel());
    }

    // MODIFIES: this
    // EFFECTS: ask for ingredient name, if found, add ingredients to grocery, if not found, ask for ingredients'
    //          information
    // REQUIRES: file is found and ingredient needs to be added
    private void loadGrocery(JPanel popUp) {
        try {
            jsonIngredientListReader = new JsonIngredientListReader(JSON_STORE_INGREDIENT);
            ingredientList = jsonIngredientListReader.read();
            popUp.add(new JLabel("Name of ingredient: "));
            JTextField name = new JTextField(6);
            popUp.add(name);
            JOptionPane.showMessageDialog(null,popUp);
            if (ingredientList.findIngredient(name.getText())) {
                Ingredient ing = ingredientList.searchIngredient(name.getText());
                addIngredientsToGroceryList(ing);
                JOptionPane.showMessageDialog(null, "Ingredient is found and added to list.");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ingredient is not found. Add ingredient information to the list.");
                addToGroceryList(name.getText());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "File not Found",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ingredient not added",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: ask for ingredients' information and add to ingredient list
    // REQUIRES: choose OK option
    private void addToGroceryList(String name) throws Exception {
        JPanel popUp = new JPanel(new GridLayout(4,1));
        popUp.add(new JLabel("How much servings does it have?"));
        JSpinner servings = new JSpinner(new SpinnerNumberModel(0,0,100,1));
        popUp.add(servings);
        popUp.add(new JLabel("How much price does it have?"));
        JSpinner price = new JSpinner(new SpinnerNumberModel(0,0,100,1));
        popUp.add(price);
        int option = JOptionPane.showConfirmDialog(null,popUp,"Add Ingredient",
                JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            int servingNum = (Integer)servings.getValue();
            int priceNum = (Integer)price.getValue();
            addIngredientsToGroceryList(new Ingredient(name,servingNum,priceNum));
            updateIngredientList(new Ingredient(name,servingNum,priceNum));
        } else {
            throw new Exception("Ingredient not added");
        }
    }

    // EFFECTS: add ingredients to json ingredient list
    private void updateIngredientList(Ingredient ingredient)  {
        try {
            ingredientWriter = new JsonIngredientListWriter(JSON_STORE_INGREDIENT);
            ingredientWriter.open();
            ingredientList.addIngredient(ingredient);
            ingredientWriter.write(ingredientList);
            ingredientWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            JOptionPane.showMessageDialog(null, fileNotFoundException.getMessage(),
                    "Unable to save", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: add ingredient to grocery, if not found, throw exception
    private void addIngredientsToGroceryList(Ingredient ing) throws Exception {
        groceryList.addToGrocery(ing);
        if (!groceryList.getGroceryList().contains(ing)) {
            throw new Exception("Ingredient not added");
        }
    }

    // MODIFIES: this
    // EFFECTS: display grocery list to text area
    private void viewGroceryList(JPanel buttonPanel, GridBagConstraints g) {
        viewGrocery = new JButton("View Grocery");
        JPanel header = new JPanel(new BorderLayout());
        JLabel list = new JLabel("Things to buy", SwingConstants.CENTER);
        purchased = new JButton("Purchased");
        purchased.addActionListener(this);
        refresh = new JButton("Refresh");
        refresh.addActionListener(this);
        textArea = new JTextArea(15,20);
        viewGrocery.addActionListener(e -> {
            textArea.setEditable(false);
            textArea.setText(printGroceries());
        });
        g.anchor = GridBagConstraints.PAGE_END;
        buttonPanel.add(viewGrocery,g);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        header.add(list, BorderLayout.CENTER);
        header.add(refresh,BorderLayout.EAST);
        header.add(purchased,BorderLayout.WEST);
        add(header, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: print grocery list and total cost of grocery
    private String printGroceries() {
        return groceryList.toStringGroceryList() + "Total Cost : $" + groceryList.totalCost();
    }

    // EFFECTS: pop up dialog to ask for ingredient name, then check if ingredient is in pantry
    private void checkPantry(JPanel popUp) {
        popUp.add(new JLabel("Name of ingredient: "));
        JTextField name = new JTextField(6);
        popUp.add(name);
        JOptionPane.showMessageDialog(null,popUp);
        processInputIngredient(name.getText());
    }

    // EFFECTS: search ingredient in pantry list, if yes, display popup dialog of servings left, if not, display
    //          popup dialog that ingredient is not found
    private void processInputIngredient(String inputIngredient) {
        if (groceryList.getPantry().searchInPantry(inputIngredient)) {
            String message = inputIngredient + " is in the pantry\n";
            String servingsLeft = groceryList.getPantry().getServingsLeft(inputIngredient);
            JOptionPane.showMessageDialog(null, message + servingsLeft);
        } else {
            JOptionPane.showMessageDialog(null, "Ingredient is not found.");
        }
    }

    // EFFECTS: action when button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addIngredients) {
            addIngredientsToGrocery();
        }
        if (e.getSource() == checkPantry) {
            checkPantry(new JPanel());
        }
        if (e.getSource() == viewGrocery) {
            textArea.setEditable(false);
            textArea.setText(printGroceries());
        }
        if (e.getSource() == refresh) {
            textArea.setEditable(false);
            textArea.setText(printGroceries());
        }
        if (e.getSource() == purchased) {
            groceryList.purchased();
            JOptionPane.showMessageDialog(null,"Moved to Pantry.");
        }
    }

    // private class to save grocery that extends AbstractAction
    private class SaveGrocery extends AbstractAction {
        public SaveGrocery() {
            super("Save Grocery List");
        }

        // EFFECTS: action when menu item is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ImageIcon saveIcon = new ImageIcon("data/saveIcon.png");
                saveIcon = new ImageIcon(saveIcon.getImage().getScaledInstance(45,45,
                        saveIcon.getImage().SCALE_SMOOTH));
                String fileName = JOptionPane.showInputDialog("Enter File Name: ");
                fileName = "data/" + fileName + ".json";
                jsonGroceryListWriter = new JsonGroceryListWriter(fileName);
                jsonGroceryListWriter.open();
                jsonGroceryListWriter.write(groceryList);
                jsonGroceryListWriter.close();
                JOptionPane.showMessageDialog(null,"Saved to " + fileName,"Saved",
                        JOptionPane.INFORMATION_MESSAGE,saveIcon);
            } catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(null, fileNotFoundException.getMessage(),
                        "Unable to save", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // private class to load grocery that extends AbstractAction
    private class LoadGrocery extends AbstractAction {
        public LoadGrocery() {
            super("Load Grocery List");
        }

        // EFFECTS: action when menu item is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonGroceryListReader = new JsonGroceryListReader(JSON_STORE_GROCERY);
                GroceryList groceryList = jsonGroceryListReader.read();
                JOptionPane.showMessageDialog(null,"Grocery list is found.");
                for (Ingredient ingredients: groceryList.getGroceryList()) {
                    addIngredientsToGroceryList(ingredients);
                }
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, ioException.getMessage(), "File not Found",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Ingredient not added",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


