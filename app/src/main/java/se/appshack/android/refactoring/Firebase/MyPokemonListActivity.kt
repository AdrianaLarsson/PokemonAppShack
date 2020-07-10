package se.appshack.android.refactoring.Firebase

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_pokemon_list.*
import se.appshack.android.refactoring.R

class MyPokemonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pokemon_list)

        readPokemonListFromFirebase()
    }



    fun readPokemonListFromFirebase(){

        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("MyPokemonList")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val value = dataSnapshot.getValue(PokemonFirebaseClass::class.java)
                Log.d("DataSnapshot : ", "Value IS: " + value!!)

                var pokemonList = ArrayList<PokemonFirebaseClass>()

                for (teamSnapshot in dataSnapshot.children) {
                    val pokemons = teamSnapshot.getValue(PokemonFirebaseClass::class.java)
                    pokemonList.add(pokemons!!)


                }

                var recyView = findViewById<RecyclerView>(R.id.mylistrecyclerView) as RecyclerView

                Log.w("SUCCESSS", "ARRAYLIST!!!" + pokemonList)
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
}
