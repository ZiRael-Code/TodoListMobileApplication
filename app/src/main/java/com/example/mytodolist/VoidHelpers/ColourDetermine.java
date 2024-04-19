package com.example.mytodolist.VoidHelpers;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.example.mytodolist.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ColourDetermine {
    public int[] realcoloursSpar(Context context) {
        int[] colors = {
                ContextCompat.getColor(context, R.color.c5spar),
                ContextCompat.getColor(context, R.color.c6spar),
                ContextCompat.getColor(context, R.color.c7spar),
                ContextCompat.getColor(context, R.color.c8spar),
                ContextCompat.getColor(context, R.color.c9spar)};
        return colors;
    }

    public int[] realColours(Context context){
        int[] colors = {
                ContextCompat.getColor(context, R.color.c5),
                ContextCompat.getColor(context, R.color.c6),
                ContextCompat.getColor(context, R.color.c7),
                ContextCompat.getColor(context, R.color.c8),
                ContextCompat.getColor(context, R.color.c9)};
        return colors;
    }


    public int[] imageAndColourDetermin(Context context, ImageView iconView, JSONObject itemObject, int i) throws JSONException {
        int colour = 0;
        int sparColour = 0;
        if (itemObject.getString("taskType").equals("OFFICE_PROJECT")){
            sparColour = ContextCompat.getColor(context, R.color.officeSpar);
            colour = ContextCompat.getColor(context, R.color.office);
            iconView.setImageResource(R.mipmap.office);

        } else if (itemObject.getString("taskType").equals("PERSONAL_PROJECT")) {
            sparColour = ContextCompat.getColor(context, R.color.personalSpar);
            colour = ContextCompat.getColor(context, R.color.personal);
            iconView.setImageResource(R.mipmap.personalproj);

        } else if (itemObject.getString("taskType").equals("DAILY_ACTIVITY")) {
            sparColour = ContextCompat.getColor(context, R.color.dailyActivityspar);
            colour = ContextCompat.getColor(context, R.color.dailyActivity);
            iconView.setImageResource(R.mipmap.dailyactivity);


        } else if (itemObject.getString("taskType").equals("DAILY_STUDIES")) {
            sparColour = ContextCompat.getColor(context, R.color.dailyStudySpar);
            colour = ContextCompat.getColor(context, R.color.dailyStudy);
            iconView.setImageResource(R.mipmap.dailystudy);
        }else {
            sparColour = realcoloursSpar(context)[i % realcoloursSpar(context).length];
            colour = realColours(context)[i % realColours(context).length];
        }
        int[] colours = {colour, sparColour};
        return colours;
    }




}
