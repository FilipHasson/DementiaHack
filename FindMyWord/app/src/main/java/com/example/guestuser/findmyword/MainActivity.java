package com.example.guestuser.findmyword;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.guestuser.findmyword.API.APIDemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APP_NAME = "FindMyWord";
    private static final boolean DEBUG_FLAG = false;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static final String DEBUG_TAG = "Filip_debug_tag";

    Button buttons[];
    String categories[];

      public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        APIDemo demo = new APIDemo();
        demo.searchPhoto("horse");

        WordFinder wordFinder = new WordFinder(this);
        categories = wordFinder.getNames();
        int numButtons = categories.length;
        int numRows = numButtons/2;
        int buttonCount = 0;
        LinearLayout layout;
        LinearLayout horizontals[];
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, dm);

        //Round numRows up if there is an odd number of buttons
        if (numRows%2 != 0){
            numRows++;
        }

        //DEBUG print out the categories
        for (int i=0; i < categories.length; i++){
            Log.d(DEBUG_TAG,categories[i]);
        }

        horizontals = new LinearLayout[numRows];
        this.buttons = new Button[numButtons];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dynamic);

        layout = (LinearLayout) findViewById(R.id.LinearLayoutVertical);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0,Math.round(dpInPx),1);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < numRows; i++){
            Log.d(DEBUG_TAG,"Creating Row #"+Integer.toString(i));
            if (numButtons%2 != 0 && i == numRows-1){
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                this.buttons[buttonCount] = new Button(this);
                this.buttons[buttonCount].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                this.buttons[buttonCount].setTextColor(Color.WHITE);
                this.buttons[buttonCount].setText(categories[buttonCount]);
                String uri = "drawable/"+categories[buttonCount].toLowerCase();
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                this.buttons[buttonCount].setBackgroundResource(imageResource);//                Log.d(DEBUG_TAG,"Attempting to add IMAGE #"+Integer.toString(buttonCount));
//                getImages(categories[buttonCount]);
                this.buttons[buttonCount].setLayoutParams(buttonParams);
                this.buttons[buttonCount].setOnClickListener(this);
                horizontals[i].addView(this.buttons[buttonCount]);
            } else {
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                int test = buttonCount+1;
                for (int j=buttonCount; j <= test ;j++) {
                    Log.d(DEBUG_TAG,"Attempting to add Button #"+Integer.toString(j));
                    if (j == numButtons){
                        Log.d(DEBUG_TAG,"Breaking");
                        break;
                    }
                    this.buttons[j] = new Button(this);
                    this.buttons[j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                    this.buttons[j].setTextColor(Color.WHITE);
                    this.buttons[j].setText(categories[j]);
                    String uri = "drawable/"+categories[j].toLowerCase();
                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    this.buttons[j].setBackgroundResource(imageResource);
//                    Log.d(DEBUG_TAG,"Attempting to add IMAGE #"+Integer.toString(j));
//                    getImages(categories[j]);
                    this.buttons[j].setLayoutParams(buttonParams);
                    this.buttons[j].setTag(this.buttons[j].getText());
                    this.buttons[j].generateViewId();
                    this.buttons[j].setOnClickListener(this);
                    horizontals[i].addView(this.buttons[j]);
                    buttonCount++;
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SubMenu.class);
        Button b = (Button)v;
        String name = b.getText().toString();
        Log.d(DEBUG_TAG,name);
        intent.putExtra(KEY_NAME,name);
        startActivity(intent);
    }
}
