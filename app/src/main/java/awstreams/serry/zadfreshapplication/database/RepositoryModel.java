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

    public RepositoryModel(String id, String name, String description, String fork, String htmlUrl, String ownerName, String ownerHtmlUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fork = fork;
        this.htmlUrl = htmlUrl;
        this.ownerName = ownerName;
        this.ownerHtmlUrl = ownerHtmlUrl;
    }

    @PrimaryKey
    @Column
    String id;

    @Column
    String name;

    @Column
    String description;

    @Column
    String fork;

    @Column
    String htmlUrl;

    @Column
    String ownerName;

    @Column
    String ownerHtmlUrl;

    public Repository getRepository() {
        return new Repository(id, name, description, fork, htmlUrl, ownerName, ownerHtmlUrl);
    }

}
