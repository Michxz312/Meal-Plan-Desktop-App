package persistence;

import model.Ingredient;
import model.IngredientList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonIngredientListReaderTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        JsonIngredientListReader reader = new JsonIngredientListReader("./data/invalidFile.json");
        try {
            IngredientList ingredientList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyIngredientList() {
        JsonIngredientListReader reader = new JsonIngredientListReader("./data/EmptyList.json");
        try {
            IngredientList ingredientList = reader.read();
            assertEquals(0, ingredientList.getSize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralIngredientList() {
        JsonIngredientListReader reader = new JsonIngredientListReader("./data/IngredientList.json");
        try {
            IngredientList ingredientList = reader.read();
            assertEquals(8, ingredientList.getSize());
            List<Ingredient> ingList = ingredientList.getIngredientList();
            checkIngredient("Blueberry", 23, 11.87, ingList.get(0));
            checkIngredient("Oats", 16, 3.17, ingList.get(1));
            checkIngredient("Eggs", 12, 3.6, ingList.get(2));
            checkIngredient("Milk",17 , 4.99, ingList.get(3));
            checkIngredient("Bacon", 8, 7.21, ingList.get(4));
            checkIngredient("Carrots", 8, 2.3, ingList.get(5));
            checkIngredient("Potato", 12, 12, ingList.get(6));
            checkIngredient("Penne", 10, 10, ingList.get(7));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
