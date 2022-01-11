package persistence;

import model.DailyMealPlan;
import model.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;

// This is a class to help JsonTest with checking ingredients and checking meal plans of days
public class JsonTest {
    void checkIngredient(String name, int servings, double costs, Ingredient ingredient) {
        assertEquals(name, ingredient.getName());
        assertEquals(servings, ingredient.getServings());
        assertEquals(costs, ingredient.getCost());
    }

    void checkDays(String day,String breakfast, String lunch, String dinner, DailyMealPlan days) {
        assertEquals(day,days.getDay());
        assertEquals(breakfast,days.getBreakfast());
        assertEquals(lunch,days.getLunch());
        assertEquals(dinner,days.getDinner());
    }
}
