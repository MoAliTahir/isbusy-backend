package com.isbusy.restapi.isbusyrestapi.Foursquare;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.isbusy.restapi.isbusyrestapi.JSON.*; // package used for general purpose JSON handling
import com.isbusy.restapi.isbusyrestapi.Classes.GenericEmplacement;

public class Request {

    // Constants used to authenticate connection using Foursquare API

    private static final String CLIENTID = "W0Y0M1K4VREUCB40I5T1G2MPHUNMGSNMQE3LA0KTYQDLRFEL";
    private static final String CLIENTSECRET = "1ZPR5I3D4ZJVFQCNFGOQS10VSMOWSDKVRTOPIIDEMPWGBJCG";
    private static final String OAUTH = "POKNX3PYYGXOONGIBEK4QMQG2HOKAE0BAXZG4WVZRALEXO1T";


    // Constants used in URL building

    private static final String VERSION = "20190429";
    private static final String PATH = "https://api.foursquare.com/v2/venues/";
    private static final String DATA = "client_id=" + CLIENTID + "&client_secret=" + CLIENTSECRET + "&v=" + VERSION;

    // Request attributes when performing a search by Name/Category ID

    private String query = null;
    private String radius = null;
    private String location = null;
    private String categoryID = null;

    // URL builder that returns a different URL based on the API functionality
    // desired

    private String urlBuilder(String option) {

        String url;
        if (option == "searchByQuery") {
            url = PATH + "search?&" + DATA;
            url = url + "&ll=" + this.location;
            url = url + "&radius=" + this.radius;
            url = url + "&query=" + this.query;
        } else if (option == "searchByCategory") {
            url = PATH + "search?&" + DATA;
            url = url + "&ll=" + this.location;
            url = url + "&radius=" + this.radius;
            url = url + "&categoryId=" + this.categoryID;
        } else if (option == "add") {
            url = PATH + option;
        } else {
            url = PATH + option + "?&" + DATA;
        }
        return url;
    }

    // Reader function designed to read the response bytes and turn them into a JSON
    // string

    private String readAll(Reader rd) throws IOException {

        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    // Robust function that handles server connections via POST/GET HTTP requests
    // Then proceeds to create JSONObjects from HTTP response
    // It also handles HTTP response errors

    private JSONObject readJsonFromResponse(String url, String content, String method)
            throws IOException, JSONException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setDoInput(true);

        if (method == "POST") {
            connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
            osw.write(content);
            osw.flush();
            osw.close();

        }
        JSONObject json = null;
        InputStream is;

        switch (connection.getResponseCode()) {

        case 200:
            is = connection.getInputStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                json = new JSONObject(jsonText);
            } finally {
                is.close();
            }
            break;

        case 400:
            throw new IOException(
                    "Error 400: Bad Request, this can be any case where a parameter is invalid, or a required parameter is missing. This includes the case where no OAuth token is provided and the case where a resource ID is specified incorrectly in a path.");

        case 401:
            throw new IOException("Error 401: Unauthorized, The OAuth token was provided but was invalid.");

        case 403:
            throw new IOException(
                    "Error 403: Forbidden, The requested information cannot be viewed by the acting user, for example, because they are not friends with the user whose data they are trying to read.");

        case 404:
            throw new IOException("Error 404: Not Found, Endpoint does not exist.");

        case 405:
            throw new IOException(
                    "Error 405: HTTP Method Not Allowed, Attempting to use POST with a GET-only endpoint, or vice-versa.");

        case 409:
            is = connection.getErrorStream();
            String jsonText;
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                jsonText = readAll(rd);
                json = new JSONObject(jsonText);
            } finally {
                is.close();
            }
            break;
        // throw new IOException("Error 409: Conflict, The request could not be
        // completed as it is. Use the information included in the response to modify
        // the request and retry."+jsonText);

        case 429:
            throw new IOException("Error 429: Quota exceeded, Daily call quota exceeded.");

