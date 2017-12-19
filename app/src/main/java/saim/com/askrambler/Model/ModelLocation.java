package saim.com.askrambler.Model;

/**
 * Created by NREL on 12/4/17.
 */

public class ModelLocation {
    String ads_id, post_user_id, lat, lon, payment_category, isType, location, userName;

    public ModelLocation(String ads_id, String post_user_id, String lat, String lon, String payment_category, String isType, String location, String userName) {
        this.ads_id = ads_id;
        this.post_user_id = post_user_id;
        this.lat = lat;
        this.lon = lon;
        this.payment_category = payment_category;
        this.isType = isType;
        this.location = location;
        this.userName = userName;
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

    public String getPayment_category() {
        return payment_category;
    }

    public String getIsType() {
        return isType;
    }

    public String getLocation() {
        return location;
    }

    public String getUserName() {
        return userName;
    }
}
