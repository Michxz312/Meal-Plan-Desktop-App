package model;

import org.json.JSONObject;

// This interface is taken from a reference of JsonSerializationDemo

public interface Writeable {
    //EFFECTS: returns this as JSON object
    JSONObject toJson();
}
