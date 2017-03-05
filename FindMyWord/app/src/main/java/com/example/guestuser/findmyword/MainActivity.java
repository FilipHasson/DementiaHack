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

import com.example.guestuser.findmyword.API.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APP_NAME = "FindMyWord";
    private static final boolean DEBUG_FLAG = false;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static final String DEBUG_TAG = "Filip_debug_tag";

    private void getImages() {
        FindMyWordAPIController controller = new FindMyWordAPIController();

        //IMPORTANT
        String searchQuery = "cat";

        controller.searchImage("cat", new Callback<SearchData>() {

            //Change these callbacks to add custom logic
            @Override
            public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                if(response.isSuccessful()) {
                    Log.d("marc_tag","sweeter sound fam");

                    //Do whatever you want with these items
                    List<Item> items = response.body().getItems();

                    Item firstResult = items.get(0);
                    Log.d("api", firstResult.getImage().getContextLink().toString());

                } else {
                    Log.d("marc_tag", "failed" + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<SearchData> call, Throwable t) {
                Log.d("marc_tag", "call failed");
                t.printStackTrace();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getImages();

        WordFinder wordFinder = new WordFinder(this);
        String categories[] = wordFinder.getNames();
        int numButtons = categories.length;
        int numRows = numButtons/2;
        int buttonCount = 0;
        LinearLayout layout;
        LinearLayout horizontals[];
        Button buttons[];
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
        buttons = new Button[numButtons];

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
        Log.d(DEBUG_TAG,name);
        intent.putExtra(KEY_NAME,name);
        startActivity(intent);
//        Intent intent = new Intent(this,SubMenu.class);
//        Log.d("Filip_debug_tag",((TextView)v.findViewById(v.getId())).getText().toString());
//        String name = ((TextView) v.findViewById(v.getId())).getText().toString();
//        intent.putExtra(KEY_NAME,name);
//        intent.putExtra(WORD_FINDER,(Parcelable) null);
//        startActivity(intent);
    }
}
