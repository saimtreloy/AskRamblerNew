package saim.com.askrambler.Model;

/**
 * Created by Android on 8/9/2017.
 */

public class ModelPostShort {
    public String ads_id, to_where, to_date,ad_type,details,full_name ,user_photo;


    public ModelPostShort(String ads_id, String to_where, String to_date, String ad_type, String details, String full_name, String user_photo) {
        this.ads_id = ads_id;
        this.to_where = to_where;
        this.to_date = to_date;
        this.ad_type = ad_type;
        this.details = details;
        this.full_name = full_name;
        this.user_photo = user_photo;
    }

    public String getAds_id() {
        return ads_id;
    }

    public String getTo_where() {
        return to_where;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getDetails() {
        return details;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getUser_photo() {
        return user_photo;
    }
}
