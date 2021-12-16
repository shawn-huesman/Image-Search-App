package com.example.image_search_app;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ImseaClient {
    //https://imsea.herokuapp.com/api/1?q=putin%20on%20a%20horse
    @GET("/api/1?q")
    Call<ImageSearchResult> imagesForSearch(@Query("q") String search);
}
