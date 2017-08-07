package awstreams.serry.serry.helpers;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Serry on 7/8/2017.
 */

public class Utils {

    public static Utils newInstance() {
        return new Utils();
    }

    public OkHttpClient getClient(final Context context) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(10, TimeUnit.SECONDS);
        return client.build();
    }
}
