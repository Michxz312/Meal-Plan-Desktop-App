package model;

import org.json.JSONObject;

// This class represents the ingredients with its name, number of servings, calories per serving, and cost
// The servings are how much of the ingredient can be used (shows leftover)
// The calories are the amount of calories per serving
// The cost is the cost of the total servings

public class Ingredient implements Writeable {
    private String name;
    private int servings;
    private double cost;

    // MODIFIES: this
    // EFFECTS: create ingredient object with capitalized name
    // REQUIRES: servings greater than 0, calories greater than or equal to 0, cost greater than or equal to 0
    public Ingredient(String name, int servings, double cost) {
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        this.name = name;
        if (servings > 0) {
            this.servings = servings;
        } else {
            this.servings = 0;
        }
        if (cost >= 0) {
            this.cost = cost;
        } else {
            this.cost = 0;
        }
    }

    // EFFECTS: return string name
    public String getName() {
        return name;
    }

    // EFFECTS: return int servings
    public int getServings() {
        return servings;
    }

    // EFFECTS: return double cost
    public double getCost() {
        return cost;
    }

    // MODIFIES: this
    // EFFECTS: change string name
    // REQUIRES: name is not null
    public void setName(String name) {
        name = name.substring(0,1).toUpperCase() + name.substring(1);
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: return string name
    // REQUIRES: servings is not negative or 0
    public void setServings(int servings) {
        this.servings = servings;
    }

    // MODIFIES: this
    // EFFECTS: return double cost
    // REQUIRES: cost is not negative or 0
    public void setCost(double cost) {
        this.cost = cost;
    }

    // EFFECTS: return ingredients' name,servings,cost to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("servings",servings);
        json.put("costs",cost);
        return json;
    }
}
