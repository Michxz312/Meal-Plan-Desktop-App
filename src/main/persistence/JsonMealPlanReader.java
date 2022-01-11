package persistence;

import model.DailyMealPlan;
import model.WeeklyMealPlan;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Represents a reader that reader JSON representation of weekly meal plan to file
// This class is written from a reference from JsonSerializationDemo
public class JsonMealPlanReader {
    private String source;

    // MODIFIES: this
    // EFFECTS: constructs reader to read from source file
    public JsonMealPlanReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads recipe from file and returns it
    // throws IOException if an error occurs reading data from file
    public WeeklyMealPlan read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWeeklyMealPlan(jsonObject);
    }

    // EFFECTS: read source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses DailyMealPlan from JSON object and returns it
    private WeeklyMealPlan parseWeeklyMealPlan(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Week");
        List<DailyMealPlan> days = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextDay = (JSONObject) json;
            addDaysToList(days, nextDay);
        }
        return new WeeklyMealPlan(days);
    }

    // MODIFIES: days
    // EFFECTS: parses days from JSON object and adds it to ingredientList
    private void addDaysToList(List<DailyMealPlan> days, JSONObject jsonObject) {
        addMealsToDays(days, jsonObject);
    }

    // MODIFIES: days
    // EFFECTS: parses days from JSON object and adds it to ingredientList
    private void addMealsToDays(List<DailyMealPlan> days, JSONObject jsonObject) {
        String day = jsonObject.getString("Day");
        String breakfast = jsonObject.getString("Breakfast");
        String lunch = jsonObject.getString("Lunch");
        String dinner = jsonObject.getString("Dinner");
        DailyMealPlan daily = new DailyMealPlan(day);
        daily.setBreakfast(breakfast);
        daily.setLunch(lunch);
        daily.setDinner(dinner);

        days.add(daily);
    }
}
