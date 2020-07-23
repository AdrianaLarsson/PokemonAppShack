package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_pokemon_list.*
import se.appshack.android.refactoring.Adapters.FirebasePokemonAdapter
import se.appshack.android.refactoring.Adapters.PokemonListAdapter
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.NamedResponseModel
import se.appshack.android.refactoring.R

class MyPokemonListActivity : AppCompatActivity() {

    private lateinit var listPokemon: MutableList<PokemonFirebaseClass>
    private lateinit var pokemonFirebaseAdapter: FirebasePokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pokemon_list)

        readPokemonListFromFirebase()
        navigationBar()
getSearchString()



    }






    fun readPokemonListFromFirebase(){

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userNode = firebaseUser!!.uid
        Log.w("Tag"," UserId : =====>>>> " + userNode)
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("${userNode}")
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
                pokemonFirebaseAdapter = FirebasePokemonAdapter(this@MyPokemonListActivity, pokemonList)
                recyView.adapter = pokemonFirebaseAdapter




                listPokemon = pokemonList
            }

            override fun onCancelled(error: DatabaseError) {

                }
        })





    }



    fun getSearchString() {

       //get the text when the user writes something in editext
        var editTxtS = findViewById<EditText>(R.id.searchMylist)

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
        var tempList: MutableList<PokemonFirebaseClass> = ArrayList()
        for (d in listPokemon) {
            if (filterItem in d.name.toString()) {
                tempList.add(d)
            }
        }
        pokemonFirebaseAdapter.updateList(tempList)
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
