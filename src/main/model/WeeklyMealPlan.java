package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

// This class keeps a list of daily meal plans, list of ingredients to grocery list,
// costs of ingredients in the grocery list and list of ingredients to pantry list.

public class WeeklyMealPlan implements Writeable {
    private List<DailyMealPlan> days;

    // MODIFIES: this
    // EFFECTS: create new grocery list, pantry list, list of dailyMealPlan, and set costs to 0
    // REQUIRES: list of DailyMealPlan is not null
    public WeeklyMealPlan(List<DailyMealPlan> dayOfWeek) {
        days = dayOfWeek;
    }

    // MODIFIES: this
    // EFFECTS: clear breakfast meal plan from a day in DailyMealPlan and set new breakfast meal plan
    // REQUIRES: dayOfWeek and newBreakfastPlan is not null
    public void editBreakfast(DailyMealPlan dayOfWeek, String newBreakfastPlan) {
        for (DailyMealPlan day: days) {
            if (day.getDay().equals(dayOfWeek.getDay())) {
                day.clearBreakfast();
                day.setBreakfast(newBreakfastPlan);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: clear lunch meal plan from a day in DailyMealPlan and set new lunch meal plan
    // REQUIRES: dayOfWeek and newLunchPlan is not null
    public void editLunch(DailyMealPlan dayOfWeek, String newLunchPlan) {
        for (DailyMealPlan day: days) {
            if (day.getDay().equals(dayOfWeek.getDay())) {
                day.clearLunch();
                day.setLunch(newLunchPlan);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: clear dinner meal plan from a day in DailyMealPlan and set new dinner meal plan
    // REQUIRES: dayOfWeek and newDinnerPlan is not null
    public void editDinner(DailyMealPlan dayOfWeek, String newDinnerPlan) {
        for (DailyMealPlan day: days) {
            if (day.getDay().equals(dayOfWeek.getDay())) {
                day.clearDinner();
                day.setDinner(newDinnerPlan);
            }
        }
    }

    // EFFECTS: return String for list of days with meal plans for breakfast, lunch and dinner in each day
    public String viewPlan() {
        StringBuilder plan = new StringBuilder("Weekly Plan: " + "\n");
        for (DailyMealPlan day: days) {
            plan.append(day.getDay()).append("\n");
            plan.append("Breakfast:\t").append(day.getBreakfast()).append("\n");
            plan.append("Lunch:\t").append(day.getLunch()).append("\n");
            plan.append("Dinner:\t").append(day.getDinner()).append("\n");
        }
        return plan.toString();
    }

    // EFFECTS: return unchangeable list of dailyMealPlan
    public List<DailyMealPlan> getDailyMealPlan() {
        return Collections.unmodifiableList(days);
    }

    // EFFECTS: return meal plan for dayOfWeek to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Week", daysToJson());
        return json;
    }

    // EFFECTS: return DailyMealPLan json representation
    private JSONArray daysToJson() {
        JSONArray jsonArray = new JSONArray();
        for (DailyMealPlan day: days) {
            jsonArray.put(day.toJson());
        }
        return jsonArray;
    }
}
