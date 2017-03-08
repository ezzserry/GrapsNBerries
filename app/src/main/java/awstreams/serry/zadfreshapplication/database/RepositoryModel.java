package awstreams.serry.zadfreshapplication.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import awstreams.serry.zadfreshapplication.models.Owner;
import awstreams.serry.zadfreshapplication.models.Repository;

/**
 * Created by PC on 3/8/2017.
 */
@Table(database = AppDatabase.class)
public class RepositoryModel extends BaseModel {
    public RepositoryModel() {
    }

    public RepositoryModel(String id, String repoName, String description, Owner ownerName) {
        this.id = id;
        this.repoName = repoName;
        this.description = description;
        this.ownerName = ownerName;
    }

    @PrimaryKey
    @Column
    String id;

    @Column
    String repoName;

    @Column
    String description;

    @Column
    Owner ownerName;

    @Column
    String fork;

    @Column
    String html_url;


    public Repository getRepository() {
        return new Repository(id, repoName, description, ownerName, fork, html_url);
    }

}
