package saim.com.askrambler.Activity;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import saim.com.askrambler.R;
import saim.com.askrambler.Util.MySingleton;

public class AdvancedSearchActivity extends AppCompatActivity {

    Toolbar toolbarAdvancedSearch;

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCBrJXQuoQXATq461rT-WoaO5Sd0c9aqTQ";

    Button btnSPostCom, btnSPostBag, btnSPostTrip, btnSPostHost;
    RelativeLayout layoutSPostCompanion, layoutSPostBaggage, layoutSPostTrip, layoutSPostHost;
    //Google Item
    ArrayList resultList = null;
    ArrayAdapter<String> adapter;
    //Companion Advanced Search
    AutoCompleteTextView inputSComFrom, inputSComTo;
    TextView inputSConExpectedDate, inputSComExpectedDate1, inputSComGender;
    Button btnSComSearch;

    //Baggage Advanced Search
    AutoCompleteTextView inputSBagFrom, inputSBagTo;
    EditText inputSBagExpectedDate, inputSBagExpectedDate1, inputSBagBaggageType;
    Button btnSBagSearchAdd;

    //Trip Advanced Search
    AutoCompleteTextView inputSTripFrom, inputSTripTo;
    EditText inputSTripExpectedDate, inputSTripExpectedDate1, inputSTripTransportType;
    Button btnSTripSearchAdd;

