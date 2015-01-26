package info.jiangchuan.wrca.parsers;

import com.google.gson.JsonObject;

import info.jiangchuan.wrca.models.Result;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class ResultParser {
    public static final String RESULT_STATUS = "status";
    public static final String RESULT_MESSAGE = "message";

    public static Result parse(JsonObject jsonObject) {
        return new Result( Integer.valueOf( jsonObject.get(RESULT_STATUS).getAsString()), // status
                       jsonObject.get(RESULT_MESSAGE).getAsString()); // message
    }
}
