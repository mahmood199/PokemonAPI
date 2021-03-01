package com.example.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {

    @GET("pokemon")
    Call<PokemonRes> obtainPokemonData();
}
