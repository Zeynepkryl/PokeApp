package com.example.pokeapp.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokeapp.Models.Example;
import com.example.pokeapp.data.Remote.ApiClient;
import com.example.pokeapp.data.Remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailViewModel extends AndroidViewModel {


    public PokemonDetailViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<Example> getDetail(String name) {
        MutableLiveData<Example> detailPokemon = new MutableLiveData<>();
        ApiService apiService = ApiClient.createApiClient().create(ApiService.class);
        apiService.getPokemonDetail(name).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.isSuccessful())
                    detailPokemon.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
            }
        });
        return detailPokemon;
    }

}


