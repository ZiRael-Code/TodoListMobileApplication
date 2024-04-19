package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mytodolist.VoidHelpers.ContextDetermine;
import com.google.gson.JsonObject;

public class ViewProjectTask extends AppCompatActivity {

    private String jsonObj;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_project_task);
        try {
            jsonObj = getIntent().getStringExtra("jsonResponse");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        }


//    public void upDateTaskHelp() throws Exception {
//        int[] image = {R.mipmap.timer1, R.mipmap.description, R.mipmap.dates, R.mipmap.dates};
//        String[] hintNames = {"Task Duration", "Description", "Start date", "End dates"};
//
//        LinearLayout layout = findViewById(R.id.praise);
//
//        for (int i = 0; i < image.length; i++) {
//
//            View updateIndividual = getLayoutInflater().inflate(R.layout.activity_add_task, null);
//
//
//
//            LinearLayout individualUpdates = updateIndividual.findViewById(R.id.individualFields);
//            TextView nameField = individualUpdates.findViewById(R.id.changeEditId);
//                nameField.setHint(hintNames[i % hintNames.length]);
//            ImageView updateTypeLogo = individualUpdates.findViewById(R.id.upDateTypeLogo);
//            updateTypeLogo.setBackground(ContextCompat.getDrawable(this, image[i]));
//
//            ((ViewGroup) individualUpdates.getParent()).removeView(individualUpdates);
//            layout.addView(individualUpdates);
//        }
//        }
//
//    public void nextScreen(View view){
//        ContextDetermine contextDetermine = new ContextDetermine();
//        Class<?> context = contextDetermine.nextScreenDeterminator(view);
//        assert context != null;
//
//
//        Intent intent = new Intent(this, context);
//
//        intent.putExtra("jsonResponse", jsonObj);
//        String ids = String.valueOf(id);
//        intent.putExtra("id", ids);
//
//        this.startActivity(intent);
//        finish();
//    }
//
//    public void getInfoAndSendUpdate() {
//        LinearLayout layout = findViewById(R.id.mainView);
//        JsonObject jsonObject = new JsonObject();
//        int notNullCount = 0;
//        for (int i = 0; i < layout.getChildCount(); i++) {
//            View child = layout.getChildAt(i);
//            if (child instanceof EditText) {
//                if (((EditText) child).getText() != null) {
//                    notNullCount++;
//// let yr backend verify if all the fields cming in are null or not if they are null it shoudnt update else update;
//                }
//                String message = ((EditText) child).getText().toString();
//                String hint = ((EditText) child).getHint().toString().toLowerCase();
//                jsonObject.addProperty(hint, message);
//                jsonObject.addProperty("userId", id);
//            }
//        }
//        LinearLayout linear = findViewById(R.id.radioGroup);
//        for (int i = 0; i < layout.getChildCount(); i++) {
//            View child = layout.getChildAt(i);
//            if (child instanceof RadioButton) {
//                if (child.isActivated()){
//                    jsonObject.addProperty("prperty" , 1);
//                }
//            }
//            }
//
//
//        if (notNullCount == 0){
//            verifyFied();
//        }
//    }
//
//    void verifyFied(){
//
//    }


}