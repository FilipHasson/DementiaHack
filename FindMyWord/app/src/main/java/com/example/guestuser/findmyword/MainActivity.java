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

import com.example.guestuser.findmyword.API.APIDemo;
import com.example.guestuser.findmyword.API.FindMyWordAPIController;
import com.example.guestuser.findmyword.API.Photo;
import com.example.guestuser.findmyword.API.PhotoResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String APP_NAME = "FindMyWord";
    private static final boolean DEBUG_FLAG = false;
    private static final String KEY_NAME = "category";
    private static final String WORD_FINDER = "word_finder";
    private static final String DEBUG_TAG = "DEBUG:";

    Button buttons[];
    String categories[];

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

        horizontals = new LinearLayout[numRows];
        this.buttons = new Button[numButtons];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dynamic);

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
                this.buttons[buttonCount] = new Button(this);
                this.buttons[buttonCount].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                this.buttons[buttonCount].setTextColor(Color.BLACK);
                this.buttons[buttonCount].setText(categories[buttonCount]);
                searchPhoto(categories[buttonCount]);
                this.buttons[buttonCount].setLayoutParams(buttonParams);
                this.buttons[buttonCount].setOnClickListener(this);
                horizontals[i].addView(this.buttons[buttonCount]);
            } else {
                horizontals[i] = new LinearLayout(this);
                horizontals[i].setOrientation(LinearLayout.HORIZONTAL);
                horizontals[i].setLayoutParams(linearParams);
                horizontals[i].setBaselineAligned(false);
                layout.addView(horizontals[i]);
                int test = buttonCount+1;
                for (int j=buttonCount; j <= test ;j++) {
                    if (j == numButtons){
                        break;
                    }
                    this.buttons[j] = new Button(this);
                    this.buttons[j].setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
                    this.buttons[j].setTextColor(Color.BLACK);
                    this.buttons[j].setText(categories[j]);
                    searchPhoto(categories[buttonCount]);
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
        intent.putExtra(KEY_NAME,name);
        startActivity(intent);
    }
}
