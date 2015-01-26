package info.jiangchuan.wrca.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.models.Event;

/**
 * Created by jiangchuan on 1/26/15.
 */
public class EventParser {
    public static final String EVENTS = "events";
    public static final String LIST_COUNT = "list_count";

    public static final String EVENT_TITLE = "title";
    public static final String EVENT_ADDRESS = "address";
    public static final String EVENT_TIME = "time";
    public static final String EVENT_DESC = "description";
    public static final String EVENT_THUMB = "thumbnail_url";
    public static final String EVENT_CONTACT = "contact";

    public static Event parse(JsonObject jsonObject) {
        return new Event(jsonObject.get(EVENT_TITLE).getAsString(), // title
                jsonObject.get(EVENT_ADDRESS).getAsString(), // location
                jsonObject.get(EVENT_TIME).getAsString(), // time
                jsonObject.get(EVENT_DESC).getAsString(), // desc
                jsonObject.get(EVENT_THUMB).getAsString(), // thumbnail
                jsonObject.get(EVENT_CONTACT).getAsString()); // contact
    }

    public static List<Event> parse(JsonArray array) throws JSONException {
        List<Event> list = new ArrayList<Event>();
        for (int i = 0; i < array.size(); i++) {
            JsonElement obj = array.get(i);
            Event event = new Event();
            event.setTitle(obj.getAsJsonObject().get("title").getAsString());
            event.setThumbnailUrl(obj.getAsJsonObject().get("thumbnail_url").getAsString());
            event.setLocation(obj.getAsJsonObject().get("location").getAsString());
            event.setDescription(obj.getAsJsonObject().get("description").getAsString());
            event.setTime(obj.getAsJsonObject().get("time").getAsString());
            // adding event to events arrayay
            list.add(event);

        }
        return list;
    }
}
