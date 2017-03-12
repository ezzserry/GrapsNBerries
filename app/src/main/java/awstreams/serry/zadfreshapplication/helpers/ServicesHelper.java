package awstreams.serry.zadfreshapplication.helpers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

/**
 * Created by PC on 3/8/2017.
 */

public class ServicesHelper {
    private String URL;
    private static ServicesHelper sharedInstance;


    public synchronized static ServicesHelper getInstance() {
        if (sharedInstance == null) {
            sharedInstance = new ServicesHelper();
        }
        return sharedInstance;
    }

    public void getRepository(Context context, String page, Response.Listener<JSONArray> success, Response.ErrorListener errorListener) {
        URL = Constants.URL;
        URL = String.format(URL, page);
        JsonArrayRequest request = new JsonArrayRequest(URL, success, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(request);
    }
}
