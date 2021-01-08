package com.faustino.consultaapigithub.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.faustino.consultaapigithub.model.Item;
import com.faustino.consultaapigithub.model.ObjetoRepos;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *  Essa classe concentrará todas as requisições, realizadas através da API Retrofit
 *  A resposta para essas requisições, quando houverem, serão retornadas através de uma interface
 *
 */
public class ControlaRequisicoes {

    private Context context;
    private RespostaRequisicoes respostaRequisicoes;

    private Retrofit retrofit;

    public ControlaRequisicoes(Context context, RespostaRequisicoes respostaRequisicoes) {
        this.context = context;
        this.respostaRequisicoes = respostaRequisicoes;

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void retornaRepositorios(String language, int page){

        ServiceRepoGitHub dataService = retrofit.create(ServiceRepoGitHub.class);
        Call<ObjetoRepos> call = dataService.recuperaRepos("language:"+language, page);

        call.enqueue(new Callback<ObjetoRepos>() {
            @Override
            public void onResponse(Call<ObjetoRepos> call, Response<ObjetoRepos> response) {
                if (response.isSuccessful()){

                    List<Item> listRepositorios = response.body().getItems();

                    Log.e("SIZE ITEMS", String.valueOf(listRepositorios.size()));

                    respostaRequisicoes.onReceiveRepositorios(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<ObjetoRepos> call, Throwable t) {
                Log.e("onFailure", "ATENTION PLEASE");
                if (t instanceof IOException) {
                    Toast.makeText(context, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                }
                t.printStackTrace();
                Log.e("Repos", t.toString());

            }
        });
    }
}
