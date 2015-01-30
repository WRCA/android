package info.jiangchuan.wrca.gcm;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.rest.Client;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static info.jiangchuan.wrca.gcm.CommonUtilities.TAG;

/**
 * Created by jiangchuan on 1/24/15.
 */
public class ServerUtilities {
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    /**
     * Register this account/device pair within the server.
     *
     */
    public static void register(final Context context, String name, String email, final String regId) {
        if (Constant.DEBUG)
            Log.i(TAG, "registering device (regId = " + regId + ")");
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("email", email);

        Client.getApi().gcmRegister(params, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                //ToastUtil.showToast(WillowRidge.getInstance(), jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                if (Constant.DEBUG)
                    Log.e(TAG, error.toString());
            }
        });
    }

    /**
     * Unregister this account/device pair within the server.
     */
    public static void unregister(final Context context, final String regId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        Client.getApi().gcmUnRegister(params, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {

//                ToastUtil.showToast(WillowRidge.getInstance(), jsonObject.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                if (Constant.DEBUG)
                    Log.e(TAG, error.toString());
            }
        });
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {

        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        if (Constant.DEBUG)
            Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            if (Constant.DEBUG)
                Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
