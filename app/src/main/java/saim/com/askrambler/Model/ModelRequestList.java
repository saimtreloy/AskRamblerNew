package saim.com.askrambler.Model;

/**
 * Created by NREL on 4/24/18.
 */

public class ModelRequestList {

    String id, ad_id, status, date, sender_user_name, sender_user_email, sender_user_mobile;

    public ModelRequestList(String id, String ad_id, String status, String date, String sender_user_name, String sender_user_email) {
        this.id = id;
        this.ad_id = ad_id;
        this.status = status;
        this.date = date;
        this.sender_user_name = sender_user_name;
        this.sender_user_email = sender_user_email;
    }

    public ModelRequestList(String id, String ad_id, String status, String date, String sender_user_name, String sender_user_email, String sender_user_mobile) {
        this.id = id;
        this.ad_id = ad_id;
        this.status = status;
        this.date = date;
        this.sender_user_name = sender_user_name;
        this.sender_user_email = sender_user_email;
        this.sender_user_mobile = sender_user_mobile;
    }

    public String getId() {
        return id;
    }

    public String getAd_id() {
        return ad_id;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getSender_user_name() {
        return sender_user_name;
    }

    public String getSender_user_email() {
        return sender_user_email;
    }

    public String getSender_user_mobile() {
        return sender_user_mobile;
    }
}
