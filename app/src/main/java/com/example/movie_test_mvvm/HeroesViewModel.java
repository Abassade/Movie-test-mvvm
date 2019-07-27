package com.example.movie_test_mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HeroesViewModel extends ViewModel {

    //this is the data that we will fetch asynchronously
    private MutableLiveData<List<Movie>> heroList;

    //we will call this method to get the data
    public LiveData<List<Movie>> getHeroes() {
        //if the list is null
        if (heroList == null) {
            heroList = new MutableLiveData<List<Movie>>();
            //we will load it asynchronously from server in this method
            loadHeroes();
        }

        //finally we will return the list
        return heroList;
    }


    //This method is using Retrofit to get the JSON data from URL
    private void loadHeroes() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<Movie>> call = api.getHeroes();


        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                Log.i("success message", response.toString());
                //finally we are setting the list to our MutableLiveData
                heroList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.i("message", t.getMessage());
            }
        });
    }
}