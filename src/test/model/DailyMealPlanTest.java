package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DailyMealPlanTest {
    private DailyMealPlan Monday;
    private DailyMealPlan Tuesday;

    @BeforeEach
    public void setUp() {
        Monday = new DailyMealPlan("Monday");
        Tuesday = new DailyMealPlan("Tuesday");

        Monday.setBreakfast("oatmeal");
        Monday.setDinner("spaghetti");
        Tuesday.setLunch("salad");
    }

    @Test
    public void constructorTest() {
        assertEquals(Monday.getDay(),"Monday");
        assertEquals(Monday.getBreakfast(),"oatmeal");
        assertEquals(Monday.getLunch(),"");
        assertEquals(Tuesday.getDinner(),"");
        assertEquals(Tuesday.getLunch(),"salad");
    }

    @Test
    public void clearAllTest() {
        Monday.clearAll();
        assertEquals(Monday.getDinner(),"");
        Tuesday.clearAll();
        assertEquals(Tuesday.getDay(),"Tuesday");
        assertEquals(Tuesday.getLunch(),"");
    }

    @Test
    public void clearBreakfastTest() {
        Monday.clearBreakfast();
        assertEquals(Monday.getBreakfast(),"");
    }

    @Test
    public void clearLunch() {
        Tuesday.clearLunch();
        assertEquals(Tuesday.getLunch(),"");
    }

    @Test
    public void clearDinnerTest() {
        Monday.clearDinner();
        assertEquals(Monday.getDinner(),"");
    }
}