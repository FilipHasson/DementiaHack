package com.example.guestuser.findmyword;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubMenu extends AppCompatActivity {
    private static final String APP_NAME = "FindMyWord";
    private static final boolean DEBUG_FLAG = false;
    private int numButtons = 8;
    private static final String KEY_NAME = "category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        WordFinder wf = new WordFinder ();
        int numRows = this.numButtons/2;
        int cButton = 0;
        LinearLayout layout;
        LinearLayout horizontals[];
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
        for (int i = 0; i < numRows; i++){
            //If there is an uneven number of buttons & this is the last row
//          android:id="@+id/LinearLayoutHorizontal01"
//          android:layout_width="fill_parent"
//          android:layout_height="wrap_content"
//          android:layout_alignParentBottom="true">
            if (this.numButtons%2 != 0 && i == numRows-1){

            } else {
                horizontals[i] = new LinearLayout(this);
                buttons[cButton] = new Button(this);

            }
        }
    }
}
