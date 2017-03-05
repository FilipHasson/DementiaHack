package com.example.guestuser.findmyword.API;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marc on 2017-03-04.
 */

public class FindMyWordAPIController {

    static final String IMAGES_BASE_URL = "https://www.googleapis.com/customsearch/";
    static final String WORDS_BASE_URL = "https://api.datamuse.com";
    static final String apiKey = "AIzaSyAfLh4FzlRPOlo1lUkPYXMxikrZJ290bc4";
    static final String searchengineKey = "009590346671647894435:g6po9lguowi";
    static final String format = "json";
    static final String searchType = "image";

    public void searchImage(String searchQuery, Callback<SearchData> callback) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMAGES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        FindMyWordAPI api = retrofit.create(FindMyWordAPI.class);

        Call<SearchData> call = api.getImages(searchQuery, apiKey, searchengineKey, format, searchType);

        call.enqueue(callback);

    }

    public void searchWord(String word, Callback<List<WordResult>> callback) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WORDS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindMyWordAPI api = retrofit.create(FindMyWordAPI.class);

        Call<List<WordResult>> call = api.getRelatedWords(word);

        call.enqueue(callback);
    }

}