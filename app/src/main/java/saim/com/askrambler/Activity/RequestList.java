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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.Adapter.AdapterRequestedList;
import saim.com.askrambler.Adapter.AdapterRequestedPost;
import saim.com.askrambler.Model.ModelRequestList;
import saim.com.askrambler.Model.ModelRequestedPost;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

public class RequestList extends AppCompatActivity {

    public static Toolbar toolbar;
    ProgressDialog progressDialog;

    public ArrayList<ModelRequestList> modelRequestLists = new ArrayList<>();
    RecyclerView recyclerViewRequestedList;
    RecyclerView.LayoutManager layoutManagerAllPopularTrip;
    RecyclerView.Adapter requestedListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_request_list);



        Initialization();
    }


    public void Initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requested List");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerViewRequestedList = (RecyclerView) findViewById(R.id.recyclerViewRequestedList);
        layoutManagerAllPopularTrip = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRequestedList.setLayoutManager(layoutManagerAllPopularTrip);
        recyclerViewRequestedList.setHasFixedSize(true);

        GetRequestedList(getIntent().getExtras().getString("AD_ID"));
    }

    public void GetRequestedList(final String ad_id) {
        modelRequestLists.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getRequestPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("POPULAR TRIP", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);

                                    String id = jsonObjectList.getString("id");
                                    String ad_id = jsonObjectList.getString("ad_id");
                                    String status = jsonObjectList.getString("status");
                                    String date = jsonObjectList.getString("date");
                                    String sender_user_name = jsonObjectList.getString("sender_user_name");//sender_user_email, sender_user_mobile
                                    String sender_user_email = jsonObjectList.getString("sender_user_email");//sender_user_email
                                    String sender_user_mobile = jsonObjectList.getString("sender_user_mobile");//sender_user_mobile

                                    ModelRequestList modelRequestList = new ModelRequestList(id, ad_id, status, date, sender_user_name, sender_user_email, sender_user_mobile);
                                    modelRequestLists.add(modelRequestList);
                                }
                                requestedListAdapter = new AdapterRequestedList(modelRequestLists);
                                recyclerViewRequestedList.setAdapter(requestedListAdapter);
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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("ad_id", ad_id);

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
