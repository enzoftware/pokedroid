package com.pokedex.enzoftware.pokedexenzoftware;


import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pokedex.enzoftware.pokedexenzoftware.models.pokemon;

import java.util.ArrayList;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{

    private ArrayList<pokemon> dataset;

    public ListaPokemonAdapter(){
        dataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon ,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pokemon p = dataset.get(position);
        holder.textViewnombre.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void adicionarPokemon(ArrayList<pokemon> listaPokemon) {
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
