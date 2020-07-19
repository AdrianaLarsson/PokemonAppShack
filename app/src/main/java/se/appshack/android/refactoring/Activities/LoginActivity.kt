package se.appshack.android.refactoring.Activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
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

        toSignInActivity()

    }


    fun btnLogin() {

        btnLogin.setOnClickListener {

            doLogin()
        }


    }

    public override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        updateUI(currentUser)
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

                        Toast.makeText(this,"Du loggas nu in",Toast.LENGTH_SHORT).show()
                    } else {

                        //Log.w("TAG", "signInWithEmail:failure", task.exception!!.message, Toast.LENGTH_SHORT).show()

                    var passwordInv= task.exception!!.message

                   if (passwordInv.equals("The password is invalid or the user does not have a password.")){

                       Log.w("Error Auth","Lösenordet är inte korrekt")
                       passwordLogin.error = "Lösenordet är inte korrekt"

                   }else if (passwordInv.equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]")){

                       Log.w("Error Auth","Vi har blockerat alla förfrågningar från den här enheten på grund av ovanlig aktivitet. Försök igen senare. [För många misslyckade inloggningsförsök. Vänligen försök igen senare. ]")

                   }else if (passwordInv.equals("There is no user record corresponding to this identifier. The user may have been deleted.")){

                       Log.w("Error Auth","Det finns ingen användare med det här namnet")
                       emailLogin.error = "Det finns ingen användare med det här namnet"
                   }


                        Toast.makeText(this, "User Authentication Failed: " + task.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
                       Log.w("Error Auth", task.exception!!.message.toString())

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

    override fun onBackPressed() {
        val mUser = FirebaseAuth.getInstance().currentUser
        if (mUser == null) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }


    fun toSignInActivity(){

        signIn.setOnClickListener {

            startActivity(Intent(this,SignInActivity::class.java))
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        }

    }


}
