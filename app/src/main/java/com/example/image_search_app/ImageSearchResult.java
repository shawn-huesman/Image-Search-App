package com.example.image_search_app;

import java.util.List;

public class ImageSearchResult {
    private String imageName;
    private List<String> results;

    public ImageSearchResult() {}

    public String getImageName() {
        return imageName;
    }

    public List<String> getResults() {
        return results;
    }
}
