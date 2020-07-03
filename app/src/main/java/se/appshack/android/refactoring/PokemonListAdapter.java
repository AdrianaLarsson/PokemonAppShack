package se.appshack.android.refactoring;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonViewHolder> {

    private final Activity activity;
    private List<NamedResponseModel> data;

    public PokemonListAdapter(Activity context, List<NamedResponseModel> pokemonModels) {
        this.activity = context;
        this.data = pokemonModels;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PokemonViewHolder(LayoutInflater.from(activity).inflate(R.layout.viewholder_pokemon_list, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder pokemonViewHolder, int i) {
        final NamedResponseModel responseModel = data.get(i);

        pokemonViewHolder.bind(responseModel);
        pokemonViewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, PokemonDetailsActivity.class);
                intent.putExtra("POKEMON_NAME", responseModel.name);
                intent.putExtra("POKEMON_URL", responseModel.url);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class PokemonViewHolder extends RecyclerView.ViewHolder {

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(NamedResponseModel pokemon) {
        String number = String.format("#%s", pokemon.url.substring(pokemon.url.indexOf("pokemon/") + 8, pokemon.url.length() - 1));
        ((TextView) itemView.findViewById(R.id.pokemon_number)).setText(number);

        String formattedName = pokemon.name.substring(0,1).toUpperCase() + pokemon.name.substring(1);
        ((TextView) itemView.findViewById(R.id.pokemon_name)).setText(formattedName);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }
}
