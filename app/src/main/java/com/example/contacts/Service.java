package com.example.contacts;

import android.content.ContentValues;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Service {

    //private String api = "https://jsonplaceholder.typicode.com/users";
    private String api = "http://192.168.1.138:90/ServiceAndroid/public/request?";

    public Service(){
    }

    public String getContacts(){
        String response = "";
        try {
            URL url = new URL(api + "method=contacts" );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStreamReader reader = new InputStreamReader( connection.getInputStream());
            int data = reader.read();
            while (data != -1) {
                response += (char) data;
                data = reader.read();
            }
            return response;
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }

    public String getContact(String code){
        String response = "";
        try {
            URL url = new URL(api + "method=getContact&args0=" + code);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStreamReader reader = new InputStreamReader( connection.getInputStream());
            int data = reader.read();
            while (data != -1) {
                response += (char) data;
                data = reader.read();
            }
            return response;
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }

    public String saveContact(String name, String phone){

        String response = "";
        try {
            URL url = new URL(api + "method=save&args0=" + name + "&args1=" + phone);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream());

            writer.write("");
            writer.flush();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream());
            int data = reader.read();
            while (data != -1) {
                response += (char) data;
                data = reader.read();
            }
            return response;
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }


    public String updateContact(String code, String name, String phone){

        String response = "";
        try {
            URL url = new URL(api + "method=update&args0=" + code + "&args1=" + name + "&args2=" + phone);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream());

            writer.write("");
            writer.flush();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream());
            int data = reader.read();
            while (data != -1) {
                response += (char) data;
                data = reader.read();
            }
            return response;
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }

    public String deleteContact(String code){

        String response = "";
        try {
            URL url = new URL(api + "method=delete&args0=" + code);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream());

            writer.write("");
            writer.flush();

            InputStreamReader reader = new InputStreamReader( connection.getInputStream());
            int data = reader.read();
            while (data != -1) {
                response += (char) data;
                data = reader.read();
            }
            return response;
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  response;
    }

}
