package persistence;

import model.GroceryList;
import model.Ingredient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonGroceryListWriterTest extends JsonTest {
    @Test
    void testWriterNonExistentFile() {
        try {
            JsonGroceryListWriter writer = new JsonGroceryListWriter("./data/\n.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyGroceryList() {
        GroceryList groceryList = new GroceryList();
        JsonGroceryListWriter writer = new JsonGroceryListWriter("./data/EmptyGroceryList.json");
        try {
            writer.open();
            writer.write(groceryList);
            writer.close();

            JsonGroceryListReader reader = new JsonGroceryListReader("./data/EmptyGroceryList.json");
            groceryList = reader.read();
            assertEquals(0,groceryList.numberOfItems());
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterGeneralGroceryList() {
        GroceryList groceryList = new GroceryList();
        JsonGroceryListWriter writer = new JsonGroceryListWriter("./data/testWriterGroceryList.json");
        try {
            groceryList.addToGrocery(new Ingredient("blueberry",23,11.87));
            groceryList.addToGrocery(new Ingredient("oats",16,3.17));
            writer.open();
            writer.write(groceryList);
            writer.close();

            JsonGroceryListReader reader = new JsonGroceryListReader("./data/testWriterGroceryList.json");
            groceryList = reader.read();
            List<Ingredient> groList = groceryList.getGroceryList();
            checkIngredient("Blueberry",23,11.87,groList.get(0));
            checkIngredient("Oats",16,3.17,groList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
