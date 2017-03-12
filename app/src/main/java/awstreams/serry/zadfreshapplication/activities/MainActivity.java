package awstreams.serry.zadfreshapplication.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import awstreams.serry.zadfreshapplication.R;
import awstreams.serry.zadfreshapplication.adapters.RepositoriesAdapter;
import awstreams.serry.zadfreshapplication.database.RepositoryModel;
import awstreams.serry.zadfreshapplication.helpers.ConnectionDetector;
import awstreams.serry.zadfreshapplication.helpers.EndlessRecyclerViewScrollListener;
import awstreams.serry.zadfreshapplication.helpers.ServicesHelper;
import awstreams.serry.zadfreshapplication.models.Repository;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_repositories)
    RecyclerView rvRepositories;
    //    @BindView(R.id.progressBar)
//    ProgressBar progressBar;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Boolean isInternetPresent = false;
    private ConnectionDetector cd;
    private int repositoryPage = 0;

    private LinearLayoutManager layoutManager;
    private RepositoriesAdapter repositoriesAdapter;
    private List<Repository> repositoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        getRepositories(repositoryPage);

    }

    private void initViews() {
        repositoryList = new ArrayList<>();
        repositoriesAdapter = new RepositoriesAdapter(repositoryList, this);
        layoutManager = new LinearLayoutManager(this);
        rvRepositories.setLayoutManager(layoutManager);
        rvRepositories.setAdapter(repositoriesAdapter);

        if (checkConnection())
            getRepositories(repositoryPage);
        else
            Snackbar.make(rvRepositories, "check your connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();

        rvRepositories.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (checkConnection()) {
                    repositoryPage++;
                    getRepositories(repositoryPage);
                } else
                    Snackbar.make(rvRepositories, "check your connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkConnection()) {
                    getRepositories(0);
                } else {
                    Snackbar.make(rvRepositories, "could't refresh now", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void getRepositories(int page) {
        ServicesHelper.getInstance().getRepos(this, String.valueOf(page), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("response", response.toString());
                Type collectionType = new TypeToken<List<Repository>>() {
                }.getType();

                List<Repository> repositories = (List<Repository>) new Gson().fromJson(response.toString(), collectionType);
                repositoryList.addAll(repositories);
                repositoriesAdapter.notifyItemRangeInserted(repositoriesAdapter.getItemCount(), repositoryList.size() - 1);
                Log.e("list size", repositoryList.size() + "");
                for (Repository repository : repositoryList) {
                    RepositoryModel categoryModel = new RepositoryModel(repository.getId(), repository.getName(), repository.getDescription(), repository.getFork(), repository.getOwner().getLogin(), repository.getOwner().getHtml_url());
                    categoryModel.save();
                }

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(rvRepositories, "check your connection", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

    public void updateUI(List<Repository> repositoryList) {
//        progressBar.setVisibility(View.GONE);
        rvRepositories.setVisibility(View.VISIBLE);
        repositoriesAdapter = new RepositoriesAdapter(repositoryList, this);
        rvRepositories.setAdapter(repositoriesAdapter);
        repositoriesAdapter.notifyDataSetChanged();
    }
}
