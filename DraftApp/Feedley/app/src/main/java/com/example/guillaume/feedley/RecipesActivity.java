package com.example.guillaume.feedley;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;


public class RecipesActivity extends Activity {

    TextView tvView;
    TextView tvView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(R.layout.actualpage);
        //tvView2 = (TextView) findViewById(R.id.textView2);
        //Intent intent = getIntent();

       /* HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://104.131.105.6:3000/session/" );


        try {
            HttpResponse response = httpclient.execute(httpget);
            textView.setText("response " + response.toString()) ;

        } catch (MalformedURLException e){
            Log.e("app", "exception caught: ",e);
            textView.setText("response: " + e.toString());
        } catch (ClientProtocolException e) {
            Log.e("app", "exception caught: ",e);
            textView.setText("response: " + e.toString());
        } catch (IOException e) {
            Log.e("app", "exception caught: ",e);
            textView.setText("response: " + e.toString());
        }

    }*/

       /* String urlToRead = "http://foodley.herokuapp.com/getrecipes?items=tuna";
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";

        try{

            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
            textView.setText("response: " + result);
        }

        catch(Exception e){
            e.printStackTrace();
            textView.setText("response: " + e.toString());

        }*/

        //System.out.println(result);

    }
}




