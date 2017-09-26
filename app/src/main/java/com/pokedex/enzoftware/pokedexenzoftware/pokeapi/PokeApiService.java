package com.pokedex.enzoftware.pokedexenzoftware.pokeapi;

import retrofit2.Call;
import retrofit2.http.GET;

import com.pokedex.enzoftware.pokedexenzoftware.models.PokemonRespuesta;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemones();

}
