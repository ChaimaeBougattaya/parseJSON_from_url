package com.example.parsejson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Picture> Pictures ;
    ListView list;
    TextView titlepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        titlepage = findViewById(R.id.Title);
        JSONTask json = new JSONTask();
        String link = "https://www.flickr.com/services/feeds/photos_public.gne?format=json&tags=morocco";
        json.execute(link+"&nojsoncallback=1");//pour lire le format json correct
    }
    public class JSONTask extends AsyncTask<String, Void, ArrayList<Picture>> {
        URL url;
        String title;
        @Override
        protected ArrayList<Picture> doInBackground(String... strings) {
            Pictures = new ArrayList<>();
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            StringBuffer buffer;
            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                JSONObject obj = new JSONObject(buffer.toString());

                title = obj.getString("title");//title of page
                JSONArray arr = obj.getJSONArray("items");
                //get title , author and url of picture and add it in list Pictures
                for (int i = 0; i < arr.length(); i++){
                    String title = arr.getJSONObject(i).getString("title");
                    String author = arr.getJSONObject(i).getString("author");
                    int length = author.length();
                    String urlPic =  arr.getJSONObject(i).getJSONObject("media").getString("m");
                    //we went to get juste a part of the string author ; juste the name of the author
                    Pictures.add(new Picture(title,author.substring(20,length-2),urlPic));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) { reader.close(); }
                } catch (IOException e) { e.printStackTrace(); }
            }
            return Pictures;
        }

        @Override
        protected void onPostExecute(ArrayList<Picture> unused) {
            super.onPostExecute(unused);
            titlepage.setText(title);
            CustomListAdapter listAdapter = new CustomListAdapter(getApplicationContext(),R.layout.custom_list_layout,Pictures);
            list.setAdapter(listAdapter);
        }
    }
}