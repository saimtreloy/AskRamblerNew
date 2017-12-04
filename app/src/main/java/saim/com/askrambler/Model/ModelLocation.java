package saim.com.askrambler.Model;

/**
 * Created by NREL on 12/4/17.
 */

public class ModelLocation {
    String id, ads_id, post_user_id, lat, lon;

    public ModelLocation(String id, String ads_id, String post_user_id, String lat, String lon) {
        this.id = id;
        this.ads_id = ads_id;
        this.post_user_id = post_user_id;
        this.lat = lat;
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public String getAds_id() {
        return ads_id;
    }

    public String getPost_user_id() {
        return post_user_id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
