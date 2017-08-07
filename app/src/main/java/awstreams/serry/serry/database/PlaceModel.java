package awstreams.serry.serry.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import awstreams.serry.serry.models.Image;
import awstreams.serry.serry.models.Place;

/**
 * Created by Serry on 7/8/2017.
 */
@Table(database = AppDatabase.class)
public class PlaceModel extends BaseModel {
    public PlaceModel() {
    }

    public PlaceModel(String id, String description, String price, Image image) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    @PrimaryKey
    @Column
    String id;
    @Column
    String description;
    @Column
    String price;
    @Column
    @ForeignKey(saveForeignKeyModel = true)
    Image image;

    public Place getPlace() {
        return new Place(id, description, price, image);
    }

}
