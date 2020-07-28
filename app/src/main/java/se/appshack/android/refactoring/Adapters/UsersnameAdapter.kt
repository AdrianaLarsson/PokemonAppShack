package se.appshack.android.refactoring.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_firebase_list.view.*
import se.appshack.android.refactoring.Activities.GameActivity
import se.appshack.android.refactoring.ModelClasses.PokemonFirebaseClass
import se.appshack.android.refactoring.ModelClasses.UserNameClass
import se.appshack.android.refactoring.R






class UsersnameAdapter (var context: Context, var userNameClass: List  <UserNameClass>): RecyclerView.Adapter<UsersnameAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.list_usernames, p0,false)
        return  ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return userNameClass.size
    }






    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

       var userName = p0.itemView.findViewById<TextView>(R.id.userName)

         userName.text =  userNameClass[p1].userName

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



    }
}





