package com.example.contacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private ListView listView;
    ArrayList<HashMap<String,String>> contactList = new ArrayList<>();

    ProgressDialog progressDialog;

    ListAdapter listAdapter;

    private String code;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNew = findViewById(R.id.btnNew);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               createContact();
            }
        });

        listView = findViewById(R.id.livContact);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> code = (HashMap)(listView.getItemAtPosition(i));
                updateContact(code.get("id"));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> data = (HashMap)(listView.getItemAtPosition(i));
                code = data.get("id");
                new deleteContact().execute();
                return false;
            }
        });
        new getContacts().execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        contactList = new ArrayList<>();
        listAdapter = null;
        listView.setAdapter(null);
        new getContacts().execute();
    }

    private void createContact(){
        Intent intent = new Intent(this, Create.class);
        startActivity(intent);
    }

    private void updateContact(String code){
        Intent intent = new Intent(this, Update.class);
        intent.putExtra("code", code);
        startActivity(intent);
    }

    private class deleteContact extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            if(response == (String) "202"){
                Toast.makeText(getApplicationContext(), "Contact has been saved.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Contact has not been saved.", Toast.LENGTH_SHORT).show();
            }
            contactList = new ArrayList<>();
            listAdapter = null;
            listView.setAdapter(null);
            new getContacts().execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Service service = new Service();

            String json = service.deleteContact(code);
            if(json != null){
                try {
                    JSONObject object = new JSONObject(json);
                    JSONArray data = object.getJSONArray("data");

                    String response = data.getJSONObject(0).getString("response");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "JsonParsing Error", Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "JsonParsing Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else{
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;

        }
    }

    private class getContacts extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

            listAdapter = new SimpleAdapter(MainActivity.this, contactList, R.layout.item, new String[]{"name"}, new int[]{R.id.contact});
            listView.setAdapter(listAdapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Service service = new Service();

            String json = service.getContacts();
            if(json != null){
                try {
                    JSONObject object = new JSONObject(json);
                    JSONArray data = object.getJSONArray("data");

                    for(int x = 0; x < data.length(); x++){
                        JSONObject jsonObject = data.getJSONObject(x);
                        HashMap<String,String> list = new HashMap<>();
                        list.put("id",jsonObject.getString("code"));
                        list.put("name",jsonObject.getString("name"));
                        contactList.add(list);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "JsonParsing Error", Toast.LENGTH_LONG).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "JsonParsing Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }else{
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;

        }
    }
}

