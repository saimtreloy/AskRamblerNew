package saim.com.askrambler.Model;

/**
 * Created by NREL on 4/23/18.
 */

public class ModelRequestedPost {

    String id, status, email, ad_type, from_where, date, to_date, phone, date2;

    public ModelRequestedPost(String id, String status, String email, String ad_type, String from_where, String date, String to_date, String phone, String date2) {
        this.id = id;
        this.status = status;
        this.email = email;
        this.ad_type = ad_type;
        this.from_where = from_where;
        this.date = date;
        this.to_date = to_date;
        this.phone = phone;
        this.date2 = date2;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getFrom_where() {
        return from_where;
    }

    public String getDate() {
        return date;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getPhone() {
        return phone;
    }

    public String getDate2() {
        return date2;
    }
}
