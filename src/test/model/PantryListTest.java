package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class PantryListTest {
    Ingredient ingredient4;
    Ingredient ingredient5;
    Ingredient ingredient6;
    GroceryList grocery;
    PantryList pantrylist;

    @BeforeEach
    public void setUp() {
        ingredient4 = new Ingredient("potato", 1, 0.99);
        ingredient5 = new Ingredient("Spaghetti",11,1.00 );
        ingredient6 = new Ingredient("candy",12, 3.99);

        grocery = new GroceryList();
        grocery.addToGrocery(ingredient4);
        grocery.addToGrocery(ingredient6);

        pantrylist = new PantryList();
    }

    @Test
    public void constructorTest() {
        assertFalse(pantrylist.searchInPantry("Candy"));
        List<Ingredient> purchased = grocery.makePurchase();
        assertTrue(pantrylist.addToPantry(purchased));
    }

    @Test
    public void addToPantryTest() {
        List<Ingredient> purchased = grocery.makePurchase();
        assertTrue(pantrylist.addToPantry(purchased));
        assertTrue(pantrylist.searchInPantry("Candy"));

        grocery.addToGrocery(ingredient5);
        assertFalse(pantrylist.searchInPantry("Spaghetti"));
        purchased = grocery.makePurchase();
        assertTrue(pantrylist.addToPantry(purchased));
        assertTrue(pantrylist.searchInPantry("Spaghetti"));
    }

    @Test
    public void searchInPantryTest() {
        List<Ingredient> purchased = grocery.makePurchase();
        assertTrue(pantrylist.addToPantry(purchased));
        assertTrue(pantrylist.searchInPantry("Candy"));
        assertTrue(pantrylist.searchInPantry("Potato"));
    }

    @Test
    public void getServingsLeftTest() {
        List<Ingredient> purchased = grocery.makePurchase();
        assertTrue(pantrylist.addToPantry(purchased));
        assertEquals("There are 12 servings left",pantrylist.getServingsLeft("Candy"));
        assertEquals("There are 1 servings left",pantrylist.getServingsLeft("Potato"));
        assertEquals("",pantrylist.getServingsLeft("pizza"));
    }
}