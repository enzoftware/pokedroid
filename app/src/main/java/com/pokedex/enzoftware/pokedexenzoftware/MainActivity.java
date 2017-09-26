package com.pokedex.enzoftware.pokedexenzoftware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pokedex.enzoftware.pokedexenzoftware.models.Pokemon;
import com.pokedex.enzoftware.pokedexenzoftware.models.PokemonRespuesta;
import com.pokedex.enzoftware.pokedexenzoftware.pokeapi.PokeApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter();
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        obtenerDatos();
    }


    private void obtenerDatos(){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall =  service.obtenerListaPokemones();
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if(response.isSuccessful()){
                    PokemonRespuesta pokemonrespuesta = response.body();
                    ArrayList<Pokemon> listaPokemon = pokemonrespuesta.getResults();

                    listaPokemonAdapter.adicionarPokemon(listaPokemon);

                    for (int i = 0 ; i<listaPokemon.size(); i++){
                        Pokemon p = listaPokemon.get(i);
                        Log.i(TAG," Pokemon : " + p.getName() );
                    }

                }else {
                    Log.e(TAG, " onResponse : " + response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.e(TAG," onFailure " + t.getMessage());
            }
        });
    }

}
