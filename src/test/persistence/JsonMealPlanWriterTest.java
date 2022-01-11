package persistence;

import model.DailyMealPlan;
import model.WeeklyMealPlan;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonMealPlanWriterTest extends JsonTest {

    @Test
    void testWriterNonExistentFile() {
        try {
            JsonMealPlanWriter writer = new JsonMealPlanWriter("data\0/file.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriteEmptyMealPlan() {
        List<DailyMealPlan> dayOfWeek = new ArrayList<>();
        WeeklyMealPlan week = new WeeklyMealPlan(dayOfWeek);
        JsonMealPlanWriter writer = new JsonMealPlanWriter("data/EmptyTestMealPlan.json");
        try {
            writer.open();
            writer.write(week);
            writer.close();

            JsonMealPlanReader reader = new JsonMealPlanReader("data/EmptyTestMealPlan.json");
            week = reader.read();
            assertEquals(0,week.getDailyMealPlan().size());
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterGeneralGroceryList() {
        DailyMealPlan mon = new DailyMealPlan("Monday");
        mon.setBreakfast("oatmeal");
        DailyMealPlan tue = new DailyMealPlan("Tuesday");
        tue.setLunch("pizza");
        DailyMealPlan wed = new DailyMealPlan("Wednesday");
        wed.setDinner("french fries");
        List<DailyMealPlan> dayOfWeek = new ArrayList<>();
        dayOfWeek.add(mon);
        dayOfWeek.add(tue);
        dayOfWeek.add(wed);

        WeeklyMealPlan week = new WeeklyMealPlan(dayOfWeek);
        JsonMealPlanWriter writer = new JsonMealPlanWriter("data/GeneralMealPlan.json");
        try {
            writer.open();
            writer.write(week);
            writer.close();

            JsonMealPlanReader reader = new JsonMealPlanReader("data/GeneralMealPlan.json");
            week = reader.read();
            List<DailyMealPlan> dailyMealPlans = week.getDailyMealPlan();
            checkDays("Monday","oatmeal","","",dailyMealPlans.get(0));
            checkDays("Tuesday","","pizza","",dailyMealPlans.get(1));
            checkDays("Wednesday","","","french fries",dailyMealPlans.get(2));
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
}