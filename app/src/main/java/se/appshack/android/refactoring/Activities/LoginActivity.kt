package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import se.appshack.android.refactoring.R

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        btnLogin()


    }


    fun btnLogin() {

        btnLogin.setOnClickListener {

            doLogin()
        }


    }


    private fun doLogin() {

        if (emailLogin.text.toString().isEmpty()){

            emailLogin.error = "Snälla skriv in din Emai-adress"
            emailLogin.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailLogin.text.toString()).matches()){

            emailLogin.error = "Snälla skriv korrekt mejl"
            emailLogin.requestFocus()
            return
        }

        if (passwordLogin.text.toString().isEmpty()){

            passwordLogin.error = "Snälla skriv in ditt lösenord"
            passwordLogin.requestFocus()
            return
        }


        auth = FirebaseAuth.getInstance()


        auth.signInWithEmailAndPassword(emailLogin.text.toString(), passwordLogin.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d("TAG", "signInWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {

                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        updateUI(null)
                    }

                }


    }


    fun updateUI(currentUser: FirebaseUser?){

        if (currentUser != null ){

            startActivity(Intent(this,MainActivity::class.java))
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            finish()

        }else {


        }

    }


}
