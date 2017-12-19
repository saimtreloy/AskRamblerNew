package saim.com.askrambler.Activity;

import android.Manifest;
import android.animation.Animator;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;

public class AddPostActivity extends AppCompatActivity {

    Toolbar toolbarAddPost;

    private static final String API_KEY = "AIzaSyCMDHpZK9wLjICfQbv9ioQy1bqWo255E0U";
    public static final String PLACE_API_FULL_LINK = "https://maps.googleapis.com/maps/api/place/autocomplete/json?key=" + API_KEY + "&input=";


    android.support.v4.app.FragmentTransaction fragmentTransaction;

    ProgressDialog progressDialog;
    Button btnAddPostCom, btnAddPostBag, btnAddPostTrip, btnAddPostHost;
    RelativeLayout layoutAddPostCompanion, layoutAddPostBaggage, layoutAddPostTrip, layoutAddPostHost;
    //Google Item
    ArrayList resultList = null;
    ArrayAdapter<String> adapter;
    //Companion
    AutoCompleteTextView inputAddComFrom, inputAddComTo;
    EditText inputAddConExpectedDate, inputAddComPayment, inputAddComGender, inputAddComTravelBy, inputAddComContact,
            inputAddComImage, inputAddComDescription;
    Button btnAddComPostAdd;

    public Bitmap bitmapCompanion;
    public static String imageEdittext = "";
    public static String trip_category_id = "";

    //Baggage
    RadioButton radioAddBagSendBag, radioAddBagCarryBag;
    AutoCompleteTextView inputAddBagFrom, inputAddBagTo;
    EditText inputAddBagExpectedDate, inputAddBagBaggageType, inputAddBagBaggageWeight, inputAddBagPaymentCategory, inputAddBagContactNo,
            inputAddBagImage, inputAddBagDescription;
    Button btnAddBagPostAdd;

    //Trip
    RadioButton radioAddTripArrageTrip, radioAddTripGoOn;
    AutoCompleteTextView inputAddTripFrom, inputAddTripTo;
    EditText inputAddTripExpectedDate, inputAddTripCategory, inputAddTripType,inputAddTripDuration, inputAddTripPayment, inputAddTripContactNo,
            inputAddTripImage, inputAddTripDescription;
    Button btnAddTripPostAdd;

    //Host
    RadioButton radioAddHostWantTo, radioAddHostNeedHost;
    AutoCompleteTextView inputAddHostLocation;
    EditText inputAddHostContactNo, inputAddHostPayment, inputAddHostHabitSmocking, inputAddHostHabitAlcohol, inputAddHostImage, inputAddHostDescription;
    CheckBox checkBoxFood, checkBoxInternet, checkBoxTransport;
    SeekBar seekBarNoTravelerHost;
    TextView txtAddHostNoTraveler;
    Button btnAddHostPostAdd;


