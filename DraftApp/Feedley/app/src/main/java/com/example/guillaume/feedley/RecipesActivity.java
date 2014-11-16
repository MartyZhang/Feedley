package com.example.guillaume.feedley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.view.View;


public class RecipesActivity extends Activity {

    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    ArrayList<HashMap<String, String>> thelist = new ArrayList<HashMap<String, String>>();
    //URL to get JSON Array
    private static String url = "http://foodley.herokuapp.com/getrecipes?items=tomato";
    JSONObject recipes = null;
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
        setContentView(R.layout.activity_recipes);
        TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt.setTypeface(font);
        new JSONParse().execute();
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
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            name = (TextView) findViewById(R.id.textName);
            pDialog = new ProgressDialog(RecipesActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                recipes = json.getJSONObject("recipes");
                Iterator<String> iter = recipes.keys();
                while (iter.hasNext()) {
                    String name = iter.next();
                    //JSONObject c = recipes.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    //String name = c.;
                    //String image_url = c.getString("image_url");
                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", name);
                    //map.put("image_url", image_url);

                    //new ImageDownloader(icon).execute(image_url);
                    thelist.add(map);
                    list = (ListView) findViewById(R.id.listView);
                    ListAdapter adapter = new SimpleAdapter(RecipesActivity.this, thelist,
                            R.layout.row_listitem,
                            new String[]{"name"/*, "image_url"*/}, new int[]{
                            R.id.textName});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            Toast.makeText(RecipesActivity.this, "You Clicked at " + thelist.get(+position).get("name"), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}




