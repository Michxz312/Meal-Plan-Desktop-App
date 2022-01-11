package persistence;

import model.GroceryList;
import model.Ingredient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads from JSON file to grocery list
// This class is written from a reference from JsonSerializationDemo
public class JsonGroceryListReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonGroceryListReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads grocery list from file and returns it
    // throws IOException if an error occurs reading data from file
    public GroceryList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGroceryList(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ingredient from JSON object and returns it
    private GroceryList parseGroceryList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Grocery List");
        GroceryList groceryList = new GroceryList();
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addGroceryList(groceryList, nextIngredient);
        }
        return groceryList;
    }

    // MODIFIES: groceryList
    // EFFECTS: parses ingredient from JSON object and adds it to grocery list
    private void addGroceryList(GroceryList groceryList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int servings = jsonObject.getInt("servings");
        double costs = jsonObject.getDouble("costs");
        Ingredient ingredient = new Ingredient(name,servings,costs);

        groceryList.addToGrocery(ingredient);
    }
}
