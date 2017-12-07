package saim.com.askrambler.Activity;

import android.Manifest;
import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.CircleTransform;
import saim.com.askrambler.Util.MySingleton;
import saim.com.askrambler.Util.SharedPrefDatabase;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbarProfile;

    ProgressDialog progressDialog;
    RelativeLayout layoutProProfile ,layoutProChangePasword;
    TextView txtProProfile, txtProChangePassword, inputProFullName, inputProEmail, inputProPhone;
    ImageView imgProfileImage, imgProUploadDocument;
    EditText inputProGender, inputProCountry, inputProState, inputProCity,
            inputProZip, inputProAddress, inputProBirthday, inputProMobile, inputProLikeTo, inputProWebsite,
            inputProFacebook, inputProInstagram, inputProYoutube, inputProDetail;
    Button btnProUpdate, btnProUpdateDocument;

    ArrayList<String> countryList = new ArrayList<>();

    public Bitmap documentUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_profile);

        toolbarProfile = (Toolbar) findViewById(R.id.toolbarProfile);
        toolbarProfile.setTitle("Profile");
        setSupportActionBar(toolbarProfile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
    }


    public void init(){

        progressDialog = new ProgressDialog(this);

        layoutProProfile = (RelativeLayout) findViewById(R.id.layoutProProfile);
        layoutProChangePasword = (RelativeLayout) findViewById(R.id.layoutProChangePasword);

        txtProProfile = (TextView) findViewById(R.id.txtProProfile);
        txtProChangePassword = (TextView) findViewById(R.id.txtProChangePassword);

        ButtonClicked();

        //Profile Information
        imgProfileImage = (ImageView) findViewById(R.id.imgProfileImage);
        imgProUploadDocument = (ImageView) findViewById(R.id.imgProUploadDocument);


        inputProFullName = (TextView) findViewById(R.id.inputProFullName);
        inputProEmail = (TextView) findViewById(R.id.inputProEmail);
        inputProGender = (EditText) findViewById(R.id.inputProGender);
        inputProCountry = (EditText) findViewById(R.id.inputProCountry);
        inputProState = (EditText) findViewById(R.id.inputProState);
        inputProCity = (EditText) findViewById(R.id.inputProCity);
        inputProZip = (EditText) findViewById(R.id.inputProZip);
        inputProAddress = (EditText) findViewById(R.id.inputProAddress);
        inputProBirthday = (EditText) findViewById(R.id.inputProBirthday);
        inputProMobile = (EditText) findViewById(R.id.inputProMobile);
        inputProPhone = (TextView) findViewById(R.id.inputProPhone);
        inputProWebsite = (EditText) findViewById(R.id.inputProWebsite);
        inputProLikeTo = (EditText) findViewById(R.id.inputProLikeTo);
        inputProFacebook = (EditText) findViewById(R.id.inputProFacebook);
        inputProInstagram = (EditText) findViewById(R.id.inputProInstagram);
        inputProYoutube = (EditText) findViewById(R.id.inputProYoutube);
        inputProDetail = (EditText) findViewById(R.id.inputProDetail);


        btnProUpdate = (Button) findViewById(R.id.btnProUpdate);
        btnProUpdateDocument = (Button) findViewById(R.id.btnProUpdateDocument);

        PopulateProfileInformation();
        InputFieldClicked();
    }

    public void InputFieldClicked(){
        inputProCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Fetching country list.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                GetCountryList(inputProCountry);
            }
        });

        inputProBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputProBirthday);
            }
        });

        inputProGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderList("Gender List", inputProGender);
            }
        });

        imgProfileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                /*Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 9999);*/
                return false;
            }
        });

        imgProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChatDialogInfo();
            }
        });

        btnProUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Profile Update");
                progressDialog.setMessage("Please wait updating profile.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                UpdateProfileInfo();
            }
        });

        btnProUpdateDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Profile Document");
                progressDialog.setMessage("Please wait updating profile.");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                UploadDocument(documentUpload);
            }
        });

        imgProUploadDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(getApplicationContext());
                startActivityForResult(intent, 9999);
            }
        });
    }

    public void ButtonClicked(){
        txtProProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutProChangePasword.setVisibility(View.GONE);
                        layoutProProfile.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInUp).duration(250).playOn(findViewById(R.id.layoutProProfile));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutProChangePasword));
            }
        });

        txtProChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutDown).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutProChangePasword.setVisibility(View.VISIBLE);
                        layoutProProfile.setVisibility(View.GONE);
                        YoYo.with(Techniques.SlideInUp).duration(250).playOn(findViewById(R.id.layoutProChangePasword));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutProProfile));
            }
        });
    }

    public void PopulateProfileInformation(){
        Log.d("SAIM TRELOY", Splash.user_photo);
        if (new SharedPrefDatabase(getApplicationContext()).RetriveUserPhoto().equals("http://askrambler.com/")){
            imgProfileImage.setImageResource(R.drawable.ic_person);
        }else {
            Glide.with(getApplicationContext())
                    .load(new SharedPrefDatabase(getApplicationContext()).RetriveUserPhoto()).transform(new CircleTransform(getApplicationContext()))
                    .placeholder(R.drawable.ic_person)
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
                    .into(imgProfileImage);
        }
        Log.d("SAIM DOCUMENT", Splash.document);
        if (Splash.document.equals("http://askrambler.com/")){
            imgProUploadDocument.setImageResource(R.drawable.ic_person);
        }else {
            Glide.with(getApplicationContext())
                    .load("http://askrambler.com/"+Splash.document)
                    .placeholder(R.drawable.ic_person)
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
                    .into(imgProUploadDocument);
        }

        inputProFullName.setText(Splash.full_name);
        inputProEmail.setText(Splash.email);
        inputProMobile.setText(Splash.mobile.equals("null") ? "" : Splash.mobile);

        Log.d("SAIM PRO CHECK", Splash.gander);

        inputProGender.setText(Splash.gander.equals("null") ? "" : Splash.gander);
        inputProCountry.setText(Splash.country.equals("null") ? "" : Splash.country);
        inputProState.setText(Splash.state.equals("null") ? "" : Splash.state);
        inputProCity.setText(Splash.city.equals("null") ? "" : Splash.city);
        inputProZip.setText(Splash.zip.equals("null") ? "" : Splash.zip);
        inputProAddress.setText(Splash.address.equals("null") ? "" : Splash.address);
        inputProBirthday.setText(Splash.birth_date.equals("null") ? "" : Splash.birth_date);
        inputProPhone.setText(Splash.phone.equals("null") ? "" : Splash.phone);
        inputProMobile.setText(Splash.mobile.equals("null") ? "" : Splash.mobile);
        inputProWebsite.setText(Splash.website.equals("null") ? "" : Splash.website);
        inputProLikeTo.setText(Splash.like_to.equals("null") ? "" : Splash.like_to);
        inputProFacebook.setText(Splash.facebook.equals("null") ? "" : Splash.facebook);
        inputProInstagram.setText(Splash.instagram.equals("null") ? "" : Splash.instagram);
        inputProYoutube.setText(Splash.youtube.equals("null") ? "" : Splash.youtube);
        inputProDetail.setText(Splash.details.equals("null") ? "" : Splash.details);

    }

    public void showGenderList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(ProfileActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String) parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void dateSelectFromDatePicker(final EditText editText) {
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    public void showCountryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(ProfileActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, countryList);
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String) parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }

    public void GetCountryList(final EditText editText) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiURL.countryList,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("countryCodes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                countryList.add(jsonObject1.getString("country_name"));
                            }

                        } catch (Exception e) {

                        }
                        showCountryList("Country List", editText);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), resultUri);
                    bitmap = getResizedBitmap(bitmap, 400, 400);
                    imgProfileImage.setImageBitmap(bitmap);

                    progressDialog.setTitle("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    UploadImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == 9999) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), resultUri);
                    bitmap = getResizedBitmap(bitmap, 600, 400);
                    documentUpload = bitmap;
                    imgProUploadDocument.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 9999) {
                Exception error = result.getError();
            }
        }
    }


    public void UploadImage(final Bitmap bitmap) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.profileImageUpload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM UPLOAD", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

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
                Log.d("HDHD ", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", Splash.user_id);
                params.put("user_photo", getStringImage(bitmap));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void UploadDocument(final Bitmap bitmap) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.documentUpload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("SAIM UPLOAD", response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

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
                Log.d("HDHD ", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", Splash.user_id);
                params.put("image", getStringImage(bitmap));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    public Bitmap getResizedBitmap(Bitmap image, int bitmapWidth, int bitmapHeight) {
        return Bitmap.createScaledBitmap(image, bitmapWidth, bitmapHeight, true);
    }


    public void showChatDialogInfo() {

        LayoutInflater factory = LayoutInflater.from(ProfileActivity.this);
        final View chatDialogView = factory.inflate(R.layout.dialog_profile, null);
        final AlertDialog chatDialog = new AlertDialog.Builder(ProfileActivity.this).create();
        chatDialog.setView(chatDialogView);
        chatDialog.setCanceledOnTouchOutside(false);
        chatDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView img = (ImageView) chatDialogView.findViewById(R.id.imgDailogProfile);
        Glide.with(getApplicationContext())
                .load(new SharedPrefDatabase(getApplicationContext()).RetriveUserPhoto())
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
                .placeholder(R.drawable.ic_image)
                .into(img);

        chatDialogView.findViewById(R.id.txtDialogCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatDialog.dismiss();
            }
        });
        chatDialogView.findViewById(R.id.txtDialogChangeProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(4,4).getIntent(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                chatDialog.dismiss();
            }
        });

        chatDialog.show();

    }


    public void UpdateProfileInfo(){

        Log.d("SAIM PHONE MOBILE", inputProPhone.getText() + " : " + inputProMobile.getText());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.profileUpdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("SAIM RESPONSE", response);
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("Success")){
                                String message = jsonObject.getString("message");
                                SaveUserLogin();
                            }else {
                                progressDialog.dismiss();
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
                Log.d("HDHD ", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id", Splash.user_id);
                params.put("gander", inputProGender.getText().toString());
                params.put("address", inputProAddress.getText().toString());
                params.put("city", inputProCity.getText().toString());
                params.put("zip", inputProZip.getText().toString());
                params.put("state", inputProState.getText().toString());
                params.put("country", inputProCountry.getText().toString());
                params.put("mobile", inputProMobile.getText().toString());
                params.put("phone", inputProPhone.getText().toString());
                params.put("birth_date", inputProBirthday.getText().toString());
                params.put("website", inputProWebsite.getText().toString());
                params.put("facebook", inputProFacebook.getText().toString());
                params.put("instagram", inputProInstagram.getText().toString());
                params.put("youtube", inputProYoutube.getText().toString());
                params.put("like_to", inputProLikeTo.getText().toString());
                params.put("details", inputProDetail.getText().toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void SaveUserLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("SAIM RESPONSE 2", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("list");
                                JSONObject jsonObjectList = jsonArray.getJSONObject(0);

                                Splash.user_id = jsonObjectList.getString("user_id");
                                Splash.nationality = jsonObjectList.getString("nationality");
                                Splash.full_name = jsonObjectList.getString("full_name");
                                Splash.email = jsonObjectList.getString("email");
                                Splash.password = jsonObjectList.getString("password");
                                Splash.agreement = jsonObjectList.getString("agreement");
                                Splash.status = jsonObjectList.getString("status");
                                Splash.roll = jsonObjectList.getString("roll");
                                Splash.first_name = jsonObjectList.getString("first_name");
                                Splash.last_name = jsonObjectList.getString("last_name");
                                Splash.gander = jsonObjectList.getString("gander");
                                Splash.address = jsonObjectList.getString("address");
                                Splash.city = jsonObjectList.getString("city");
                                Splash.zip = jsonObjectList.getString("zip");
                                Splash.state = jsonObjectList.getString("state");
                                Splash.country = jsonObjectList.getString("country");
                                Splash.mobile = jsonObjectList.getString("mobile");
                                Splash.phone = jsonObjectList.getString("phone");
                                Splash.birth_date = jsonObjectList.getString("birth_date");
                                Splash.user_photo = jsonObjectList.getString("user_photo");
                                Splash.document = jsonObjectList.getString("document");
                                Splash.verify = jsonObjectList.getString("verify");
                                Splash.website = jsonObjectList.getString("website");
                                Splash.facebook = jsonObjectList.getString("facebook");
                                Splash.instagram = jsonObjectList.getString("instagram");
                                Splash.youtube = jsonObjectList.getString("youtube");
                                Splash.code1 = jsonObjectList.getString("code");
                                Splash.cornjob = jsonObjectList.getString("cornjob");
                                Splash.like_to = jsonObjectList.getString("like_to");
                                Splash.details = jsonObjectList.getString("details");
                                Splash.server_date = jsonObjectList.getString("server_date");

                                Toast.makeText(getApplicationContext(), "Your profile updated successfully", Toast.LENGTH_SHORT).show();
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
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
