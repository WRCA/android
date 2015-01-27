package info.jiangchuan.wrca.rest;

import retrofit.RestAdapter;
/**
 * Created by jiangchuan on 1/25/15.
 */
public class Client {
    private static API api;

    public static API getApi() {
        if (api == null) {
            RestAdapter.Builder adapterBuilder = new RestAdapter.Builder();
            RestAdapter adapter = adapterBuilder
                    .setEndpoint(RestConst.DOMAIN_WillowRidge)
                    .build();

            api = adapter.create(API.class);
        }
        return api;
    }
}
