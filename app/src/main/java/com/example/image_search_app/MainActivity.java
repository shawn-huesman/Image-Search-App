package com.example.image_search_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageListAdapter adapter;
    List<String> imageResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText imageSearchEditText = findViewById(R.id.editTextImageSearch);

        RecyclerView recyclerView = findViewById(R.id.rvImages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageListAdapter(this, imageResults);
        recyclerView.setAdapter(adapter);

        imageSearchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                adapter.clear();
                String searchTerm = imageSearchEditText.getText().toString();

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
                        ArrayList<String> results = new ArrayList<>();

                        // get only odd indexed images due to consecutive duplicate image results
                        for (int i = 0; i < result.getResults().size(); i++) {
                            if (i % 2 == 0) {
                                results.add(result.getResults().get(i));
                            }
                        }
                        adapter.setImageList(results);
                    }

                    @Override
                    public void onFailure(Call<ImageSearchResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Bad image request",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}