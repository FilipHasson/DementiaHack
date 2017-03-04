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
    private static final boolean DEBUG_FLAG = false;
    private int numButtons = 7;
    private static final String KEY_NAME = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Filip_debug_tag","onCreate Launched");
        WordFinder wf = new WordFinder ();
        int numRows = this.numButtons/2;
        int cButton = 0;
        LinearLayout layout;
        LinearLayout horizontals[];

        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, dm);

        Button buttons[];
        if (numRows%2 != 0){
            numRows++;
        }
        horizontals = new LinearLayout[numRows];
        buttons = new Button[numButtons];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);
        String category = getIntent().getStringExtra("category");
        setTitle(category);
        layout = (LinearLayout) findViewById(R.id.LinearLayoutVertical);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(0,Math.round(dpInPx),1);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout test = new LinearLayout(this);
        test.setOrientation(LinearLayout.HORIZONTAL);
        test.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < numRows; i++){
            //If there is an uneven number of buttons & this is the last row
            if (this.numButtons%2 != 0 && i == numRows-1){
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                buttons[cButton] = new Button(this);
                buttons[cButton].setText(category+"."+Integer.toString(cButton));
                buttons[cButton].setLayoutParams(buttonParams);
                buttons[cButton].setOnClickListener(this);
                horizontals[i].addView(buttons[cButton]);
            } else {
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                layout.addView(horizontals[i]);
                for (int j=cButton; j <= cButton+1;j++) {
                    buttons[j] = new Button(this);
                    buttons[j].setText(category+"."+Integer.toString(j));
                    buttons[j].setLayoutParams(buttonParams);
                    buttons[j].setTag(buttons[j].getText());
                    buttons[j].generateViewId();
                    buttons[j].setOnClickListener(this);
                    horizontals[i].addView(buttons[j]);
                }
                cButton = cButton + 2;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,SubMenu.class);
        Button b = (Button)v;
        //Log.d("Filip_debug_tag",((TextView)v.findViewById(v.getId())).getText().toString());
        String name = b.getText().toString();
        intent.putExtra(KEY_NAME,name);
        startActivity(intent);
    }
}
