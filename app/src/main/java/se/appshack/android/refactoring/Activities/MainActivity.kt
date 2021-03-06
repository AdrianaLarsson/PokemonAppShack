package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_my_side_settings.*
import okhttp3.OkHttpClient
import okhttp3.Request
import se.appshack.android.refactoring.NamedResponseModel
import se.appshack.android.refactoring.Adapters.PokemonListAdapter
import se.appshack.android.refactoring.PokemonListResponse
import se.appshack.android.refactoring.R
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private lateinit var pokemonlist: MutableList<NamedResponseModel>
    private lateinit var pokemonAdapter: PokemonListAdapter
    var mAuth: FirebaseAuth? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val getPokemonListTask = GetPokemonListTask()
        getPokemonListTask.execute()
        navigationBar()


        //  var intent = getIntent()
        mAuth = FirebaseAuth.getInstance()

    }


    internal inner class GetPokemonListTask : AsyncTask<Void?, Void?, PokemonListResponse?>() {
        override fun doInBackground(vararg params: Void?): PokemonListResponse? {
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
            pokemonAdapter = PokemonListAdapter(this@MainActivity, pokemonModels)


            Log.w("pokemonModels", "pokemonModels ____" + pokemonModels.toString())
            val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
            recyclerView.adapter = pokemonAdapter
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            findViewById<View>(R.id.loader).visibility = View.GONE


            pokemonlist = pokemonModels
            getSearchString()

        }

    }


    fun getSearchString() {

//get the text when the user writes something in editext
        var editTxtS = findViewById<EditText>(R.id.searchEdtxt)

        editTxtS.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                filterlist(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }


        })


    }

    //filter text
    private fun filterlist(filterItem: String) {
        var tempList: MutableList<NamedResponseModel> = ArrayList()
        for (d in pokemonlist) {
            if (filterItem in d.name.toString()) {
                tempList.add(d)
            }
        }
        pokemonAdapter.updateList(tempList)
    }


    fun navigationBar() {

        var navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationAllUser)
        navigationView.selectedItemId = R.id.searchPokemon

        navigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.myMyPokemonList -> {

                    var intent = Intent(this, MyPokemonListActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)




                    Log.w("Extra", "String extra =>> " + intent.getStringExtra("UserListPokemon"))
                    // intent.putExtra("POKEMONLIST", list)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }


            }
            when (it.itemId) {
                R.id.settings -> {


                    var intent = Intent(this, MySideSettingsActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }


            }





            when (it.itemId) {
                R.id.userlist -> {

                    var intent = Intent(this, AllUsersActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }


            }
            true


        })


    }


}

