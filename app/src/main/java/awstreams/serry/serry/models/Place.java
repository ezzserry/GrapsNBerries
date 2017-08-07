package awstreams.serry.serry.models;

import java.io.Serializable;

/**
 * Created by Serry on 7/8/2017.
 */

public class Place implements Serializable {
    String id;
    String productDescription;
    String price;
    Image image;

    public Place(String id, String productDescription, String price, Image image) {
        this.id = id;
        this.productDescription = productDescription;
        this.price = price;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
