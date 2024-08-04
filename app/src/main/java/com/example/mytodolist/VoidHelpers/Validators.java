package com.example.mytodolist.VoidHelpers;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class Validators {
    private static boolean isPasswordVisible = false;

    public static void togglePasswordVisibility(EditText password) {
        if (isPasswordVisible) {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        isPasswordVisible = !isPasswordVisible;
        password.setSelection(password.getText().length());
    }
    public static boolean throwEmptyNessError(EditText ... ids) {
        int count = 0;
        for (EditText id : ids) {
            EditText editText = id;
            if (editText.getText().toString().trim().isEmpty()) {
                editText.setError("Fields cannot be empty");
                count++;
            }
        }
        if (count == 0){
            return true;
        }
        return false;
    }
}
