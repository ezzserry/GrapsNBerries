package awstreams.serry.serry.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.io.Serializable;

import awstreams.serry.serry.database.AppDatabase;

/**
 * Created by Serry on 7/8/2017.
 */
@Table(database = AppDatabase.class)
public class Image implements Serializable {
    @Column
    String width;
    @Column
    String height;
    @PrimaryKey
    @Column
    String url;

    public Image(String width, String height, String url) {
        this.width = width;
        this.height = height;
        this.url = url;
    }

    public Image() {
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
