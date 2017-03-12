package awstreams.serry.zadfreshapplication.unit_testing;

import android.content.Context;
import android.graphics.Color;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import awstreams.serry.zadfreshapplication.adapters.RepositoriesAdapter;
import awstreams.serry.zadfreshapplication.database.RepositoryModel;
import awstreams.serry.zadfreshapplication.models.Repository;

/**
 * Created by LENOVO on 12/03/2017.
 */

public class MyTests {
    Context context;

    @SmallTest
    public int setForkColor(Repository repository) {

        if (repository.getFork().equals("true"))
            return Color.WHITE;
        else
            return Color.GREEN;

    }

    @LargeTest
    public void getCahceData(List<RepositoryModel> repositoryModels) {
        repositoryModels = SQLite.select().from(RepositoryModel.class).queryList();
        if (repositoryModels.size() == 0) {
            Log.e("cached data", "no data saved");
        }
    }

    @MediumTest
    public void clearCachedData(RepositoryModel repositoryModel) {
        repositoryModel = new RepositoryModel();
        repositoryModel.delete();
    }

    @LargeTest
    public void cacheData(Repository repository) {
        RepositoryModel repositoryModel = new RepositoryModel(repository.getId(), repository.getName(), repository.getDescription(), repository.getFork(), repository.getHtml_url(),
                repository.getOwner().getLogin(), repository.getOwner().getHtml_url());
        repositoryModel.save();

    }

    @LargeTest
    public int checkAdapterCount(List<Repository> repositoryList) {
        RepositoriesAdapter adapter = new RepositoriesAdapter(repositoryList, context);
        return adapter.getItemCount();

    }

}
