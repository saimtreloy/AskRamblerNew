package saim.com.askrambler.Model;

/**
 * Created by Android on 8/6/2017.
 */

public class ModelPost {
    public String from_where, to_where, from_date, to_date, payment_category, gender, traveling_by, isType, baggage_type, baggage_weight, trip_category,
            trip_category_id, transport_type, trip_duration, location, travelers, offers_1, offers_2, offers_3, smoking_habit, alcohol_habit, images, contacts,
            details, ad_type, ad_type_id, date, status, user_id, user_photo, vote_count;

    public ModelPost(String from_where, String to_where, String from_date, String to_date, String payment_category, String gender, String traveling_by, String isType, String baggage_type, String baggage_weight, String trip_category, String trip_category_id, String transport_type, String trip_duration, String location, String travelers, String offers_1, String offers_2, String offers_3, String smoking_habit, String alcohol_habit, String images, String contacts, String details, String ad_type, String ad_type_id, String date, String status, String user_id, String user_photo, String vote_count) {
        this.from_where = from_where;
        this.to_where = to_where;
        this.from_date = from_date;
        this.to_date = to_date;
        this.payment_category = payment_category;
        this.gender = gender;
        this.traveling_by = traveling_by;
        this.isType = isType;
        this.baggage_type = baggage_type;
        this.baggage_weight = baggage_weight;
        this.trip_category = trip_category;
        this.trip_category_id = trip_category_id;
        this.transport_type = transport_type;
        this.trip_duration = trip_duration;
        this.location = location;
        this.travelers = travelers;
        this.offers_1 = offers_1;
        this.offers_2 = offers_2;
        this.offers_3 = offers_3;
        this.smoking_habit = smoking_habit;
        this.alcohol_habit = alcohol_habit;
        this.images = images;
        this.contacts = contacts;
        this.details = details;
        this.ad_type = ad_type;
        this.ad_type_id = ad_type_id;
        this.date = date;
        this.status = status;
        this.user_id = user_id;
        this.user_photo = user_photo;
        this.vote_count = vote_count;
    }

    public String getFrom_where() {
        return from_where;
    }

    public String getTo_where() {
        return to_where;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public String getPayment_category() {
        return payment_category;
    }

    public String getGender() {
        return gender;
    }

    public String getTraveling_by() {
        return traveling_by;
    }

    public String getIsType() {
        return isType;
    }

    public String getBaggage_type() {
        return baggage_type;
    }

    public String getBaggage_weight() {
        return baggage_weight;
    }

    public String getTrip_category() {
        return trip_category;
    }

    public String getTrip_category_id() {
        return trip_category_id;
    }

    public String getTransport_type() {
        return transport_type;
    }

    public String getTrip_duration() {
        return trip_duration;
    }

    public String getLocation() {
        return location;
    }

    public String getTravelers() {
        return travelers;
    }

    public String getOffers_1() {
        return offers_1;
    }

    public String getOffers_2() {
        return offers_2;
    }

    public String getOffers_3() {
        return offers_3;
    }

    public String getSmoking_habit() {
        return smoking_habit;
    }

    public String getAlcohol_habit() {
        return alcohol_habit;
    }

    public String getImages() {
        return images;
    }

    public String getContacts() {
        return contacts;
    }

    public String getDetails() {
        return details;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getAd_type_id() {
        return ad_type_id;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public String getVote_count() {
        return vote_count;
    }
}
