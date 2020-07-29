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
import se.appshack.android.refactoring.Adapters.ChattAdapter
import se.appshack.android.refactoring.ModelClasses.ChattClass
import se.appshack.android.refactoring.R

class ChattActivity : AppCompatActivity() {
    var  globalMessageList : List<ChattClass>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatt)

        chatt()
        sendMessage()

        retriveMessage()

    }

    fun chatt() {


        val chattUserName = intent.extras.getString("USER_NAME")

        userNameChatt.text = chattUserName
    }


    fun sendMessage() {
        var messagefromUser : EditText?
        var messageBtnSend : ImageButton?
        messageBtnSend = findViewById(R.id.sendMessageButton)

        messagefromUser = findViewById(R.id.messageUser)
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser?.uid

        val db = FirebaseDatabase.getInstance()


        val myRef = db.getReference("Chatt")


        messageBtnSend?.setOnClickListener {
            Log.w("Sending", "Message send")
            val chatt = ChattClass(

                    "${firebaseUser}",
                    "${messagefromUser.text}",
                    "${"Du"}",
                    "${"Jag"}"

            )

            val pushKey = myRef.push().key!!
            myRef.child(pushKey).setValue(chatt)


         }
    }



    fun retriveMessage(){
        var personalId = intent.getStringExtra("PERSONAL ID")
        Log.w("toChatt", "Team Id get Extra :" + personalId)




        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Chatt")



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



