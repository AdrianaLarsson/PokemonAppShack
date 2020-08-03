package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.R

class SignInActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()


        sign.setOnClickListener {

            signUpUser()


        }

        toLogin()
    }


    private fun signUpUser() {


        if (emailSign.text.toString().isEmpty()) {
            emailSign.error = "Please enter email"
            emailSign.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailSign.text.toString()).matches()) {
            emailSign.error = "Please enter valid email"

            return
        }

        if (passwordSign.text.toString().isEmpty()) {
            passwordSign.error = "Please enter password"
            var sizePas = passwordSign.textSize.toInt()

            if (sizePas <= 6) {
                passwordSign.error = "The password must be more than six characters long"
            }
            passwordSign.requestFocus()
            return
        }


        if (userNameSignIn.text.toString().isEmpty()) {
            userNameSignIn.error = "Please enter Username"
            userNameSignIn.requestFocus()
            return
        }


        if (firstNameSign.text.toString().isEmpty()) {
            firstNameSign.error = "Please enter Firstname"
            firstNameSign.requestFocus()
            return
        }

        if (lastNameSign.text.toString().isEmpty()) {
            lastNameSign.error = "Please enter Lastname"
            lastNameSign.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailSign.text.toString(), passwordSign.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        userId = auth!!.currentUser!!.uid
                        Log.w("USERUID", "User Uid " + userId)
                        val intent = Intent(this, MySideSettingsActivity::class.java)

                        val db = FirebaseDatabase.getInstance()
                        val myRef = db!!.getReference("Users")


                        val team = UserClass(

                                "${firstNameSign.text}",
                                "${lastNameSign.text}",
                                "${emailSign.text}",
                                "${userNameSignIn.text}"


                        )




                        myRef.child(userId).setValue(team)
                        startActivity(intent)

                        finish()

                        addUser()
                    } else {

                    }
                }
    }


    fun toLogin() {


        loginAct.setOnClickListener {

            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
    }


    fun addUser() {


        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("Usernames")

        val pokemon = UserNameClass(

                "${userNameSignIn.text}",
                userId

        )
        val pushKey = myRef.push().key!!
        myRef.child(userId).setValue(pokemon)

    }


}



