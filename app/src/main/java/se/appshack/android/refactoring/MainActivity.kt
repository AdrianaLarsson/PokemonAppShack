package se.appshack.android.refactoring

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val getPokemonListTask = GetPokemonListTask()
        getPokemonListTask.execute()
    }

   internal inner class GetPokemonListTask : AsyncTask<Void?, Void?, PokemonListResponse?>() {
        protected override fun doInBackground(vararg params: Void?): PokemonListResponse? {
            println("doInBackground")
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("https://pokeapi.co/api/v2/pokemon/?limit=151")
                    .get()
                    .addHeader("Content-Type", "application/json; charset=utf8")
                    .build()
            var response: PokemonListResponse? = null
            try {
                val httpResponse = client.newCall(request).execute()
                response = Gson().fromJson(httpResponse.body?.string(), PokemonListResponse::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return response
        }

        override fun onPostExecute(result: PokemonListResponse?) {
            super.onPostExecute(result)
            val pokemonModels: MutableList<NamedResponseModel> = ArrayList()
            val ids: MutableList<Int> = ArrayList()
            var i = 0
            while (i < 20) {
                val id = (Math.random() * 151 + 1).toInt()
                if (ids.contains(id)) {
                    i--
                } else {
                    ids.add(id)
                }
                i++
            }
            Collections.sort(ids) { integer, t1 -> integer - t1 }
            for (i in ids) {
                pokemonModels.add(result?.results?.get(i - 1)!!)
            }
            val pokemonAdapter = PokemonListAdapter(this@MainActivity, pokemonModels)
            val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
            recyclerView.adapter = pokemonAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            findViewById<View>(R.id.loader).visibility = View.GONE
        }
    }
}