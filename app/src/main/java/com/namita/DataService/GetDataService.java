package com.namita.DataService;

import com.namita.Model.Articles;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/api/v2/articles")
    Call<ArrayList<Articles>> getArticles();
}
