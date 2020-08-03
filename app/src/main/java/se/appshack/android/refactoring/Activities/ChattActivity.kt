package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.Tag
import kotlinx.android.synthetic.main.activity_chatt.*
import kotlinx.android.synthetic.main.activity_my_side_settings.*
import se.appshack.android.refactoring.Adapters.ChattAdapter
import se.appshack.android.refactoring.Adapters.UsersnameAdapter
import se.appshack.android.refactoring.ModelClasses.ChattClass
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.R

class ChattActivity : AppCompatActivity() {
    var  globalMessageList : List<ChattClass>? =null
    lateinit var adapter : ChattAdapter
lateinit var auth: FirebaseAuth
    var name : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatt)

        auth = FirebaseAuth.getInstance()
        showUserInfo()
        sendMessage()

        retriveMessage()

        userNameChatt.setOnClickListener {
            val intent = Intent(this, AllUsersActivity::class.java)
            startActivity(intent)
        }

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
/*

    fun sendMessage() {


        val toUserId = intent.extras.getString("USER_ID")

        val chattUserName = intent.extras.getString("USER_NAME")


      //  userNameChatt.text = chattUserName
        var messagefromUser : EditText?
        var messageBtnSend : ImageButton?
        messageBtnSend = findViewById(R.id.sendMessageButton)

        messagefromUser = findViewById(R.id.messageUser)
        val firebaseAuth = FirebaseAuth.getInstance()
        val fromFirebaseUser = firebaseAuth.currentUser?.uid

        val db = FirebaseDatabase.getInstance()


        val reference = db.getReference("user-chatt/Adriana/Larsson")
         val torefrence = db.getReference("user-chatt/Marie/Kristoffersson")
        currentUserInfo()

        val chattmassage = ChattClass(reference.key, "${messagefromUser.text}", fromFirebaseUser, toUserId)
        messageBtnSend?.setOnClickListener {
           Log.w("Sending", "Message send")
            val chatt = ChattClass("${fromFirebaseUser}",
                    "${messagefromUser.text}",
                    "${toUserId}",
                    "${name}"

            )

           reference.setValue(chatt)


            torefrence.setValue(chatt)


         }
    }
*/


    fun sendMessage() {

        currentUserInfo()


        var messagefromUser : EditText?  = findViewById(R.id.messageUser)
        val toUserId = intent.extras.getString("USER_ID")
        val firebaseAuth = FirebaseAuth.getInstance()
        val fromCurrentfirebaseUserId = firebaseAuth.currentUser?.uid

        var messageBtnSend : ImageButton? =findViewById(R.id.sendMessageButton)
        val db = FirebaseDatabase.getInstance()
        val reference = db.getReference("/user-chatts/$fromCurrentfirebaseUserId/$toUserId").push()
        val torefrence = db.getReference("/user-chatts/$toUserId/$fromCurrentfirebaseUserId").push()



        messageBtnSend?.setOnClickListener {

            val chatt = ChattClass(

                    "${fromCurrentfirebaseUserId}",
                    "${messageUser.text}",
                    "${fromCurrentfirebaseUserId}",
                    "${name}"
            )
                    reference.setValue(chatt)
                    .addOnSuccessListener {
                        messagefromUser!!.text.clear()


                        Log.w("SEND", "Message has been send ${reference.key}")
                    }

                    .addOnFailureListener {it.message

                        Log.w("FAILURE", "Message has not been send ${it.message}")

                    }

            torefrence.setValue(chatt)

        }


    }



    fun retriveMessage(){

        val fromCurrentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val toUserId = intent.extras.getString("USER_ID")
        val myRef = FirebaseDatabase.getInstance().getReference("/user-chatts/$fromCurrentUserId/$toUserId")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var messagePerson = ArrayList<ChattClass>()

                for (messageSnapshot in dataSnapshot.children) {
                    val message = messageSnapshot.getValue(ChattClass::class.java)
                    Log.w("Messsss ", "Messaggeee :::: " +message)

                    messagePerson.add(message!!)

                }
                globalMessageList = messagePerson
                setUpTheRecyclerview()
            }

            override fun onCancelled(databaseError: DatabaseError) {

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



