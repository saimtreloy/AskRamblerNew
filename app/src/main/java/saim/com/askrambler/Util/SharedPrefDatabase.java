package saim.com.askrambler.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefDatabase {

    public static final String KEY_LOGIN = "KEY_LOGIN";
    public static final String KEY_USER_EMAIL = "KEY_USER_EMAIL";
    public static final String KEY_USER_PASSWORD = "KEY_USER_PASSWORD";
    public static final String KEY_USER_FULLNAME = "KEY_USER_FULLNAME";
    public static final String KEY_USER_PHOTO = "KEY_USER_PHOTO";
    public static final String KEY_LAT = "KEY_LAT";
    public static final String KEY_LON = "KEY_LON";
    public static final String SOCIAL_LOGIN = "SOCIAL_LOGIN";


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    public SharedPrefDatabase(Context ctx) {
        this.context = ctx;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = sharedPreferences.edit();
    }

    public void StoreLogin(String data){
        editor.putString(KEY_LOGIN, data);
        editor.commit();
    }
    public String RetriveLogin(){
        String text = sharedPreferences.getString(KEY_LOGIN, null);
        return text;
    }

    public void StoreUserEmail(String data){
        editor.putString(KEY_USER_EMAIL, data);
        editor.commit();
    }
    public String RetriveUserEmail(){
        String text = sharedPreferences.getString(KEY_USER_EMAIL, null);
        return text;
    }

    public void StoreUserPassword(String data){
        editor.putString(KEY_USER_PASSWORD, data);
        editor.commit();
    }
    public String RetriveUserPassword(){
        String text = sharedPreferences.getString(KEY_USER_PASSWORD, null);
        return text;
    }

    public void StoreUserFullName(String data){
        editor.putString(KEY_USER_FULLNAME, data);
        editor.commit();
    }
    public String RetriveUserFullName(){
        String text = sharedPreferences.getString(KEY_USER_FULLNAME, null);
        return text;
    }

    public void StoreUserPhoto(String data){
        editor.putString(KEY_USER_PHOTO, data);
        editor.commit();
    }
    public String RetriveUserPhoto(){
        String text = sharedPreferences.getString(KEY_USER_PHOTO, null);
        return text;
    }


    public void StoreLat(String data){
        editor.putString(KEY_LAT, data);
        editor.commit();
    }
    public String RetriveLat(){
        String text = sharedPreferences.getString(KEY_LAT, null);
        return text;
    }

    public void StoreLon(String data){
        editor.putString(KEY_LON, data);
        editor.commit();
    }
    public String RetriveLon(){
        String text = sharedPreferences.getString(KEY_LON, null);
        return text;
    }

    public void StoreSocialLogin(Boolean data){
        editor.putBoolean(SOCIAL_LOGIN, data);
        editor.commit();
    }
    public Boolean RetriveSocialLogin(){
        Boolean text = sharedPreferences.getBoolean(SOCIAL_LOGIN, false);
        return text;
    }
}
