package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_in.*
import se.appshack.android.refactoring.ModelClasses.UserClass
import se.appshack.android.refactoring.R

class SignInActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()


        sign.setOnClickListener {

            signUpUser()


        }
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
            var sizePas =  passwordSign.textSize.toInt()

            if (sizePas <= 6){
                passwordSign.error = "Lösenordet måste vara mer än sex tecken"
            }
            passwordSign.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(emailSign.text.toString(), passwordSign.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val userId = auth!!.currentUser!!.uid


                        val intent = Intent(this, MainActivity::class.java)

                        val db = FirebaseDatabase.getInstance()
                        val myRef = db!!.getReference("Personals")


                        val team = UserClass(

                                "${firstNameSign.text}",
                                "${lastNameSign.text}",
                                "${emailSign.text}",
                                "${userNameSignIn.text}"

                        )
                        myRef.child(userId).setValue(team)
                        startActivity(intent)
                        finish()
                    } else {

                    }
                }
    }



}
