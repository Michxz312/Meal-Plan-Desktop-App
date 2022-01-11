package persistence;

import model.DailyMealPlan;
import model.WeeklyMealPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonMealPlanReaderTest extends JsonTest {
    @Test
    void testReaderInvalidFile() {
        JsonMealPlanReader reader = new JsonMealPlanReader("data/invalidFile.json");
        try {
            WeeklyMealPlan week = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyMealPLan() {
        JsonMealPlanReader reader = new JsonMealPlanReader("data/testEmptyMealPlan.json");
        try {
            WeeklyMealPlan week = reader.read();
            assertEquals(0,week.getDailyMealPlan().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGeneralMealPlan() {
        JsonMealPlanReader reader = new JsonMealPlanReader("data/testMealPlan.json");
        try {
            WeeklyMealPlan week = reader.read();
            assertEquals(7,week.getDailyMealPlan().size());
            List<DailyMealPlan> days = week.getDailyMealPlan();
            checkDays("Monday","cereal","apples","pancake",days.get(0));
            checkDays("Wednesday","oatmeal","yoghurt","spaghetti",days.get(2));
            checkDays("Friday","eggs and bacon","salad","",days.get(4));
            checkDays("Saturday","oatmeal","baked salmon","french toast",days.get(5));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}