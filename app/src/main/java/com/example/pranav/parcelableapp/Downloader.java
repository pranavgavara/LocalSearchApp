package com.example.pranav.parcelableapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Pranav on 7/26/2016.
 */
public class Downloader extends AsyncTask<String,Integer,ArrayList> {

    ResultsActivity activity;


    public Downloader(ResultsActivity activity) {
        this.activity = activity;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.displayProgressBar();
    }

    @Override
    protected ArrayList doInBackground(String... params) {

        String yqlURL = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20local.search%20where%20zip%3D'" + params[1] + "'%20and%20query%3D'" + params[0] + "'&format=json&callback=";
        ArrayList<Results> resultsArrayList = new ArrayList<Results>();
        try {
            URL url = new URL(yqlURL);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
            String json = reader.readLine();

            JSONObject jsonObject = new JSONObject(json);
            JSONObject queryObject = jsonObject.getJSONObject("query");
            JSONObject resultsObject = queryObject.getJSONObject("results");
            JSONArray resultsArray = resultsObject.getJSONArray("Result");

            for (int i = 0; i < resultsArray.length(); i++) {
                publishProgress((i+1)*10);
                Thread.sleep(100);
                JSONObject singleObject = resultsArray.getJSONObject(i);
                String title = singleObject.getString("Title");
                String address = singleObject.getString("Address");
                String city = singleObject.getString("City");
                String state = singleObject.getString("State");
                String phone = singleObject.getString("Phone");
                double longitude = Double.parseDouble(singleObject.getString("Longitude"));
                double latitude = Double.parseDouble(singleObject.getString("Latitude"));
                String distance = singleObject.getString("Distance");
                String businessURL = singleObject.getString("Url");

                Results results = new Results(title, address, city, state, phone, longitude, latitude, distance, businessURL);
                resultsArrayList.add(results);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resultsArrayList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        activity.setProgressBar(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        activity.drawListView(arrayList);
    }

}
