package saim.com.askrambler.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import saim.com.askrambler.Adapter.AdapterPost;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.CircleTransform;
import saim.com.askrambler.Util.MySingleton;
import saim.com.askrambler.Util.SharedPrefDatabase;

public class PostDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    Toolbar toolbarPostDetail;

    ProgressDialog progressDialog;
    LinearLayout layoutPostDetailMain;
    private SliderLayout mDemoSlider;
    public List<String> bannerImage = new ArrayList<>();
    public String post_id = "";

    String from_where, to_where, from_date, to_date, payment_category, gender, traveling_by, isType,
            baggage_type, baggage_weight, trip_category, trip_category_id, transport_type, trip_duration,
            location, travelers, offers_1, offers_2, offers_3, smoking_habit, alcohol_habit, images,
            contacts, details, ad_type, ad_type_id, date, status, user_id, user_photo, vote_count, user_name, user_email, user_phone, user_verify, rateing;

    public LinearLayout layoutDetailCompanion, layoutDetailBaggage, layoutDetailTrip, layoutDetailHost;
    public TextView txtPDDetail, txtPDLocation, txtPDService, txtPDHost, txtPDTraveler, txtPDOffering,
            txtPDSmocking, txtPDAlcohol, txtPDUserName, txtPDUserEmail, txtPDUserPhone;
    //Companion Textview
    public TextView txtPDCompDestination, txtPDCompTravelDate, txtPDCompServiceType, txtPDCompGender, txtPDCompTravelBy;
    //Baggage Textview
    public TextView txtPDBagDestination, txtPDBagTravelDate, txtPDBagServiceType, txtPDBagBaggage, txtPDBagBaggageType, txtPDBagBaggageWeight;
    //Baggage Textview
    public TextView txtPDTripDestination, txtPDTripTravelDate, txtPDTripServiceType, txtPDTripTrip, txtPDTripCategory, txtPDTripType, txtPDTripDuration;
    public ImageView imgPDStatus, imgPDUser;

    public RatingBar ratingBar;
    public TextView txtRateing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_post_detail);

        init();
    }


    public void init() {
        toolbarPostDetail = (Toolbar) findViewById(R.id.toolbarPostDetail);
        toolbarPostDetail.setTitle("Post Detail");
        setSupportActionBar(toolbarPostDetail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait data is loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        post_id = getIntent().getExtras().getString("POST_ID");

        layoutPostDetailMain = (LinearLayout) findViewById(R.id.layoutPostDetailMain);
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        layoutDetailCompanion = (LinearLayout) findViewById(R.id.layoutDetailCompanion);
        layoutDetailBaggage = (LinearLayout) findViewById(R.id.layoutDetailBaggage);
        layoutDetailTrip = (LinearLayout) findViewById(R.id.layoutDetailTrip);
        layoutDetailHost = (LinearLayout) findViewById(R.id.layoutDetailHost);

        txtPDDetail = (TextView) findViewById(R.id.txtPDDetail);
        txtPDLocation = (TextView) findViewById(R.id.txtPDLocation);
        txtPDService = (TextView) findViewById(R.id.txtPDService);
        txtPDHost = (TextView) findViewById(R.id.txtPDHost);
        txtPDTraveler = (TextView) findViewById(R.id.txtPDTraveler);
        txtPDOffering = (TextView) findViewById(R.id.txtPDOffering);
        txtPDSmocking = (TextView) findViewById(R.id.txtPDSmocking);
        txtPDAlcohol = (TextView) findViewById(R.id.txtPDAlcohol);
        txtPDUserName = (TextView) findViewById(R.id.txtPDUserName);
        txtPDUserEmail = (TextView) findViewById(R.id.txtPDUserEmail);
        txtPDUserPhone = (TextView) findViewById(R.id.txtPDUserPhone);

        imgPDStatus = (ImageView) findViewById(R.id.imgPDStatus);
        imgPDUser = (ImageView) findViewById(R.id.imgPDUser);

        //Companion
        txtPDCompDestination = (TextView) findViewById(R.id.txtPDCompDestination);
        txtPDCompTravelDate = (TextView) findViewById(R.id.txtPDCompTravelDate);
        txtPDCompServiceType = (TextView) findViewById(R.id.txtPDCompServiceType);
        txtPDCompGender = (TextView) findViewById(R.id.txtPDCompGender);
        txtPDCompTravelBy = (TextView) findViewById(R.id.txtPDCompTravelBy);

        //Baggage
        txtPDBagDestination = (TextView) findViewById(R.id.txtPDBagDestination);
        txtPDBagTravelDate = (TextView) findViewById(R.id.txtPDBagTravelDate);
        txtPDBagServiceType = (TextView) findViewById(R.id.txtPDBagServiceType);
        txtPDBagBaggage = (TextView) findViewById(R.id.txtPDBagBaggage);
        txtPDBagBaggageType = (TextView) findViewById(R.id.txtPDBagBaggageType);
        txtPDBagBaggageWeight = (TextView) findViewById(R.id.txtPDBagBaggageWeight);

        //Trip
        txtPDTripDestination = (TextView) findViewById(R.id.txtPDTripDestination);
        txtPDTripTravelDate = (TextView) findViewById(R.id.txtPDTripTravelDate);
        txtPDTripServiceType = (TextView) findViewById(R.id.txtPDTripServiceType);
        txtPDTripTrip = (TextView) findViewById(R.id.txtPDTripTrip);
        txtPDTripCategory = (TextView) findViewById(R.id.txtPDTripCategory);
        txtPDTripType = (TextView) findViewById(R.id.txtPDTripType);
        txtPDTripDuration = (TextView) findViewById(R.id.txtPDTripDuration);

        txtRateing = (TextView) findViewById(R.id.txtRateing);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser == true){
                    DialogRatingSubmit(Splash.user_id, user_id, post_id, rating+"");
                }
            }
        });

        SaveGetPostInformation();

        txtPDUserEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(txtPDUserEmail.getText().toString().trim());
            }
        });

        txtPDUserPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_DIAL, Uri.parse("tel:"+txtPDUserPhone.getText().toString()));
                startActivity(intent);
            }
        });

    }

    public void PopulateInformationCompanion(){
        txtPDDetail.setText(details);

        txtPDCompDestination.setText(to_where);
        txtPDCompTravelDate.setText(from_date);
        txtPDCompServiceType.setText(payment_category);
        txtPDCompGender.setText(gender);
        txtPDCompTravelBy.setText(traveling_by);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getApplicationContext())
                .load(user_photo).transform(new CircleTransform(getApplicationContext()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgPDUser);


    }

    public void PopulateInformationBaggage(){
        txtPDDetail.setText(details);

        txtPDBagDestination.setText(to_where);
        txtPDBagTravelDate.setText(from_date);
        txtPDBagServiceType.setText(payment_category);
        txtPDBagBaggage.setText(isType);
        txtPDBagBaggageType.setText(baggage_type);
        txtPDBagBaggageWeight.setText(baggage_weight);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getApplicationContext())
                .load(user_photo).transform(new CircleTransform(getApplicationContext()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgPDUser);
    }

    public void PopulateInformationTrip(){
        txtPDDetail.setText(details);

        txtPDTripDestination.setText(to_where);
        txtPDTripTravelDate.setText(from_date);
        txtPDTripServiceType.setText(payment_category);
        txtPDTripTrip.setText(isType);
        txtPDTripCategory.setText(trip_category);
        txtPDTripType.setText(transport_type);
        txtPDTripDuration.setText(trip_duration);

        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getApplicationContext())
                .load(user_photo).transform(new CircleTransform(getApplicationContext()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgPDUser);
    }

    public void PopulateInformationHost(){
        txtPDDetail.setText(details);
        txtPDLocation.setText(location);
        txtPDService.setText(payment_category);
        txtPDHost.setText(isType);
        txtPDTraveler.setText(travelers);
        txtPDOffering.setText(offers_1);
        txtPDSmocking.setText(smoking_habit);
        txtPDAlcohol.setText(alcohol_habit);
        txtPDUserName.setText(user_name);
        txtPDUserEmail.setText(user_email);
        txtPDUserPhone.setText(user_phone);

        if (user_verify.equals("1")){
            imgPDStatus.setImageResource(R.drawable.ic_verified_user);
        }else if (user_verify.equals("0")){
            imgPDStatus.setImageResource(R.drawable.ic_not_varified_user);
        }

        Glide.with(getApplicationContext())
                .load(user_photo).transform(new CircleTransform(getApplicationContext()))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgPDUser);
    }

    public void PopulateSlider() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        for (int i = 0; i < bannerImage.size(); i++) {
            url_maps.put("Image " + i + 1, bannerImage.get(i));
            Log.d("IMAGE LINK", bannerImage.get(i));
        }
        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getApplicationContext());
            textSliderView.description(name).image(url_maps.get(name)).setScaleType(BaseSliderView.ScaleType.Fit).setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(PostDetailActivity.this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getApplicationContext(), slider.getBundle().get("extra") + "" + slider.getUrl(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void StringToList(String s) {
        for (; ; ) {
            if (s.contains(",")) {
                String a = s.substring(0, s.indexOf(","));
                s = s.replace(a + ",", "");
                bannerImage.add("http://askrambler.com/uploads/" + a);
            } else {
                bannerImage.add("http://askrambler.com/uploads/" + s);
                break;
            }
        }
    }

    public void SaveGetPostInformation() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPostDetail+ post_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM RESPONSE", response);
                        progressDialog.dismiss();
                        layoutPostDetailMain.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);
                                from_where = jsonObjectList.getString("from_where");
                                to_where = jsonObjectList.getString("to_where");
                                from_date = jsonObjectList.getString("from_date");
                                to_date = jsonObjectList.getString("to_date");
                                payment_category = jsonObjectList.getString("payment_category");
                                gender = jsonObjectList.getString("gender");
                                traveling_by = jsonObjectList.getString("traveling_by");
                                isType = jsonObjectList.getString("isType");
                                baggage_type = jsonObjectList.getString("baggage_type");
                                baggage_weight = jsonObjectList.getString("baggage_weight");
                                trip_category = jsonObjectList.getString("trip_category");
                                trip_category_id = jsonObjectList.getString("trip_category_id");
                                transport_type = jsonObjectList.getString("transport_type");
                                trip_duration = jsonObjectList.getString("trip_duration");
                                location = jsonObjectList.getString("location");
                                travelers = jsonObjectList.getString("travelers");
                                offers_1 = jsonObjectList.getString("offers_1");
                                offers_2 = jsonObjectList.getString("offers_2");
                                offers_3 = jsonObjectList.getString("offers_3");
                                smoking_habit = jsonObjectList.getString("smoking_habit");
                                alcohol_habit = jsonObjectList.getString("alcohol_habit");
                                images = jsonObjectList.getString("images");
                                StringToList(images);
                                contacts = jsonObjectList.getString("contacts");
                                details = jsonObjectList.getString("details");
                                ad_type = jsonObjectList.getString("ad_type");
                                ad_type_id = jsonObjectList.getString("ad_type_id");
                                date = jsonObjectList.getString("date");
                                status = jsonObjectList.getString("status");
                                user_id = jsonObjectList.getString("user_id");
                                user_photo = jsonObjectList.getString("user_photo");
                                vote_count = jsonObjectList.getString("vote_count");
                                user_name = jsonObjectList.getString("user_name");
                                user_email = jsonObjectList.getString("user_email");
                                user_phone = jsonObjectList.getString("user_phone");
                                user_verify = jsonObjectList.getString("user_verify");
                                rateing = jsonObjectList.getString("rateing");
                            }
                            PopulateSlider();
                            if (ad_type.equals("Companion")){
                                layoutDetailCompanion.setVisibility(View.VISIBLE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationCompanion();
                            } else if (ad_type.equals("Host")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.VISIBLE);
                                PopulateInformationHost();
                            } else if (ad_type.equals("Baggage")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.VISIBLE);
                                layoutDetailTrip.setVisibility(View.GONE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationBaggage();
                            } else if (ad_type.equals("Trip")){
                                layoutDetailCompanion.setVisibility(View.GONE);
                                layoutDetailBaggage.setVisibility(View.GONE);
                                layoutDetailTrip.setVisibility(View.VISIBLE);
                                layoutDetailHost.setVisibility(View.GONE);
                                PopulateInformationTrip();
                            }
                            System.out.println(rateing);
                            if (!rateing.equals("null")){
                                if (rateing.length()>2){
                                    txtRateing.setText("Rate: "+ rateing.substring(0,3));
                                }else {
                                    txtRateing.setText("Rate: "+ rateing);
                                }
                                float rate = Float.parseFloat(rateing);
                                ratingBar.setRating(rate);
                            }else {
                                txtRateing.setText("Rate: 0.0");
                                float rate = Float.parseFloat("0.0");
                                ratingBar.setRating(rate);
                            }

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    protected void sendEmail(String mail) {
        String[] TO = {mail};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ask Rambler");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PostDetailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void DialogRatingSubmit(final String userID, final String postUserID, final String postID, final String rateing){
        final AlertDialog.Builder builder = new AlertDialog.Builder(PostDetailActivity.this);
        builder.setTitle("Submit Rating");
        builder.setMessage("Please rate this post. Your rating very precious for us.");
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
        });
        builder.setPositiveButton("YES",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin() == null || new SharedPrefDatabase(getApplicationContext()).RetriveLogin().equals("No")){
                            Toast.makeText(getApplicationContext(), "You are not login\nPlease login first", Toast.LENGTH_SHORT).show();
                        }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().toString().equals("Yes") ){
                            progressDialog.show();
                            SubmitRating(userID, postUserID, postID, rateing);
                        }

                    }
        });
        builder.show();
    }


    public void SubmitRating(final String userID, final String postUserID, final String postID, final String rateing) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.submitRating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id_voter", userID);
                params.put("post_user_id", postUserID);
                params.put("ads_id", postID);
                params.put("vote", rateing);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
