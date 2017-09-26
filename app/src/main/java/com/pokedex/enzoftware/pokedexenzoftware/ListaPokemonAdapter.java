package com.pokedex.enzoftware.pokedexenzoftware;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pokedex.enzoftware.pokedexenzoftware.models.Pokemon;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataset;
    private Context context;

    public ListaPokemonAdapter(Context context){
        dataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon ,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon p = dataset.get(position);
        holder.textViewnombre.setText(p.getName());

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/"+p.getNumber()+".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageViewfoto);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarPokemon(ArrayList<Pokemon> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewfoto;
        private TextView textViewnombre;

        public ViewHolder (View itemView){
            super(itemView);

            imageViewfoto = (ImageView) itemView.findViewById(R.id.fotoImgView);
            textViewnombre = (TextView) itemView.findViewById(R.id.nombreTextView);
        }
    }
}
