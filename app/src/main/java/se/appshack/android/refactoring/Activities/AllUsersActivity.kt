package se.appshack.android.refactoring.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import se.appshack.android.refactoring.Adapters.UsersnameAdapter
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.R

class AllUsersActivity : AppCompatActivity() {
    private lateinit var usersnameAdapter: UsersnameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_users)


        getUser()
    }


    fun getUser(){

        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Usernames")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val value = dataSnapshot.getValue(UserNameClass::class.java)

                var userList = ArrayList<UserNameClass>()

                for (userSnapshot in dataSnapshot.children) {
                    val users = userSnapshot.getValue(UserNameClass::class.java)
                    userList.add(users!!)


                }

                var recyView = findViewById<RecyclerView>(R.id.recyclerViewUser) as RecyclerView

                Log.w("SUCCESSS", "ARRAYLIST!!!" + userList)




                val layoutManager = LinearLayoutManager(this@AllUsersActivity)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                recyView.layoutManager = layoutManager
                usersnameAdapter = UsersnameAdapter(this@AllUsersActivity, userList)
                recyView.adapter = usersnameAdapter





            }

            override fun onCancelled(error: DatabaseError) {

            }
        })






    }




}
