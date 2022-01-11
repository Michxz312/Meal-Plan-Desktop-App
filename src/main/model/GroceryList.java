package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class keeps list of ingredients and methods to add ingredients,
// remove ingredients and give summary of the list

public class GroceryList implements Writeable {
    private List<Ingredient> groceryList;
    private PantryList pantry;

    // MODIFIES: this
    // EFFECTS: create new ingredient arraylist
    public GroceryList() {
        groceryList = new ArrayList<>();
        pantry = new PantryList();
    }

    // MODIFIES: this
    // EFFECTS: add ingredient to the grocery list
    // REQUIRES: ingredient object to not be null
    public void addToGrocery(Ingredient ingredientName) {
        groceryList.add(ingredientName);
        EventLog.getInstance().logEvent(new Event(ingredientName.getName() + " is added to grocery"));
    }

    // EFFECTS: return list of Ingredients from grocery list that cannot be modified
    public List<Ingredient> getGroceryList() {
        return Collections.unmodifiableList(groceryList);
    }

    // MODIFIES: this
    // EFFECTS: remove ingredient from the grocery list
    // REQUIRES: ingredient object to not be null
    public void removeFromGrocery(Ingredient ingredientName) {
        groceryList.remove(ingredientName);
        EventLog.getInstance().logEvent(new Event(ingredientName.getName() + " is removed from grocery"));
    }

    // MODIFIES: this
    // EFFECTS: add list of ingredients to pantry and return list of ingredients
    public void purchased() {
        List<Ingredient> ingredientList = makePurchase();
        pantry.addToPantry(ingredientList);
        EventLog.getInstance().logEvent(new Event("Ingredients are move to pantry"));
    }

    // EFFECTS: return PantryList from pantry
    public PantryList getPantry() {
        return pantry;
    }

    // EFFECTS: count number of items in grocery list
    public int numberOfItems() {
        return groceryList.size();
    }

    // EFFECTS: count the total cost of every ingredient in grocery list
    public double totalCost() {
        double cost = 0;
        for (Ingredient name: groceryList) {
            cost += name.getCost();
        }
        return cost;
    }

    // EFFECTS:count the total servings of every ingredient in grocery list
    public int totalServings(Ingredient ingredient) {
        int servings = 0;
        for (Ingredient name: groceryList) {
            if (ingredient.getName().equals(name.getName())) {
                servings += name.getServings();
            }
        }
        return servings;
    }

    // MODIFIES: this
    // EFFECTS: remove all ingredients from grocery list and return a list of ingredient
    public List<Ingredient> makePurchase() {
        List<Ingredient> temp = new ArrayList<>(groceryList);
        groceryList.removeAll(Collections.unmodifiableList(groceryList));
        return temp;
    }

    // EFFECTS: check ingredient in grocery list return true if ingredient is found, else false
    // REQUIRES: string ingredient to not be null
    public boolean checkIngredientInGroceryList(String ingredient) {
        for (Ingredient name: groceryList) {
            if (name.getName().contains(ingredient)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return string of list of ingredients and ingredient information in the list
    public String toStringGroceryList() {
        StringBuilder view = new StringBuilder();
        for (Ingredient name: groceryList) {
            view.append(name.getName()).append(" ");
            view.append("$").append(name.getCost()).append("\n");
        }
        return view.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Grocery List", ingredientToJson());
        return json;
    }

    // EFFECTS: return ingredient of grocery list as JSON array
    private JSONArray ingredientToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Ingredient i: groceryList) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
