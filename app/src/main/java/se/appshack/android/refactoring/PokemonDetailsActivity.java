package se.appshack.android.refactoring;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PokemonDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String pokemonName = intent.getExtras().getString("POKEMON_NAME");
        setTitle(pokemonName.toUpperCase());

        String pokemonUrl = intent.getExtras().getString("POKEMON_URL");
        GetPokemonDetailsTask detailsTask = new GetPokemonDetailsTask();
        detailsTask.execute(pokemonUrl);
    }

    class GetPokemonDetailsTask extends AsyncTask<String, Void, PokemonDetailsResponse> {

        @Override
        protected PokemonDetailsResponse doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urls[0])
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build();

            PokemonDetailsResponse response = null;
            try {
                Response httpResponse = client.newCall(request).execute();
                String jsonBody = httpResponse.body().string();
                response = new Gson().fromJson(jsonBody, PokemonDetailsResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(PokemonDetailsResponse result) {
            super.onPostExecute(result);

            ImageView imageFront = (ImageView) findViewById(R.id.imageFront);
            Picasso.with(PokemonDetailsActivity.this).load(result.sprites.urlFront).into(imageFront);

            ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
            Picasso.with(PokemonDetailsActivity.this).load(result.sprites.urlBack).into(imageBack);

            ((TextView) findViewById(R.id.pokemonNumber)).setText(String.format("#%s", result.id));

            String formattedName = result.name.substring(0, 1).toUpperCase() + result.name.substring(1);
            ((TextView) findViewById(R.id.pokemonName)).setText(formattedName);

            Collections.sort(result.types, new Comparator<PokemonTypeModel>() {
                @Override
                public int compare(PokemonTypeModel pokemonTypeModel, PokemonTypeModel t1) {
                    return pokemonTypeModel.slot - t1.slot;
                }
            });

            String types = "";
            for (int i = 0; i < result.types.size(); i++) {
                PokemonTypeModel typeModel = result.types.get(i);
                types += typeModel.type.name.substring(0, 1).toUpperCase() + typeModel.type.name.substring(1);
                if (i < result.types.size() - 1)
                    types += ", ";
            }

            ((TextView) findViewById(R.id.pokemonTypes)).setText(types);

            ((TextView) findViewById(R.id.pokemonHeight)).setText(String.format("%s decimetres", result.height));

            ((TextView) findViewById(R.id.pokemonWeight)).setText(String.format("%s hectograms", result.weight));

            new GetPokemonSpeciesDetailsTask().execute(result.species.url);
        }
    }

    class GetPokemonSpeciesDetailsTask extends AsyncTask<String, Void, PokemonSpeciesResponse> {

        @Override
        protected PokemonSpeciesResponse doInBackground(String... urls) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(urls[0])
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build();

            PokemonSpeciesResponse response = null;
            try {
                Response httpResponse = client.newCall(request).execute();
                String jsonBody = httpResponse.body().string();
                response = new Gson().fromJson(jsonBody, PokemonSpeciesResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(PokemonSpeciesResponse result) {
            super.onPostExecute(result);

            String genus = "";
            for (GenusResponseModel genusModel : result.genera) {
                if (genusModel.language.name.equals("en")) {
                    genus = genusModel.genus;
                    break;
                }
            }

            ((TextView) findViewById(R.id.pokemonSpecies)).setText(genus);

            findViewById(R.id.loader).setVisibility(View.GONE);
        }
    }
}
