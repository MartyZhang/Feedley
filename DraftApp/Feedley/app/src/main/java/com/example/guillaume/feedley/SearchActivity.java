package com.example.guillaume.feedley;

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


public class SearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.dropdownstyle, INGREDIENTS);

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);
        textView.setAdapter(adapter);

        // set focus to it
        textView.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
        TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt.setTypeface(font);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();
        //if (id == R.id.action_settings) {
            return true;
        //}
        //return super.onOptionsItemSelected(item);
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
