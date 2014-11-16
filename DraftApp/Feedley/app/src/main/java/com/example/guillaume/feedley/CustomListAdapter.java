package com.example.guillaume.feedley;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Guillaume on 2014-11-15.
 */
public class CustomListAdapter extends ArrayAdapter<JSONObject> {
    private final Context mContext;

    public CustomListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mContext = context;
    }

    public CustomListAdapter(Context context, int resource, List<JSONObject> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.row_listitem, null);

        }*/
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.row_listitem, parent, false);

        JSONObject recipe = getItem(position);
        try {
            String name = recipe.getString("title");
            //String steps = recipe.getString("description");
            String image_url = recipe.getString("image_url");
            TextView textName = (TextView) v.findViewById(R.id.textName);
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
}
