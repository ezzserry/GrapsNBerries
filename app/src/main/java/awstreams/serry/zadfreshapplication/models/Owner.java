package awstreams.serry.zadfreshapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by PC on 3/8/2017.
 */

public class Owner implements Serializable {

    public Owner(String login, String html_url) {
        this.login = login;
        this.html_url = html_url;
    }

    @SerializedName("login")
    String login;
    @SerializedName("html_url")
    String html_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
