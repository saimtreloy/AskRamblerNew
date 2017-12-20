package saim.com.askrambler.Activity;

import android.app.ProgressDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import saim.com.askrambler.Adapter.AdapterPost;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

public class PopularTrip extends AppCompatActivity {

    public static Toolbar toolbar;

    ProgressDialog progressDialog;

    public ArrayList<ModelPostShort> modelPostsList = new ArrayList<>();
    RecyclerView recyclerAllPopularTrip;
    RecyclerView.LayoutManager layoutManagerAllPopularTrip;
    RecyclerView.Adapter popularTripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_popular_trip);
        Initialization();
    }


    public void Initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbarPopularTrip);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerAllPopularTrip = (RecyclerView) findViewById(R.id.recyclerViewAllPopularTrip);
        layoutManagerAllPopularTrip = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAllPopularTrip.setLayoutManager(layoutManagerAllPopularTrip);
        recyclerAllPopularTrip.setHasFixedSize(true);

        SaveGetAllPost();
    }


    public void SaveGetAllPost() {
        modelPostsList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.getPopularTrip,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String ads_id = jsonObjectList.getString("add_id");
                                    String to_where = jsonObjectList.getString("to_where");
                                    Log.d("Saim Khan", to_where);
                                    String to_date = jsonObjectList.getString("to_date");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String details = jsonObjectList.getString("details");
                                    String full_name = jsonObjectList.getString("full_name");
                                    String user_photo = jsonObjectList.getString("user_photo");

                                    ModelPostShort modelPostShort = new ModelPostShort(ads_id, to_where, to_date,ad_type, details, full_name, user_photo);
                                    modelPostsList.add(modelPostShort);
                                }
                                popularTripAdapter = new AdapterPost(modelPostsList);
                                recyclerAllPopularTrip.setAdapter(popularTripAdapter);
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
