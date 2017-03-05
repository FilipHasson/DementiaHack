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

    public void searchPhoto(String searchQuery) {

        FindMyWordAPIController controller = new FindMyWordAPIController();
        controller.searchPhotos(searchQuery, new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                if (response.isSuccessful()) {

                    List<Photo> photoList = response.body().getPhotos().getPhoto();

                    //iterate through all photos
                    for(Photo photo : photoList) {
                        Log.d("marc_tag", photo.toUrl());
                    }

                    //or you can get only first photo
                    String firstPhotoURL = photoList.get(0).toUrl();

                }
                else {
                    Log.d("marc_tag", "failed");
                }
            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                Log.d("marc_tag", "search photo fail");
            }
        });

    }

    public void searchWord(String searchQuery) {

        FindMyWordAPIController controller = new FindMyWordAPIController();
        controller.searchWord(searchQuery, new Callback<List<WordResult>>() {
            @Override
            public void onResponse(Call<List<WordResult>> call, Response<List<WordResult>> response) {
                if (response.isSuccessful()) {
                    //List of word results
                    List<WordResult> wordResults = response.body();

                    for(WordResult wordResult : wordResults) {
                        Log.d("marc_tag", wordResult.getWord());
                    }

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
