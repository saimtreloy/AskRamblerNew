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

import saim.com.askrambler.Adapter.AdapterManagePost;
import saim.com.askrambler.Adapter.AdapterRequestedPost;
import saim.com.askrambler.Model.ModelManagePost;
import saim.com.askrambler.Model.ModelRequestedPost;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

public class RequestedPost extends AppCompatActivity {

    public static Toolbar toolbar;
    ProgressDialog progressDialog;

    public ArrayList<ModelRequestedPost> modelRequestedPosts = new ArrayList<>();
    RecyclerView recyclerViewRequestedPost;
    RecyclerView.LayoutManager layoutManagerAllPopularTrip;
    RecyclerView.Adapter requestedPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_requested_post);

        Initialization();
    }


    public void Initialization() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requested Post");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        recyclerViewRequestedPost = (RecyclerView) findViewById(R.id.recyclerViewRequestedPost);
        layoutManagerAllPopularTrip = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRequestedPost.setLayoutManager(layoutManagerAllPopularTrip);
        recyclerViewRequestedPost.setHasFixedSize(true);

        GetRequestedPost(Splash.user_id);
    }

    public void GetRequestedPost(final String user_id) {
        modelRequestedPosts.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getUserRequestPost,
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
                                    String status = jsonObjectList.getString("status");
                                    String email = jsonObjectList.getString("email");
                                    String ad_type = jsonObjectList.getString("ad_type");
                                    String from_where = jsonObjectList.getString("from_where");
                                    String date = jsonObjectList.getString("date");
                                    String to_date = jsonObjectList.getString("to_date");
                                    String phone = jsonObjectList.getString("phone");
                                    String date2 = jsonObjectList.getString("date2");

                                    ModelRequestedPost modelRequestedPost = new ModelRequestedPost(id, status, email, ad_type, from_where, date, to_date, phone, date2);
                                    modelRequestedPosts.add(modelRequestedPost);
                                }
                                requestedPostAdapter = new AdapterRequestedPost(modelRequestedPosts);
                                recyclerViewRequestedPost.setAdapter(requestedPostAdapter);
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
                params.put("sender_user_id", user_id);

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
