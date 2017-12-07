package saim.com.askrambler.Util;

/**
 * Created by sam on 8/5/17.
 */

public class ApiURL {

    public static String getLatLongFromLocation = "http://maps.google.com/maps/api/geocode/json?address=";

    public static String getAllPost = "http://askrambler.com/Android_API/getAllPost.php";
    public static String getAllPostShort = "http://askrambler.com/Android_API/getAllPostShort.php";
    public static String getAllPostDetail = "http://askrambler.com/Android_API/getPostDetail.php?post_id=";
    public static String getLogin = "http://askrambler.com/Android_API/login.php";

    //Advanced Search API Link
    public static String searchCompanion = "http://askrambler.com/Android_API/searchCompanion.php";
    public static String searchBaggage = "http://askrambler.com/Android_API/searchBaggage.php";
    public static String searchHost = "http://askrambler.com/Android_API/searchHost.php";
    public static String searchTrip = "http://askrambler.com/Android_API/searchTrip.php";

    //Country List
    public static String countryList = "http://askrambler.com/Android_API/countryList.php";

    //Registration
    public static String userRegistration = "http://askrambler.com/Android_API/userRegistration.php";
    public static String confirm = "http://askrambler.com/Android_API/confirm.php";
    public static String profileUpdate = "http://askrambler.com/Android_API/profileUpdate.php";

    //Image Upload
    public static String profileImageUpload = "http://askrambler.com/Android_API/imageUpload.php";
    public static String documentUpload = "http://askrambler.com/Android_API/documentUpload.php";

    //Add Post For ALl Departmemt
    public static String companionAddPost = "http://askrambler.com/Android_API/addPostCompanion.php";
    public static String baggageAddPost = "http://askrambler.com/Android_API/addPostBaggage.php";
    public static String tripAddPost = "http://askrambler.com/Android_API/addPostTrip.php";
    public static String hostAddPost = "http://askrambler.com/Android_API/addPostHost.php";


    public static String locationInformation = "http://askrambler.com/Android_API/getAllLatLon.php";


    //Social Login
    public static String facebookLogin = "http://askrambler.com/Android_API/loginFacebook.php";
    public static String googleLogin = "http://askrambler.com/Android_API/loginGoogle.php";
}
