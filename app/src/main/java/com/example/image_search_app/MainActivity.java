package com.example.image_search_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String searchTerm = "chocolate";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://imsea.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ImseaClient client = retrofit.create(ImseaClient.class);
        Call<ImageSearchResult> call = client.imagesForSearch(searchTerm);

        call.enqueue(new Callback<ImageSearchResult>() {
            @Override
            public void onResponse(Call<ImageSearchResult> call, Response<ImageSearchResult> response) {
                ImageSearchResult result = response.body();

                System.out.println("IMAGE RESULTS: " + result.getResults());
            }

            @Override
            public void onFailure(Call<ImageSearchResult> call, Throwable t) {
                System.out.println("error");
                System.out.println(t.getMessage());
                Toast.makeText(MainActivity.this, "error dude", Toast.LENGTH_SHORT).show();
            }
        });
    }
}