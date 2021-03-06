package com.aindong.restoko.library;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Model {
    private final String LOG_TAG = Model.class.getSimpleName();
    public static final String API_URL  = "http://192.168.1.2:8000/api/v1/";

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String PUT_METHOD = "PUT";
    private static final String PATCH_METHOD = "PATCH";

    public String fetch(String resource) {
        return this.request(GET_METHOD, resource);
    }

    public String post(String resource) {
        return this.request(POST_METHOD, resource);
    }

    public String put(String resource) {
        return this.request(PUT_METHOD, resource);
    }

    public String patch(String resource) {
        return this.request(PATCH_METHOD, resource);
    }

    public JSONArray getData(String json) throws JSONException {
        JSONObject data = new JSONObject(json);

        JSONArray result = data.getJSONArray("data");

        return result;
    }

    public int getStatus(String json) throws JSONException {
        JSONObject data = new JSONObject(json);

        return data.getInt("status");
    }

    private String request(String method, String resource) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String jsonStr = null;

        try {
            // Construct the URL for the API query
            String finalUrl = API_URL + resource;
            Log.i(LOG_TAG, finalUrl);
            URL url = new URL(finalUrl);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return jsonStr;
    }
}
