package info.jiangchuan.wrca.parsers;

import com.google.gson.JsonObject;

/**
 * Created by jiangchuan on 1/29/15.
 */
public class ParserUtil {

    private static final String AUTH_TOKEN = "authToken";

    public static String parseToken(JsonObject jsonObject) {
        return jsonObject.get(AUTH_TOKEN).getAsString();
    }
}
