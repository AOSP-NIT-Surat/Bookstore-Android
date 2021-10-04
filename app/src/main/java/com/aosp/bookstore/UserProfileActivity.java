package com.aosp.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserProfileActivity extends AppCompatActivity {

    ImageView profile_pic;
    TextView name;
    TextView email;
    TextView textView;
    ListView booklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profile_pic = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        textView = findViewById(R.id.textView);
        booklist = findViewById(R.id.booklist);

        Intent intent = getIntent();
    }
}