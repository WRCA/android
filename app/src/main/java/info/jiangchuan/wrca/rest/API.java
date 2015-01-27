package info.jiangchuan.wrca.rest;

import retrofit.http.FieldMap;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.FormUrlEncoded;

import java.util.Map;
import retrofit.Callback;
import com.google.gson.*;
/**
 * Created by jiangchuan on 1/25/15.
 */
public interface API {
    @GET("/account/verificationCode")
    void vericode(
            @Query("email") String email,
            Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/account/login")
    void login(
            @FieldMap Map<String, String> params,
            Callback<JsonObject> callback);

    @FormUrlEncoded
    @POST("/account/register")
    void register(
            @FieldMap Map<String, String> params,
            Callback<JsonObject> callback);


    @GET(RestConst.PATH_EVENTS)
    void events(
            @Query(RestConst.REQ_PARAM_TOKEN) String token,
            @Query(RestConst.REQ_PARAM_RANGE) String range,
            @Query(RestConst.REQ_PARAM_OFFSET) String offset,
            @Query(RestConst.REQ_PARAM_LIMIT) String limit,
            Callback<JsonObject> callback);

    @GET(RestConst.PATH_account_password)
    void password(
            @Query("email") String email,
            Callback<JsonObject> callback);
}
