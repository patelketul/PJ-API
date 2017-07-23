package Util;

import org.json.JSONArray;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONException;

import static com.jayway.restassured.RestAssured.*;

/**
 * Created by New User on 21-Jul-17.
 */
public class Utils {

    public static String path;

    /*Set Base URL*/
    public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
    }

    /*Set Base Path*/
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }

    /*Set content type*/
    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }

    /*Add search term to request url*/
    public static void  createSearchQueryPath(String searchTerm) {
        path = searchTerm;
    }

    /*Send reqest to URL and get response    */
    public static Response getResponse() {
        return get(path);
    }

    /*Convert JSON response to JSONArray    */
    public static JSONArray jresp (Response res) throws JSONException {
        return new JSONArray(res.asString());
    }
}
