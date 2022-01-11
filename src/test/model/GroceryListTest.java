package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GroceryListTest {
    Ingredient ingredient4;
    Ingredient ingredient5;
    Ingredient ingredient6;

    GroceryList list;
    PantryList pantryList;

    @BeforeEach
    public void setUp() {
        ingredient4 = new Ingredient("potato", 1, 0.99);
        ingredient5 = new Ingredient("Spaghetti",11,1.00 );
        ingredient6 = new Ingredient("candy",12, 3.99);

        list = new GroceryList();
        pantryList = new PantryList();
    }

    @Test
    public void constructorTest() {
        assertEquals(0, list.numberOfItems());
        list.addToGrocery(ingredient4);
        assertEquals(1, list.numberOfItems());
        assertEquals(0,pantryList.ingredientList.size());
    }

    @Test
    public void totalServingsTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient6);

        assertEquals(list.totalServings(ingredient4),1);
        assertEquals(list.totalServings(ingredient5),33);
    }

    @Test
    public void totalCostTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient6);

        assertEquals(list.totalCost(),7.98);
    }

    @Test
    public void purchasedTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient6);
        list.purchased();

        pantryList = list.getPantry();
        assertTrue(pantryList.searchInPantry(ingredient4.getName()));
        assertFalse(pantryList.searchInPantry(ingredient5.getName()));
    }

    @Test
    public void makePurchaseTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient6);

        List<Ingredient> list2= list.makePurchase();

        assertFalse(list.checkIngredientInGroceryList("Spaghetti"));
        assertEquals(list.totalCost(),0);
        assertTrue(list2.contains(ingredient5));
    }

    @Test
    public void addToGroceryTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);

        assertTrue(list.checkIngredientInGroceryList("Potato"));
        assertTrue(list.checkIngredientInGroceryList("Spaghetti"));
        assertFalse(list.checkIngredientInGroceryList("Candy"));

        list.addToGrocery(ingredient6);
        assertTrue(list.checkIngredientInGroceryList("Candy"));
    }

    @Test
    public void removeFromGroceryTest() {
        list.addToGrocery(ingredient4);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient5);
        list.addToGrocery(ingredient6);

        assertTrue(list.checkIngredientInGroceryList("Potato"));
        assertTrue(list.checkIngredientInGroceryList("Spaghetti"));
        assertTrue(list.checkIngredientInGroceryList("Candy"));

        list.removeFromGrocery(ingredient4);
        list.removeFromGrocery(ingredient5);
        list.removeFromGrocery(ingredient6);

        assertFalse(list.checkIngredientInGroceryList("Potato"));
        assertTrue(list.checkIngredientInGroceryList("Spaghetti"));
        assertFalse(list.checkIngredientInGroceryList("Candy"));
    }

    @Test
    public void toStringGroceryListTest() {
        list.addToGrocery(ingredient4);
        assertTrue(list.toStringGroceryList().contains("Potato $0.99"));
    }
}
