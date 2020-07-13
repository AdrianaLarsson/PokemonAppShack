package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_pokemon_list.*
import se.appshack.android.refactoring.Adapters.FirebasePokemonAdapter
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.R

class MyPokemonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pokemon_list)

        readPokemonListFromFirebase()
        navigationBar()
    }



    fun readPokemonListFromFirebase(){

        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("MyPokemonList")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val value = dataSnapshot.getValue(PokemonFirebaseClass::class.java)

                var pokemonList = ArrayList<PokemonFirebaseClass>()

                for (teamSnapshot in dataSnapshot.children) {
                    val pokemons = teamSnapshot.getValue(PokemonFirebaseClass::class.java)
                    pokemonList.add(pokemons!!)


                }

                var recyView = findViewById<RecyclerView>(R.id.mylistrecyclerView) as RecyclerView

                Log.w("SUCCESSS", "ARRAYLIST!!!" + pokemonList)



                if (pokemonList.isEmpty()){

                    Log.w("EMPTY", "Pokemon list is empty")

                    sadNolist.visibility = View.VISIBLE

                }
                val layoutManager = LinearLayoutManager(this@MyPokemonListActivity)
               layoutManager.orientation = LinearLayoutManager.VERTICAL
                recyView.layoutManager = layoutManager
                val adapter = FirebasePokemonAdapter(this@MyPokemonListActivity, pokemonList)
                recyView.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {

                }
        })





    }



    fun navigationBar(){

        var navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationMyList)
        navigationView.selectedItemId = R.id.myMyPokemonList

        navigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.searchPokemon -> {

                    var intent = Intent(this, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
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
            true



        })


    }
}
