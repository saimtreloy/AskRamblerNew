package saim.com.askrambler.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.Adapter.AdapterPost;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

public class SearchResultActivity extends AppCompatActivity {

    Toolbar toolbarSearchResult;

    ProgressDialog progressDialog;

    public ArrayList<ModelPostShort> modelPostsList = new ArrayList<>();
    RecyclerView recyclerAllPost;
    RecyclerView.LayoutManager layoutManagerAllPost;
    RecyclerView.Adapter allPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_search_result);

        init();
    }


    public void init() {
        toolbarSearchResult = (Toolbar) findViewById(R.id.toolbarSearchResult);
        toolbarSearchResult.setTitle("Search Result");
        setSupportActionBar(toolbarSearchResult);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerAllPost = (RecyclerView) findViewById(R.id.recyclerViewAllPostSearch);
        layoutManagerAllPost = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAllPost.setLayoutManager(layoutManagerAllPost);
        recyclerAllPost.setHasFixedSize(true);

        String ad_type = getIntent().getExtras().getString("ad_type");

        if (ad_type.equals("Companion")){
            String from_where = getIntent().getExtras().getString("from_where");
            String to_where = getIntent().getExtras().getString("to_where");
            String from_date = getIntent().getExtras().getString("from_date");
            String to_date = getIntent().getExtras().getString("to_date");
            String gender = getIntent().getExtras().getString("gender");

            GetAllCompanionPost(ad_type, from_where, to_where, from_date, to_date, gender);
        } else if (ad_type.equals("Baggage")){
            String from_where = getIntent().getExtras().getString("from_where");
            String to_where = getIntent().getExtras().getString("to_where");
            String from_date = getIntent().getExtras().getString("from_date");
            String to_date = getIntent().getExtras().getString("to_date");
            String baggage_type = getIntent().getExtras().getString("baggage_type");

            GetAllBaggagePost(ad_type, from_where, to_where, from_date, to_date, baggage_type);
        } else if (ad_type.equals("Trip")){
            String from_where = getIntent().getExtras().getString("from_where");
            String to_where = getIntent().getExtras().getString("to_where");
            String from_date = getIntent().getExtras().getString("from_date");
            String to_date = getIntent().getExtras().getString("to_date");
            String transport_type = getIntent().getExtras().getString("transport_type");

            GetAllTripPost(ad_type, from_where, to_where, from_date, to_date, transport_type);
        } else if (ad_type.equals("Host")){
            String location = getIntent().getExtras().getString("location");
            String travelers = getIntent().getExtras().getString("travelers");
            String payment_category = getIntent().getExtras().getString("payment_category");
            Log.d("SAIM SPLASH 1", ad_type + "\n" + location + "\n" + travelers + "\n" + payment_category);
            GetAllHostPost(ad_type, location, travelers, payment_category);
        }

    }


    public void GetAllCompanionPost(final String ad_type1, final String from_where1, final String to_where1, final String from_date1, final String to_date1, final String gender1) {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.searchCompanion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                Log.d("SAIM SPLASH 2", response);
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

                                    ModelPostShort modelPostShort = new ModelPostShort(ads_id, to_where, to_date,ad_type, details, full_name, user_photo,user_location);
                                    modelPostsList.add(modelPostShort);
                                }
                                progressDialog.dismiss();
                                allPostAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPost.setAdapter(allPostAdapter);
                            }else {
                                Log.d("SAIM SPLASH 3", response);
                                progressDialog.dismiss();
                                new AwesomeWarningDialog(SearchResultActivity.this)
                                        .setTitle("Search Result")
                                        .setMessage("No result found.")
                                        .setColoredCircle(R.color.dialogWarningBackgroundColor)
                                        .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                                        .setCancelable(true)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setWarningButtonClick(new Closure() {
                                            @Override
                                            public void exec() {
                                                finish();
                                            }
                                        }).show();
                            }


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
                params.put("ad_type", ad_type1);
                params.put("from_where", from_where1);
                params.put("to_where", to_where1);
                params.put("from_date", from_date1);
                params.put("to_date", to_date1);
                params.put("gender", gender1);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void GetAllBaggagePost(final String ad_type1, final String from_where1, final String to_where1, final String from_date1, final String to_date1, final String baggage_type1) {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.searchBaggage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SPLASH 1", ad_type1 + "\n" + from_where1 + "\n" + to_where1 + "\n" + from_date1+ "\n" + to_date1 + "\n" + baggage_type1);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            Log.d("SAIM SPLASH C", code);
                            if (code.equals("success")) {
                                Log.d("SAIM SPLASH 2", response);
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
                                progressDialog.dismiss();
                                allPostAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPost.setAdapter(allPostAdapter);
                            }else {
                                progressDialog.dismiss();
                                new AwesomeWarningDialog(SearchResultActivity.this)
                                        .setTitle("Search Result")
                                        .setMessage("No result found.")
                                        .setColoredCircle(R.color.dialogWarningBackgroundColor)
                                        .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                                        .setCancelable(true)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setWarningButtonClick(new Closure() {
                                            @Override
                                            public void exec() {
                                                finish();
                                            }
                                        }).show();
                            }


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
                params.put("ad_type", ad_type1);
                params.put("from_where", from_where1);
                params.put("to_where", to_where1);
                params.put("from_date", from_date1);
                params.put("to_date", to_date1);
                params.put("baggage_type", baggage_type1);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void GetAllTripPost(final String ad_type1, final String from_where1, final String to_where1, final String from_date1, final String to_date1, final String transport_type1) {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.searchTrip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SPLASH 1", ad_type1 + "\n" + from_where1 + "\n" + to_where1 + "\n" + from_date1+ "\n" + to_date1 + "\n" + transport_type1);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            Log.d("SAIM SPLASH C", code);
                            if (code.equals("success")) {
                                Log.d("SAIM SPLASH 2", response);
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
                                progressDialog.dismiss();
                                allPostAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPost.setAdapter(allPostAdapter);
                            }else {
                                progressDialog.dismiss();
                                new AwesomeWarningDialog(SearchResultActivity.this)
                                        .setTitle("Search Result")
                                        .setMessage("No result found.")
                                        .setColoredCircle(R.color.dialogWarningBackgroundColor)
                                        .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                                        .setCancelable(true)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setWarningButtonClick(new Closure() {
                                            @Override
                                            public void exec() {
                                                finish();
                                            }
                                        }).show();
                            }


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
                params.put("ad_type", ad_type1);
                params.put("from_where", from_where1);
                params.put("to_where", to_where1);
                params.put("from_date", from_date1);
                params.put("to_date", to_date1);
                params.put("transport_type", transport_type1);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void GetAllHostPost(final String ad_type1, final String location1, final String travelers1, final String payment_category1) {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.searchHost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM SPLASH 1", ad_type1 + "\n" + location1 + "\n" + travelers1 + "\n" + payment_category1);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            Log.d("SAIM SPLASH C", code);
                            if (code.equals("success")) {
                                Log.d("SAIM SPLASH 2", response);
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
                                progressDialog.dismiss();
                                allPostAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPost.setAdapter(allPostAdapter);
                            }else {
                                progressDialog.dismiss();
                                new AwesomeWarningDialog(SearchResultActivity.this)
                                        .setTitle("Search Result")
                                        .setMessage("No result found.")
                                        .setColoredCircle(R.color.dialogWarningBackgroundColor)
                                        .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                                        .setCancelable(true)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setButtonBackgroundColor(R.color.dialogWarningBackgroundColor)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setWarningButtonClick(new Closure() {
                                            @Override
                                            public void exec() {
                                                finish();
                                            }
                                        }).show();
                            }


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
                params.put("ad_type", ad_type1);
                params.put("location", location1);
                params.put("travelers", travelers1);
                params.put("payment_category", payment_category1);
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