    //Image Uploading
    List<Uri> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_add_post);

        toolbarAddPost = (Toolbar) findViewById(R.id.toolbarAddPost);
        toolbarAddPost.setTitle("Create new post");
        setSupportActionBar(toolbarAddPost);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
    }


    public void init(){
        haveStoragePermission();

        progressDialog = new ProgressDialog(this);

        btnAddPostCom = (Button) findViewById(R.id.btnAddPostCom);
        btnAddPostBag = (Button) findViewById(R.id.btnAddPostBag);
        btnAddPostTrip = (Button) findViewById(R.id.btnAddPostTrip);
        btnAddPostHost = (Button) findViewById(R.id.btnAddPostHost);

        layoutAddPostCompanion = (RelativeLayout) findViewById(R.id.layoutAddPostCompanion);
        layoutAddPostBaggage = (RelativeLayout) findViewById(R.id.layoutAddPostBaggage);
        layoutAddPostTrip = (RelativeLayout) findViewById(R.id.layoutAddPostTrip);
        layoutAddPostHost = (RelativeLayout) findViewById(R.id.layoutAddPostHost);

        ButtonClickedTop();

        //Companion
        inputAddComFrom = (AutoCompleteTextView) findViewById(R.id.inputAddComFrom);
        inputAddComTo = (AutoCompleteTextView) findViewById(R.id.inputAddComTo);

        inputAddConExpectedDate = (EditText) findViewById(R.id.inputAddConExpectedDate);
        inputAddComPayment = (EditText) findViewById(R.id.inputAddComPayment);
        inputAddComGender = (EditText) findViewById(R.id.inputAddComGender);
        inputAddComTravelBy = (EditText) findViewById(R.id.inputAddComTravelBy);
        inputAddComContact = (EditText) findViewById(R.id.inputAddComContact);
        inputAddComImage = (EditText) findViewById(R.id.inputAddComImage);
        inputAddComDescription = (EditText) findViewById(R.id.inputAddComDescription);

        btnAddComPostAdd = (Button) findViewById(R.id.btnAddComPostAdd);

        CompanionEditTextClick();

        //Baggage
        inputAddBagFrom = (AutoCompleteTextView) findViewById(R.id.inputAddBagFrom);
        inputAddBagTo = (AutoCompleteTextView) findViewById(R.id.inputAddBagTo);

        radioAddBagSendBag = (RadioButton) findViewById(R.id.radioAddBagSendBag);
        radioAddBagCarryBag = (RadioButton) findViewById(R.id.radioAddBagCarryBag);

        inputAddBagExpectedDate = (EditText) findViewById(R.id.inputAddBagExpectedDate);
        inputAddBagBaggageType = (EditText) findViewById(R.id.inputAddBagBaggageType);
        inputAddBagBaggageWeight = (EditText) findViewById(R.id.inputAddBagBaggageWeight);
        inputAddBagPaymentCategory = (EditText) findViewById(R.id.inputAddBagPaymentCategory);
        inputAddBagContactNo = (EditText) findViewById(R.id.inputAddBagContactNo);
        inputAddBagImage = (EditText) findViewById(R.id.inputAddBagImage);
        inputAddBagDescription = (EditText) findViewById(R.id.inputAddBagDescription);

        btnAddBagPostAdd = (Button) findViewById(R.id.btnAddBagPostAdd);
        BaggageEditTextClick();


        //Trip
        inputAddTripFrom = (AutoCompleteTextView) findViewById(R.id.inputAddTripFrom);
        inputAddTripTo = (AutoCompleteTextView) findViewById(R.id.inputAddTripTo);

        radioAddTripArrageTrip = (RadioButton) findViewById(R.id.radioAddTripArrageTrip);
        radioAddTripGoOn = (RadioButton) findViewById(R.id.radioAddTripGoOn);

        inputAddTripExpectedDate = (EditText) findViewById(R.id.inputAddTripExpectedDate);
        inputAddTripCategory = (EditText) findViewById(R.id.inputAddTripCategory);
        inputAddTripType = (EditText) findViewById(R.id.inputAddTripType);
        inputAddTripDuration = (EditText) findViewById(R.id.inputAddTripDuration);
        inputAddTripPayment = (EditText) findViewById(R.id.inputAddTripPayment);
        inputAddTripContactNo = (EditText) findViewById(R.id.inputAddTripContactNo);
        inputAddTripImage = (EditText) findViewById(R.id.inputAddTripImage);
        inputAddTripDescription = (EditText) findViewById(R.id.inputAddTripDescription);

        btnAddTripPostAdd = (Button) findViewById(R.id.btnAddTripPostAdd);
        TripEditTextClick();

        //Host
        inputAddHostLocation = (AutoCompleteTextView) findViewById(R.id.inputAddHostLocation);

        radioAddHostWantTo = (RadioButton) findViewById(R.id.radioAddHostWantTo);
        radioAddHostNeedHost = (RadioButton) findViewById(R.id.radioAddHostNeedHost);

        inputAddHostContactNo = (EditText) findViewById(R.id.inputAddHostContactNo);
        inputAddHostPayment = (EditText) findViewById(R.id.inputAddHostPayment);
        inputAddHostHabitSmocking = (EditText) findViewById(R.id.inputAddHostHabitSmocking);
        inputAddHostHabitAlcohol = (EditText) findViewById(R.id.inputAddHostHabitAlcohol);
        inputAddHostImage = (EditText) findViewById(R.id.inputAddHostImage);
        inputAddHostDescription = (EditText) findViewById(R.id.inputAddHostDescription);

        checkBoxFood = (CheckBox) findViewById(R.id.checkBoxFood);
        checkBoxInternet = (CheckBox) findViewById(R.id.checkBoxInternet);
        checkBoxTransport = (CheckBox) findViewById(R.id.checkBoxTransport);

        seekBarNoTravelerHost = (SeekBar) findViewById(R.id.seekBarNoTravelerHost);

        txtAddHostNoTraveler = (TextView) findViewById(R.id.txtAddHostNoTraveler);

        btnAddHostPostAdd = (Button) findViewById(R.id.btnAddHostPostAdd);

        HostEditTextClick();

        //Populate some information

        populateEditTextInfo();
    }


    public void ButtonClickedTop(){

        btnAddPostCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostCom.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.VISIBLE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        layoutAddPostCompanion.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutAddPostCompanion));
            }
        });

        btnAddPostBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostBag.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));


                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.VISIBLE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostBaggage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutAddPostBaggage));
            }
        });

        btnAddPostTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostTrip.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostHost.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.VISIBLE);
                layoutAddPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostTrip.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutAddPostTrip));
            }
        });

        btnAddPostHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddPostHost.setTextColor(Color.parseColor("#FFFFFF"));
                btnAddPostCom.setTextColor(Color.parseColor("#000000"));
                btnAddPostBag.setTextColor(Color.parseColor("#000000"));
                btnAddPostTrip.setTextColor(Color.parseColor("#000000"));

                layoutAddPostCompanion.setVisibility(View.GONE);
                layoutAddPostBaggage.setVisibility(View.GONE);
                layoutAddPostTrip.setVisibility(View.GONE);
                layoutAddPostHost.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutAddPostHost.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutAddPostHost));
            }
        });

    }


    public void populateEditTextInfo(){
        inputAddComContact.setText(Splash.phone);
        inputAddBagContactNo.setText(Splash.phone);
        inputAddTripContactNo.setText(Splash.phone);
        inputAddHostContactNo.setText(Splash.phone);
    }


    public void CompanionEditTextClick(){
        inputAddComFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddComFrom);
            }
        });
        inputAddComTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddComTo);
            }
        });
        inputAddConExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddConExpectedDate);
            }
        });
        inputAddComPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select payment category", inputAddComPayment);
            }
        });
        inputAddComGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderList("Select Gender", inputAddComGender);
            }
        });
        inputAddComTravelBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelByList("Select transport type", inputAddComTravelBy);
            }
        });
        inputAddComImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(AddPostActivity.this);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                imageEdittext = "Companion";
            }
        });


        btnAddComPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String from_where = inputAddComFrom.getText().toString();
                String to_where = inputAddComTo.getText().toString();
                String from_date = inputAddConExpectedDate.getText().toString();
                String to_date = curretDateAndTime();
                String payment_category = inputAddComPayment.getText().toString();
                String gender = inputAddComGender.getText().toString();
                String traveling_by = inputAddComTravelBy.getText().toString();
                String contacts = inputAddComContact.getText().toString();
                String details = inputAddComDescription.getText().toString();
                String ad_type = "Companion";
                String ad_type_id = "1";
                String dates = curretDateAndTime2();
                String status = "1";
                String user_id = Splash.user_id;

                if (from_where.isEmpty() || to_where.isEmpty() || from_date.isEmpty() || payment_category.isEmpty() || gender.isEmpty() || traveling_by.isEmpty() || contacts.isEmpty() || inputAddComImage.getText().toString().isEmpty() || details.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input filed can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Profile Document");
                    progressDialog.setMessage("Please wait updating profile.");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String images = getStringImage(bitmapCompanion);


                    Log.d("SAIM POST CHECK", from_where + "\n" + to_where + "\n" + from_date + "\n" + to_date + "\n" + payment_category + "\n" + gender + "\n" + traveling_by + "\n" +
                            contacts + "\n" + images + "\n" + details + "\n" + ad_type + "\n" + ad_type_id + "\n" + dates + "\n" + status + "\n" + user_id);

                    AddPostCompanion(from_where, to_where, from_date, to_date, payment_category, gender, traveling_by, contacts, images, details, ad_type, ad_type_id, dates, status, user_id);
                }
            }
        });

    }


    public void BaggageEditTextClick(){
        inputAddBagFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddBagFrom);
            }
        });
        inputAddBagTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddBagTo);
            }
        });

        radioAddBagSendBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddBagSendBag.setChecked(true);
                radioAddBagCarryBag.setChecked(false);
            }
        });
        radioAddBagCarryBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddBagCarryBag.setChecked(true);
                radioAddBagSendBag.setChecked(false);
            }
        });

        inputAddBagExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddBagExpectedDate);
            }
        });

        inputAddBagBaggageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaggageTypeList("Select baggage type", inputAddBagBaggageType);
            }
        });

        inputAddBagPaymentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select payment category", inputAddBagPaymentCategory);
            }
        });

        inputAddBagImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                imageEdittext = "Baggage";
            }
        });

        btnAddBagPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isType = "";

                String from_where = inputAddBagFrom.getText().toString();
                String to_where = inputAddBagTo.getText().toString();
                String from_date = inputAddBagExpectedDate.getText().toString();
                String to_date = curretDateAndTime();
                String payment_category = inputAddBagPaymentCategory.getText().toString();
                if (radioAddBagSendBag.isChecked()){
                    isType = radioAddBagSendBag.getText().toString();
                }else if (radioAddBagCarryBag.isChecked()){
                    isType = radioAddBagCarryBag.getText().toString();
                }
                String baggage_type = inputAddBagBaggageType.getText().toString();
                String baggage_weight = inputAddBagBaggageWeight.getText().toString();
                String contacts = inputAddBagContactNo.getText().toString();
                String details = inputAddBagDescription.getText().toString();
                String ad_type = "Baggage";
                String ad_type_id = "2";
                String dates = curretDateAndTime2();
                String status = "1";
                String user_id = Splash.user_id;

                if (from_where.isEmpty() || to_where.isEmpty() || from_date.isEmpty() || payment_category.isEmpty() || baggage_type.isEmpty() || baggage_weight.isEmpty() || contacts.isEmpty() || inputAddBagImage.getText().toString().isEmpty() || details.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input filed can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Profile Document");
                    progressDialog.setMessage("Please wait updating profile.");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String images = getStringImage(bitmapCompanion);


                    Log.d("SAIM POST CHECK", from_where + "\n" + to_where + "\n" + from_date + "\n" + isType + "\n" + to_date + "\n" + payment_category + "\n" + baggage_type + "\n" + baggage_weight + "\n" +
                            contacts + "\n" + images + "\n" + details + "\n" + ad_type + "\n" + ad_type_id + "\n" + dates + "\n" + status + "\n" + user_id);

                    AddPostBaggage(from_where, to_where, from_date, to_date, payment_category, isType, baggage_type, baggage_weight, contacts, images, details, ad_type, ad_type_id, dates, status, user_id);
                }
            }
        });
    }


    public void TripEditTextClick(){
        inputAddTripFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddTripFrom);
            }
        });
        inputAddTripTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddTripTo);
            }
        });

        radioAddTripArrageTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddTripArrageTrip.setChecked(true);
                radioAddTripGoOn.setChecked(false);
            }
        });
        radioAddTripGoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddTripGoOn.setChecked(true);
                radioAddTripArrageTrip.setChecked(false);
            }
        });

        inputAddTripExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputAddTripExpectedDate);
            }
        });

        inputAddTripCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTripCategoryList("Select trip category", inputAddTripCategory);
            }
        });

        inputAddTripType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelByList("Select trip transport type", inputAddTripType);
            }
        });

        inputAddTripPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select trip payment category", inputAddTripPayment);
            }
        });

        inputAddTripImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                imageEdittext = "Trip";
            }
        });

        btnAddTripPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isType = "";

                String from_where = inputAddTripFrom.getText().toString();
                String to_where = inputAddTripTo.getText().toString();
                String from_date = inputAddTripExpectedDate.getText().toString();
                String to_date = curretDateAndTime();
                String payment_category = inputAddTripPayment.getText().toString();
                if (radioAddTripArrageTrip.isChecked()){
                    isType = radioAddTripArrageTrip.getText().toString();
                }else if (radioAddTripGoOn.isChecked()){
                    isType = radioAddTripGoOn.getText().toString();
                }
                String trip_category     = inputAddTripCategory.getText().toString();
                String trip_type = inputAddTripType.getText().toString();
                String trip_duration = inputAddTripDuration.getText().toString();
                String contacts = inputAddTripContactNo.getText().toString();
                String details = inputAddTripDescription.getText().toString();
                String ad_type = "Baggage";
                String ad_type_id = "2";
                String dates = curretDateAndTime2();
                String status = "1";
                String user_id = Splash.user_id;

                if (from_where.isEmpty() || to_where.isEmpty() || from_date.isEmpty() || payment_category.isEmpty() || trip_category.isEmpty() || trip_type.isEmpty() || trip_duration.isEmpty() || contacts.isEmpty() || inputAddTripImage .getText().toString().isEmpty() || details.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input filed can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Profile Document");
                    progressDialog.setMessage("Please wait updating profile.");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String images = getStringImage(bitmapCompanion);


                    Log.d("SAIM POST CHECK", from_where + "\n" + to_where + "\n" + from_date + "\n" + isType + "\n" + to_date + "\n" + payment_category + "\n" + trip_category + "\n" + trip_type + "\n" +
                            contacts + "\n" + images + "\n" + details + "\n" + ad_type + "\n" + ad_type_id + "\n" + dates + "\n" + status + "\n" + user_id);

                    AddPostTrip(from_where, to_where, from_date, to_date, payment_category, isType, trip_category, trip_category_id,
                            trip_type, trip_duration, contacts, images, details, ad_type, ad_type_id, dates, status, user_id);
                }
            }
        });
    }


    public void HostEditTextClick(){
        inputAddHostLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputAddHostLocation);
            }
        });

        radioAddHostWantTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddHostWantTo.setChecked(true);
                radioAddHostNeedHost.setChecked(false);
            }
        });
        radioAddHostNeedHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioAddHostNeedHost.setChecked(true);
                radioAddHostWantTo.setChecked(false);
            }
        });

        inputAddHostPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select trip payment category", inputAddHostPayment);
            }
        });

        inputAddHostHabitSmocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSmockingHabitList("Select trip payment category", inputAddHostHabitSmocking);
            }
        });

        inputAddHostHabitAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlcoholHabitList("Select trip payment category", inputAddHostHabitAlcohol);
            }
        });
        seekBarNoTravelerHost.setMax(20);
        seekBarNoTravelerHost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtAddHostNoTraveler.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inputAddHostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setAspectRatio(16,9).getIntent(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                imageEdittext = "Host";
            }
        });

        btnAddHostPostAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isType = "";

                String to_date = curretDateAndTime();
                String payment_category = inputAddHostPayment.getText().toString();
                if (radioAddHostWantTo.isChecked()){
                    isType = radioAddHostWantTo.getText().toString();
                }else if (radioAddHostNeedHost.isChecked()){
                    isType = radioAddHostNeedHost.getText().toString();
                }
                String location = inputAddHostLocation.getText().toString();
                String travelers = seekBarNoTravelerHost.getProgress() + "";
                String offer = "";
                if (checkBoxFood.isChecked()){
                    offer = offer + ", " + checkBoxFood.getText().toString();
                }
                if (checkBoxInternet.isChecked()){
                    offer = offer + ", " + checkBoxInternet.getText().toString();
                }
                if (checkBoxTransport.isChecked()){
                    offer = offer + ", " + checkBoxTransport.getText().toString();
                }
                String smoking_habit = inputAddHostHabitSmocking.getText().toString();
                String alcohol_habit = inputAddHostHabitAlcohol.getText().toString();
                String contacts = inputAddHostContactNo.getText().toString();
                String details = inputAddHostDescription.getText().toString();
                String ad_type = "Host";
                String ad_type_id = "4";
                String dates = curretDateAndTime2();
                String status = "1";
                String user_id = Splash.user_id;

                if (payment_category.isEmpty() || isType.isEmpty() || location.isEmpty() || travelers.isEmpty() || offer.isEmpty() || smoking_habit.isEmpty() || alcohol_habit.isEmpty()
                        || inputAddHostImage.getText().toString().isEmpty() || details.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input filed can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setTitle("Host");
                    progressDialog.setMessage("Please wait posting you add.");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    String images = getStringImage(bitmapCompanion);


                    Log.d("SAIM POST CHECK", to_date + "\n" + isType + "\n" + payment_category + "\n" + location + "\n" + travelers + "\n" + offer + "\n" + smoking_habit + "\n" + alcohol_habit + "\n" +
                            contacts + "\n" + images + "\n" + details + "\n" + ad_type + "\n" + ad_type_id + "\n" + dates + "\n" + status + "\n" + user_id);

                    AddPostHost(to_date, payment_category, isType, location, travelers, offer, smoking_habit, alcohol_habit, contacts, images, details, ad_type, ad_type_id, dates, status, user_id);
                }
            }
        });
    }


    //Google Autocomplete  Adapter
    public ArrayList SaveFutureProject(String place, final AutoCompleteTextView auto) {
        //String url = PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?key=" + API_KEY + "&components=country:bd" + "&input=" + place;
        String url = PLACE_API_FULL_LINK + place;
        url = url.replace(" ", "%20");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

                            // Extract the Place descriptions from the results
                            resultList = new ArrayList(predsJsonArray.length());
                            for (int i = 0; i < predsJsonArray.length(); i++) {
                                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                                System.out.println("============================================================");
                                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, resultList){
                                @NonNull
                                @Override
                                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                    TextView textView = (TextView) super.getView(position, convertView, parent);

                                    //String currentLocation = resultList.get(position).toString();
                                    textView.setTextColor(Color.BLACK);
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_place, 0, 0, 0);

                                    return textView;
                                }
                            };
                            auto.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }catch (Exception e){

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        return resultList;
    }


    public void showPaymentCategoryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.payment_category));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void showGenderList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.gender));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void showTravelByList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transport_type));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void showBaggageTypeList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.baggage_type));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void showTripCategoryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.trip_category));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
                Log.d("SAIM CATEGORY POSITION", (position + 1)+"");
                trip_category_id = (position + 1)+"";
            }
        });
        infoDialog.show();
    }


    public void showSmockingHabitList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.smoking_habit));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void showAlcoholHabitList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(AddPostActivity.this);
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(AddPostActivity.this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.alcohol_habit));
        listDialog.setAdapter(arrayAdapter);
        listDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText((String)parent.getItemAtPosition(position));
                infoDialog.dismiss();
            }
        });
        infoDialog.show();
    }


    public void dateSelectFromDatePicker(final EditText editText){
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(AddPostActivity.this, new DatePickerDialog.OnDateSetListener() {
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    bitmapCompanion = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), resultUri);
                    String s = getStringImage(bitmapCompanion);
                    Log.d("Saim Image BASE64", s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String s = resultUri.toString().substring(resultUri.toString().lastIndexOf("/")+1,resultUri.toString().length());

                if (imageEdittext.equals("Companion")){
                    inputAddComImage.setText(s);
                } else if (imageEdittext.equals("Baggage")){
                    inputAddBagImage.setText(s);
                } else if (imageEdittext.equals("Trip")){
                    inputAddTripImage.setText(s);
                } else if (imageEdittext.equals("Host")){
                    inputAddHostImage.setText(s);
                }

                Log.d("Saim Image BASE642222", s);

                Toast.makeText(getApplicationContext(), resultUri.toString(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getApplicationContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(AddPostActivity.this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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


    public String curretDateAndTime(){
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM, yyyy HH:mm:ss a");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public String curretDateAndTime2(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public void AddPostCompanion(final String from_where, final String to_where, final String from_date, final String to_date, final String payment_category, final String gender, final String traveling_by, final String contacts, final String images, final String details, final String ad_type, final String ad_type_id, final String dates, final String status, final String user_id ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.companionAddPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("SAIM RESPONSE IMAGES", images);
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("Success")){
                                progressDialog.dismiss();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                getApplicationContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.receiverPost"));
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
                params.put("from_where", from_where);
                params.put("to_where", to_where);
                params.put("from_date", from_date);
                params.put("to_date", to_date);
                params.put("payment_category", payment_category);
                params.put("gender", gender);
                params.put("traveling_by", traveling_by);
                params.put("contacts", contacts);
                params.put("images", images);
                params.put("details", details);
                params.put("ad_type", ad_type);
                params.put("ad_type_id", ad_type_id);
                params.put("date", dates);
                params.put("status", status);
                params.put("user_id", user_id);
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


    public void AddPostBaggage(final String from_where, final String to_where, final String from_date, final String to_date, final String payment_category, final String isType, final String baggage_type, final String baggage_weight, final String contacts, final String images, final String details, final String ad_type, final String ad_type_id, final String dates, final String status, final String user_id ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.baggageAddPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("SAIM RESPONSE IMAGES", images);
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("Success")){
                                progressDialog.dismiss();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                getApplicationContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.receiverPost"));
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
                params.put("from_where", from_where);
                params.put("to_where", to_where);
                params.put("from_date", from_date);
                params.put("to_date", to_date);
                params.put("payment_category", payment_category);
                params.put("isType", isType);
                params.put("baggage_type", baggage_type);
                params.put("baggage_weight", baggage_weight);
                params.put("contacts", contacts);
                params.put("images", images);
                params.put("details", details);
                params.put("ad_type", ad_type);
                params.put("ad_type_id", ad_type_id);
                params.put("date", dates);
                params.put("status", status);
                params.put("user_id", user_id);
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


    public void AddPostTrip(final String from_where, final String to_where, final String from_date, final String to_date, final String payment_category, final String isType, final String trip_category, final String trip_category_id,
                            final String transport_type, final String trip_duration, final String contacts, final String images, final String details, final String ad_type, final String ad_type_id, final String dates, final String status, final String user_id ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.tripAddPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("SAIM RESPONSE IMAGES", images);
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("Success")){
                                progressDialog.dismiss();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                getApplicationContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.receiverPost"));
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
                params.put("from_where", from_where);
                params.put("to_where", to_where);
                params.put("from_date", from_date);
                params.put("to_date", to_date);
                params.put("payment_category", payment_category);
                params.put("isType", isType);
                params.put("trip_category", trip_category);
                params.put("trip_category_id", trip_category_id);
                params.put("transport_type", transport_type);
                params.put("trip_duration", trip_duration);
                params.put("contacts", contacts);
                params.put("images", images);
                params.put("details", details);
                params.put("ad_type", ad_type);
                params.put("ad_type_id", ad_type_id);
                params.put("date", dates);
                params.put("status", status);
                params.put("user_id", user_id);
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


    public void AddPostHost(final String to_date, final String payment_category, final String isType, final String location, final String travelers,
                            final String offers_1, final String smoking_habit, final String alcohol_habit, final String contacts, final String images, final String details, final String ad_type,
                            final String ad_type_id, final String dates, final String status, final String user_id ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.hostAddPost,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("SAIM RESPONSE IMAGES", images);
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("Success")){
                                progressDialog.dismiss();
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                getApplicationContext().sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.receiverPost"));
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
                params.put("to_date", to_date);
                params.put("payment_category", payment_category);
                params.put("isType", isType);
                params.put("location", location);
                params.put("travelers", travelers);
                params.put("offers_1", offers_1);
                params.put("smoking_habit", smoking_habit);
                params.put("alcohol_habit", alcohol_habit);
                params.put("contacts", contacts);
                params.put("images", images);
                params.put("details", details);
                params.put("ad_type", ad_type);
                params.put("ad_type_id", ad_type_id);
                params.put("date", dates);
                params.put("status", status);
                params.put("user_id", user_id);
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
