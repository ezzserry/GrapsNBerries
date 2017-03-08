package awstreams.serry.zadfreshapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 3/8/2017.
 */

public class Repository implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("owner")
    Owner owner;
    @SerializedName("fork")
    String fork;
    @SerializedName("html_url")
    String html_url;

    public Repository(String id, String name, String description, Owner owner, String fork, String html_url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.fork = fork;
        this.html_url = html_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
