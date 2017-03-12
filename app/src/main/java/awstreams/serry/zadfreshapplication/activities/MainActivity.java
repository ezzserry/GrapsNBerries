package awstreams.serry.zadfreshapplication.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
import awstreams.serry.zadfreshapplication.interfaces.OnItemLongListener;
import awstreams.serry.zadfreshapplication.models.Repository;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemLongListener {
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
    }

    private void initViews() {
        repositoryList = new ArrayList<>();
        repositoriesAdapter = new RepositoriesAdapter(repositoryList, this);
        layoutManager = new LinearLayoutManager(this);
        rvRepositories.setLayoutManager(layoutManager);
        rvRepositories.setAdapter(repositoriesAdapter);

        if (checkConnection())
            getRepositories(repositoryPage);
        else {

            List<RepositoryModel> repositoryModels = SQLite.select().from(RepositoryModel.class).queryList();
            if (repositoryModels.size() >= 1) {
                for (RepositoryModel repositoryModel : repositoryModels) {
                    repositoryList.add(repositoryModel.getRepository());
                }
                repositoriesAdapter.notifyItemRangeInserted(repositoriesAdapter.getItemCount(), repositoryList.size() - 1);
            } else
                Snackbar.make(rvRepositories, "no saved data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

        rvRepositories.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (checkConnection()) {
                    repositoryPage++;
                    getRepositories(repositoryPage);
                } else
                    Snackbar.make(rvRepositories, "couldn't load more", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (checkConnection()) {
                    RepositoryModel repositoryModel = new RepositoryModel();
                    repositoryModel.delete();
                    getRepositories(0);
                } else {
                    Snackbar.make(rvRepositories, "could't refresh now", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void getRepositories(int page) {
        ServicesHelper.getInstance().getRepository(this, String.valueOf(page), new Response.Listener<JSONArray>() {
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
                    RepositoryModel repositoryModel = new RepositoryModel(repository.getId(), repository.getName(), repository.getDescription(), repository.getFork(), repository.getHtml_url(),
                            repository.getOwner().getLogin(), repository.getOwner().getHtml_url());
                    repositoryModel.save();
                }

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(rvRepositories, "check your connection error", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
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
    public void OnLongItemClick(final Repository repositoryItem) {
        new AlertDialog.Builder(this)
                .setTitle("Zad Fresh Task")
                .setMessage("Choose from the next urls ?")
                .setPositiveButton("show repository's url", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositoryItem.getHtml_url()));
                        startActivity(browserIntent);
                    }
                })
                .setNegativeButton("show owner's url", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent;

                        try {

                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositoryItem.getOwner().getHtml_url()));
                        } catch (NullPointerException e) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(repositoryItem.getOwnerHtmlUrl()));

                        }
                        startActivity(browserIntent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
