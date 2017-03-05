package com.example.guestuser.findmyword;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubMenu extends AppCompatActivity implements View.OnClickListener{
    private static final String APP_NAME = "FindMyWord";
    private static final String DEBUG_TAG = "Filip_debug_tag";
    private static final boolean DEBUG_FLAG = false;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static WordFinder wf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG,"onCreate Launched");
        int numButtons;
        int numRows;// = this.numButtons/2;
        int buttonCount = 0;
        LinearLayout layout;
        LinearLayout horizontals[];
        Button buttons[];
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, dm);
        String category = getIntent().getStringExtra("category");
        String categories[];
        
        if (this.wf == null){
            Log.d(DEBUG_TAG,"NULL WordFinder");
            this.wf = new WordFinder(this);
            this.wf.setName(category);
        } else {
            Log.d(DEBUG_TAG,this.wf.getName());
        }

        if(this.wf.hasNextCategories()) {
            this.wf.selectCategory(category);
            categories = this.wf.getNames();
        } else if (this.wf.hasWords()){
            categories = this.wf.getWords(category);
           // categories = this.wf.getNames();
        } else {
            //shit goes crazy
            categories = new String[0];
            categories[0]="THIS SHOULD NEVER HAPPEN!";
            Log.d(DEBUG_TAG,categories[0]);
        }
        numButtons = categories.length;
        numRows = numButtons/2;

        if (numRows%2 != 0){
            numRows++;
        }

        horizontals = new LinearLayout[numRows];
        buttons = new Button[numButtons];


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);
        setTitle(category);
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
                buttons[buttonCount] = new Button(this);
                buttons[buttonCount].setText(categories[buttonCount]);
                buttons[buttonCount].setLayoutParams(buttonParams);
                buttons[buttonCount].setOnClickListener(this);
                horizontals[i].addView(buttons[buttonCount]);
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
                    buttons[j] = new Button(this);
                    buttons[j].setText(categories[j]);
                    buttons[j].setLayoutParams(buttonParams);
                    buttons[j].setTag(buttons[j].getText());
                    buttons[j].generateViewId();
                    buttons[j].setOnClickListener(this);
                    horizontals[i].addView(buttons[j]);
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
        intent.putExtra(KEY_NAME,name);
        startActivity(intent);
    }
}