    //Host Advanced Search
    AutoCompleteTextView inputSHostFrom;
    EditText inputSHostPaymentCategory;
    TextView txtSHostNoTraveler;
    SeekBar seekBarSHostNoTraveler;
    Button btnSHostSearchAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        init();
    }


    public void init(){
        toolbarAdvancedSearch = (Toolbar) findViewById(R.id.toolbarAdvancedSearch);
        toolbarAdvancedSearch.setTitle("Advance Search");
        setSupportActionBar(toolbarAdvancedSearch);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btnSPostCom = (Button) findViewById(R.id.btnSPostCom);
        btnSPostBag = (Button) findViewById(R.id.btnSPostBag);
        btnSPostTrip = (Button) findViewById(R.id.btnSPostTrip);
        btnSPostHost = (Button) findViewById(R.id.btnSPostHost);

        layoutSPostCompanion = (RelativeLayout) findViewById(R.id.layoutSPostCompanion);
        layoutSPostBaggage = (RelativeLayout) findViewById(R.id.layoutSPostBaggage);
        layoutSPostTrip = (RelativeLayout) findViewById(R.id.layoutSPostTrip);
        layoutSPostHost = (RelativeLayout) findViewById(R.id.layoutSPostHost);

        ButtonClickedTop();


        //Companion Advanced Search
        inputSComFrom = (AutoCompleteTextView) findViewById(R.id.inputSComFrom);
        inputSComTo = (AutoCompleteTextView) findViewById(R.id.inputSComTo);

        inputSConExpectedDate = (TextView) findViewById(R.id.inputSConExpectedDate);
        inputSComExpectedDate1 = (TextView) findViewById(R.id.inputSComExpectedDate1);
        inputSComGender = (TextView) findViewById(R.id.inputSComGender);

        btnSComSearch = (Button) findViewById(R.id.btnSComSearch);

        CompanionEditTextClicked();

        //Baggage Advanced Search
        inputSBagFrom = (AutoCompleteTextView) findViewById(R.id.inputSBagFrom);
        inputSBagTo = (AutoCompleteTextView) findViewById(R.id.inputSBagTo);

        inputSBagExpectedDate = (EditText) findViewById(R.id.inputSBagExpectedDate);
        inputSBagExpectedDate1 = (EditText) findViewById(R.id.inputSBagExpectedDate1);
        inputSBagBaggageType = (EditText) findViewById(R.id.inputSBagBaggageType);

        btnSBagSearchAdd = (Button) findViewById(R.id.btnSBagSearchAdd);

        BaggageEditTextClicked();


        //Trip Advanced Search
        inputSTripFrom = (AutoCompleteTextView) findViewById(R.id.inputSTripFrom);
        inputSTripTo = (AutoCompleteTextView) findViewById(R.id.inputSTripTo);

        inputSTripExpectedDate = (EditText) findViewById(R.id.inputSTripExpectedDate);
        inputSTripExpectedDate1 = (EditText) findViewById(R.id.inputSTripExpectedDate1);
        inputSTripTransportType = (EditText) findViewById(R.id.inputSTripTransportType);

        btnSTripSearchAdd = (Button) findViewById(R.id.btnSTripSearchAdd);

        TripEditTextClicked();

        //Host Advanced Search
        inputSHostFrom = (AutoCompleteTextView) findViewById(R.id.inputSHostFrom);
        inputSHostPaymentCategory = (EditText) findViewById(R.id.inputSHostPaymentCategory);
        txtSHostNoTraveler = (TextView) findViewById(R.id.txtSHostNoTraveler);
        seekBarSHostNoTraveler = (SeekBar) findViewById(R.id.seekBarSHostNoTraveler);
        btnSHostSearchAdd = (Button) findViewById(R.id.btnSHostSearchAdd);

        HostEditTextClicked();
    }

    public void ButtonClickedTop(){

        btnSPostCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostCom.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.VISIBLE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        layoutSPostCompanion.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutSPostCompanion));
            }
        });

        btnSPostBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostBag.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));


                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.VISIBLE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostBaggage.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutSPostBaggage));
            }
        });

        btnSPostTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostTrip.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostHost.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.VISIBLE);
                layoutSPostHost.setVisibility(View.GONE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostTrip.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutSPostTrip));
            }
        });

        btnSPostHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSPostHost.setTextColor(Color.parseColor("#FFFFFF"));
                btnSPostCom.setTextColor(Color.parseColor("#000000"));
                btnSPostBag.setTextColor(Color.parseColor("#000000"));
                btnSPostTrip.setTextColor(Color.parseColor("#000000"));

                layoutSPostCompanion.setVisibility(View.GONE);
                layoutSPostBaggage.setVisibility(View.GONE);
                layoutSPostTrip.setVisibility(View.GONE);
                layoutSPostHost.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        layoutSPostHost.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.layoutSPostHost));
            }
        });

    }

    public void CompanionEditTextClicked() {

        inputSComFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSComFrom);
            }
        });
        inputSComTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSComTo);
            }
        });

        inputSConExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateSelectFromDatePicker(inputSConExpectedDate);
            }
        });

        inputSComExpectedDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputSComExpectedDate1);
            }
        });

        inputSComGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGenderList("Select Gender", inputSComGender);
            }
        });

        btnSComSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputSComFrom.getText().toString().isEmpty() && !inputSComTo.getText().toString().isEmpty() &&
                        !inputSConExpectedDate.getText().toString().isEmpty() && !inputSComExpectedDate1.getText().toString().isEmpty() &&
                        !inputSComGender.getText().toString().isEmpty()){
                    String comFrom = inputSComFrom.getText().toString();
                    String comTo = inputSComTo.getText().toString();
                    String expectedDateFrom = inputSConExpectedDate.getText().toString();
                    String expectedDateTo = inputSComExpectedDate1.getText().toString();
                    String gender = inputSComGender.getText().toString();

                    Intent intent = new Intent(AdvancedSearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("ad_type", "Companion");
                    intent.putExtra("from_where", comFrom);
                    intent.putExtra("to_where", comTo);
                    intent.putExtra("from_date", expectedDateFrom);
                    intent.putExtra("to_date", expectedDateTo);
                    intent.putExtra("gender", gender);
                    startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void BaggageEditTextClicked() {

        inputSBagFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSBagFrom);
            }
        });
        inputSBagTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSBagTo);
            }
        });

        inputSBagExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputSBagExpectedDate);
            }
        });

        inputSBagExpectedDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputSBagExpectedDate1);
            }
        });

        inputSBagBaggageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaggageTypeList("Select baggage type", inputSBagBaggageType);
            }
        });

        btnSBagSearchAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputSBagFrom.getText().toString().isEmpty() && !inputSBagTo.getText().toString().isEmpty() &&
                        !inputSBagExpectedDate.getText().toString().isEmpty() &&
                        !inputSBagExpectedDate1.getText().toString().isEmpty() &&
                        !inputSBagBaggageType.getText().toString().isEmpty()){

                    String comFrom = inputSBagFrom.getText().toString();
                    String comTo = inputSBagTo.getText().toString();
                    String from_date = inputSBagExpectedDate.getText().toString();
                    String to_date = inputSBagExpectedDate1.getText().toString();
                    String baggage_type = inputSBagBaggageType.getText().toString();

                    Intent intent = new Intent(AdvancedSearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("ad_type", "Baggage");
                    intent.putExtra("from_where", comFrom);
                    intent.putExtra("to_where", comTo);
                    intent.putExtra("from_date", from_date);
                    intent.putExtra("to_date", to_date);
                    intent.putExtra("baggage_type", baggage_type);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void TripEditTextClicked() {

        inputSTripFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSTripFrom);
            }
        });
        inputSTripTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSTripTo);
            }
        });

        inputSTripExpectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputSTripExpectedDate);
            }
        });

        inputSTripExpectedDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateSelectFromDatePicker(inputSTripExpectedDate1);
            }
        });

        inputSTripTransportType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelByList("Select Transport type", inputSTripTransportType);
            }
        });

        btnSTripSearchAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputSTripFrom.getText().toString().isEmpty() && !inputSTripTo.getText().toString().isEmpty() &&
                        !inputSTripExpectedDate.getText().toString().isEmpty() &&
                        !inputSTripExpectedDate1.getText().toString().isEmpty() &&
                        !inputSTripTransportType.getText().toString().isEmpty()){

                    String comFrom = inputSTripFrom.getText().toString();
                    String comTo = inputSTripTo.getText().toString();
                    String from_date = inputSTripExpectedDate.getText().toString();
                    String to_date = inputSTripExpectedDate1.getText().toString();
                    String transport_type = inputSTripTransportType.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                    intent.putExtra("ad_type", "Trip");
                    intent.putExtra("from_where", comFrom);
                    intent.putExtra("to_where", comTo);
                    intent.putExtra("from_date", from_date);
                    intent.putExtra("to_date", to_date);
                    intent.putExtra("transport_type", transport_type);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void HostEditTextClicked() {

        inputSHostFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                resultList = new ArrayList<String>();
                SaveFutureProject(s.toString(), inputSHostFrom);
            }
        });

        inputSHostPaymentCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPaymentCategoryList("Select payment category type", inputSHostPaymentCategory);
            }
        });
        seekBarSHostNoTraveler.setMax(20);
        seekBarSHostNoTraveler.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSHostNoTraveler.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSHostSearchAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputSHostFrom.getText().toString().isEmpty() && !inputSHostPaymentCategory.getText().toString().isEmpty() &&
                        !txtSHostNoTraveler.getText().toString().isEmpty() ){

                    String location = inputSHostFrom.getText().toString();
                    String travelers = txtSHostNoTraveler.getText().toString();
                    String payment_category = inputSHostPaymentCategory.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                    intent.putExtra("ad_type", "Host" + "");
                    intent.putExtra("location", location);
                    intent.putExtra("travelers", travelers);
                    intent.putExtra("payment_category", payment_category);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    //Google Autocomplete  Adapter
    public ArrayList SaveFutureProject(String place, final AutoCompleteTextView auto) {
        //String url = PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?key=" + API_KEY + "&components=country:bd" + "&input=" + place;
        String url = PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?key=" + API_KEY  + "&input=" + place + "&types=geocode";
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
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, resultList) {
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
                        } catch (Exception e) {

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

    public void dateSelectFromDatePicker(final TextView editText) {
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

    public void showGenderList(String title, final TextView editText) {

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
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

    public void showTravelByList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.transport_type));
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

    public void showPaymentCategoryList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.payment_category));
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

    public void showBaggageTypeList(String title, final EditText editText) {

        LayoutInflater factory = LayoutInflater.from(getApplicationContext());
        final View infoDialogView = factory.inflate(R.layout.dialog_list, null);
        final AlertDialog infoDialog = new AlertDialog.Builder(this).create();
        infoDialog.setView(infoDialogView);
        infoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView txtDialog = (TextView) infoDialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(title);

        ListView listDialog = (ListView) infoDialogView.findViewById(R.id.listDialog);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.baggage_type));
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
}
