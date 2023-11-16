package com.example.gourav.app_listview_json;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class JSONActivity extends AppCompatActivity {

    private ArrayList<String> List;
    private ArrayAdapter Adapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        List = new ArrayList<>();
        Adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, List);
        listView.setAdapter(Adapter);

        new JsonReader().execute("https://dummyjson.com/users");
    }

    private class JsonReader extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject main = new JSONObject(s);
                JSONArray jsonArray = main.getJSONArray("users");
                JSONObject jsonObject;
                String text;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    text = jsonObject.getString("lastName");
                    List.add(text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Adapter.notifyDataSetChanged();
        }
    }
}
