package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Update extends AppCompatActivity {

    ProgressDialog progressDialog;

    EditText code;
    EditText name;
    EditText phone;
    LinearLayout mainLayout;

    String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        code = findViewById(R.id.txtCode);
        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtPhone);
        Intent intent = getIntent();
        code.setText(intent.getStringExtra("code"));

        new Update.getContact().execute();

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainLayout = (LinearLayout)findViewById(R.id.mainLayout);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
                new Update.saveData().execute();
            }
        });

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update.super.onBackPressed();
            }
        });
    }

    public class getContact extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(progressDialog.isShowing())
                progressDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update.this);
            progressDialog.setMessage("Sending data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Service service = new Service();

            String json = service.getContact(String.valueOf(code.getText()));
            if(json != null){
                try {
                    JSONObject object = new JSONObject(json);
                    JSONArray data = object.getJSONArray("data");

                    code.setText(data.getJSONObject(0).getString("code"));
                    name.setText(data.getJSONObject(0).getString("name"));
                    phone.setText(data.getJSONObject(0).getString("phone"));

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

    public class saveData extends AsyncTask<Void,Void,Void> {

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
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Update.this);
            progressDialog.setMessage("Sending data...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Service service = new Service();

            String json = service.updateContact(String.valueOf(code.getText()), name.getText().toString(), phone.getText().toString());
            if(json != null){
                try {
                    JSONObject object = new JSONObject(json);
                    JSONArray data = object.getJSONArray("data");
                    response = data.getJSONObject(0).getString("response");
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