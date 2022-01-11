package persistence;

import model.Ingredient;
import model.IngredientList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Recipe list from JSON data stored in file
// This class is written with reference from JsonSerializationDemo
public class JsonIngredientListReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonIngredientListReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads recipe from file and returns it
    // throws IOException if an error occurs reading data from file
    public IngredientList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseIngredientList(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ingredient from JSON object and returns it
    private IngredientList parseIngredientList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Ingredient List");
        IngredientList ingredientList = new IngredientList();
        for (Object json : jsonArray) {
            JSONObject nextIngredient = (JSONObject) json;
            addIngredient(ingredientList, nextIngredient);
        }
        return ingredientList;
    }

    // MODIFIES: ingredientList
    // EFFECTS: parses ingredient from JSON object and adds it to ingredientList
    private void addIngredient(IngredientList ingredientList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int servings = jsonObject.getInt("servings");
        double costs = jsonObject.getDouble("costs");
        Ingredient ingredient = new Ingredient(name,servings,costs);
        ingredientList.addIngredient(ingredient);
    }
}
