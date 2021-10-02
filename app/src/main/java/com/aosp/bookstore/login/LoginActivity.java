package com.aosp.bookstore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import com.aosp.bookstore.MainActivity;
import com.aosp.bookstore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout emailEditTextLayout, passwordEditTextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_input_editText);
        emailEditTextLayout = findViewById(R.id.email_input_layout);
        passwordEditText = findViewById(R.id.password_input_editText);
        passwordEditTextLayout = findViewById(R.id.password_input_layout);
        TextView registerTv = findViewById(R.id.register_text);
        MaterialButton submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> performLogin());
        registerTv.setOnClickListener(v -> navigateToSignUp());
    }

    private void performLogin() {
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditTextLayout.setError("Invalid Email address");
            return;
        } else {
            emailEditTextLayout.setError(null);
        }

        if (password.length() < 8) {
            passwordEditTextLayout.setError("Password cannot be less than 8 characters");
            return;
        } else {
            passwordEditTextLayout.setError(null);
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void navigateToSignUp() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}