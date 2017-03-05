package com.example.guestuser.findmyword;

import android.content.Intent;
import android.graphics.Color;
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
    private int colorInt =0;

    private int getPictureOrColor(String category){
        String uri = "drawable/"+category.toLowerCase();
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        if (imageResource == 0){
            String color = "red";
            if (colorInt == 0) colorInt = 1;
            switch (colorInt){
                case 1:
                    color = "red";
                    colorInt++;
                    break;
                case 2:
                    color = "blue";
                    colorInt++;
                    break;
                case 3:
                    color = "yellow";
                    colorInt++;
                    break;
                case 4:
                    color = "green";
                    colorInt = 1;
                    break;
            }
            uri = "drawable/"+color;
            imageResource = getResources().getIdentifier(uri, null, getPackageName());
        }
        return imageResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG,"onCreate Launched");
        boolean terminal_node = false;
        int numButtons;
        int numRows;
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
        this.wf.selectCategory(category);
        if(this.wf.hasNextCategories()) {
            categories = this.wf.getNames();
        } else {
            if (this.wf.hasWords()){
                terminal_node=true;
                categories=this.wf.getWords();
            } else {
                categories = new String[1];
                categories[0]="This should not happen";
                Log.d(DEBUG_TAG,categories[0]);
            }
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
            if (numButtons%2 != 0 && i == numRows-1){
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                buttons[buttonCount] = new Button(this);
                buttons[buttonCount].setText(categories[buttonCount]);
                buttons[buttonCount].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                buttons[buttonCount].setTextColor(Color.BLACK);
                buttons[buttonCount].setBackgroundResource(getPictureOrColor(categories[buttonCount]));
                buttons[buttonCount].getBackground().setAlpha(85);
                buttons[buttonCount].setLayoutParams(buttonParams);
                if (!terminal_node) buttons[buttonCount].setOnClickListener(this);
                horizontals[i].addView(buttons[buttonCount]);
            } else {
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                int test = buttonCount+1;
                for (int j=buttonCount; j <= test ;j++) {
                    if (j == numButtons) break;
                    buttons[j] = new Button(this);
                    buttons[j].setText(categories[j]);
                    buttons[j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                    buttons[j].setTextColor(Color.BLACK);
                    buttons[j].setBackgroundResource(getPictureOrColor(categories[buttonCount]));
                    buttons[j].getBackground().setAlpha(85);
                    buttons[j].setLayoutParams(buttonParams);
                    buttons[j].setTag(buttons[j].getText());
                    buttons[j].generateViewId();
                    if (!terminal_node) buttons[j].setOnClickListener(this);
                    horizontals[i].addView(buttons[j]);
                    buttonCount++;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.wf.goBack();
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
