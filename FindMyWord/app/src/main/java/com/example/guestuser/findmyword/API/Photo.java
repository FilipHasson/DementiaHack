package com.example.guestuser.findmyword.API;

/**
 * Created by Marc on 2017-03-05.
 */

public class Photo {

    String id;
    String server;
    String farm;
    String secret;

    public String toUrl() {
        return "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+"_q.jpg";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