        case 500:
            throw new IOException(
                    "Error 500: Internal Server Error, Foursquare’s servers are unhappy. The request is probably valid but needs to be retried later.");

        }
        return json;
    }

    // Function responsible for Parsing JSONObject into a GenericEmplacement

    private GenericEmplacement setGenericEmplacementAttributes(Object o) throws JSONException {

        JSONObject GenericEmplacementJson = (JSONObject) o;
        String name = GenericEmplacementJson.getString("name");
        String id = GenericEmplacementJson.getString("id");
        JSONObject location = GenericEmplacementJson.getJSONObject("location");
        Double longitude = location.getDouble("lng");
        Double latitude = location.getDouble("lat");
        String category = null;
        for (Object oo : GenericEmplacementJson.getJSONArray("categories")) {
            JSONObject categoryJSON = (JSONObject) oo;
            if (categoryJSON.getBoolean("primary")) {
                category = categoryJSON.getString("name");
            }
        }
        GenericEmplacement GenericEmplacement = new GenericEmplacement(id, longitude, latitude, name, category);
        return GenericEmplacement;
    }

    // Function used in search for multiple GenericEmplacements nearby, uses
    // previous function
    // to produce an ArrayList of GenericEmplacements

    private ArrayList<GenericEmplacement> getGenericEmplacementsFromJson(JSONObject response)
            throws IOException, JSONException {

        ArrayList<GenericEmplacement> GenericEmplacements = new ArrayList<GenericEmplacement>();
        JSONObject json = response.getJSONObject("response");
        JSONArray jsonArray = json.getJSONArray("venues");
        for (Object o : jsonArray) {
            GenericEmplacements.add(setGenericEmplacementAttributes(o));
        }
        return GenericEmplacements;
    }

    // Similar to previous function but only useful in cases where response is only
    // one GenericEmplacement

    private GenericEmplacement getGenericEmplacementFromJson(JSONObject response) throws IOException, JSONException {

        JSONObject json = response.getJSONObject("response");
        if (json.has("venue")) {
            json = json.getJSONObject("venue");
        } else if (json.has("candidateDuplicateVenues")) {
            for (Object o : json.getJSONArray("candidateDuplicateVenues")) {
                json = (JSONObject) o;
            }
        }
        return setGenericEmplacementAttributes(json);
    }

    /*
     * the following functions are the public methods, they use all previous private
     * methods in order to deliver the desired functionality, be it: - Search by
     * GenericEmplacement name. - Search by GenericEmplacement category ID. -
     * Getting GenericEmplacement details from GenericEmplacement ID. - Adding new
     * GenericEmplacements in Foursquare Database.
     */

    // Search function, by either Name or Category

    public ArrayList<GenericEmplacement> getNearbyGenericEmplacements(String option, String queryOrCategoryID,
            String radius, String location) throws IOException, JSONException {

        String opt = null;
        if (option == "category") {
            this.categoryID = queryOrCategoryID;
            opt = "searchByCategory";
        } else if (option == "query") {
            this.query = queryOrCategoryID;
            opt = "searchByQuery";
        }
        this.radius = radius;
        this.location = location;
        return this.getGenericEmplacementsFromJson(readJsonFromResponse(urlBuilder(opt), null, "GET"));
    }

    // Get palce details by ID

    public GenericEmplacement getGenericEmplacementDetails(String id) throws IOException, JSONException {

        return this.getGenericEmplacementFromJson(readJsonFromResponse(urlBuilder(id), null, "GET"));
    }

    // Create a new GenericEmplacement in Foursquare Database

    public GenericEmplacement createNewGenericEmplacement(String name, String location, String categoryID)
            throws IOException, JSONException {

        return this.getGenericEmplacementFromJson(readJsonFromResponse(urlBuilder("add"), DATA + "&name=" + name
                + "&ll=" + location + "&primaryCategoryId=" + categoryID + "&oauth_token=" + OAUTH, "POST"));
    }
}