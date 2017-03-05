package com.example.guestuser.findmyword;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guestuser.findmyword.API.FindMyWordAPIController;
import com.example.guestuser.findmyword.API.Photo;
import com.example.guestuser.findmyword.API.PhotoResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubMenu extends AppCompatActivity implements View.OnClickListener{
    private static final String APP_NAME = "FindMyWord";
    private static final String DEBUG_TAG = "Filip_debug_tag";
    private static final boolean DEBUG_FLAG = false;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static WordFinder wf;
    private int colorInt =0;
    private String categories[];
    private Button buttons[];

    public void searchPhoto(final String searchQuery) {
        FindMyWordAPIController controller = new FindMyWordAPIController();
        controller.searchPhotos(searchQuery, new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                if (response.isSuccessful()) {
                    List<Photo> photoList = response.body().getPhotos().getPhoto();
                    int myint;
                    for (myint =0; myint < categories.length; myint++){
                        if (categories[myint].equals(searchQuery)){
                            break;
                        }
                    }
                    final int finalint = myint;
                    //or you can get only first photo
                    String firstPhotoURL = photoList.get(0).toUrl();
                    final ImageView targetImageView = new ImageView(getBaseContext());//(ImageView) findViewById(R.id.imageView);
                    Context context = getBaseContext();
                    Picasso
                            .with(context)
                            .load(firstPhotoURL)
                            .into(targetImageView, new com.squareup.picasso.Callback(){
                                @Override
                                public void onSuccess() {
                                    Drawable d = targetImageView.getDrawable();
                                    buttons[finalint].setBackground(d);
                                    buttons[finalint].getBackground().setAlpha(95);
                                }
                                @Override
                                public void onError() {
                                    Log.d("BALLS", "Shit Failed yo");
                                }
                            });
                } else {
                    Log.d(DEBUG_TAG, "failed");
                }
            }
            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                Log.d(DEBUG_TAG, "search photo fail");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean terminal_node = false;
        int numButtons;
        int numRows;
        int buttonCount = 0;
        LinearLayout layout;
        LinearLayout horizontals[];
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, dm);
        String category = getIntent().getStringExtra("category");

        if (this.wf == null){
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
                horizontals[i].setBaselineAligned(false);
                layout.addView(horizontals[i]);
                buttons[buttonCount] = new Button(this);
                buttons[buttonCount].setText(categories[buttonCount]);
                buttons[buttonCount].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                buttons[buttonCount].setTextColor(Color.BLACK);
                searchPhoto(categories[buttonCount]);
                buttons[buttonCount].setLayoutParams(buttonParams);
                if (!terminal_node) buttons[buttonCount].setOnClickListener(this);
                horizontals[i].addView(buttons[buttonCount]);
            } else {
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                horizontals[i].setBaselineAligned(false);
                layout.addView(horizontals[i]);
                int test = buttonCount+1;
                for (int j=buttonCount; j <= test ;j++) {
                    if (j == numButtons) break;
                    buttons[j] = new Button(this);
                    buttons[j].setText(categories[j]);
                    buttons[j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
                    buttons[j].setTextColor(Color.BLACK);
                    searchPhoto(categories[buttonCount]);
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
