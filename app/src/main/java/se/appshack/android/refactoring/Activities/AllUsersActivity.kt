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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_my_pokemon_list.*
import se.appshack.android.refactoring.Adapters.FirebasePokemonAdapter
import se.appshack.android.refactoring.Adapters.UsersnameAdapter
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.NamedResponseModel
import se.appshack.android.refactoring.R

class AllUsersActivity : AppCompatActivity() {
    private lateinit var listUser: MutableList<UserNameClass>
    private lateinit var usersnameAdapter: UsersnameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_users)


        getUser()
        navigationBar()
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





                listUser = userList

                getSearchString()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })






    }



    fun getSearchString() {

        //get the text when the user writes something in editext
        var editTxtS = findViewById<EditText>(R.id.searchUserNames)

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
        var tempList: MutableList<UserNameClass> = ArrayList()
        for (d in listUser) {
            if (filterItem in d.userName.toString()) {
                tempList.add(d)
            }
        }
        usersnameAdapter.updateList(tempList)
    }



    fun navigationBar() {

        var navigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationAllUser)
        navigationView.selectedItemId = R.id.userlist


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
