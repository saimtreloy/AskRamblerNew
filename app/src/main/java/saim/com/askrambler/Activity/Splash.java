package saim.com.askrambler.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.Model.ModelLocation;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.Model.ModelUser;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;
import saim.com.askrambler.Util.SharedPrefDatabase;

public class Splash extends AppCompatActivity {

    public static ArrayList<ModelPostShort> modelPostsList = new ArrayList<>();
    public static ArrayList<ModelLocation> modelLocationList = new ArrayList<>();
    public static ModelUser modelUser = new ModelUser();

    public static String user_id, nationality, full_name, email, password, agreement, status, roll,
            first_name, last_name, gander, address, city, zip, state, country, mobile, phone, birth_date,
            user_photo, document, verify, website, facebook, instagram, youtube, code1, cornjob,
            like_to, details, server_date,rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("saim.com.askrambler", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("SAIM KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        SaveGetAllPost();
    }


    public void SaveGetAllPost() {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getAllPostShort,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SAVE GET ALL POST", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String ads_id = jsonObjectList.getString("add_id");
                                    String to_where = jsonObjectList.getString("to_where");
                                    String to_date = jsonObjectList.getString("to_date");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String details = jsonObjectList.getString("details");
                                    String full_name = jsonObjectList.getString("full_name");
                                    String user_photo = jsonObjectList.getString("user_photo");
                                    String user_location = jsonObjectList.getString("user_location");
                                    ModelPostShort modelPostShort = new ModelPostShort(ads_id, to_where, to_date,ad_type, details, full_name, user_photo, user_location);
                                    modelPostsList.add(modelPostShort);
                                }

                                SaveGetAllLocationInformation();


                            }else {
                                Log.d("SAIM SPLASH 3", response);
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


    public void SaveUserLogin() {
        String e = new SharedPrefDatabase(getApplicationContext()).RetriveUserEmail();
        String p = new SharedPrefDatabase(getApplicationContext()).RetriveUserPassword();

        Log.d("SAIM USER LOGIN", e + "\n" + p);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SAVE USER LOGIN", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);

                                user_id = jsonObjectList.getString("user_id");
                                nationality = jsonObjectList.getString("nationality");
                                full_name = jsonObjectList.getString("full_name");
                                email = jsonObjectList.getString("email");
                                password = jsonObjectList.getString("password");
                                agreement = jsonObjectList.getString("agreement");
                                status = jsonObjectList.getString("status");
                                roll = jsonObjectList.getString("roll");
                                first_name = jsonObjectList.getString("first_name");
                                last_name = jsonObjectList.getString("last_name");
                                gander = jsonObjectList.getString("gander");
                                address = jsonObjectList.getString("address");
                                city = jsonObjectList.getString("city");
                                zip = jsonObjectList.getString("zip");
                                state = jsonObjectList.getString("state");
                                country = jsonObjectList.getString("country");
                                mobile = jsonObjectList.getString("mobile");
                                phone = jsonObjectList.getString("phone");
                                birth_date = jsonObjectList.getString("birth_date");
                                user_photo = jsonObjectList.getString("user_photo");
                                document = jsonObjectList.getString("document");
                                verify = jsonObjectList.getString("verify");
                                website = jsonObjectList.getString("website");
                                facebook = jsonObjectList.getString("facebook");
                                instagram = jsonObjectList.getString("instagram");
                                youtube = jsonObjectList.getString("youtube");
                                code1 = jsonObjectList.getString("code");
                                cornjob = jsonObjectList.getString("cornjob");
                                like_to = jsonObjectList.getString("like_to");
                                details = jsonObjectList.getString("details");
                                server_date = jsonObjectList.getString("server_date");
                                rate = jsonObjectList.getString("rate");

                                new SharedPrefDatabase(getApplicationContext()).StoreLogin("Yes");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserFullName(full_name);
                                if (new SharedPrefDatabase(getApplicationContext()).RetriveSocialLogin() == false){
                                    new SharedPrefDatabase(getApplicationContext()).StoreUserPhoto(user_photo);
                                }

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();

                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD ", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("email", new SharedPrefDatabase(getApplicationContext()).RetriveUserEmail());
                params.put("password", new SharedPrefDatabase(getApplicationContext()).RetriveUserPassword());

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void SaveGetAllLocationInformation() {
        modelLocationList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.locationInformation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SAVE GET LOCATION", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String code = jsonObject.getString("code").trim();

                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String ads_id = jsonObjectList.getString("ads_id");
                                    String post_user_id = jsonObjectList.getString("post_user_id");
                                    String lat = jsonObjectList.getString("lat");
                                    String lon = jsonObjectList.getString("lon");
                                    String payment_category = jsonObjectList.getString("payment_category");
                                    String isType = jsonObjectList.getString("isType");
                                    String location = jsonObjectList.getString("location");
                                    String userName = jsonObjectList.getString("userName");


                                    ModelLocation modelLocation = new ModelLocation(ads_id, post_user_id, lat, lon, payment_category, isType, location, userName);
                                    modelLocationList.add(modelLocation);
                                }
                                if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin() != null){
                                    if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().equals("Yes")){
                                        SaveUserLogin();
                                    }else if (new SharedPrefDatabase(getApplicationContext()).RetriveLogin().equals("No")){
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();
                                    }
                                }else {
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    finish();
                                }


                            }else {
                                Log.d("SAIM SPLASH 3", response);
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

}
