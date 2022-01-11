package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeeklyMealPlanTest {
    private WeeklyMealPlan week1;
    private DailyMealPlan Monday;
    private DailyMealPlan Tuesday;
    private Ingredient ingredient1;
    private Ingredient ingredient2;

    @BeforeEach
    public void setUp() {
        List<DailyMealPlan> week = new ArrayList<>();

        Monday = new DailyMealPlan("Monday");
        Tuesday = new DailyMealPlan("Tuesday");

        ingredient1 = new Ingredient("potato", 1, 0.99);
        ingredient2 = new Ingredient("Spaghetti",11,1.00 );

        Monday.setBreakfast("oatmeal");
        Monday.setDinner("spaghetti");
        Tuesday.setLunch("salad");

        week.add(Monday);
        week.add(Tuesday);
        week1 = new WeeklyMealPlan(week);
    }

    @Test
    public void editBreakfast() {
        week1.editBreakfast(Tuesday,"pancake");
        assertEquals("pancake", Tuesday.getBreakfast());
    }

    @Test
    public void editLunch() {
        week1.editLunch(Monday,"pizza");
        assertEquals("pizza", Monday.getLunch());
    }

    @Test
    public void editDinner() {
        week1.editDinner(Tuesday,"sushi");
        assertEquals("sushi", Tuesday.getDinner());
    }

    @Test
    public void viewPlan() {
        assertNotEquals("", week1.viewPlan());
    }
}