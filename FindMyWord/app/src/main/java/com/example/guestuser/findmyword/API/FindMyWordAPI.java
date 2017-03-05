package com.example.guestuser.findmyword.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marc on 2017-03-04.
 */

public interface FindMyWordAPI {

    @GET("v1")
    Call<SearchData> getImages(
            @Query("q") String searchQuery,
            @Query("key") String apiKey,
            @Query("cx") String searchengineKey,
            @Query("alt") String format,
            @Query("searchType") String searchType
    );

    @GET("words")
    Call<List<WordResult>> getRelatedWords(
            @Query("ml") String relatedTo
    );

}
