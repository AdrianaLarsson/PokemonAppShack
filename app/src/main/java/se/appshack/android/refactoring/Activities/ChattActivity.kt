package se.appshack.android.refactoring.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chatt.*
import kotlinx.android.synthetic.main.activity_my_side_settings.*
import se.appshack.android.refactoring.Adapters.ChattAdapter
import se.appshack.android.refactoring.ModelClasses.ChattClass
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.R

class ChattActivity : AppCompatActivity() {
    var  globalMessageList : List<ChattClass>? =null
lateinit var auth: FirebaseAuth
    var name : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatt)

        auth = FirebaseAuth.getInstance()
        showUserInfo()
        sendMessage()

        retriveMessage()

    }


    fun showUserInfo(){

        val userId = intent.extras.getString("USER_ID")

        val firebaseAuth = FirebaseAuth.getInstance()



        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Usernames")
        val userRef = myRef.child(userId)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userNameChatt.text = dataSnapshot.child("userName").value as String
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException())
            }


        })
    }




    fun currentUserInfo() {


        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser
        var userNode = firebaseUser!!.uid




        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Users")
        val userRef = myRef.child(auth.currentUser!!.uid)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.w("ERROR", "Failed to read value.")

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

               name = dataSnapshot.child("userName").value as String

            }


        })
    }

    fun sendMessage() {

    
        val userId = intent.extras.getString("USER_ID")

        val chattUserName = intent.extras.getString("USER_NAME")


      //  userNameChatt.text = chattUserName
        var messagefromUser : EditText?
        var messageBtnSend : ImageButton?
        messageBtnSend = findViewById(R.id.sendMessageButton)

        messagefromUser = findViewById(R.id.messageUser)
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser?.uid

        val db = FirebaseDatabase.getInstance()


        val myRef = db.getReference("${"Chatt => " + userId + chattUserName}")
        currentUserInfo()

        messageBtnSend?.setOnClickListener {
            Log.w("Sending", "Message send")
            val chatt = ChattClass(

                    "${firebaseUser}",
                    "${messagefromUser.text}",
                    "${userId}",
                    "${name}"



            )


            val pushKey = myRef.push().key!!
            myRef.child(pushKey).setValue(chatt)


         }
    }



    fun retriveMessage(){


        val chattUserName = intent.extras.getString("USER_NAME")



        val firebaseAuth = FirebaseAuth.getInstance()
        val userId = intent.extras.getString("USER_ID")
        val firebaseUser = firebaseAuth.currentUser?.uid
        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("${"Chatt => " + userId + chattUserName}")



        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val value = dataSnapshot.getValue(ChattClass::class.java)
                //   Log.d("DataSnapshot : ", "Value IS: " + value!!)

                var messagePerson = ArrayList<ChattClass>()

                for (messageSnapshot in dataSnapshot.children) {
                    val message = messageSnapshot.getValue(ChattClass::class.java)
                    messagePerson.add(message!!)

                }

                globalMessageList = messagePerson
                setUpTheRecyclerview()
                var messageList = ArrayList<String>()


                Log.w("Message", "ARRAYLIST!!!" + messagePerson)

                for (p in messagePerson){

                    messageList.add(p.message.toString())



                    // personalFirstNameChatt.text = firstNameArray.toString()
                    Log.w("SUCCESSS", "ARRAYLIST!!!" + messageList)



                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("ERROR", "Failed to read value.", error.toException())
            }
        })


    }



    fun setUpTheRecyclerview(){

        Log.w("Messages", "ARRAYLIST!!!" + globalMessageList)

        val layoutManager = LinearLayoutManager(this@ChattActivity)

        layoutManager.orientation = LinearLayoutManager.VERTICAL
        messages_recy.layoutManager = layoutManager

        val adapter = globalMessageList?.let { ChattAdapter(this@ChattActivity, it) }
        adapter?.notifyDataSetChanged()
        messages_recy.adapter = adapter


    }


}



