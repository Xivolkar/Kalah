package rest.beans;

import java.util.HashMap;

public class GameBean {
    private String id;
    private String url;
    private HashMap<String, Integer> status;

    public GameBean(){
        status = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Integer> getStatus() {
        return status;
    }

    public void setStatus(HashMap<String, Integer> status) {
        this.status = status;
    }
}
