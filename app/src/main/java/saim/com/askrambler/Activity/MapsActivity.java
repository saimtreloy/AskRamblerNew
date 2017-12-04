package saim.com.askrambler.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import saim.com.askrambler.Adapter.AdapterPost;
import saim.com.askrambler.Maps.GPSTracker;
import saim.com.askrambler.Model.ModelLocation;
import saim.com.askrambler.Model.ModelPostShort;
import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;
import saim.com.askrambler.Util.SharedPrefDatabase;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    Toolbar toolbarMaps;

    ProgressDialog progressDialog;
    public static ArrayList<ModelLocation> modelLocationList = new ArrayList<>();

    private GoogleMap mMap;
    private Marker myMarker;
    GPSTracker gps;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_maps);

        toolbarMaps = (Toolbar) findViewById(R.id.toolbarMaps);
        toolbarMaps.setTitle("Nearest Host");
        setSupportActionBar(toolbarMaps);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading please wait...!");
        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.show();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);



    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera

        gps = new GPSTracker(MapsActivity.this);
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            LatLng sydney = new LatLng(latitude, longitude);
            CameraUpdate center = CameraUpdateFactory.newLatLng(sydney);
            CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(sydney,7);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            //mMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));

            myMarker = mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("My Spot")
                    .snippet("This is my spot!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            for (int i=0; i<Splash.modelLocationList.size(); i++){
                double lt = Double.parseDouble(Splash.modelLocationList.get(i).getLat());
                double ln = Double.parseDouble(Splash.modelLocationList.get(i).getLon());
                LatLng latLng = new LatLng(lt, ln);
                String postTitle = Splash.modelLocationList.get(i).getAds_id();
                myMarker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(postTitle));
            }

        }else{
            gps.showSettingsAlert();
        }
    }


    public void SaveGetAllLocationInformation() {
        modelLocationList.clear();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.locationInformation,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String code = jsonObject.getString("code").trim();

                            if (code.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObjectList = jsonArray.getJSONObject(i);
                                    String id = jsonObjectList.getString("id");
                                    String ads_id = jsonObjectList.getString("ads_id");
                                    String post_user_id = jsonObjectList.getString("post_user_id");
                                    String lat = jsonObjectList.getString("lat");
                                    String lon = jsonObjectList.getString("lon");


                                    ModelLocation modelLocation = new ModelLocation(id, ads_id, post_user_id, lat, lon);
                                    modelLocationList.add(modelLocation);
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)) {
            AdapterPost.post_id = marker.getTitle();
            startActivity(new Intent(getApplicationContext(), PostDetailActivity.class));
        }

        return true;
    }
}
