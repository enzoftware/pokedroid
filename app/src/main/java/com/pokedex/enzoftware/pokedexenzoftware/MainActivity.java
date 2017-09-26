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
    private int offset ;
    private ListaPokemonAdapter listaPokemonAdapter;

    private Boolean aptoCargar = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition();

                    if(aptoCargar){
                        if((visibleItemCount + pastVisibleItem ) >= totalItemCount){
                            Log.i(TAG,"Llegamos al final");

                            aptoCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder().baseUrl("http://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        aptoCargar = true;
        offset = 0;
        obtenerDatos(offset);
    }


    private void obtenerDatos(int offset){
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall =  service.obtenerListaPokemones(20,offset);
        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                aptoCargar = true;
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
                aptoCargar = true;
                Log.e(TAG," onFailure " + t.getMessage());
            }
        });
    }

}
