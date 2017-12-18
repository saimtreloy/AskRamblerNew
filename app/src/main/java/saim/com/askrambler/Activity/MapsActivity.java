package saim.com.askrambler.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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
        mMap.clear();

        gps = new GPSTracker(MapsActivity.this);
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d("SAIM LOCATION", latitude + " " + longitude);
            LatLng sydney = new LatLng(latitude, longitude);
            CameraUpdate center = CameraUpdateFactory.newLatLng(sydney);
            CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(sydney,12);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            Circle circle = mMap.addCircle(new CircleOptions()
                    .center(sydney)
                    .radius(2000)
                    .strokeColor(0x220000FF)
                    .fillColor(0x220000FF));

            Drawable dPersonal = getResources().getDrawable(R.drawable.ic_map_person);
            BitmapDescriptor markerIconP = getMarkerIconFromDrawable(dPersonal);

            myMarker = mMap.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title("My Spot")
                    .snippet("This is my spot!")
                    .icon(markerIconP));

            for (int i=0; i<Splash.modelLocationList.size(); i++){
                double lt = Double.parseDouble(Splash.modelLocationList.get(i).getLat());
                double ln = Double.parseDouble(Splash.modelLocationList.get(i).getLon());
                LatLng latLng = new LatLng(lt, ln);
                String postTitle = Splash.modelLocationList.get(i).getAds_id();
                String postMessage = Splash.modelLocationList.get(i).getIsType();

                Drawable dHost = getResources().getDrawable(R.drawable.ic_map_host);
                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(dHost);

                myMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(postTitle).snippet(postMessage).icon(markerIcon));
            }

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    if (!marker.getTitle().equals("My Spot")){
                        Intent i = new Intent(getApplicationContext(), PostDetailActivity.class);
                        i.putExtra("POST_ID", marker.getTitle().trim());
                        startActivity(i);
                    }
                }
            });
        }else{
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)) {

        }

        return true;
    }


    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
