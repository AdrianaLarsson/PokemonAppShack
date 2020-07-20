package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_side_settings.*
import se.appshack.android.refactoring.R


class MySideSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_side_settings)
        navigationBar()
        signOut()



showUserInfo()


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


    fun signOut(){


        signOut.setOnClickListener {


            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    fun showUserInfo() {


        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userNode = firebaseUser!!.uid
        email.text = firebaseUser.email



        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Users")
        val userRef = myRef.child(firebaseUser.uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("ERROR", "Failed to read value.")

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                firstName.text = dataSnapshot.child("firstName").value as String
                lastName.text = dataSnapshot.child("lastName").value as String
                userName.text = dataSnapshot.child("userName").value as String
                email.text = dataSnapshot.child("email").value as String





            }


        })
    }

}

