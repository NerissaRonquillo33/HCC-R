package com.example.hcc.helper;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.hcc.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation implements TextWatcher {

    private View view;
    private TextInputEditText textInputEditText;
    private TextInputLayout textInputLayout;

    public Validation(View view, TextInputEditText textInputEditText, TextInputLayout textInputLayout) {
        this.view = view;
        this.textInputEditText = textInputEditText;
        this.textInputLayout = textInputLayout;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        switch (view.getId()) {
            case R.id.tf:
                validateNumber();
                break;
            case R.id.lai:
                validateNumber();
                break;
            case R.id.rf:
                validateNumber();
                break;
            case R.id.cpf:
                validateNumber();
                break;
            case R.id.gac:
                validateNumber();
                break;
            case R.id.sif:
                validateNumber();
                break;
            case R.id.sh:
                validateNumber();
                break;
            case R.id.sp:
                validateNumber();
                break;
            case R.id.ins:
                validateNumber();
                break;
            case R.id.ta:
                validateNumber();
                break;
            case R.id.ds:
                validateNumber();
                break;
            case R.id.na:
                validateNumber();
                break;
            case R.id.ccp:
                validateNumber();
                break;
            case R.id.ob:
                validateNumber();
                break;

            case R.id.studentid:
                validateNumber();
                break;
            case R.id.lastname:
                validateNames();
                break;
            case R.id.firstname:
                validateNames();
                break;
            case R.id.address:
                validateText();
                break;
            case R.id.contact:
                validatePhoneNumber();
                break;
            case R.id.birthday:
                validateBirthday();
                break;
            case R.id.email:
                validateEmail();
                break;
            case R.id.password:
                validatePassword();
                break;
            case R.id.section:
                validateNames();
                break;

            case R.id.username:
                validateUsername();
                break;
        }
    }
    public boolean validateEmail() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Email is required");
            return false;
        } else {
            String emailId = textInputEditText.getText().toString();
            Boolean  isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
            if (!isValid) {
                textInputLayout.setError("Invalid Email address, ex: abc@example.com");
                return false;
            } else {
                textInputLayout.setErrorEnabled(false);
            }
        }
        return true;
    }

    public boolean validatePassword() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Password is required");
            return false;
        }else if(textInputEditText.getText().toString().length() < 6){
            textInputLayout.setError("Password can't be less than 6 digit");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    public boolean validateNumber() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Number is required");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateNames() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("String is required");
            return false;
        }
        else if(!textInputEditText.getText().toString().trim().matches("[a-zA-Z ]+")){
            textInputLayout.setError("Alphabet and space only");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateText() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Address is required");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validatePhoneNumber() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Contact is required");
            return false;
        }
        else if(textInputEditText.getText().toString().length() != 11){
            textInputLayout.setError("11 numbers only");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateUsername() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Username is required");
            return false;
        }
        else if(!textInputEditText.getText().toString().matches("^[a-zA-Z][a-zA-Z0-9_]{6,19}$")){
            textInputLayout.setError("Invalid username! Username range between 7 to 19");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public boolean validateBirthday() {
        if (textInputEditText.getText().toString().trim().isEmpty()) {
            textInputLayout.setError("Birthday is required");
            return false;
        }
        else if(!checkDateFormat(textInputEditText.getText().toString())){
            textInputLayout.setError("Invalid date");
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }
    public Boolean checkDateFormat(String date){
        if (date == null || !date.matches("^[0-9]{4}/(0[1-9]|1[0-2])/(1[0-9]|0[1-9]|3[0-1]|2[1-9])$")) return false;
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        try {
            format.parse(date);
            return true;
        }catch (ParseException e){
            return false;
        }
    }
}
