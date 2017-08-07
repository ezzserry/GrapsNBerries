package awstreams.serry.serry.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.ProgressBar;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import awstreams.serry.serry.R;
import awstreams.serry.serry.adapters.PlacesListAdapter;
import awstreams.serry.serry.apis.GetPlaces;

import awstreams.serry.serry.database.PlaceModel;
import awstreams.serry.serry.helpers.ConnectionDetector;
import awstreams.serry.serry.helpers.Constants;
import awstreams.serry.serry.helpers.EndlessRecyclerViewScrollListener;
import awstreams.serry.serry.helpers.Utils;
import awstreams.serry.serry.interfaces.OnPlaceClickListener;
import awstreams.serry.serry.models.Place;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Serry on 7/8/2017.
 */
public class MainActivity extends AppCompatActivity implements OnPlaceClickListener {
    @BindView(R.id.rv_places)
    RecyclerView rvPlaces;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Boolean isInternetPresent = false;
    private ConnectionDetector cd;

    private LinearLayoutManager layoutManager;
    private PlacesListAdapter placesListAdapter;
    private List<Place> places;
    private int pCount = 10;
    private int pFrom = 0;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        places = new ArrayList<>();
        placesListAdapter = new PlacesListAdapter(places, this);
        layoutManager = new GridLayoutManager(this, 2);
        rvPlaces.setLayoutManager(layoutManager);
        rvPlaces.setAdapter(placesListAdapter);

        if (checkConnection())
            getPlaces(pCount, pFrom);
        else {

            List<PlaceModel> placeModels = SQLite.select().from(PlaceModel.class).queryList();
            if (placeModels.size() >= 1) {
                progressBar.setVisibility(View.GONE);
                for (PlaceModel repositoryModel : placeModels) {
                    places.add(repositoryModel.getPlace());
                }
                placesListAdapter.notifyItemRangeInserted(placesListAdapter.getItemCount(), places.size() - 1);
            } else
                Snackbar.make(rvPlaces, "no saved data", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        }

        rvPlaces.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (checkConnection()) {
                    pFrom = pFrom + pCount;
                    getPlaces(pCount, pFrom);
                } else
                    Snackbar.make(rvPlaces, "couldn't load more", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkConnection()) {
                    getPlaces(pCount, 0);
                } else {
                    Snackbar.make(rvPlaces, "could't refresh now", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void getPlaces(int count, int from) {
        progressBar.setVisibility(View.GONE);
        retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Utils.newInstance().getClient(this))
                .build();
        GetPlaces getPlaces = retrofit.create(GetPlaces.class);
        Call<List<Place>> placeListCall = getPlaces.getPlaces(String.valueOf(count), String.valueOf(from));
        placeListCall.enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                List<Place> newPlaces = response.body();
                places.addAll(newPlaces);
                placesListAdapter.notifyItemRangeInserted(placesListAdapter.getItemCount(), places.size() - 1);
                for (Place place : places) {
                    PlaceModel placeModel = new PlaceModel(place.getId(), place.getProductDescription(), place.getPrice(), place.getImage());
                    placeModel.save();
                }
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(rvPlaces, "check your connection error", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }

    private Boolean checkConnection() {
        cd = new ConnectionDetector(this);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
            return true;
        else
            return false;
    }

    @Override
    public void onPlaceClick(Place place) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.KEY_INTENT, place);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
