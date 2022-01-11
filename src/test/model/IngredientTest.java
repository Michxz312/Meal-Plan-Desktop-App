package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientTest {
    Ingredient ingredient1;
    Ingredient ingredient2;
    Ingredient ingredient3;

    @BeforeEach
    public void setUp() {
        ingredient1 = new Ingredient("potato", -1, 0.99);
        ingredient2 = new Ingredient("Spaghetti",11,-1.00 );
        ingredient3 = new Ingredient("candy",12, 3.99);
    }

    @Test
    public void getNameTest(){
        assertNotEquals(ingredient1.getName(),"Sweet Potato");
        ingredient1.setName("tuna");
        assertEquals(ingredient1.getName(),"Tuna");
        assertEquals(ingredient2.getName(),"Spaghetti");
        assertEquals(ingredient3.getName(),"Candy");
    }

    @Test
    public void getServingsTest(){
        assertNotEquals(-1, ingredient1.getServings());
        assertEquals(ingredient1.getServings(),0);
        ingredient1.setServings(1);
        assertEquals(ingredient1.getServings(),1);
        assertEquals(ingredient2.getServings(),11);
        assertEquals(ingredient3.getServings(),12);
    }

    @Test
    public void getCostTest(){
        assertEquals(ingredient1.getCost(),0.99);
        assertNotEquals(1.00, ingredient2.getCost());
        assertEquals(ingredient2.getCost(),0);
        ingredient2.setCost(1.00);
        assertEquals(ingredient2.getCost(),1.00);
        assertEquals(ingredient3.getCost(),3.99);
    }

}