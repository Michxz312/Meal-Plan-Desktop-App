package persistence;

import model.GroceryList;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of grocery list to file
// This class is written from a reference from JsonSerializationDemo

public class JsonGroceryListWriter {
    private PrintWriter writer;
    private String fileName;

    // MODIFIES: this
    // EFFECTS: constructs JsonWriter to write into the fileName
    public JsonGroceryListWriter(String fileName) {
        this.fileName = fileName;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileName));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of groceryList to file
    public void write(GroceryList groceryList) {
        JSONObject json = groceryList.toJson();
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
