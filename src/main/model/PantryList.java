package model;

import java.util.ArrayList;
import java.util.List;

// This class keeps list of ingredients and methods to add and search ingredients in the list.

public class PantryList {
    List<Ingredient> ingredientList;

    // MODIFIES: this
    // EFFECTS: create new ingredient arraylist
    public PantryList() {
        ingredientList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds list of ingredients to this
    // REQUIRES: list to not be null
    public boolean addToPantry(List<Ingredient> list) {
        ingredientList.addAll(list);
        EventLog.getInstance().logEvent(new Event("Ingredients are added to pantry"));
        return ingredientList.containsAll(list);
    }

    // EFFECTS: search for ingredient name in pantry, return true if ingredient is found, else false
    // REQUIRES: ingredient name to not be null
    public boolean searchInPantry(String ingredientName) {
        for (Ingredient name: ingredientList) {
            if (name.getName().equalsIgnoreCase(ingredientName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: search for ingredient name in pantry, return string that shows servings left, else return nothing
    // REQUIRES: ingredientName to exist in pantry
    public String getServingsLeft(String ingredientName) {
        for (Ingredient name: ingredientList) {
            if (name.getName().equalsIgnoreCase(ingredientName)) {
                return "There are " + name.getServings() + " servings left";
            }
        }
        return "";
    }
}
