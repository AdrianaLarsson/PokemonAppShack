package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import se.appshack.android.refactoring.R


class MySideSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_side_settings)
        navigationBar()


        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("MyPokemonList")
        myRef.child("pidgey").removeValue()




    }



    fun navigationBar(){

        var navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationSetting)
        navigationView.selectedItemId = R.id.settings

        navigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.myMyPokemonList -> {

                    var intent = Intent(this, MyPokemonListActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }






            }
            when (it.itemId) {
                R.id.searchPokemon -> {

                    var intent = Intent(this, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }



            }
            true



        })


    }
}
