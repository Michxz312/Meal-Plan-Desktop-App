package model;

import org.json.JSONObject;

// This class should store what to eat in a day, decide what to eat in a day, purchase ingredients, then get cost.

public class DailyMealPlan implements Writeable {
    private String day;
    private String breakfast;
    private String lunch;
    private String dinner;

    // MODIFIES: this
    // EFFECTS: total all ingredients to buy from each day and view the user's grocery list of things to buy
    // REQUIRES: day to not be null
    public DailyMealPlan(String day) {
        this.day = day;
        breakfast = "";
        lunch = "";
        dinner = "";
    }

    // EFFECTS: return String day
    public String getDay() {
        return day;
    }

    // EFFECTS: return String breakfast
    public String getBreakfast() {
        return breakfast;
    }

    // EFFECTS: return String lunch
    public String getLunch() {
        return lunch;
    }

    // EFFECTS: return String dinner
    public String getDinner() {
        return dinner;
    }

    // MODIFIES: this
    // EFFECTS: set breakfast
    // REQUIRES: string breakfast is not null
    public void setBreakfast(String breakfast) {
        this.breakfast = breakfast;
        if (!breakfast.isEmpty()) {
            EventLog.getInstance().logEvent(new Event(breakfast + " is set as breakfast on " + day));
        }
    }

    // MODIFIES: this
    // EFFECTS: set lunch
    // REQUIRES: string lunch is not null
    public void setLunch(String lunch) {
        this.lunch = lunch;
        if (!lunch.isEmpty()) {
            EventLog.getInstance().logEvent(new Event(lunch + " is set as lunch on " + day));
        }
    }

    // MODIFIES: this
    // EFFECTS: set dinner
    // REQUIRES: string dinner is not null
    public void setDinner(String dinner) {
        this.dinner = dinner;
        if (!dinner.isEmpty()) {
            EventLog.getInstance().logEvent(new Event(dinner + " is set as dinner on " + day));
        }
    }

    // MODIFIES: this
    // EFFECTS: set all breakfast, lunch, and dinner to empty string
    public void clearAll() {
        breakfast = "";
        lunch = "";
        dinner = "";
        EventLog.getInstance().logEvent(new Event("Breakfast, Lunch, and Dinner is cleared on " + day));
    }

    // MODIFIES: this
    // EFFECTS: set breakfast to empty string
    public void clearBreakfast() {
        breakfast = "";
        EventLog.getInstance().logEvent(new Event("Breakfast is cleared on " + day));
    }

    // MODIFIES: this
    // EFFECTS: set lunch to empty string
    public void clearLunch() {
        lunch = "";
        EventLog.getInstance().logEvent(new Event("Lunch is cleared on " + day));
    }

    // MODIFIES: this
    // EFFECTS: set dinner to empty string
    public void clearDinner() {
        dinner = "";
        EventLog.getInstance().logEvent(new Event("Dinner is cleared on " + day));
    }

    // EFFECTS: convert daily meal plan to json
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Day", day);
        json.put("Breakfast", breakfast);
        json.put("Lunch", lunch);
        json.put("Dinner", dinner);
        return json;
    }
}
