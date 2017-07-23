package TestREST;

import Util.Utils;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by New User on 21-Jul-17.
 */
public class test {
    private Response resp;
    private JSONArray respArray;
    Utils utils = new Utils();
    int i;

    @BeforeTest
    public void setup () throws JSONException {
        //Test Setup
        utils.setBaseURI("https://restcountries.eu/rest/v2"); //Setup Base URI
        utils.setBasePath("currency"); //Setup Base Path
        utils.setContentType(ContentType.JSON); //Setup Content Type
        utils.createSearchQueryPath("INR"); //Construct the path
        resp = utils.getResponse(); //Get response
        respArray = utils.jresp(resp); //Get response in JSON Array
    }

    //Verify the request was successful
    @Test
    public void testStatusCode(){
        Assert.assertEquals(resp.getStatusCode(),200, "Status code does not match");
    }

    //Verify response is not empty
    @Test
    public void testNonEmptyResponse(){
        Assert.assertTrue(respArray.length() != 0, "Empty response");
    }

    //Verify country names are correct in response
    @Test
    public void testCountryName() throws JSONException {
        String[] country_names = {"Bhutan","India","Zimbabwe"};
        for (i=0;i<respArray.length();i++){
            Assert.assertEquals(respArray.getJSONObject(i).getString("name"),country_names[i], "Country name does not match");
        }
    }

    //Verify names of capitals are correct in response
    @Test
    public void testCapitalName() throws JSONException {
        String[] capital = {"Thimphu","New Delhi","Harare"};
        for (i=0;i<respArray.length();i++){
            Assert.assertEquals(respArray.getJSONObject(i).getString("capital"),capital[i], "Capital name does not match");
        }
    }

    //Verify regions names are correct in response
    @Test
    public void testRegionName() throws JSONException {
        String[] region = {"Asia","Asia","Africa"};
        for (i=0;i<respArray.length();i++){
            Assert.assertEquals(respArray.getJSONObject(i).getString("region"),region[i], "Region name does not match");
        }
    }

    /*Verify expected currency name is present in response*/
    @Test
    public void testCurrencyName() throws JSONException {

        for (i=0;i<respArray.length();i++){
            //Assert.assertTrue(respArray.getJSONObject(i).getString("currencies").contains("INR"),"Currency name does not match");
            int num_curr = respArray.getJSONObject(i).getJSONArray("currencies").length();//Get number of currencies. Some nations have multiple currencies
            for(int j=0;j<num_curr;j++){
                try{
                    //Compare each currency to look for INR
                    Assert.assertTrue(respArray.getJSONObject(i).getJSONArray("currencies").getJSONObject(j).getString("code").contains("INR"));
                    //if found, break the loop
                    break;
                }catch (AssertionError e){//Handle the exception if curecny is not equal to INR
                    if(j == num_curr-1){//If at the end of search INR is not found throw assertion error
                        throw e;
                    }
                }
            }
        }
    }

    /*Verify population count is correct in response*/
    @Test
    public void testPopulation() throws JSONException {
        int[] population = {775620,1295210000,14240168};
        for (i=0;i<respArray.length();i++){
            Assert.assertEquals(Integer.parseInt(respArray.getJSONObject(i).getString("population")),population[i], "Population does not match");
        }
    }
}
