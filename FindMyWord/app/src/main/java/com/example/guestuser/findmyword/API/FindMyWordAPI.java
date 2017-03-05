package com.example.guestuser.findmyword.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marc on 2017-03-04.
 */

public interface FindMyWordAPI {

    @GET("services/rest/")
    Call<PhotoResult> getPhotos(
        @Query("method") String method,
        @Query("api_key") String api_key,
        @Query("text") String text,
        @Query("sort") String sort,
        @Query("safe_search") String safe_search,
        @Query("content_type") String content_type,
        @Query("format") String format,
        @Query("nojsoncallback") String nojsoncallback
    );


    @GET("words")
    Call<List<WordResult>> getRelatedWords(
            @Query("ml") String relatedTo
    );

}
