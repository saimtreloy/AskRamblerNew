package saim.com.askrambler.Activity;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import saim.com.askrambler.R;
import saim.com.askrambler.Util.ApiURL;
import saim.com.askrambler.Util.MySingleton;
import saim.com.askrambler.Util.SharedPrefDatabase;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbarLogin;

    ProgressDialog progressDialog;
    CardView cardLogin, cardSignup;

    //Login Layout Attributes
    EditText inputLoginEmailOrMobile, inputLoginPassword;
    Button btnLoginLogin;
    TextView txtLoginForgetPassword, txtLoginSignup;

    //Signup Layout Attributes
    TextView txtRegSignin;
    EditText inputRegFirstName, inputRegLastName, inputRegEmail, inputRegMobile, inputRegPassword, inputRegPasswordC;
    Button btnRegSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeNew);
        setContentView(R.layout.activity_login);

        init();
    }


    public void init(){
        toolbarLogin = (Toolbar) findViewById(R.id.toolbarLogin);
        toolbarLogin.setTitle("User Login");
        setSupportActionBar(toolbarLogin);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        progressDialog = new ProgressDialog(this);

        cardLogin = (CardView) findViewById(R.id.cardLogin);
        cardSignup = (CardView) findViewById(R.id.cardSignup);

        //Login layout
        inputLoginEmailOrMobile = (EditText) findViewById(R.id.inputLoginEmailOrMobile);
        inputLoginPassword = (EditText) findViewById(R.id.inputLoginPassword);
        btnLoginLogin = (Button) findViewById(R.id.btnLoginLogin);
        txtLoginForgetPassword = (TextView) findViewById(R.id.txtLoginForgetPassword);
        txtLoginSignup = (TextView) findViewById(R.id.txtLoginSignup);

        //Signup Layout
        txtRegSignin = (TextView) findViewById(R.id.txtRegSignin);
        inputRegFirstName = (EditText) findViewById(R.id.inputRegFirstName);
        inputRegLastName = (EditText) findViewById(R.id.inputRegLastName);
        inputRegEmail = (EditText) findViewById(R.id.inputRegEmail);
        inputRegMobile = (EditText) findViewById(R.id.inputRegMobile);
        inputRegPassword = (EditText) findViewById(R.id.inputRegPassword);
        inputRegPasswordC = (EditText) findViewById(R.id.inputRegPasswordC);
        btnRegSignUp = (Button) findViewById(R.id.btnRegSignUp);

        ButtonAction();
        //SaveUserLogin("asasa", "sasa");
    }


    public void ButtonAction(){
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputLoginEmailOrMobile.getText().toString().isEmpty() && !inputLoginPassword.getText().toString().isEmpty()){
                    progressDialog.setTitle("Login");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();
                    SaveUserLogin(inputLoginEmailOrMobile.getText().toString(), inputLoginPassword.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "Email or Password filed can not be empty!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String fname = inputRegFirstName.getText().toString();
                String lname = inputRegLastName.getText().toString();
                String email = inputRegEmail.getText().toString();
                String phone = inputRegMobile.getText().toString();
                String password = inputRegPassword.getText().toString();
                String passwordC = inputRegPasswordC.getText().toString();

                if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordC.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input field can not be empty!", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    if (password.equals(passwordC) && password.length()>6){
                        UserRegistration(fname, lname, email, phone, password);
                    }else {
                        Toast.makeText(getApplicationContext(), "Password not matched or short!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLoginSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutRight).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cardLogin.setVisibility(View.GONE);
                        cardSignup.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft).duration(250).playOn(findViewById(R.id.cardSignup));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.cardLogin));
            }
        });

        txtRegSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.SlideOutLeft).duration(250).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cardSignup.setVisibility(View.GONE);
                        cardLogin.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInRight).duration(250).playOn(findViewById(R.id.cardLogin));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).playOn(findViewById(R.id.cardSignup));
            }
        });

    }


    public void SaveUserLogin(final String email, final String pass) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.getLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("SAIM RESPONSE", response);
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

                                new SharedPrefDatabase(getApplicationContext()).StoreLogin("Yes");
                                new SharedPrefDatabase(getApplicationContext()).StoreUserEmail(email);
                                new SharedPrefDatabase(getApplicationContext()).StoreUserPassword(pass);
                                new SharedPrefDatabase(getApplicationContext()).StoreUserFullName(Splash.full_name);
                                new SharedPrefDatabase(getApplicationContext()).StoreUserPhoto(Splash.user_photo);

                                sendBroadcast(new Intent("com.synergyinterface.askrambler.Activity.ChangeLayoutOnLogin"));
                                finish();
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
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
                params.put("email", email);
                params.put("password", pass);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    public void UserRegistration(final String first_name, final String last_name, final String email, final String phone, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiURL.userRegistration,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        YoYo.with(Techniques.SlideOutLeft).duration(250).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                cardSignup.setVisibility(View.GONE);
                                cardLogin.setVisibility(View.VISIBLE);
                                YoYo.with(Techniques.SlideInRight).duration(250).playOn(findViewById(R.id.cardLogin));
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).playOn(findViewById(R.id.cardSignup));
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            if (code.equals("success")){
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Log.d("HDHD 1", e.toString() + "\n" + response);
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
                params.put("first_name", first_name);
                params.put("last_name", last_name);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", password);

                return params;
            }
        };
        stringRequest.setShouldCache(false);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
