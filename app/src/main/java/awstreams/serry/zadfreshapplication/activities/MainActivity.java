package awstreams.serry.zadfreshapplication.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;

import awstreams.serry.zadfreshapplication.R;
import awstreams.serry.zadfreshapplication.helpers.ConnectionDetector;
import awstreams.serry.zadfreshapplication.helpers.ServicesHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_repositories)
    RecyclerView rv_repositories;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Boolean isInternetPresent = false;
    private ConnectionDetector cd;
    private String page, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rv_repositories.setHasFixedSize(true);
        rv_repositories.setLayoutManager(new LinearLayoutManager(this));
        if (checkConnection())
            getRepositories("0");
        else
            Snackbar.make(rv_repositories, "check your connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkConnection()) {
                    getRepositories("0");
                } else {
                    Snackbar.make(rv_repositories, "could't refresh now", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void getRepositories(String page) {
        ServicesHelper.getInstance().getRepos(this, page, "10", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
}
