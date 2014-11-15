package com.example.guillaume.feedley;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.widget.Toast;
import android.view.View.OnClickListener;

import static android.view.KeyEvent.KEYCODE_ENTER;


public class SearchActivity extends Activity implements OnClickListener{

    Button btnStartAnotherActivity;
    AutoCompleteTextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        btnStartAnotherActivity = (Button) findViewById(R.id.btnStartAnotherActivity);

        btnStartAnotherActivity.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.dropdownstyle, INGREDIENTS);

        // final AutoCompleteTextView textView = (AutoCompleteTextView)
        textView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        textView.setAdapter(adapter);


        // set focus to it
        textView.requestFocus();
        textView.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT);
        TextView txt = (TextView) findViewById(R.id.custom_font);
        Typeface font = Typeface.createFromAsset(getAssets(), "LeckerliOne_Regular.otf");
        txt.setTypeface(font);


        textView.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(SearchActivity.this, textView.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });



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


        Intent intent = new Intent(SearchActivity.this, RecipesActivity.class);

        intent.putExtra("input", textView.getText());
        //intent.putExtra("input", "Tomato");
        startActivity(intent);
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

    private static final String[] INGREDIENTS = new String[] {
            "Tomato", "Bread", "Milk", "Flour", "Chicken", "Meat", "Pizza", "Broccoli", "Steak",
            "Beef", "Coriander", "Parsley", "Salt", "Pepper", "Flour", "Pasta", "Penne", "Tortellini",
            "Spaghetti", "Macaroni", "Noodles", "Rice", "Pork", "Sausage", "Onion", "Spinach", "Cucumber",
            "Lemon", "Garlic", "Ground meat", "Eggs", "Tomato Sauce", "Lettuce", "Peppers", "Banana", "Peanut Butter",
            "Duck", "Lamb", "Salmon", "Tilapia", "Tuna", "Cod", "Trout", "Oysters", "Mushrooms", "Ketchup", "Mustard", "Mayo",
            "Cheese", "Celery", "Tortilla", "Pita", "Olive oil", "Apple", "Oranges", "Grapefruit"
    };
}
