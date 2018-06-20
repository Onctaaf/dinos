package com.example.baksteen_13.dinopackopening;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import org.w3c.dom.Text;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards>{

    //public static Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items){
        super(context, resourceId, items);
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent){
        cards card_item = getItem(position);

        if (convertView ==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        String deImage = card_item.getImage();
        name.setText(card_item.getName());


        //KAAS
        Resources res = getContext().getResources();
        String s = card_item.getName().toLowerCase();
        Log.d("mine", s);
        int resID = res.getIdentifier(s, "drawable", getContext().getPackageName());
        Drawable drawable = res.getDrawable(resID);
        image.setImageDrawable(drawable);
        //KAAS


        //image.setImageResource(deImage); // example:    R.mipmap.ic_launcher
        return convertView;
    }
}
