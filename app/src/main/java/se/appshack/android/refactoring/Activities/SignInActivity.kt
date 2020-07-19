package se.appshack.android.refactoring.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import se.appshack.android.refactoring.R

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }
}
