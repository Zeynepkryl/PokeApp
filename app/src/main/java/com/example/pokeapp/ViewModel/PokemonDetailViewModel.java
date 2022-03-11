package com.example.pokeapp.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.pokeapp.Models.PokemonDetail;
import com.example.pokeapp.data.Remote.ApiClient;
import com.example.pokeapp.data.Remote.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokemonDetailViewModel extends AndroidViewModel {


    public PokemonDetailViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<PokemonDetail> getDetail(String name) {
        MutableLiveData<PokemonDetail> detailPokemon = new MutableLiveData<>();
        ApiService apiService = ApiClient.createApiClient().create(ApiService.class);
        apiService.getPokemonDetail(name).enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful())
                        detailPokemon.setValue(response.body());

            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {

            }
        });
        return detailPokemon;
    }


}


