package persistence;

import org.json.JSONObject;

// Code adapted from the sample application provided in class
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
