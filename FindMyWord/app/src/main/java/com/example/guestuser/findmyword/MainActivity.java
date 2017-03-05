package com.example.guestuser.findmyword;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APP_NAME = "FindMyWord";
    private static final boolean DEBUG_FLAG = false;
    private int numButtons = 8;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static final String DEBUG_TAG = "Filip_debug_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        WordFinder wordFinder = new WordFinder();
//        wordFinder.getAllCategories(this);

//        String categories[] = wordFinder.getNames();

//        for (int i=0; i < categories.length; i++){
//            Log.d(DEBUG_TAG,"NULL WordFinder");
//        }

        setContentView(R.layout.activity_main);
        for (int i = 1; i <= numButtons; i++) {
            (findViewById(getResources().getIdentifier("Button" + i, "id",this.getPackageName()))).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SubMenu.class);
        Log.d("Filip_debug_tag",((TextView)v.findViewById(v.getId())).getText().toString());
        String name = ((TextView) v.findViewById(v.getId())).getText().toString();
        intent.putExtra(KEY_NAME,name);
        intent.putExtra(WORD_FINDER,(Parcelable) null);
        startActivity(intent);
    }
}
