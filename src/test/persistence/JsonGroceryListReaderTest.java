package persistence;

import model.GroceryList;
import model.Ingredient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonGroceryListReaderTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        JsonGroceryListReader reader = new JsonGroceryListReader("./data/invalidFile.json");
        try {
            GroceryList groceryList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGroceryList() {
        JsonGroceryListReader reader = new JsonGroceryListReader("data/testEmptyGroceryList.json");
        try {
            GroceryList groceryList = reader.read();
            assertEquals(0,groceryList.numberOfItems());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralGroceryList() {
        JsonGroceryListReader reader = new JsonGroceryListReader("data/testGroceryList.json");
        try {
            GroceryList groceryList = reader.read();
            assertEquals(2, groceryList.numberOfItems());
            List<Ingredient> groList = groceryList.getGroceryList();
            checkIngredient("Blueberry",23, 11.87, groList.get(0));
            checkIngredient("Oats", 16, 3.17, groList.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}