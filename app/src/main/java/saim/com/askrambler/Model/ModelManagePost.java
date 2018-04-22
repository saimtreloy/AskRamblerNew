package saim.com.askrambler.Model;

/**
 * Created by NREL on 4/22/18.
 */

public class ModelManagePost {

    String id, ad_type, from_where, date, to_date, phone, request;

    public ModelManagePost(String id, String ad_type, String from_where, String date, String to_date, String phone, String request) {
        this.id = id;
        this.ad_type = ad_type;
        this.from_where = from_where;
        this.date = date;
        this.to_date = to_date;
        this.phone = phone;
        this.request = request;
    }

    public String getId() {
        return id;
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

    public String getRequest() {
        return request;
    }
}
