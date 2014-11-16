package com.example.guillaume.feedley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class RecipesActivity extends Activity {

    ListView list;
    TextView ver;
    TextView name;
    TextView api;
    Button Btngetdata;
    ArrayList<JSONObject> theList;

    ImageLoader imgLoader;
    private static String url = "http://foodley.herokuapp.com/getrecipes?items=";
    String value=null;
    JSONArray recipes = null;
    TextView tvView;
    TextView tvView2;

    private static final String[] INGREDIENTS = new String[] {
            "Tomato", "Bread", "Milk", "Flour", "Chicken", "Meat", "Pizza", "Broccoli", "Steak",
            "Beef", "Coriander", "Parsley", "Salt", "Pepper", "Flour", "Pasta", "Penne", "Tortellini",
            "Spaghetti", "Macaroni", "Noodles", "Rice", "Pork", "Sausage", "Onion", "Spinach", "Cucumber",
            "Lemon", "Garlic", "Ground meat", "Eggs", "Tomato Sauce", "Lettuce", "Banana", "Peanut Butter",
            "Duck", "Lamb", "Salmon", "Tilapia", "Tuna", "Cod", "Trout", "Oysters", "Mushrooms", "Ketchup", "Mustard", "Mayo",
            "Cheese", "Celery", "Tortilla", "Pita", "Olive oil", "Apple", "Oranges", "Grapefruit"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("inputIngredients");
        }

        Intent intent = getIntent();
        //String message = intent.getStringExtra(SearchActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(value);

        // Set the text view as the activity layout
        setContentView(R.layout.activity_recipes);
        TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt.setTypeface(font);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.dropdownstyle, INGREDIENTS);

        // final AutoCompleteTextView textView = (AutoCompleteTextView)
        AutoCompleteTextView textView2 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView2);
        textView2.setAdapter(adapter);


        // set focus to it
        textView2.requestFocus();
        textView2.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView2, InputMethodManager.SHOW_IMPLICIT);
        new JSONParse(this).execute();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        private Context mContext;

        private JSONParse(Context mContext) {
            this.mContext = mContext;
        }

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
            JSONObject json = jParser.getJSONFromUrl(url+value);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                recipes = json.getJSONArray("recipes");

                theList = new ArrayList<JSONObject>();
                for (int i=0; i<recipes.length(); i++) {
                    JSONObject recipe = recipes.getJSONObject(i);
                    theList.add(recipe);
                }

                list = (ListView) findViewById(R.id.listView);
                CustomListAdapter adapter = new CustomListAdapter(mContext, R.layout.row_listitem, theList);
                list.setAdapter(adapter);
                /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Toast.makeText(RecipesActivity.this, "You Clicked at " + theList.get(position).get("name"), Toast.LENGTH_SHORT).show();
                    }
                });*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}




