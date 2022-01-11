package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientListTest {
    private IngredientList ingredientList;
    private Ingredient ingredient7;
    private Ingredient ingredient8;

    @BeforeEach
    void setUp() {
        ingredientList = new IngredientList();

        ingredient7 = new Ingredient("ice cream", 5, 4.99);
        ingredient8 = new Ingredient("chips",3,2.99 );
    }

    @Test
    void addIngredient() {
        ingredientList.addIngredient(ingredient7);
        ingredientList.addIngredient(ingredient8);

        assertTrue(ingredientList.findIngredient("ice cream"));
        assertTrue(ingredientList.findIngredient("chips"));
        assertFalse(ingredientList.findIngredient("oatmeal"));
    }

    @Test
    void searchIngredient() {
        ingredientList.addIngredient(ingredient7);
        ingredientList.addIngredient(ingredient8);

        assertEquals(ingredient7, ingredientList.searchIngredient("ice cream"));
        assertEquals(ingredient8, ingredientList.searchIngredient("chips"));
        Ingredient empty = new Ingredient("ingredient",0,0);
        assertEquals(empty.getCost(), ingredientList.searchIngredient("pizza").getCost());
    }

    @Test
    void getSize() {
        assertEquals(0,ingredientList.getSize());
        ingredientList.addIngredient(ingredient7);
        ingredientList.addIngredient(ingredient8);
        assertEquals(2,ingredientList.getSize());
    }
}