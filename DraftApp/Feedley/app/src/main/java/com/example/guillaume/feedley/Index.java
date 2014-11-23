package com.example.guillaume.feedley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Index extends Activity implements OnClickListener {
    private static Set<String> checkCopies;
    private static ArrayList<String> mData;
    private HorizontalFlowLayout Layout;
    private ImageView add;
    private AutoCompleteTextView searchBar;
    private LinearLayout container;
    private String search_message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        add = (ImageView) findViewById(R.id.addIngredient);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search_bar);
        checkCopies = new HashSet<String>();
        mData = new ArrayList<String>();
        Layout = (HorizontalFlowLayout) findViewById(R.id.horizontalView);

        //setLogoFont
        TextView txt1 = (TextView) findViewById(R.id.feedley_logo);
        TextView txt2 = (TextView) findViewById(R.id.look);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt1.setTypeface(font);
        txt2.setTypeface(font);

        //setAutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.dropdownstyle, INGREDIENTS);
        searchBar.setAdapter(adapter);

        // set focus to it
        searchBar.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchBar, InputMethodManager.SHOW_IMPLICIT);

        searchBar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    add.callOnClick();

                }
                return false;
            }
        });


    }

    //add ingredient bubble on click
    public void addIngredient(View v) {
        add = (ImageView) findViewById(R.id.addIngredient);
        searchBar = (AutoCompleteTextView) findViewById(R.id.search_bar);
        Layout = (HorizontalFlowLayout) findViewById(R.id.horizontalView);
        container = (LinearLayout)findViewById(R.id.container);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) container.getLayoutParams();

        params.setMargins(0,200,0,0);
        container.setLayoutParams(params);
        Fx.slide_up(this, container);
        //hide keyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        String ingredient = searchBar.getText().toString().substring(0, 1).toUpperCase()
                + searchBar.getText().toString().substring(1).toLowerCase();

        if (ingredient.isEmpty()) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter an ingredient";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        if (!checkCopies.contains(ingredient.toLowerCase())) {
            View new_view = getLayoutInflater().inflate(R.layout.ingredient_bubble, null);
            TextView add_view = (TextView) new_view.findViewById(R.id.ingredient_bubble);
            add_view.setText(ingredient);
            Layout.addView(new_view);
            checkCopies.add(ingredient.toLowerCase());
            searchBar.setText("");
        } else {
            Context context = getApplicationContext();
            CharSequence text = "You already entered the ingredient, dumbass";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            searchBar.setText("");
        }
    }

    //remove ingredient bubble on click
    public void removeView(View v) {
        HorizontalFlowLayout parent = (HorizontalFlowLayout) v.getParent().getParent().getParent().getParent();
        LinearLayout child = (LinearLayout) v.getParent().getParent().getParent();
        parent.removeView(child);
        TextView view = (TextView) child.findViewById(R.id.ingredient_bubble);
        String ingredient = view.getText().toString();
        checkCopies.remove(ingredient.toLowerCase());
    }

    public void search(View v) {
                Intent intent = new Intent(Index.this, RecipesActivity.class);

                for(String ingredient: checkCopies) {
                    if(!search_message.isEmpty()){
                        search_message = search_message + "," + ingredient;
                    }
                    if(search_message.isEmpty()) {
                        search_message = ingredient;
                    }
                }
                if (search_message.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Please enter an ingredient";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                } else {
                    intent.putExtra("inputIngredients", search_message.replace(" ", "%20"));
                    startActivity(intent);
                    search_message="";
                }
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


    @Override
    public void onClick(View view) {

        //calling an activity using <intent-filter> action name


    }

//    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//
//        switch (keyCode) {
//            case KEYCODE_ENTER
//                Intent intent = new Intent("com.example.guillaume.feedley.RecipesActivity");
//                RecipesActivity(intent);
//                return true;
//            default:
//                return super.onKeyUp(keyCode, event);
//        }
//    }

    private static final String[] INGREDIENTS = new String[]{
            "Tomato", "Bread", "Milk", "Flour", "Chicken", "Meat", "Pizza", "Broccoli", "Steak",
            "Beef", "Coriander", "Parsley", "Salt", "Pepper", "Flour", "Pasta", "Penne", "Tortellini",
            "Spaghetti", "Macaroni", "Noodles", "Rice", "Pork", "Sausage", "Onion", "Spinach", "Cucumber",
            "Lemon", "Garlic", "Ground meat", "Eggs", "Tomato Sauce", "Lettuce", "Banana", "Peanut Butter",
            "Duck", "Lamb", "Salmon", "Tilapia", "Tuna", "Cod", "Trout", "Oysters", "Mushrooms", "Ketchup", "Mustard", "Mayo",
            "Cheese", "Celery", "Tortilla", "Pita", "Olive oil", "Apple", "Oranges", "Grapefruit"
    };
}
