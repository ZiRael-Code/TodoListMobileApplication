package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytodolist.ChatConnect.ApiClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTask extends AppCompatActivity {
    EditText taskGroupType;
    ApiClient apiClient;

    EditText title ;
    EditText description ;
    EditText startDate ;
    EditText endDate;
    //                EditText priority =  findViewById(R.id.endDateEditId);
    EditText startHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        apiClient = new ApiClient();
        taskGroupType = findViewById(R.id.taskTypeEdit);
        title =  findViewById(R.id.titleEditId);
        description =  findViewById(R.id.descriptionEditId);
        startDate =  findViewById(R.id.startEditId);
        endDate =  findViewById(R.id.endDateEditId);
        startHour =  findViewById(R.id.timerEditId);

        String itemToUpdate = getIntent().getStringExtra("updateItem");
        String jsonResponseString = getIntent().getStringExtra("jsonResponse");

    if (itemToUpdate != null) {
        // test this when u start configuring task can be clicked
        try {
            JSONObject upDatable = new JSONObject(itemToUpdate);
            taskGroupType.setHint(upDatable.getString("taskType"));
            title.setHint(upDatable.getString("title"));
            description.setHint(upDatable.getString("description"));
            startDate.setHint(upDatable.getString("startDate"));
            endDate.setHint(upDatable.getString("dueDate"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //to this

    } else if (jsonResponseString != null) {

        Spinner spinner = findViewById(R.id.projectType);
        try {
            JSONObject jsonObject = new JSONObject(jsonResponseString);
            int id = jsonObject.getInt("id");

            List<String> projectGroups = getIntent().getStringArrayListExtra("projectGroups");
            assert projectGroups != null;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, projectGroups);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    taskGroupType.setText(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            Button addTask = findViewById(R.id.updateSave);
            addTask.setOnClickListener(v -> {

            if (validateEditTextFields()) {
                JsonObject jsonObj = new JsonObject();
                jsonObj.addProperty("userId", id);
                jsonObj.addProperty("title",title.getText().toString().trim());
                jsonObj.addProperty("description",description.getText().toString().trim());
                jsonObj.addProperty("startDate", startDate.getText().toString().trim());
                jsonObj.addProperty("endDate", endDate.getText().toString().trim());
                jsonObj.addProperty("priority", "HIGH");
                jsonObj.addProperty("taskType", taskGroupType.getText().toString());
                jsonObj.addProperty("startHour", startHour.getText().toString().trim());


                RequestBody requestBody = FormBody.create(jsonObj.toString(), MediaType.parse("application/json"));




                Call<JsonObject> callback = apiClient.returnApiService().sendAddTaskReq(requestBody);
                callback.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Toast.makeText(AddTask.this, "Success ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddTask.this, DashBooardActivity.class);
                            intent.putExtra("jsonResponse", jsonResponseString);
                            startActivity(intent);

                        } else {
                            Toast.makeText(AddTask.this, "Body is null ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddTask.this, "not successful", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable throwable) {

                }
            });

                } else {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    }

    private boolean validateEditTextFields() {

        boolean isValid = true;

        int[] editTextIds = new int[]{R.id.taskTypeEdit,R.id.titleEditId, R.id.descriptionEditId, R.id.startEditId, R.id.endDateEditId};

        for (int editTextId : editTextIds) {
            EditText editText = findViewById(editTextId);
            if (editText.getText().toString().trim().isEmpty()) {
                editText.setError("Field cannot be empty");
                isValid = false;
            }
        }
        return isValid;
    }

    public void sartDate(View view){
        TextView startDate = findViewById(R.id.startEditId);
        showDialogOofDateAndReturnTheStringOfDatePiced(startDate);
    }

    public void timerDp(View view){
        TextView startDate = findViewById(R.id.timerDp);
        showDialogOofDateAndReturnTheStringOfDatePiced(startDate);
    }

    public void endDate(View view){
        TextView endDate = findViewById(R.id.endDateEditId);
        showDialogOofDateAndReturnTheStringOfDatePiced(endDate);
    }

    void showDialogOofDateAndReturnTheStringOfDatePiced(TextView textView){
        final String[] date = {null};
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        View dateDialog = getLayoutInflater().inflate(R.layout.custom_date_picker, null);
        final DatePicker[] datePicker = {dateDialog.findViewById(R.id.dialog_date_datePicker)};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        datePicker[0].setMinDate(calendar.getTimeInMillis());
        datePicker[0].init(currentYear, currentMonth, currentDay, null);

        ((ViewGroup) datePicker[0].getParent()).removeView(datePicker[0]);
        builder.setView(datePicker[0])
                .setTitle("Select Date")
                .setPositiveButton("OK", (dialog, which) -> {
                    int year = datePicker[0].getYear();
                    int month = datePicker[0].getMonth();
                    int dayOfMonth = datePicker[0].getDayOfMonth();
                    String dates = month + "-" + dayOfMonth+ "-" + year ;
                    textView.setText(dates);
                    date[0] = dates;
                })
                .setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();

    }

    private static String arrangingHint(String testing) {
        char[] lowers = testing.toLowerCase().toCharArray();
        List<String>values = new ArrayList<>();
        for (int i = 0; i < lowers.length; i++) {
            char chars = lowers[i];
            if (i > 0){
                if (lowers[i-1] == ' '){
                    values.add(String.valueOf(chars).toUpperCase());
                    continue;
                }
            }
            values.add(String.valueOf(chars));
        }
        final String[] res = {""};
        values.stream().filter(x-> !x.equals(" ")).forEach(x-> res[0] +=x);
        return res[0];
    }
}
