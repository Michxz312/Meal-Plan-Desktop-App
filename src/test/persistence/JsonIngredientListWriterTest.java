package persistence;

import model.IngredientList;
import model.Ingredient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonIngredientListWriterTest extends JsonTest {
    @Test
    void testWriterNoneExistentFile() {
        try {
            JsonIngredientListWriter writer = new JsonIngredientListWriter("./data\nnonexistent.json");
            writer.open();
            fail("IOException expected");
        } catch(IOException io) {
            // pass
        }
    }

    @Test
    void testWriterEmptyIngredientList() {
        IngredientList ingredientList = new IngredientList();
        JsonIngredientListWriter writer = new JsonIngredientListWriter("./data/testEmptyIngredientList.json");
        try {
            writer.open();
            writer.write(ingredientList);
            writer.close();

            JsonIngredientListReader reader = new JsonIngredientListReader("./data/testEmptyIngredientList.json");
            ingredientList = reader.read();
            assertEquals(0,ingredientList.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralIngredientList() {
        IngredientList ingredientList = new IngredientList();
        JsonIngredientListWriter writer = new JsonIngredientListWriter("./data/GeneralIngredientList.json");
        try {
            ingredientList.addIngredient(new Ingredient("blueberry",23,11.87));
            ingredientList.addIngredient(new Ingredient("oats",16,3.17));
            writer.open();
            writer.write(ingredientList);
            writer.close();

            JsonIngredientListReader reader = new JsonIngredientListReader("./data/GeneralIngredientList.json");
            ingredientList = reader.read();
            List<Ingredient> ingredients = ingredientList.getIngredientList();
            checkIngredient("Blueberry",23,11.87,ingredients.get(0));
            checkIngredient("Oats",16,3.17,ingredients.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
