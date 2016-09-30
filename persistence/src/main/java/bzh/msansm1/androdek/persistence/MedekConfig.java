package bzh.msansm1.androdek.persistence;

import io.realm.RealmObject;

/**
 * Created by ronan on 09/08/2016.
 */
public class MedekConfig extends RealmObject {
    private String apiUrl;
    private String token;
    private String discogsToken;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDiscogsToken() {
        return discogsToken;
    }

    public void setDiscogsToken(String discogsToken) {
        this.discogsToken = discogsToken;
    }
}
