package com.example.guillaume.feedley;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Guillaume on 2014-11-16.
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private JSONArray recipes;

    public CustomExpandableListAdapter(Context context, JSONArray pRecipes) {
        this._context = context;
        recipes = pRecipes;
    }

    @Override
    public JSONObject getChild(int groupPosition, int childPosititon) {
        try {
            return this.recipes.getJSONObject(groupPosition);
        } catch (JSONException e) {
            Log.e("CustomExpandableListAdapter", e.toString());
            return null;
        }
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
    boolean isLastChild, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item, parent, false);

        JSONObject recipe = getChild(groupPosition, childPosition);
        try {
            JSONArray ingredients = recipe.getJSONArray("ingredients");
            JSONArray steps = recipe.getJSONArray("recipe");

            StringBuilder sb = new StringBuilder();
            sb.append("Ingredients: ").append('\n').append('\n');
            for (int i=0; i<ingredients.length(); i++)
            sb.append("").append(ingredients.getString(i)).append('\n').append('\n');
            sb.append("\nSteps: ").append('\n').append('\n');
            for (int i=0; i<steps.length(); i++)
                sb.append(i+1).append(". ").append(steps.getString(i)).append('\n').append('\n');
            String name = recipe.getString("title");
            //String steps = recipe.getString("description");
            String image_url = recipe.getString("image_url");
            TextView textSteps = (TextView) v.findViewById(R.id.textSteps);
            Typeface font = Typeface.createFromAsset(_context.getAssets(), "Rex_Bold.otf");
            textSteps.setTypeface(font);
            textSteps.setText(sb.toString());
            Fx.slide_down(_context, textSteps);


        } catch(JSONException e) {
            Log.e("ListAdapter", "Error parsing data " + e.toString());
        }

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1; // Always 1 per child
    }

    @Override
    public JSONObject getGroup(int groupPosition) {
        try {
            return this.recipes.getJSONObject(groupPosition);
        } catch (JSONException e) {
            Log.e("CustomExpandableListAdapter", e.toString());
            return null;
        }
    }

    @Override
    public int getGroupCount() {
        return this.recipes.length();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
    View convertView, ViewGroup parent) {
       /* String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;*/
        LayoutInflater inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_group, parent, false);

        JSONObject recipe = getGroup(groupPosition);
        try {
            String name = recipe.getString("title");
            //String steps = recipe.getString("description");
            String image_url = recipe.getString("image_url");
            TextView textName = (TextView) v.findViewById(R.id.textName);
            Typeface font = Typeface.createFromAsset(_context.getAssets(), "Rex_Bold.otf");
            textName.setTypeface(font);
            ImageView preview = (ImageView) v.findViewById(R.id.imagePreview);
            if (recipe != null) {
                textName.setText(name);
                ImageLoader.getInstance().displayImage(image_url, preview);
            }
        } catch(JSONException e) {
            Log.e("ListAdapter", "Error parsing data " + e.toString());
        }

        return v;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
