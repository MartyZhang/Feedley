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
import android.widget.ProgressBar;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

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

    private EditText value;
    private Button btn;
    private ProgressBar pb;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Second search bar
      /*  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.dropdownstyle, INGREDIENTS);

        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        textView.setAdapter(adapter);
        textView.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
        TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt.setTypeface(font);*/



        setContentView(R.layout.actualpage);

        // Input from page uno
   /*     Intent intent = getIntent();
        String message = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);
        TextView textView2 = (TextView) findViewById(R.id.textView);
        textView2.setText(message); // delete this laterz*/

       // Get URL:

    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private String result = "";
        @Override
        protected String doInBackground(String... params) {

            Intent intent = getIntent();
            String message = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);

            // TODO Auto-generated method stub
            result =  getData(message);

            publishProgress(100);

            // Test result??
            //TextView textView2 = (TextView) findViewById(R.id.textView);
            //textView2.setText(result); // delete this laterz
            Log.d("App", result);
            return result;
        }

        protected void onPostExecute(Double result) {
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "command sent", Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
            if(progress[0] == 100){
                textView.setText(result);
            }
        }

        public String getData(String valueIWantToSend) {
            // Create a new HttpClient and Get Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = null;

            try {
                httpget = new HttpGet("http://foodley.herokuapp.com/getrecipes?items="+valueIWantToSend);

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity responseEntity = response.getEntity();
                if(responseEntity!=null) {
                    result = EntityUtils.toString(responseEntity);
                    Log.d("JSON", result);

                }
                else{
                    Log.d("JSON", "no json :(");
                }
                return result;
            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "true";
        }
    }

    private static final String[] INGREDIENTS = new String[] {
            "Tomato", "Bread", "Milk", "Flour", "Chicken", "Meat", "Pizza", "Broccoli", "Steak",
            "Beef", "Coriander", "Parsley", "Salt", "Pepper", "Flour", "Pasta", "Penne", "Tortellini",
            "Spaghetti", "Macaroni", "Noodles", "Rice", "Pork", "Sausage", "Onion", "Spinach", "Cucumber",
            "Lemon", "Garlic", "Ground meat", "Eggs", "Tomato Sauce", "Lettuce", "Peppers", "Banana", "Peanut Butter",
            "Duck", "Lamb", "Salmon", "Tilapia", "Tuna", "Cod", "Trout", "Oysters", "Mushrooms", "Ketchup", "Mustard", "Mayo",
            "Cheese", "Celery", "Tortilla", "Pita", "Olive oil", "Apple", "Oranges", "Grapefruit"
    };
}




