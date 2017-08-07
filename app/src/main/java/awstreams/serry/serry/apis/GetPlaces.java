package awstreams.serry.serry.apis;

import java.util.List;

import awstreams.serry.serry.helpers.Constants;
import awstreams.serry.serry.models.Place;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Serry on 7/8/2017.
 */

public interface GetPlaces {
    @GET(Constants.URL_EXPLORE)
    Call<List<Place>> getPlaces(@Query("count") String count,
                                @Query("from") String from);
}
