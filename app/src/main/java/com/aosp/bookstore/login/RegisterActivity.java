package com.aosp.bookstore.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.aosp.bookstore.R;

public class RegisterActivity extends AppCompatActivity {

    TextView mSignInTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mSignInTv = findViewById(R.id.sign_in_text);
        mSignInTv.setOnClickListener(v -> navigateToSignIn());
    }

    private void navigateToSignIn() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

}