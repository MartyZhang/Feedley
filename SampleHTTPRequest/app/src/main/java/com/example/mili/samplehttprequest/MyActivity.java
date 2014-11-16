package com.example.mili.samplehttprequest;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class MyActivity extends Activity implements View.OnClickListener {

    // Link the layout to the activity
    private EditText value;
    private Button btn;
    private ProgressBar pb;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        value=(EditText)findViewById(R.id.editText1);
        btn=(Button)findViewById(R.id.button1);
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        textView=(TextView)findViewById(R.id.textView2);
        btn.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(value.getText().toString().length()<1){
            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        }else{
            pb.setVisibility(View.VISIBLE);
            new MyAsyncTask().execute(value.getText().toString());
        }
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private String result = "";
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            result =  getData(params[0]);

            publishProgress(100);
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
                    Log.d("App", result);
                }
                return result;
            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "true";
        }
    }
}
