package persistence;

import model.IngredientList;
import org.json.JSONObject;

import java.io.*;

//Represents a writer that writes JSON representatives of Ingredient list to file
// This class is written from a reference from JsonSerializationDemo

public class JsonIngredientListWriter {
    private PrintWriter writer;
    private String fileName;

    // MODIFIES: this
    // EFFECTS: constructs JsonWriter to write into the fileName
    public JsonIngredientListWriter(String fileName) {
        this.fileName = fileName;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(fileName);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of IngredientList to file
    public void write(IngredientList ingredientList) {
        JSONObject json = ingredientList.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
