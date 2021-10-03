package com.aosp.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton expand;
    FloatingActionButton sellBook;

    TextView text;

    Boolean isAllFabVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expand = findViewById(R.id.expand);
        sellBook = findViewById(R.id.sellBook);

        text = findViewById(R.id.text);

        sellBook.setVisibility(View.GONE);
        text.setVisibility(View.GONE);

        isAllFabVisible = false;

        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isAllFabVisible){

                    sellBook.show();
                    text.setVisibility(View.VISIBLE);

                    isAllFabVisible = true;
                }
                else{

                    sellBook.hide();
                    text.setVisibility(View.GONE);

                    isAllFabVisible = false;
                }
            }
        });

        sellBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Clicked!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}