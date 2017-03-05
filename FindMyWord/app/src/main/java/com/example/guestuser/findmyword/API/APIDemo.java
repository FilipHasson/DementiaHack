package com.example.guestuser.findmyword.API;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marc on 2017-03-05.
 */

public class APIDemo {

    public void searchWord(String searchQuery) {

        FindMyWordAPIController controller = new FindMyWordAPIController();
        controller.searchWord(searchQuery, new Callback<List<WordResult>>() {
            @Override
            public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                if (response.isSuccessful()) {
                    //List of word results
                    List<WordResult> wordResults = response.body();
                    //Access first result and get word
                    String word = wordResults.get(0).getWord().toString();
                    Log.d("marc_tag", word);
                }
                else {
                    Log.d("marc_tag", "failed");
                }
            }

            @Override
            public void onFailure(Call<List<WordResult>> call, Throwable t) {
                Log.d("marc_tag", "call failed");
                t.printStackTrace();
            }
        });
    }
}
