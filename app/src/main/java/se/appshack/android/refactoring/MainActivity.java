package se.appshack.android.refactoring;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetPokemonListTask getPokemonListTask = new GetPokemonListTask();
        getPokemonListTask.execute();
    }

    class GetPokemonListTask extends AsyncTask<Void, Void, PokemonListResponse> {

        @Override
        protected PokemonListResponse doInBackground(Void... ignore) {
            System.out.println("doInBackground");
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://pokeapi.co/api/v2/pokemon/?limit=151")
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build();

            PokemonListResponse response = null;
            try {
                Response httpResponse = client.newCall(request).execute();
                response = new Gson().fromJson(httpResponse.body().string(), PokemonListResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(PokemonListResponse result) {
            super.onPostExecute(result);

            List<NamedResponseModel> pokemonModels = new ArrayList<>();

            List<Integer> ids = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                int id = (int) (Math.random() * 151 + 1);
                if (ids.contains(id)) {
                    i--;
                } else {
                    ids.add(id);
                }
            }

            Collections.sort(ids, new Comparator<Integer>() {
                @Override
                public int compare(Integer integer, Integer t1) {
                    return integer - t1;
                }
            });

            for (int i : ids) {
                pokemonModels.add(result.results.get(i - 1));
            }

            PokemonListAdapter pokemonAdapter = new PokemonListAdapter(MainActivity.this, pokemonModels);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setAdapter(pokemonAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            findViewById(R.id.loader).setVisibility(View.GONE);
        }
    }
}
