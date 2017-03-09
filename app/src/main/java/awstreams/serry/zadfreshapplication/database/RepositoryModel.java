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

    public RepositoryModel(String id, String repoName, String description, String ownerName,String fork) {
        this.id = id;
        this.repoName = repoName;
        this.description = description;
        this.ownerName = ownerName;
        this.fork = fork;
    }

    @PrimaryKey
    @Column
    String id;

    @Column
    String repoName;

    @Column
    String description;

    @Column
    String ownerName;

    @Column
    String fork;


    public Repository getRepository() {
        return new Repository(id, repoName, description, ownerName, fork);
    }

}
