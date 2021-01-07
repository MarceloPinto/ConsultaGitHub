package com.faustino.consultaapigithub.service;

import com.faustino.consultaapigithub.model.Item;
import com.faustino.consultaapigithub.model.ObjetoRepos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceRepoGitHub {

    @GET("/search/repositories")
    Call<ObjetoRepos> recuperaRepos(@Query("q") String language, @Query("page") int page);
    //https://api.github.com/search/repositories?q=language:java
}
