package com.example.guestuser.findmyword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);
        String category = getIntent().getStringExtra("category");
        setTitle(category);
    }
}
