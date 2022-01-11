package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// This class provides a list of ingredients
public class IngredientList implements Writeable {
    private List<Ingredient> ingredientList;

    // MODIFIES: this
    // EFFECTS: create new ingredient arraylist
    public IngredientList() {
        ingredientList = new ArrayList<>();
    }

    // EFFECTS: return list of Ingredients from ingredient list that cannot be modified
    public List<Ingredient> getIngredientList() {
        return Collections.unmodifiableList(ingredientList);
    }

    // MODIFIES: this
    // EFFECTS: add ingredient to the grocery list
    // REQUIRES: ingredient object to not be null
    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    // EFFECTS: search for ingredient name in ingredient list, return Ingredient name if found
    // REQUIRES: ingredient name to not be null and found in ingredient list
    public Ingredient searchIngredient(String ingredientName) {
        for (Ingredient i: ingredientList) {
            if (i.getName().equalsIgnoreCase(ingredientName)) {
                return i;
            }
        }
        return new Ingredient("ingredient",0,0);
    }

    // EFFECTS: search for ingredient name in ingredient list, return true if ingredient is found, else false
    // REQUIRES: ingredient name to not be null
    public boolean findIngredient(String ingredientName) {
        for (Ingredient i: ingredientList) {
            if (i.getName().equalsIgnoreCase(ingredientName)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: return size of ingredient list
    public int getSize() {
        return ingredientList.size();
    }

    // EFFECTS: return ingredient list converted to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Ingredient List", ingredientToJson());
        return json;
    }

    // EFFECTS: return JSONArray of ingredient to json
    private JSONArray ingredientToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Ingredient i: ingredientList) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }
}
