package com.pokedex.enzoftware.pokedexenzoftware.pokemonapi;

import retrofit2.Call;
import retrofit2.http.GET;

import com.pokedex.enzoftware.pokedexenzoftware.models.pokemonRespuesta;

public interface pokemonService{
    @GET("pokemon")
    Call<pokemonRespuesta> obtenerListaPokemones();

}
